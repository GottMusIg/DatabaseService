package com.gottmusig.database.service.configuration;

import com.gottmusig.database.service.domain.character.jpa.blizzard.SearchCharacterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
@Import(JpaConfiguration.class)
@PropertySource({"classpath:/blizzard.properties"})
@ComponentScan(basePackages = {"com.gottmusig.database.service.domain"} )
public class DatabaseServiceConfiguration {

    private final Environment env;

    @Autowired
    public DatabaseServiceConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public SearchCharacterClient searchCharacterClient() {
        return new SearchCharacterClient(env.getRequiredProperty("api.path"), env.getRequiredProperty("api.key"));
    }

}
