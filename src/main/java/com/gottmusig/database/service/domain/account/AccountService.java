package com.gottmusig.database.service.domain.account;

import com.gottmusig.database.service.domain.account.jpa.exception.CharacterAlreadyExistsException;

import java.util.Optional;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
public interface AccountService {

    /**
     * Creates a new Account with the given Credentials,
     * the username is unique.
     *
     * @param userName the desired username of the Account
     * @param password the chosen password of the account (hash)
     * @return the created Account Entity if request is successful
     * @throws CharacterAlreadyExistsException, if the username is already in use.
     */
    Account register(String userName, String password);

    /**
     * Deletes the given Account
     *
     * @param account to delete from the database
     */
    void delete(Account account);

    /**
     *
     * @param userName the name to search an Account for
     * @return Optional of the searched Account
     */
    Optional<Account> searchAccount(String userName);

}
