package com.gottmusig.database.service.domain.account.jpa;

import com.gottmusig.database.service.domain.account.Account;
import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.character.jpa.CharacterEntity;
import com.gottmusig.database.service.domain.jpa.NumericSequenceId;
import com.gottmusig.database.service.domain.jpa.SpringEntityListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Component
@Entity
@Table(name = "account")
@EntityListeners(SpringEntityListener.class)
public class AccountEntity implements Account {

    @Autowired
    private transient AccountRepository accountRepository;

    @EmbeddedId
    private NumericSequenceId id;

    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String userName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "characteraccountrelation", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id"))
    private Set<CharacterEntity> characters;

    @Autowired
    public AccountEntity() {
        this.id = new NumericSequenceId();
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void addCharacter(Character character) {
       characters.add((CharacterEntity) character);
       accountRepository.save(this);
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
        accountRepository.save(this);
    }

    @Override
    public Set<Character> getCharacters() {
        return characters.stream().map(characterEntity -> (Character) characterEntity).collect(Collectors.toSet());
    }

    @Override
    public Id getId() {
        return id;
    }

    @Repository
    public interface AccountRepository extends CrudRepository<AccountEntity, NumericSequenceId> {

        Optional<Account> findByUserName(String userName);

    }
}
