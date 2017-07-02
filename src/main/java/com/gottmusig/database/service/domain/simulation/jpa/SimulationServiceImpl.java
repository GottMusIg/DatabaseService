package com.gottmusig.database.service.domain.simulation.jpa;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.simulation.SimulationService;
import com.gottmusig.database.service.domain.simulation.jpa.simulationcraft.Player;
import com.gottmusig.database.service.domain.simulation.jpa.simulationcraft.SimulationCraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Service
@PropertySource({ "classpath:/simulation.properties" })
public class SimulationServiceImpl implements SimulationService {

    @Autowired
    private Environment env;

    private ObjectMapper mapper = new ObjectMapper();

    private final Client client;

    public SimulationServiceImpl(){
        client = ClientBuilder.newClient();
    }


    @Override
    public Character simulateDPS(Character character) {


        String simulation = getSimulationFor(character);

        try {
            SimulationCraft simulationCraft = mapper.readValue(simulation, SimulationCraft.class);
            int dps = getDpsFor(character, simulationCraft);
            character.setDPS(dps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return character;

    }

    private String getSimulationFor(Character character){
        WebTarget target = client.target(env.getRequiredProperty("BASE_URL")).path(env.getRequiredProperty("PLAYER_SIMULATION_PATH")) //
                .queryParam(env.getRequiredProperty("REGION"), "eu") //TODO
                .queryParam(env.getRequiredProperty("REALM"), character.getRealm().getName()) //
                .queryParam(env.getRequiredProperty("USER"), character.getName());
                return target.request(MediaType.APPLICATION_JSON).buildGet().invoke().readEntity(String.class);

    }

    private int getDpsFor(Character character, SimulationCraft simulationCraft) throws Exception {
        for(Player player : simulationCraft.getSim().getPlayers()){
            if(player.getName().equals(character.getName())){
                return player.getCollectedData().getDps().getMean().intValue();
            }
        }
        throw new Exception("Something went wrong while getting dps for character "+character.getName());
    }
}
