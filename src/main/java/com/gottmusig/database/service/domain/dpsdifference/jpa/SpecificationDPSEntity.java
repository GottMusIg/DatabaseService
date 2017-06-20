package com.gottmusig.database.service.domain.dpsdifference.jpa;

import com.gottmusig.database.service.domain.character.jpa.ClassSpecificationEntity;
import com.gottmusig.database.service.domain.dpsdifference.SpecificationDPS;
import com.gottmusig.database.service.domain.jpa.NumericSequenceId;
import com.gottmusig.database.service.domain.jpa.SpringEntityListener;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.List;

/**
 * @author leong
 * @since 20.11.2016
 */
@Entity
@Table(name = "specificationdps")
@EntityListeners(SpringEntityListener.class)
public class SpecificationDPSEntity implements SpecificationDPS {

    @EmbeddedId
    private NumericSequenceId id;

    private int dps;

    @OneToOne
    @JoinColumn(name = "classSpecification_id", referencedColumnName = "id")
    private ClassSpecificationEntity specification;

    public SpecificationDPSEntity() {
        this.id = new NumericSequenceId();
    }

    @Override
    public int getDPS() {
        return dps;
    }
    public void setDps(int dps) {
        this.dps = dps;
    }

    @Override
    public ClassSpecificationEntity getSpecification() {
        return specification;
    }
    public void setSpecification(ClassSpecificationEntity specification) {
        this.specification = specification;
    }

    @Override
    public NumericSequenceId getId() {
        return id;
    }
    public void setId(NumericSequenceId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SpecificationDPSEntity{" +
                "id=" + id +
                ", dps=" + dps +
                ", specification=" + specification +
                '}';
    }

    public interface SpecificationDPSRepository extends CrudRepository<SpecificationDPSEntity, NumericSequenceId> {

        List<SpecificationDPS> findByDpsGreaterThan(int dps, Sort sort);

        SpecificationDPS findFirstByDpsGreaterThan(int dps, Sort sort);


        SpecificationDPS findBySpecification_Id(NumericSequenceId specification_id);

    }
}
