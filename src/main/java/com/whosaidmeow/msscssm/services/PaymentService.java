package com.whosaidmeow.msscssm.services;

import com.whosaidmeow.msscssm.domain.Payment;
import com.whosaidmeow.msscssm.domain.PaymentEvent;
import com.whosaidmeow.msscssm.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {

    Payment newPayment(Payment payment);

    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> authorize(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId);
}
