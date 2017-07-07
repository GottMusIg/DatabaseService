package com.gottmusig.database.service.domain.account;

import com.gottmusig.database.service.domain.Entity;
import com.gottmusig.database.service.domain.character.Character;

import java.util.Set;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
public interface Account extends Entity {

    String getUserName();
    void setUserName(String userName);

    String getPassword();
    void setPassword(String password);

    /**
     * Add a World of Warcraft Character to an existing Account
     *
     * @param character, the character to save to the account
     */
    void addCharacter(Character character);

    /**
     *
     * @return all World of Warcraft characters added to this Account
     */
    Set<Character> getCharacters();

    /**
     *
     * @param character, the World of Warcraft Character to remove from the account
     */
    void removeCharacter(Character character);
}
