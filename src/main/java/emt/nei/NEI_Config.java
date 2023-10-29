package emt.nei;

import net.minecraft.item.ItemStack;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import emt.EMT;
import emt.init.EMTItems;

public class NEI_Config implements IConfigureNEI {

    @Override
    public void loadConfig() {
        API.hideItem(new ItemStack(EMTItems.itemEMTItems, 1, 16));
        API.hideItem(new ItemStack(EMTItems.itemEMTItems, 1, 17));
        API.hideItem(new ItemStack(EMTItems.itemEMTItems, 1, 18));
        API.hideItem(new ItemStack(EMTItems.itemEMTItems, 1, 19));
    }

    @Override
    public String getName() {
        return "EMT";
    }

    @Override
    public String getVersion() {
        return EMT.VERSION;
    }
}
