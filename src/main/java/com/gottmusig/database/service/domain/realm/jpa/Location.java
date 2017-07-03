package com.gottmusig.database.service.domain.realm.jpa;

import java.util.Arrays;
import java.util.List;

/**
 * @author lgottschick
 * @since 27.09.2016
 */
public enum Location {

    deDE("de_DE"),
    enGB("en_GB"),
    ruRU("ru_RU"),
    frFR("fr_FR"),
    esES("es_ES"),
    itIT("it_IT"),
    ptBR("pt_BR");

    private final String location;

    Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    /**
     * Creates a list with all {@link Location} and returns it.
     *
     * @return A list with all {@link Location}
     */
    public static List<Location> getLocations() {
        return Arrays.asList(Location.values());
    }
}