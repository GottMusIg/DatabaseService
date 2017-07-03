package com.gottmusig.database.service.domain.realm.jpa;

import java.util.Arrays;
import java.util.List;

/**
 * @author lgottschick
 * @since 27.09.2016
 */
public enum Location {

    DEDE("de_DE"),
    ENGB("en_GB"),
    RURU("ru_RU"),
    FRFR("fr_FR"),
    ESES("es_ES"),
    ITIT("it_IT"),
    PTBR("pt_BR");

    private final String name;

    Location(String location) {
        this.name = location;
    }

    public String getName() {
        return name;
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