package com.gottmusig.database.service.domain.character;


import com.gottmusig.database.service.domain.Entity;
import com.gottmusig.database.service.domain.dpsdifference.SpecificationDPS;

/**
 * @author Leon Gottschick
 * @since 0.0.1
 */
public interface ClassSpecification extends Entity {

    /**
     * @return the name of the Specification
     */
    String getName();


    /**
     * @return the WOWClass the Specification is belonging to
     */
    WOWClass getWOWClass();

    SpecificationDPS getSpecificationDPS();


}
