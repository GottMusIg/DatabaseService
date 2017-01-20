package com.gottmusig.character.jpa;

import com.gottmusig.character.domain.api.Character;
import com.gottmusig.dpsdifference.domain.api.ClassSpecification;
import com.gottmusig.dpsdifference.domain.api.WOWClass;
import com.gottmusig.dpsdifference.jpa.ClassSpecificationEntity;
import com.gottmusig.dpsdifference.jpa.NumericSequenceId;
import com.gottmusig.searchcharacter.domain.api.Realm;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Entity
@Table(name = "character")
public class CharacterEntity implements Character {

    @EmbeddedId
    private NumericSequenceId id;

    private int dps;

    private String name;

    @OneToOne
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private Realm realm;

    @OneToOne
    @JoinColumn(name = "classSpecification_id", referencedColumnName = "id")
    private ClassSpecificationEntity classSpecification;

    public CharacterEntity() {
        this.id = new NumericSequenceId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDPS() {
        return dps;
    }

    @Override
    public ClassSpecification getClassSpecification() {
        return classSpecification;
    }

    @Override
    public WOWClass getWOWClass() {
        return classSpecification.getWOWClass();
    }

    @Override
    public Realm getRealm() {
        return realm;
    }

    @Override
    public Id getId() {
        return id;
    }

    public static interface CharacterRepository extends CrudRepository<CharacterEntity, NumericSequenceId> {

        CharacterEntity findByName(String name);

    }

}