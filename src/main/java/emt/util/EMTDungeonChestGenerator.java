package emt.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import cpw.mods.fml.common.Loader;
import emt.init.EMTItems;

public class EMTDungeonChestGenerator {

    public static void generateLoot() {
        // GTNH replaces the whole dungeonChest table from config/TooMuchLoot/loot/dungeonChest.xml at
        // FMLServerStartingEvent, which discards everything registered here. Edit that file instead.
        if (Loader.isModLoaded("dreamcraft")) {
            return;
        }
        if (!EMTConfigHandler.thorHammerResearch) {
            ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
                    new WeightedRandomChestContent(
                            new ItemStack(EMTItems.taintedThorHammer),
                            0,
                            1,
                            EMTConfigHandler.chanceTaintedMjolnir));
        }
        if (!EMTConfigHandler.oneRingSpawn) {
            ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(
                    new WeightedRandomChestContent(
                            new ItemStack(EMTItems.onering),
                            0,
                            1,
                            EMTConfigHandler.chanceOneRing));
        }
    }
}
