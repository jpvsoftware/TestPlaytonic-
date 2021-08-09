package com.playtomic.tests.wallet.service;


import com.playtomic.tests.wallet.model.Account;
import com.playtomic.tests.wallet.service.AccountService;
import com.playtomic.tests.wallet.service.StripeServiceException;
import com.playtomic.tests.wallet.service.StripeService;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StripeServiceTest {

    public static final String CREDIT_CARD_NUMBER0 = "1234567890123456";
    public static final double AMOUNT0 = 10000.0;

    @Autowired
    StripeService stripeService;
    @Autowired
    AccountService accountService;

    @Before
    public void init() {
        final Account account = accountService.SearchFromCreditCardNumber(CREDIT_CARD_NUMBER0);
        account.setAmount(AMOUNT0);
        accountService.save(account);
    }

    @Test
    public void test_exception() {
        Assertions.assertThrows(StripeServiceException.class, () -> {
            stripeService.getAmount("***");
        });
    }

    @Test
    public void test_getAmount() throws StripeServiceException{
        final BigDecimal amount = stripeService.getAmount(CREDIT_CARD_NUMBER0);
        System.out.println("test_getAmount" + amount.doubleValue());
        Assertions.assertEquals(amount.doubleValue(), AMOUNT0);
    }

    @Test
    public void test_recharge() throws StripeServiceException {
        final BigDecimal recharge = stripeService.recharge(CREDIT_CARD_NUMBER0, new BigDecimal(15));
        System.out.println("test_recharge" + recharge.doubleValue());
        Assertions.assertEquals(recharge.doubleValue(), AMOUNT0+15.0);
    }

    @Test
    public void test_charge_threshold() throws StripeServiceException {
        Assertions.assertThrows(StripeServiceException.class, () -> {
            stripeService.charge(CREDIT_CARD_NUMBER0, new BigDecimal(5));
        });
    }

    @Test
    public void test_charge() throws StripeServiceException {
        final BigDecimal charge = stripeService.charge(CREDIT_CARD_NUMBER0, new BigDecimal(50));
        System.out.println("test_charge" + charge.doubleValue());
        Assertions.assertEquals(charge.doubleValue(), AMOUNT0-50.0);
    }
}
