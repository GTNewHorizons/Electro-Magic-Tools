package emt.nei;

import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.event.FMLInterModComms;
import emt.EMT;
import net.minecraft.nbt.NBTTagCompound;

public class NEI_Config implements IConfigureNEI {

    public static boolean isAdded = true;

    @Override
    public void loadConfig() {
        NEI_Config.isAdded = false;
        NEI_Config.isAdded = true;
    }

    @Override
    public String getName() {
        return "EMT";
    }

    @Override
    public String getVersion() {
        return EMT.VERSION;
    }

    public static void Init() {
        sendHandler("emt.recipe.fusioncrafting", "gregtech:gt.blockmachines:5001");
        sendCatalyst("emt.recipe.fusioncrafting", "gregtech:gt.blockmachines:5001");
    }

    private static void sendHandler(String name, String block) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", name);
        aNBT.setString("modName", EMT.NAME);
        aNBT.setString("modId", EMT.MOD_ID);
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", block);
        aNBT.setInteger("handlerHeight", 135);
        aNBT.setInteger("handlerWidth", 166);
        aNBT.setInteger("maxRecipesPerPage", 2);
        aNBT.setInteger("yShift", 6);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
    }

    private static void sendCatalyst(String name, String stack, int priority) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", name);
        aNBT.setString("itemName", stack);
        aNBT.setInteger("priority", priority);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }

    private static void sendCatalyst(String name, String stack) {
        sendCatalyst(name, stack, 0);
    }
}
