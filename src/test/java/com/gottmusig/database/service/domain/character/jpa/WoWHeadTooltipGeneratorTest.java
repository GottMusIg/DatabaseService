package com.gottmusig.database.service.domain.character.jpa;

import com.google.common.collect.Lists;
import com.gottmusig.database.service.domain.character.jpa.characterpojo.GeneralItem;
import com.gottmusig.database.service.domain.character.jpa.characterpojo.TooltipParams;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author leong
 * @since 16.05.2017
 */
public class WoWHeadTooltipGeneratorTest  {

    private GeneralItem generalItem() {
        return new GeneralItem() {
            @Override
            public Long getItemId() {
                return 10L;
            }

            @Override
            public String getName() {
                return "TestItem";
            }

            @Override
            public Long getItemLevel() {
                return 710L;
            }

            @Override
            public Long getArmor() {
                return 50L;
            }

            @Override
            public Long getQuality() {
                return 4L;
            }

            @Override
            public TooltipParams getTooltipParams() {
                TooltipParams tooltipParams = new TooltipParams();
                tooltipParams.setEnchant(123L);
                tooltipParams.setGem0(1234L);
                tooltipParams.setGem1(123456L);
                return tooltipParams;
            }

            @Override
            public String getContext() {
                return "raid-mythic";
            }

            @Override
            public List<Long> getBonusLists() {
                return Lists.newArrayList(1L, 2L, 3L, 4L);
            }

            @Override
            public String getIcon() {
                return "";
            }
        };
    }

    private WoWHeadTooltipGenerator woWHeadTooltipGenerator() {
        return new WoWHeadTooltipGenerator();
    }




    @Test
    public void testConvert() throws Exception {
        WoWHeadToolTip convert = woWHeadTooltipGenerator().convert(generalItem());
        assertEquals("item=10&bonus=1:2:3:4&ench=123&gems=1234:123456", convert.getTooltip());

    }


}