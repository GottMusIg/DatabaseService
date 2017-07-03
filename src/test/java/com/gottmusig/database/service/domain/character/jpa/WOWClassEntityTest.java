package com.gottmusig.database.service.domain.character.jpa;

import com.gottmusig.database.service.configuration.DatabaseServiceConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author leong
 * @since 11.05.2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseServiceConfiguration.class })
@TestPropertySource("classpath:test-h2-context.properties")
public class WOWClassEntityTest {

    @Autowired
    ResourceLoader loader;
    @Autowired
    DataSource dataSource;
    @Autowired
    EntityManager entityManager;

    SQLUtil sqlUtil;

    @Autowired
    WOWClassEntity.WOWClassRepository wowClassRepository;

    public WOWClassEntity wowClassEntity() {
        WOWClassEntity wowClassEntity = new WOWClassEntity();
        wowClassEntity.setName("Test");
        return wowClassEntity;
    }

    @Before
    public void setUp() throws Exception {
        sqlUtil = new SQLUtil(dataSource);
        sqlUtil.execute(Files.readAllBytes(loader.getResource("classpath:create_schema.sql").getFile().toPath()));
        sqlUtil.execute(Files.readAllBytes(loader.getResource("classpath:create_tables.sql").getFile().toPath()));
    }

    @After
    public void after() throws Exception {
        sqlUtil.execute(Files.readAllBytes(loader.getResource("classpath:drop_schema.sql").getFile().toPath()));
    }

    @Test
    public void testWoWClassRepositoryInsert() {
        String expectedName = "Test";
        wowClassRepository.save(wowClassEntity());
        WOWClassEntity classEntity = wowClassRepository.findByName(expectedName);

        assertEquals(expectedName, classEntity.getName());
        wowClassRepository.deleteAll();
    }

    @Test
    public void testWoWClassRepositoryDeletion() {
        WOWClassEntity save = wowClassRepository.save(wowClassEntity());
        wowClassRepository.delete(save);
        assertTrue(!wowClassRepository.findAll().iterator().hasNext());
    }

}