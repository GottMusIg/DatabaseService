
package com.gottmusig.database.service.domain.simulation.jpa.simulationcraft;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "sum",
    "count",
    "mean"
})
@Getter
public class NumDirectResults implements Serializable
{

    @JsonProperty("sum")
    private Double sum;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("mean")
    private Double mean;
    private final static long serialVersionUID = 2948102299154204320L;

}
