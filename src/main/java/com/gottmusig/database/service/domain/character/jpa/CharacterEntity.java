package com.gottmusig.database.service.domain.character.jpa;

import com.gottmusig.database.service.domain.character.Character;
import com.gottmusig.database.service.domain.character.ClassSpecification;
import com.gottmusig.database.service.domain.character.WOWClass;
import com.gottmusig.database.service.domain.item.EquipmentSet;
import com.gottmusig.database.service.domain.item.jpa.EquipmentSetImpl;
import com.gottmusig.database.service.domain.item.jpa.ItemEntity;
import com.gottmusig.database.service.domain.jpa.NumericSequenceId;
import com.gottmusig.database.service.domain.jpa.SpringEntityListener;
import com.gottmusig.database.service.domain.realm.Realm;
import com.gottmusig.database.service.domain.realm.jpa.RealmEntity;
import com.gottmusig.database.service.domain.simulation.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.Optional;

/**
 * Description
 *
 * @author lgottschick
 * @since 1.0.0-SNAPSHOT
 */
@Entity
@Table(name = "wowcharacter")
@EntityListeners(SpringEntityListener.class)
public class CharacterEntity implements Character {

    @Autowired
    private transient SimulationService simulationService;

    @EmbeddedId
    private NumericSequenceId id;

    @Column(name = "dps")
    private int dps;

    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail_id")
    private String thumbnailId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private RealmEntity realm;

    @ManyToOne(optional = false)
    @JoinColumn(name = "classSpecification_id", referencedColumnName = "id")
    private ClassSpecificationEntity classSpecification;

    @Column(name = "average_item_level")
    private Long averageItemLevel;

    @Column(name = "average_item_level_equipped")
    private Long averageItemLevelEquipped;

    @OneToOne
    @JoinColumn(name = "head_id", referencedColumnName = "id")
    private ItemEntity head;

    @OneToOne
    @JoinColumn(name = "neck_id", referencedColumnName = "id")
    private ItemEntity neck;

    @OneToOne
    @JoinColumn(name = "shoulder_id", referencedColumnName = "id")
    private ItemEntity shoulders;

    @OneToOne
    @JoinColumn(name = "back_id", referencedColumnName = "id")
    private ItemEntity back;

    @OneToOne
    @JoinColumn(name = "skirt_id", referencedColumnName = "id")
    private ItemEntity skirt;

    @OneToOne
    @JoinColumn(name = "chest_id", referencedColumnName = "id")
    private ItemEntity chest;

    @OneToOne
    @JoinColumn(name = "wrist_id", referencedColumnName = "id")
    private ItemEntity wrist;

    @OneToOne
    @JoinColumn(name = "main_hand_id", referencedColumnName = "id")
    private ItemEntity mainHand;

    @OneToOne
    @JoinColumn(name = "off_hand_id", referencedColumnName = "id")
    private ItemEntity offHand;

    @OneToOne
    @JoinColumn(name = "hands_id", referencedColumnName = "id")
    private ItemEntity hands;

    @OneToOne
    @JoinColumn(name = "finger_1_id", referencedColumnName = "id")
    private ItemEntity finger1;

    @OneToOne
    @JoinColumn(name = "finger_2_id", referencedColumnName = "id")
    private ItemEntity finger2;

    @OneToOne
    @JoinColumn(name = "waist_id", referencedColumnName = "id")
    private ItemEntity waist;

    @OneToOne
    @JoinColumn(name = "legs_id", referencedColumnName = "id")
    private ItemEntity legs;

    @OneToOne
    @JoinColumn(name = "feet_id", referencedColumnName = "id")
    private ItemEntity feet;

    @OneToOne
    @JoinColumn(name = "trinket_1_id", referencedColumnName = "id")
    private ItemEntity trinket1;

    @OneToOne
    @JoinColumn(name = "trinket_2_id", referencedColumnName = "id")
    private ItemEntity trinket2;


    public CharacterEntity() {
        this.id = new NumericSequenceId();
    }



    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getDPS() {

        return dps;
    }
    public void setDPS(int dps) {
        this.dps = dps;
    }

    public int calculateDps(){

        simulationService.simulateDPS(this);
        return this.dps;
    }


    @Override
    public ClassSpecification getClassSpecification() {
        return classSpecification;
    }
    public void setClassSpecification(ClassSpecification classSpecification) {
        this.classSpecification = (ClassSpecificationEntity) classSpecification;
    }

    @Override
    public WOWClass getWOWClass() {
        return classSpecification.getWOWClass();
    }

    @Override
    public Realm getRealm() {
        return realm;
    }
    public void setRealm(Realm realm) {
        this.realm = (RealmEntity) realm;
    }

    @Override
    public String getThumbnailId() {
        return thumbnailId;
    }
    public void setThumbnailId(String thumbnail) {
        this.thumbnailId = thumbnail;
    }

    @Override
    public EquipmentSet getEquipmentSet() {
        return new EquipmentSetImpl(averageItemLevel, averageItemLevelEquipped, head, neck, shoulders, back, skirt, chest, wrist, mainHand, offHand, hands,
                                    finger1, finger2, waist, legs, feet, trinket1, trinket2);
    }

    public void setEquipmentSet(EquipmentSet equipmentSet) {
        this.averageItemLevel = equipmentSet.getAverageItemLevel();
        this.averageItemLevelEquipped = equipmentSet.getAverageItemLevelEquipped();
        this.head = (ItemEntity) equipmentSet.getHead();
        this.neck = (ItemEntity) equipmentSet.getNeck();
        this.shoulders = (ItemEntity) equipmentSet.getShoulders();
        this.back = (ItemEntity) equipmentSet.getBack();
        this.skirt = (ItemEntity) equipmentSet.getSkirt();
        this.chest = (ItemEntity) equipmentSet.getChest();
        this.wrist = (ItemEntity) equipmentSet.getWrist();
        this.mainHand = (ItemEntity) equipmentSet.getMainHand();
        this.offHand = (ItemEntity) equipmentSet.getOffHand();
        this.hands = (ItemEntity) equipmentSet.getHands();
        this.finger1 = (ItemEntity) equipmentSet.getFinger1();
        this.finger2 = (ItemEntity) equipmentSet.getFinger2();
        this.waist = (ItemEntity) equipmentSet.getWaist();
        this.legs = (ItemEntity) equipmentSet.getLegs();
        this.feet = (ItemEntity) equipmentSet.getFeet();
        this.trinket1 = (ItemEntity) equipmentSet.getTrinket1();
        this.trinket2 = (ItemEntity) equipmentSet.getTrinket2();
    }


    @Override
    public NumericSequenceId getId() {
        return id;
    }

    public interface CharacterRepository extends CrudRepository<CharacterEntity, NumericSequenceId> {

        Optional<Character> findByNameAndRealm(String name, Realm realm);

    }

}
