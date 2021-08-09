package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.model.Account;
import com.playtomic.tests.wallet.service.AccountService;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    AccountService accountService;
    @Autowired
    StripeService stripeService;

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @GetMapping(value= "/amount")
    public String getAmount(@RequestParam(name = "creditCardNumber") String creditCardNumber){
        log.info("getAmount creditCardNumber=["+creditCardNumber+"]");
        String result = "";
        try{
            final BigDecimal amount = stripeService.getAmount(creditCardNumber);
            result = "The amount of the account ["+ creditCardNumber +"] is " + amount.toString();
        } catch (StripeServiceException e) {
            log.info("StripeServiceException e=[" + e.getMessage() + "]");
            result = e.getMessage();
        }
        log.info("getAmount result=["+result+"]");
        return result;
    }

    @GetMapping(value= "/recharge")
    public String recharge (@RequestParam(name = "creditCardNumber") String creditCardNumber, @RequestParam(name = "amount") BigDecimal amount ) {
        log.info("Recharge creditCardNumber=["+creditCardNumber+"] amount=["+amount+"]");
        String result = "";
        try {
            BigDecimal charge = stripeService.recharge(creditCardNumber, amount);
            result = "The account has been recharged, new amount = " + charge.toString();
        } catch (StripeServiceException e) {
            log.info("StripeServiceException e=["+e.getMessage()+"]");
            result = e.getMessage();
        }
        log.info("recharge result=["+result+"]");
        return result;
    }

    @GetMapping(value= "/charge")
    public String charge (@RequestParam(name = "creditCardNumber") String creditCardNumber, @RequestParam(name = "amount") BigDecimal amount ) {
        log.info("charge creditCardNumber=["+creditCardNumber+"] amount=["+amount+"]");
        String result = "";
        try {
            BigDecimal charge = stripeService.charge(creditCardNumber, amount);
            result = "The account has been charged, new amount = " + charge.toString();
        } catch (StripeServiceException e) {
            log.info("StripeServiceException e=["+e.getMessage()+"]");
            result = e.getMessage();
        }
        log.info("charge result=["+result+"]");
        return result;
    }
}
