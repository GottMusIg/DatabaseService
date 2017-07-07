package com.gottmusig.database.service.domain.account.jpa;

import com.gottmusig.database.service.domain.account.Account;
import com.gottmusig.database.service.domain.account.AccountService;
import com.gottmusig.database.service.domain.account.jpa.AccountEntity.AccountRepository;
import com.gottmusig.database.service.domain.account.jpa.exception.CharacterAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account register(String userName, String password) {
        AccountEntity account = new AccountEntity();
        account.setPassword(password);
        account.setUserName(userName);
        Optional<Account> accountOptional = accountRepository.findByUserName(userName);
        accountOptional.ifPresent(acc -> new CharacterAlreadyExistsException(acc.getUserName() + "already exists"));
        return accountRepository.save(account);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete((AccountEntity) account);
    }

    @Override
    public Optional<Account> searchAccount(String userName) {
        return accountRepository.findByUserName(userName);
    }
}
