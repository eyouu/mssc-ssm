package com.whosaidmeow.msscssm.services;

import com.whosaidmeow.msscssm.domain.Payment;
import com.whosaidmeow.msscssm.domain.PaymentEvent;
import com.whosaidmeow.msscssm.domain.PaymentState;
import com.whosaidmeow.msscssm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder()
                .amount(new BigDecimal("12.99"))
                .build();
    }

    @Test
    void preAuth() {
        Payment saved = paymentService.newPayment(payment);

        paymentService.preAuth(saved.getId());

        Optional<Payment> byId = paymentRepository.findById(saved.getId());

        System.out.println(byId.orElse(null));
    }
}