package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceTest {

    public static final String CREDIT_CARD_NUMBER0 = "1234567890123456";

    @Autowired
    AccountService accountService;

    @Test
    void searchFromCreditCardNumber_notFound() {
        final Account account = accountService.SearchFromCreditCardNumber("*");
        Assertions.assertNull(account);
    }

    @Test
    void searchFromCreditCardNumber_found() {
        final Account account = accountService.SearchFromCreditCardNumber(CREDIT_CARD_NUMBER0);
        Assertions.assertNotNull(account);
    }
}