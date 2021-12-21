package com.whosaidmeow.msscssm.services;

import com.whosaidmeow.msscssm.domain.PaymentEvent;
import com.whosaidmeow.msscssm.domain.PaymentState;
import com.whosaidmeow.msscssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.whosaidmeow.msscssm.services.PaymentServiceImpl.PAYMENT_ID_HEADER;

@Component
@RequiredArgsConstructor
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    private final PaymentRepository paymentRepository;

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state,
                               Message<PaymentEvent> message,
                               Transition<PaymentState, PaymentEvent> transition,
                               StateMachine<PaymentState, PaymentEvent> stateMachine,
                               StateMachine<PaymentState, PaymentEvent> rootStateMachine) {

        Optional.ofNullable(message).ifPresent(m -> {
            Optional.ofNullable((Long) m.getHeaders().getOrDefault(PAYMENT_ID_HEADER, -1L))
                    .ifPresent(paymentId -> {
                        paymentRepository.findById(paymentId).ifPresent(payment -> {
                            payment.setPaymentState(state.getId());
                            paymentRepository.save(payment);
                        });
                    });
        });
    }
}
