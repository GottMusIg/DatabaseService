package com.gottmusig.database.service;

import com.gottmusig.database.service.configuration.DatabaseServiceConfiguration;
import com.gottmusig.database.service.domain.GottMusIg;
import com.gottmusig.database.service.domain.character.Character;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

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
        Optional<Character> malahkh = bean.characterService()
                                          .searchCharacter("Malahkh", bean.realmService()
                                                                          .getAllRealms()
                                                                          .get(0));

    }
}