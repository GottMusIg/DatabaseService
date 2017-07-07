package com.gottmusig.database.service.domain.character.jpa;

import com.gottmusig.database.service.domain.character.WOWClass;
import com.gottmusig.database.service.domain.jpa.NumericSequenceId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author leong
 * @since 20.11.2016
 */
@Component
@Entity
@Table(name = "wowclass")
public class WOWClassEntity implements WOWClass {

    @EmbeddedId
    private NumericSequenceId id;

    private String name;

    public WOWClassEntity() {
        this.id = new NumericSequenceId();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public NumericSequenceId getId() {
        return id;
    }

    public interface WOWClassRepository extends CrudRepository<WOWClassEntity, NumericSequenceId> {

        WOWClassEntity findByName(String name);

    }
}
