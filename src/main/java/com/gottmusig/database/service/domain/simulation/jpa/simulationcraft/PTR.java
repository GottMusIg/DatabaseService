
package com.gottmusig.database.service.domain.simulation.jpa.simulationcraft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "build_level",
    "wow_version"
})
@Getter
public class PTR implements Serializable
{

    @JsonProperty("build_level")
    private Integer buildLevel;
    @JsonProperty("wow_version")
    private String wowVersion;
    private final static long serialVersionUID = -1867972800043519859L;

}
