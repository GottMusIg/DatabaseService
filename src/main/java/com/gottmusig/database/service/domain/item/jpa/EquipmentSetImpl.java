package com.gottmusig.database.service.domain.item.jpa;

import com.gottmusig.database.service.domain.character.jpa.WoWHeadToolTip;
import com.gottmusig.database.service.domain.character.jpa.WoWHeadTooltipGenerator;
import com.gottmusig.database.service.domain.character.jpa.characterpojo.GeneralItem;
import com.gottmusig.database.service.domain.character.jpa.characterpojo.Items;
import com.gottmusig.database.service.domain.item.EquipmentSet;
import com.gottmusig.database.service.domain.item.Item;

/**
 * @author leong
 * @since 07.05.2017
 */
public class EquipmentSetImpl implements EquipmentSet {

    private static WoWHeadTooltipGenerator woWHeadTooltipGenerator = new WoWHeadTooltipGenerator();

    private static final String ICON_TOOLTIP_PREFIX = "http://media.blizzard.com/wow/icons/56/";

    private static final String ICON_TOOLTIP_SUFFIX = ".jpg";

    private static final String EMPTY_SLOT = "empty slot";


    private Long averageItemLevel;

    private Long averageItemLevelEquipped;

    private Item head;

    private Item neck;

    private Item shoulders;

    private Item back;

    private Item skirt;

    private Item chest;

    private Item wrist;

    private Item mainHand;

    private Item offHand;

    private Item hands;

    private Item finger1;

    private Item finger2;

    private Item waist;

    private Item legs;

    private Item feet;

    private Item trinket1;

    private Item trinket2;

    public EquipmentSetImpl(Long averageItemLevel, Long averageItemLevelEquipped, Item head, Item neck, Item shoulders, Item back, Item skirt, Item chest, Item wrist, Item mainHand, Item offHand, Item hands, Item finger1, Item finger2, Item waist, Item legs, Item feet, Item trinket1, Item trinket2) {
        this.averageItemLevel = averageItemLevel;
        this.averageItemLevelEquipped = averageItemLevelEquipped;
        this.head = head;
        this.neck = neck;
        this.shoulders = shoulders;
        this.back = back;
        this.skirt = skirt;
        this.chest = chest;
        this.wrist = wrist;
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.hands = hands;
        this.finger1 = finger1;
        this.finger2 = finger2;
        this.waist = waist;
        this.legs = legs;
        this.feet = feet;
        this.trinket1 = trinket1;
        this.trinket2 = trinket2;
    }

    public EquipmentSetImpl(Items items) {
        this(items.getAverageItemLevel(),
             items.getAverageItemLevelEquipped(),
             convert(items.getHead()),
             convert(items.getNeck()),
             convert(items.getShoulder()),
             convert(items.getBack()),
             convert(items.getShirt()),
             convert(items.getChest()),
             convert(items.getWrist()),
             convert(items.getMainHand()),
             convert(items.getOffHand()),
             convert(items.getHands()),
             convert(items.getFinger1()),
             convert(items.getFinger2()),
             convert(items.getWaist()),
             convert(items.getLegs()),
             convert(items.getFeet()),
             convert(items.getTrinket1()),
             convert(items.getTrinket2()));
    }

    private static Item convert(GeneralItem source) {
        ItemEntity itemEntity = new ItemEntity();

        if (source == null) {
            itemEntity.setWowHeadTooltip(EMPTY_SLOT);
            itemEntity.setContext(EMPTY_SLOT);
            itemEntity.setName(EMPTY_SLOT);
            itemEntity.setItemLevel(0L);
            itemEntity.setIconTooltip(EMPTY_SLOT);
            return itemEntity;
        }
        WoWHeadToolTip wowHeadTooltip = woWHeadTooltipGenerator.convert(source);
        itemEntity.setContext(source.getContext());
        itemEntity.setWowHeadTooltip(wowHeadTooltip.getTooltip());
        itemEntity.setName(source.getName());
        itemEntity.setItemLevel(source.getItemLevel());
        itemEntity.setIconTooltip(ICON_TOOLTIP_PREFIX + source.getIcon() + ICON_TOOLTIP_SUFFIX);

        return itemEntity;
    }


    @Override
    public Long getAverageItemLevel() {
        return averageItemLevel;
    }

    public void setAverageItemLevel(Long averageItemLevel) {
        this.averageItemLevel = averageItemLevel;
    }

    @Override
    public Long getAverageItemLevelEquipped() {
        return averageItemLevelEquipped;
    }

    public void setAverageItemLevelEquipped(Long averageItemLevelEquipped) {
        this.averageItemLevelEquipped = averageItemLevelEquipped;
    }

    @Override
    public Item getHead() {
        return head;
    }

    public void setHead(Item head) {
        this.head = head;
    }

    @Override
    public Item getNeck() {
        return neck;
    }

    public void setNeck(Item neck) {
        this.neck = neck;
    }

    @Override
    public Item getShoulders() {
        return shoulders;
    }

    public void setShoulders(Item shoulders) {
        this.shoulders = shoulders;
    }

    @Override
    public Item getBack() {
        return back;
    }

    public void setBack(Item back) {
        this.back = back;
    }

    @Override
    public Item getSkirt() {
        return skirt;
    }

    public void setSkirt(Item skirt) {
        this.skirt = skirt;
    }

    @Override
    public Item getChest() {
        return chest;
    }

    public void setChest(Item chest) {
        this.chest = chest;
    }

    @Override
    public Item getWrist() {
        return wrist;
    }

    public void setWrist(Item wrist) {
        this.wrist = wrist;
    }

    @Override
    public Item getMainHand() {
        return mainHand;
    }

    public void setMainHand(Item mainHand) {
        this.mainHand = mainHand;
    }

    @Override
    public Item getOffHand() {
        return offHand;
    }

    public void setOffHand(Item offHand) {
        this.offHand = offHand;
    }

    @Override
    public Item getHands() {
        return hands;
    }

    public void setHands(Item hands) {
        this.hands = hands;
    }

    @Override
    public Item getFinger1() {
        return finger1;
    }

    public void setFinger1(Item finger1) {
        this.finger1 = finger1;
    }

    @Override
    public Item getFinger2() {
        return finger2;
    }

    public void setFinger2(Item finger2) {
        this.finger2 = finger2;
    }

    @Override
    public Item getWaist() {
        return waist;
    }

    public void setWaist(Item waist) {
        this.waist = waist;
    }

    @Override
    public Item getLegs() {
        return legs;
    }

    public void setLegs(Item legs) {
        this.legs = legs;
    }

    @Override
    public Item getFeet() {
        return feet;
    }

    public void setFeet(Item feet) {
        this.feet = feet;
    }

    @Override
    public Item getTrinket1() {
        return trinket1;
    }

    public void setTrinket1(Item trinket1) {
        this.trinket1 = trinket1;
    }

    @Override
    public Item getTrinket2() {
        return trinket2;
    }

    public void setTrinket2(Item trinket2) {
        this.trinket2 = trinket2;
    }
}
