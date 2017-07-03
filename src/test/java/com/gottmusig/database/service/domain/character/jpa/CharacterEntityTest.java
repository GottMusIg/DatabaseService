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

import javax.sql.DataSource;
import java.nio.file.Files;

/**
 * @author leong
 * @since 11.05.2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseServiceConfiguration.class })
@TestPropertySource("classpath:test-h2-context.properties")
public class CharacterEntityTest {

    @Autowired
    private
    DataSource dataSource;

    @Autowired
    private
    ResourceLoader loader;

    @Autowired
    private CharacterEntity.CharacterRepository characterRepository;

    private SQLUtil sqlUtil;

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

    public WOWClassEntity wowClassEntity() {
        WOWClassEntity wowClassEntity = new WOWClassEntity();
        wowClassEntity.setName("Test");
        return wowClassEntity;
    }

    public ClassSpecificationEntity classSpecificationEntity() {
        ClassSpecificationEntity entity = new ClassSpecificationEntity();
        entity.setName("TestSpezifikation");
        entity.setWowClass(wowClassEntity());
        return entity;
    }

    private void characterEntity() {
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setClassSpecification(classSpecificationEntity());
        characterEntity.setDPS(12);
    }

    @Test
    public void testCharacterEntity() throws Exception {

    }

}