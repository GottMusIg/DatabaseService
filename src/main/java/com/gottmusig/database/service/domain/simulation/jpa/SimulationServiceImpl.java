package com.gottmusig.database.service.domain.simulation.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.simulation.SimulationService;
import com.gottmusig.database.service.domain.simulation.jpa.simulationcraft.Player;
import com.gottmusig.database.service.domain.simulation.jpa.simulationcraft.SimulationCraft;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class SimulationServiceImpl implements SimulationService {

    private static final String BASE_URL = "http://localhost:8080/"; //TODO
    private static final String SIMULATION_PATH = "gottmusig-simulation/rest/simulation/player";

    private static final String REGION = "region";
    private static final String REALM = "realm";
    private static final String USER = "user";
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
        WebTarget target = client.target(BASE_URL).path(SIMULATION_PATH) //
                .queryParam(REGION, "eu") //TODO
                .queryParam(REALM, character.getRealm().getName()) //
                .queryParam(USER, character.getName());
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
