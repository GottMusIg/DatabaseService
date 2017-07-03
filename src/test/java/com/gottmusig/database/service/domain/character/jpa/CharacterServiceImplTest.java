package com.gottmusig.database.service.domain.character.jpa;

import com.google.gson.Gson;
import com.gottmusig.database.service.configuration.DatabaseServiceConfiguration;
import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.character.jpa.characterpojo.WoWCharacter;
import com.gottmusig.database.service.domain.item.EquipmentSet;
import com.gottmusig.database.service.domain.item.Item;
import com.gottmusig.database.service.domain.item.jpa.EquipmentSetImpl;
import com.gottmusig.database.service.domain.item.jpa.ItemEntity;
import com.gottmusig.database.service.domain.realm.jpa.Location;
import com.gottmusig.database.service.domain.realm.jpa.RealmEntity;
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * @author leong
 * @since 16.05.2017
 */
@ContextConfiguration(classes = { DatabaseServiceConfiguration.class })
@TestPropertySource("classpath:test-h2-context.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class CharacterServiceImplTest {

    private Gson gson = new Gson();

    SQLUtil sqlUtil;

    @Autowired
    private
    ResourceLoader loader;
    @Autowired
    private
    DataSource dataSource;

    @Autowired
    private
    CharacterServiceImpl characterService;

    @Autowired
    CharacterEntity.CharacterRepository characterRepository;

    @Autowired
    RealmEntity.RealmRepository realmRepository;

    @Autowired
    WOWClassEntity.WOWClassRepository wowClassRepository;

    @Autowired
    ClassSpecificationEntity.ClassSpecificationRepository classSpecificationRepository;



    private WoWCharacter woWCharacter() throws IOException {

        String characterString = new BufferedReader(new FileReader(Paths.get("src/test/resources/testCharacter").toFile())).readLine();
        return gson.fromJson(characterString, WoWCharacter.class);
    }

    private Item item() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setIconTooltip("test");
        itemEntity.setContext("test");
        itemEntity.setItemLevel(100L);
        itemEntity.setName("testItem");
        itemEntity.setWowHeadTooltip("test");
        return itemEntity;
    }

    private EquipmentSet equipmentSet() {
        return new EquipmentSetImpl(100L, 100L, item(), item(), item(), item(), item(), item(), item(), item(), item(), item(), item(), item(), item(), item(),item(), item(), item() );
    }

    private CharacterEntity characterEntity() {
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setName("Malahkh");
        characterEntity.setDPS(100);
        characterEntity.setEquipmentSet(equipmentSet());
        characterEntity.setClassSpecification(classSpecificationRepository.findByName("Fury"));
        characterEntity.setRealm(realmRepository.findByName("Blackhand").get());
        return characterEntity;
    }

    @Before
    public void setUp() throws Exception {
        sqlUtil = new SQLUtil(dataSource);
        sqlUtil.execute(Files.readAllBytes(loader.getResource("classpath:create_schema.sql").getFile().toPath()));
        sqlUtil.execute(Files.readAllBytes(loader.getResource("classpath:create_tables.sql").getFile().toPath()));
        RealmEntity realmEntity = new RealmEntity();
        realmEntity.setLocation(Location.deDE);
        realmEntity.setName("Blackhand");
        realmRepository.save(realmEntity);
        WOWClassEntity wowClassEntity = new WOWClassEntity();
        wowClassEntity.setName("Warrior");
        ClassSpecificationEntity classSpecificationEntity = new ClassSpecificationEntity();
        classSpecificationEntity.setWowClass(wowClassRepository.save(wowClassEntity));
        classSpecificationEntity.setName("Fury");
        classSpecificationRepository.save(classSpecificationEntity);
    }

    @After
    public void after() throws Exception {
        sqlUtil.execute(Files.readAllBytes(loader.getResource("classpath:drop_schema.sql").getFile().toPath()));
    }

    @Test
    public void testConvertCharacterNotInDB() {
        Optional<Character> character = characterService.searchCharacter("Blackhand", "Malahkh");
        assertEquals("Malahkh", character.get().getName() );
        assertEquals("Blackhand", character.get().getRealm().getName());
        characterRepository.delete((CharacterEntity) character.get());
    }

    @Test
    public void testConvertCharacterInDB() {
        characterRepository.save(characterEntity());
        Character character = characterService.searchCharacter("Blackhand", "Malahkh").get();
        assertEquals("Malahkh", character.getName() );
        assertEquals("Blackhand", character.getRealm().getName());
        assertEquals("testItem", character.getEquipmentSet().getBack().getName());
        assertEquals(100, character.getDPS());
        assertEquals("Fury", character.getClassSpecification().getName());
        assertEquals("Warrior", character.getWOWClass().getName());
        assertEquals("test", character.getThumbnailId());
        Long expectedItemLevel = 100L;
        assertEquals(expectedItemLevel, character.getEquipmentSet().getAverageItemLevel());
        characterRepository.delete((CharacterEntity) character);
    }

    @Test
    public void testConvertRealmNotAvailable() {
        Optional<Character> character = characterService.searchCharacter("nonexistent", "test");
        assertEquals(Optional.empty(), character);
    }

    @Test
    public void testConvertCharacterNotAvailable() {
        Optional<Character> character = characterService.searchCharacter("Blackhand", "nonExistentCharacter");
        assertEquals(Optional.empty(), character);
    }

}