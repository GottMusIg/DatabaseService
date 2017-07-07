package com.gottmusig.database.service.configuration;

import com.gottmusig.database.service.domain.jpa.SpringEntityListener;
import com.mysql.jdbc.Driver;
import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Main configuration for all JPA aspects. Couples Eclipse Link with Spring Data JPA.
 *
 * @author Leon Gottschick
 * @since 0.0.1
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.gottmusig", considerNestedRepositories = true)
@PropertySource({"classpath:/database.properties"})
public class JpaConfiguration {

    private final Environment env;
    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public JpaConfiguration(Environment env, AutowireCapableBeanFactory beanFactory) {
        this.env = env;
        this.beanFactory = beanFactory;
    }


    @Bean
    public SpringEntityListener springEntityListener() {
        SpringEntityListener listener = SpringEntityListener.get();
        listener.setBeanFactory(beanFactory);
        return listener;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl(env.getRequiredProperty("datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("datasource.password"));
        dataSource.setTestOnBorrow(false);
        return dataSource;
    }

    @Bean
    protected Map<String, Object> getVendorProperties() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(PersistenceUnitProperties.WEAVING, Boolean.FALSE.toString());
        map.put(PersistenceUnitProperties.LOGGING_LEVEL, "FINE");
        return map;
    }

    @Bean
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean
    protected EclipseLinkJpaVendorAdapter jpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        return adapter;
    }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(jpaVendorAdapter(), jpaProperties().getProperties(), null);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        Map<String, Object> vendorProperties = getVendorProperties();
        return entityManagerFactoryBuilder().dataSource(dataSource())
                                            .packages("com.gottmusig")
                                            .properties(vendorProperties)
                                            .jta(true)
                                            .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}