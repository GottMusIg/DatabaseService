package com.gottmusig.database.service;

import com.gottmusig.database.service.configuration.DatabaseServiceConfiguration;
import com.gottmusig.database.service.domain.character.jpa.ClassSpecificationEntity;
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
        ClassSpecificationEntity.ClassSpecificationRepository bean = run.getBean(ClassSpecificationEntity.ClassSpecificationRepository.class);
        ClassSpecificationEntity fury = bean.findByName("Fury");


    }
}
