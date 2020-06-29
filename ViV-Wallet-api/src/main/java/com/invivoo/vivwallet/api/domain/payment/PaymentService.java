package com.invivoo.vivwallet.api.domain.payment;

import com.invivoo.vivwallet.api.domain.action.Action;
import com.invivoo.vivwallet.api.domain.action.ActionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ActionRepository actionRepository;

    public List<Payment> getAll() {
        List<Payment> payments = paymentRepository.getAllByOrderByDateDesc();
        return payments != null && !payments.isEmpty() ? payments : Collections.emptyList();
    }

    public List<Action> getActionsByPaymentId(Long paymentId) {
        List<Action> actions = actionRepository.findAllByPaymentIOrderOrderByDateDesc(paymentId);
        return actions != null && !actions.isEmpty() ? actions : Collections.emptyList();
    }

}
