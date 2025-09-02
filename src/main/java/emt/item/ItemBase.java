package emt.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import emt.EMT;
import emt.util.EMTTextHelper;

public class ItemBase extends Item {

    public ItemBase(String unlocName, String textureName) {
        super();
        setUnlocalizedName(EMT.MOD_ID + unlocName);
        setTextureName(EMT.RESOURCE_PATH + ":" + textureName);
        setCreativeTab(EMT.TAB);
    }

    public ItemBase(String unlocName) {
        super();
        setUnlocalizedName(EMT.MOD_ID + ".item." + unlocName);
        setCreativeTab(EMT.TAB);
    }

    public String getItemStackDisplayName(ItemStack stack) {
        return (EMTTextHelper.GREEN
                + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }
}
