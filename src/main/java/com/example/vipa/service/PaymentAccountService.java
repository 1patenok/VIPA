package com.example.vipa.service;

import com.example.vipa.exception.NotEnoughMoneyException;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.model.PaymentAccount;
import com.example.vipa.repository.PaymentAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentAccountService {
    private final PaymentAccountRepository paymentAccountRepository;
    private static final String CLIENT_NOT_FOUND_MESSAGE = "Пользователь с указанным номером карты не найден.";

    public PaymentAccount getPaymentAccountByCardNumber(String cardNumber) {
        log.info("inside getPaymentAccountByCardNumber(), cardNumber: {}", cardNumber);
        return paymentAccountRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public void makeAPayment(int orderSum, String cardNumber) {
        PaymentAccount paymentAccount = paymentAccountRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND_MESSAGE));
        if (paymentAccount.getCurrentSum() < orderSum) {
            throw new NotEnoughMoneyException("Недостаточно средств на счету.");
        }
        paymentAccount.setCurrentSum(paymentAccount.getCurrentSum() - orderSum);
        paymentAccountRepository.save(paymentAccount);
    }

    public PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) {
        log.info("inside savePaymentAccount(), paymentAccount: {}", paymentAccount);
        return paymentAccountRepository.save(paymentAccount);
    }
}
