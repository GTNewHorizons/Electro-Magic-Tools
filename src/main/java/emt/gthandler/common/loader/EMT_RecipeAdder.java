package emt.gthandler.common.loader;

import emt.init.EMTItems;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;

import java.util.HashSet;

import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;


public class EMT_RecipeAdder {
    public static final GT_Recipe.GT_Recipe_Map sFusionCraftingRecipes = new GT_Recipe.GT_Recipe_Map(new HashSet<>(4), "emt.recipe.fusioncrafting", "Draconic Evolution Fusion Crafter", null, RES_PATH_GUI + "basicmachines/Default", 6, 1, 1, 0, 1, "Tier Casing: ", 1, E, false, false);

    public static void addFusionCraftingRecipe(ItemStack[] inputs, ItemStack output, int aDuration, int aEUt, int aTier) {
        sFusionCraftingRecipes.addRecipe(true, inputs, new ItemStack[]{output}, null, null, null, aDuration, aEUt, aTier);
    }

    public static void Run() {

        // CORES

        addFusionCraftingRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 4),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Ichorium, 1),
                GT_OreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1),
                new ItemStack(EMTItems.itemEMTItems, 0, 16)},
            GT_ModHandler.getModItem("DraconicEvolution", "draconicCore", 1, 0),
            400, 30720, 1);

        addFusionCraftingRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 6),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2),
                GT_ModHandler.getModItem("DraconicEvolution", "draconicCore", 4, 0),
                GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1),
                new ItemStack(EMTItems.itemEMTItems, 0, 17)},
            GT_ModHandler.getModItem("DraconicEvolution", "wyvernCore", 1, 0),
            800, 65536, 2);

        addFusionCraftingRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 8),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 4),
                GT_ModHandler.getModItem("DraconicEvolution", "wyvernCore", 4, 0),
                GT_ModHandler.getModItem("dreamcraft", "ManyullynCrystal", 1, 0),
                new ItemStack(EMTItems.itemEMTItems, 0, 18)},
            GT_ModHandler.getModItem("DraconicEvolution", "awakenedCore", 1, 0),
            1600, 122880, 3);

        addFusionCraftingRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 12),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 6),
                GT_ModHandler.getModItem("DraconicEvolution", "awakenedCore", 4, 0),
                GT_ModHandler.getModItem("DraconicEvolution", "chaosShard", 1, 0),
                new ItemStack(EMTItems.itemEMTItems, 0, 19)},
            GT_ModHandler.getModItem("DraconicEvolution", "chaoticCore", 1, 0),
            3200, 500000, 4);


        // ENERGY CORES

        addFusionCraftingRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 8),
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 4),
                GT_ModHandler.getModItem("AdvancedSolarPanel", "asp_crafting_items", 4, 1),
                GT_ModHandler.getModItem("DraconicEvolution", "draconicCore", 1, 0),
                new ItemStack(EMTItems.itemEMTItems, 0, 17)},
            GT_ModHandler.getModItem("DraconicEvolution", "draconiumEnergyCore", 1, 0),
            1000, 65536, 2);

        addFusionCraftingRecipe(new ItemStack[]{
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 8),
                GT_ModHandler.getModItem("DraconicEvolution", "draconiumEnergyCore", 4, 0),
                GT_ModHandler.getModItem("AdvancedSolarPanel", "asp_crafting_items", 4, 4),
                GT_ModHandler.getModItem("DraconicEvolution", "wyvernCore", 1, 0),
                new ItemStack(EMTItems.itemEMTItems, 0, 18)},
            GT_ModHandler.getModItem("DraconicEvolution", "draconiumEnergyCore", 1, 1),
            2000, 122880, 3);


    }

}
