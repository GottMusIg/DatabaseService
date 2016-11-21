package com.gottmusig.dpsdifference.domain.api;


import com.gottmusig.dpsdifference.domain.Entity;

/**
 * @author Leon Gottschick
 * @since 0.0.1
 */
public interface ClassSpecification extends Entity {

    String getName();

    WOWClass getWOWClass();
}
