package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {}
