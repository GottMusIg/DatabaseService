
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
    "name"
})
@Getter
public class Rng implements Serializable
{

    @JsonProperty("name")
    private String name;
    private final static long serialVersionUID = 2292829000265295074L;

}
