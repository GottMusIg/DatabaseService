package com.gottmusig.database.service;

import com.gottmusig.database.service.configuration.DatabaseServiceConfiguration;
import com.gottmusig.database.service.domain.GottMusIg;
import com.gottmusig.database.service.domain.character.Character;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author leong
 * @since 24.11.2016
 */

public class Application extends SpringApplication {

    public Application() {
        super(DatabaseServiceConfiguration.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = new Application().run(args);

        GottMusIg bean = run.getBean(GottMusIg.class);
        Character character = bean.characterService().searchCharacter("Blackhand", "Malahkh").get();
        Character character1 = bean.simulationService().simulateDPS(character);

    }


}
