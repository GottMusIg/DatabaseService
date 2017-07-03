package com.gottmusig.database.service.domain.simulation.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.character.jpa.exception.CharacterNotFoundException;
import com.gottmusig.database.service.domain.jpa.SpringEntityListener;
import com.gottmusig.database.service.domain.simulation.SimulationService;
import com.gottmusig.database.service.domain.simulation.jpa.simulationcraft.Player;
import com.gottmusig.database.service.domain.simulation.jpa.simulationcraft.SimulationCraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Service
@PropertySource({ "classpath:/simulation.properties" })
public class SimulationServiceImpl implements SimulationService {

    private static final Logger LOG = LoggerFactory.getLogger(SpringEntityListener.class);

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
        } catch (CharacterNotFoundException e) {
            LOG.error("dps for character {}", character.getName());
        } catch (IOException e) {
            LOG.error("JSON parsing failed for character {}", simulation);
        }

        return character;

    }

    private String getSimulationFor(Character character){
        WebTarget target = client.target(env.getRequiredProperty("BASE_URL")).path(env.getRequiredProperty("PLAYER_SIMULATION_PATH")) //
                .queryParam(env.getRequiredProperty("REGION"), "eu") // fix
                .queryParam(env.getRequiredProperty("REALM"), character.getRealm().getName()) //
                .queryParam(env.getRequiredProperty("USER"), character.getName());
                return target.request(MediaType.APPLICATION_JSON).buildGet().invoke().readEntity(String.class);

    }

    private int getDpsFor(Character character, SimulationCraft simulationCraft) throws CharacterNotFoundException {
        for(Player player : simulationCraft.getSim().getPlayers()){
            if(player.getName().equals(character.getName())){
                return player.getCollectedData().getDps().getMean().intValue();
            }
        }
        throw new CharacterNotFoundException("Something went wrong while getting dps for character "+character.getName());
    }
}
