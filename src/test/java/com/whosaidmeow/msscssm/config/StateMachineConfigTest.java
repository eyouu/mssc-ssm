package com.whosaidmeow.msscssm.config;

import com.whosaidmeow.msscssm.domain.PaymentEvent;
import com.whosaidmeow.msscssm.domain.PaymentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@SpringBootTest
class StateMachineConfigTest {

    @Autowired
    private StateMachineFactory<PaymentState, PaymentEvent> factory;

    @Test
    public void testStateMachine() {
        StateMachine<PaymentState, PaymentEvent> stateMachine = factory.getStateMachine(UUID.randomUUID());
        stateMachine.start();

        System.out.println(stateMachine.getState().toString());
        stateMachine.sendEvent(PaymentEvent.PRE_AUTHORIZE);
        System.out.println(stateMachine.getState().toString());
        stateMachine.sendEvent(PaymentEvent.PRE_AUTH_APPROVED);
        System.out.println(stateMachine.getState().toString());
    }
}