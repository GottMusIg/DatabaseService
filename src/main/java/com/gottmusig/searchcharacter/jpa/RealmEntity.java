package com.gottmusig.searchcharacter.jpa;

import com.gottmusig.dpsdifference.jpa.NumericSequenceId;
import com.gottmusig.searchcharacter.domain.api.Realm;
import com.gottmusig.utils.RealmLocation;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @author leong
 * @since 09.12.2016
 */
@Entity
@Table(name = "realm")
public class RealmEntity implements Realm {

    @EmbeddedId
    private NumericSequenceId id;

    private String name;

    private RealmLocation location;

    public RealmEntity() {
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
    public RealmLocation getLocation() {
        return location;
    }

    public void setLocation(RealmLocation location) {
        this.location = location;
    }

    @Override
    public NumericSequenceId getId() {
        return id;
    }

    public void setId(NumericSequenceId id) {
        this.id = id;
    }

    public static interface RealmRepository extends CrudRepository<RealmEntity, NumericSequenceId> {

        List<RealmEntity> findByLocation(RealmLocation location);

    }

}
