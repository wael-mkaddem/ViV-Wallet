package com.invivoo.vivwallet.api.domain.payment;

import com.invivoo.vivwallet.api.domain.action.ActionRepository;
import com.invivoo.vivwallet.api.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ActionRepository actionRepository;

    @Test
    public void getAll() {
        // Given
        User creator = User.builder().id((long) 1).build();
        User receiver = User.builder().id((long) 1).build();
        Payment payment1 = Payment.builder().id((long) 1).date(LocalDateTime.now()).creator(creator).receiver(receiver).build();
        Payment payment2 = Payment.builder().id((long) 2).date(LocalDateTime.now()).creator(creator).receiver(receiver).build();
        List<Payment> payments = Arrays.asList(payment1, payment2);
        when(paymentRepository.getAllByOrderByDateDesc()).thenReturn(payments);
        PaymentService paymentService = new PaymentService(paymentRepository, actionRepository);
        // When
        List<Payment> list = paymentService.getAll();

        // Then
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void getActionsByPaymentId() {
    }
}