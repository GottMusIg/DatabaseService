package com.gottmusig.database.service.domain.dpsdifference.jpa;

import com.gottmusig.database.service.domain.character.jpa.ClassSpecificationEntity;
import com.gottmusig.database.service.domain.dpsdifference.SpecificationDPS;
import com.gottmusig.database.service.domain.jpa.NumericSequenceId;
import com.gottmusig.database.service.domain.jpa.SpringEntityListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

/**
 * @author leong
 * @since 20.11.2016
 */
@Component
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

    @Override
    public NumericSequenceId getId() {
        return id;
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

        SpecificationDPS findBySpecification (ClassSpecificationEntity specification);

    }
}
