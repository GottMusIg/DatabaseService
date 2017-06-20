package com.gottmusig.database.service.domain.account.jpa;

import com.gottmusig.database.service.domain.account.Account;
import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.character.CharacterService;
import com.gottmusig.database.service.domain.character.jpa.CharacterEntity;
import com.gottmusig.database.service.domain.jpa.NumericSequenceId;
import com.gottmusig.database.service.domain.jpa.SpringEntityListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Entity
@Table(name = "account")
@EntityListeners(SpringEntityListener.class)
public class AccountEntity implements Account {

    @Autowired
    private transient CharacterAccountRelationEntity.CharacterAccountRelationRepository accountRelationRepository;

    @Autowired
    private transient CharacterService characterService;

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
    public Character addCharacter(Character character) {
        Optional<CharacterAccountRelationEntity> relationEntityOptional = accountRelationRepository
                .findFirstByAccountAndCharacter(
                this, (CharacterEntity) character);
        if (!relationEntityOptional.isPresent()) {
            addAccountRelation(character);
            characters.add((CharacterEntity) character);
        }
        return character;
    }

    private void addAccountRelation(Character character) {
        character = characterService.saveCharacter(character);
        CharacterAccountRelationEntity characterAccountRelationEntity = new CharacterAccountRelationEntity();
        characterAccountRelationEntity.setAccount(this);
        characterAccountRelationEntity.setCharacter((CharacterEntity) character);
        accountRelationRepository.save(characterAccountRelationEntity);
    }

    public void removeCharacter(Character character) {
        CharacterAccountRelationEntity characterAccountRelation = accountRelationRepository.findFirstByAccount(this);
        accountRelationRepository.delete(characterAccountRelation);
    }

    @Override
    public Set<Character> getCharacters() {
        Set<Character> characters = new HashSet<>();
        characters.addAll(this.characters);
        return characters;
    }

    @Override
    public Id getId() {
        return id;
    }

    public interface AccountRepository extends CrudRepository<AccountEntity, NumericSequenceId> {

        Optional<Account> findByUserName(String userName);

    }
}
