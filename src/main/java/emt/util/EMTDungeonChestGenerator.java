package emt.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import emt.init.EMTItems;

public class EMTDungeonChestGenerator {

    public static void generateLoot() {
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
