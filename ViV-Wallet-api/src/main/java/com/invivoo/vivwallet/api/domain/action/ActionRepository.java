package com.invivoo.vivwallet.api.domain.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> findAllByPaymentIsNull();

    List<Action> findAllByPaymentIOrderOrderByDateDesc(@Param("paymentId") Long paymentId);
}
