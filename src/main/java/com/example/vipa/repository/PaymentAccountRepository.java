package com.example.vipa.repository;

import com.example.vipa.model.Client;
import com.example.vipa.model.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Integer> {

    Optional<PaymentAccount> findByCardNumber(String cardNumber);

}
