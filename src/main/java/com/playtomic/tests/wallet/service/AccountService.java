package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Account;
import com.playtomic.tests.wallet.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    @Autowired
    AccountRepository repository;

    public void save(final Account account) {
        repository.save(account);
    }

    public Account SearchFromCreditCardNumber(final String creditCardNumber) {
        Account theAccount = null;
        if ( creditCardNumber != null) {
            final List<Account> accounts = new ArrayList<>();
            repository.findAll().forEach(account -> {
                accounts.add(account);
            });
            try {
                theAccount = accounts.stream().filter(account -> creditCardNumber.equals(account.getCreditCardNumber())).findFirst().get();
            } catch ( NoSuchElementException nsee){
            }
        }
        return theAccount;
    }
}
