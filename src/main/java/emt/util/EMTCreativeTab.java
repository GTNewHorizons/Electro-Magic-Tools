package emt.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import emt.init.EMTItems;

public class EMTCreativeTab extends CreativeTabs {

    public EMTCreativeTab(String tabLabel) {
        super(tabLabel);
    }

    @Override
    public Item getTabIconItem() {
        return EMTItems.electricThorHammer;
    }
}
