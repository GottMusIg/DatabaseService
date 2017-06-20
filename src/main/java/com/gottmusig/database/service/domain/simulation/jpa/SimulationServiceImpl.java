package com.gottmusig.database.service.domain.simulation.jpa;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.simulation.SimulationService;
import com.gottmusig.gottmusig.model.dpscalculation.Player;
import com.gottmusig.gottmusig.model.dpscalculation.SimulationCraft;
import org.springframework.stereotype.Service;

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

    private final Client client;

    public SimulationServiceImpl(){
        client = ClientBuilder.newClient();
    }


    @Override
    public Character simulateDPS(Character character) {

        SimulationCraft simulation = getSimulationFor(character);
        try {
            int dps = getDpsFor(character, simulation);
            character.setDPS(dps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return character;

    }

    private SimulationCraft getSimulationFor(Character character){
        WebTarget target = client.target(BASE_URL).path(SIMULATION_PATH) //
                .queryParam(REGION, "eu") //TODO
                .queryParam(REALM, character.getRealm().getName()) //
                .queryParam(USER, character.getName());
        return target.request().get(SimulationCraft.class);
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
