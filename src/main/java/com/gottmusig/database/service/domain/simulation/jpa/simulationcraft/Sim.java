
package com.gottmusig.database.service.domain.simulation.jpa.simulationcraft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "options",
    "overrides",
    "players",
    "targets",
    "statistics"
})
@Getter
public class Sim implements Serializable
{

    @JsonProperty("options")
    private Options options;
    @JsonProperty("overrides")
    private Overrides overrides;
    @JsonProperty("players")
    private List<Player> players = new ArrayList<Player>();
    @JsonProperty("targets")
    private List<Target> targets = new ArrayList<Target>();
    @JsonProperty("statistics")
    private Statistics statistics;
    private final static long serialVersionUID = 5596736989808049009L;

}
