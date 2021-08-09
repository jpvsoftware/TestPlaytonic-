package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;


/**
 * Handles the communication with Stripe.
 *
 * A real implementation would call to String using their API/SDK.
 * This dummy implementation throws an error when trying to charge less than 10€.
 */
@Service
public class StripeService {
    final private static BigDecimal THRESHOLD = new BigDecimal(10);

    /**
     * Charges money in the credit card.
     *
     * Ignore the fact that no CVC or expiration date are provided.
     *
     * @param creditCardNumber The number of the credit card
     * @param amount The amount that will be charged.
     *
     * @throws StripeServiceException
     */
    @Autowired
    AccountService accountService;

    public BigDecimal getAmount(String creditCardNumber) throws StripeServiceException {
        Assert.notNull(creditCardNumber, "creditCardNumber == null");
        final Account account = accountService.SearchFromCreditCardNumber(creditCardNumber);
        if ( account == null ){
            throw new StripeServiceException("No account use this credit card");
        }
        return new BigDecimal(account.getAmount());
    }

    public BigDecimal charge(String creditCardNumber, BigDecimal amount) throws StripeServiceException {
        Assert.notNull(creditCardNumber, "creditCardNumber == null");
        Assert.notNull(amount, "amount == null");

        if (amount.compareTo(THRESHOLD) < 0) {
            throw new StripeServiceException("The amount to be charged can not less than " + THRESHOLD.toString());
        }

        final Account account = accountService.SearchFromCreditCardNumber(creditCardNumber);
        BigDecimal result = BigDecimal.ZERO;
        if ( account == null ){
            throw new StripeServiceException("No account use this credit card");
        } else {
            final BigDecimal oldAmount = new BigDecimal(account.getAmount());
            final BigDecimal newAmount = oldAmount.subtract(amount);
            if ( newAmount.compareTo(BigDecimal.ZERO) < 0){
                throw new StripeServiceException("Not enough money on the account");
            } else {
                account.setAmount(newAmount.doubleValue());
                accountService.save(account);
                result = newAmount;
            }
        }
        return result;
    }
    public BigDecimal recharge (String creditCardNumber, BigDecimal amount) throws StripeServiceException {
        Assert.notNull(creditCardNumber, "creditCardNumber == null");
        Assert.notNull(amount, "amount == null");

        final Account account = accountService.SearchFromCreditCardNumber(creditCardNumber);
        BigDecimal result = BigDecimal.ZERO;
        if ( account == null ){
            throw new StripeServiceException("No account use this credit card");
        } else {
            final BigDecimal oldAmount = new BigDecimal(account.getAmount());
            final BigDecimal newAmount = oldAmount.add(amount);
            account.setAmount(newAmount.doubleValue());
            accountService.save(account);
            result = newAmount;
        }
        return result;
    }
}
