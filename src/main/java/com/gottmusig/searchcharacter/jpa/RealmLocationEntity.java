package com.gottmusig.searchcharacter.jpa;

import com.gottmusig.searchcharacter.domain.api.RealmLocation;

/**
 * @author lgottschick
 * @since 27.09.2016
 */
public class RealmLocationEntity implements RealmLocation {

    private Location location;

    public RealmLocationEntity(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
		return location;
	}

    public void setLocation(Location location) {
        this.location = location;
    }
}