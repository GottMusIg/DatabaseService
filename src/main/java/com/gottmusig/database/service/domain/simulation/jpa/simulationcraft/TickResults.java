
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
    "crit",
    "hit"
})
@Getter
public class TickResults implements Serializable
{

    @JsonProperty("crit")
    private Crit crit;
    @JsonProperty("hit")
    private Hit hit;
    private final static long serialVersionUID = -2415068417104099732L;

}
