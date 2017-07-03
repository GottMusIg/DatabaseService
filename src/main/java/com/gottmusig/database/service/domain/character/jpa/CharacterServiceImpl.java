package com.gottmusig.database.service.domain.character.jpa;

import com.google.gson.Gson;
import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.character.CharacterService;
import com.gottmusig.database.service.domain.character.jpa.CharacterEntity.CharacterRepository;
import com.gottmusig.database.service.domain.character.jpa.WOWClassEntity.WOWClassRepository;
import com.gottmusig.database.service.domain.character.jpa.blizzard.SearchCharacterClient;
import com.gottmusig.database.service.domain.character.jpa.characterpojo.WOWClassId;
import com.gottmusig.database.service.domain.character.jpa.characterpojo.WoWCharacter;
import com.gottmusig.database.service.domain.character.jpa.exception.CharacterNotFoundException;
import com.gottmusig.database.service.domain.item.jpa.EquipmentSetImpl;
import com.gottmusig.database.service.domain.realm.Realm;
import com.gottmusig.database.service.domain.realm.jpa.RealmEntity.RealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired SearchCharacterClient searchCharacterClient;
    @Autowired CharacterRepository characterRepository;
    @Autowired RealmRepository realmRepository;
    @Autowired WOWClassRepository wowClassRepository;

    private final Gson gson = new Gson();

    @Override
    public Optional<Character> searchCharacter(String realmName, String name) {
        Optional<Realm> realmByName = realmRepository.findByName(realmName);
        if (!realmByName.isPresent()) {
            return Optional.empty();
        }
        Realm realm = realmByName.get();
        Optional<Character> characterOptional = characterRepository.findByNameAndRealm(name, realm);
        if(characterOptional.isPresent()) {
            return characterOptional;
        }
        Optional<Character> characterViaBlizzard = searchCharacterViaBlizzard(name, realm);
        return characterViaBlizzard.map(this::saveCharacter);
    }

    @Override
    public Character saveCharacter(Character character) {
        return characterRepository.save((CharacterEntity) character);
    }

    private Optional<Character> searchCharacterViaBlizzard(String name, Realm realm) {

        String response;
        try {
            response = searchCharacterClient.searchCharacter(realm.getName(), name);
        } catch (CharacterNotFoundException e) {
            return Optional.empty();
        }
        CharacterEntity characterEntity = createCharacterFromResponse(response);
        characterEntity.setRealm(realm);
        characterEntity.setName(name);

        return Optional.of(characterEntity);
    }

    private CharacterEntity createCharacterFromResponse(String response) {
        WoWCharacter source = gson.fromJson(response, WoWCharacter.class);
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setThumbnailId(source.getThumbnail().replaceAll("-avatar[.]jpg",""));
        ClassSpecificationEntity specificationEntity = new ClassSpecificationEntity();
        specificationEntity.setName(source.getTalents().get(0).getSpec().getName());
        String wowClassName = WOWClassId.getWowClassName(source.getClazz());
        specificationEntity.setWowClass(wowClassRepository.findByName(wowClassName));
        characterEntity.setClassSpecification(specificationEntity);
        characterEntity.setEquipmentSet(new EquipmentSetImpl(source.getItems()));
        return characterEntity;
    }
}
