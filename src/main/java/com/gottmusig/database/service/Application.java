package com.gottmusig.database.service;

import com.gottmusig.database.service.configuration.DatabaseServiceConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * @author leong
 * @since 24.11.2016
 */

public class Application extends SpringApplication {

    public Application() {
        super(DatabaseServiceConfiguration.class);
    }

    public static void main(String[] args) {
        new Application().run(args);
    }
}
