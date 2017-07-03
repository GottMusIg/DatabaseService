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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author leong
 * @since 11.05.2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseServiceConfiguration.class })
@TestPropertySource("classpath:test-h2-context.properties")
public class ClassSpecificationEntityTest {

    @Autowired
    private
    ResourceLoader loader;
    @Autowired
    private
    DataSource dataSource;

    private SQLUtil sqlUtil;

    @Autowired
    private
    WOWClassEntity.WOWClassRepository wowClassRepository;

    @Autowired
    private
    ClassSpecificationEntity.ClassSpecificationRepository classSpecificationRepository;

    public WOWClassEntity wowClassEntity() {
        WOWClassEntity wowClassEntity = new WOWClassEntity();
        wowClassEntity.setName("Test");
        return wowClassEntity;
    }

    public ClassSpecificationEntity classSpecificationEntity() {
        ClassSpecificationEntity entity = new ClassSpecificationEntity();
        entity.setName("TestSpezifikation");
        return entity;
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
    public void testClassSpecificationInsert() {
        String expectedName = "TestSpezifikation";
        ClassSpecificationEntity classSpecificationEntity = classSpecificationEntity();
        classSpecificationEntity.setWowClass(wowClassRepository.save(wowClassEntity()));

        ClassSpecificationEntity save = classSpecificationRepository.save(classSpecificationEntity);

        assertEquals(expectedName, save.getName());
        assertEquals("Test", save.getWOWClass().getName());

        classSpecificationRepository.deleteAll();
        wowClassRepository.deleteAll();
    }

    @Test
    public void testClassSpecificationDelete() {

        ClassSpecificationEntity classSpecificationEntity = classSpecificationEntity();
        classSpecificationEntity.setWowClass(wowClassRepository.save(wowClassEntity()));

        ClassSpecificationEntity save = classSpecificationRepository.save(classSpecificationEntity);
        classSpecificationRepository.delete(save);

        assertFalse(classSpecificationRepository.findAll().iterator().hasNext());

        classSpecificationRepository.deleteAll();
        wowClassRepository.deleteAll();
    }
}