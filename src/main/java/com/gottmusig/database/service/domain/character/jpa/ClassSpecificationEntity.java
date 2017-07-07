package com.gottmusig.database.service.domain.character.jpa;

import com.gottmusig.database.service.domain.character.ClassSpecification;
import com.gottmusig.database.service.domain.character.WOWClass;
import com.gottmusig.database.service.domain.dpsdifference.SpecificationDPS;
import com.gottmusig.database.service.domain.dpsdifference.jpa.SpecificationDPSEntity;
import com.gottmusig.database.service.domain.jpa.NumericSequenceId;
import com.gottmusig.database.service.domain.jpa.SpringEntityListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * @author leong
 * @since 20.11.2016
 */
@Component
@Entity
@Table(name = "classspecification")
@EntityListeners(SpringEntityListener.class)
public class ClassSpecificationEntity implements ClassSpecification {

    @Autowired
    private transient SpecificationDPSEntity.SpecificationDPSRepository specificationDPSRepository;

    @EmbeddedId
    @Column(name = "id")
    private NumericSequenceId id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "WOWClass_id", referencedColumnName = "id")
    private WOWClassEntity wowClass;

    public ClassSpecificationEntity() {
        this.id = new NumericSequenceId();
    }

    @Override
    public SpecificationDPS getSpecificationDPS() {
        return specificationDPSRepository.findBySpecification(this);
    }

    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public WOWClassEntity getWOWClass() {
        return wowClass;
    }
    public void setWowClass(WOWClassEntity wowClass) {
        this.wowClass = wowClass;
    }

    @Override
    public NumericSequenceId getId() {
        return id;
    }
    public void setId(NumericSequenceId id) {
        this.id = id;
    }

    public interface ClassSpecificationRepository extends CrudRepository<ClassSpecificationEntity, NumericSequenceId> {

        ClassSpecificationEntity findByNameAndWowClass(String name, WOWClass wowClass);

        ClassSpecificationEntity findByName(String name);

    }
}
