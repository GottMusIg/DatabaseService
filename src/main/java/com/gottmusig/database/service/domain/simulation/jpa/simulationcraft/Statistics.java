
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
    "elapsed_cpu_seconds",
    "elapsed_time_seconds",
    "simulation_length",
    "raid_dps",
    "total_dmg"
})
@Getter
public class Statistics implements Serializable
{

    @JsonProperty("elapsed_cpu_seconds")
    private Double elapsedCpuSeconds;
    @JsonProperty("elapsed_time_seconds")
    private Double elapsedTimeSeconds;
    @JsonProperty("simulation_length")
    private SimulationLength simulationLength;
    @JsonProperty("raid_dps")
    private RaidDps raidDps;
    @JsonProperty("total_dmg")
    private TotalDmg totalDmg;
    private final static long serialVersionUID = -9137795853525845254L;

}
