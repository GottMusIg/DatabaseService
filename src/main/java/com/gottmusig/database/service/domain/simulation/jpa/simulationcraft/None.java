
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
    "actual",
    "overflow",
    "count"
})
@Getter
public class None implements Serializable
{

    @JsonProperty("actual")
    private Double actual;
    @JsonProperty("overflow")
    private Double overflow;
    @JsonProperty("count")
    private Double count;
    private final static long serialVersionUID = -2587666591384291495L;

}
