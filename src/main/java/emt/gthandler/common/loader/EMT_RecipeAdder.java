package emt.gthandler.common.loader;

import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

import cpw.mods.fml.common.Loader;
import emt.init.EMTItems;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import java.util.HashSet;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class EMT_RecipeAdder {
    public static final GT_Recipe.GT_Recipe_Map sFusionCraftingRecipes = new GT_Recipe.GT_Recipe_Map(
            new HashSet<>(4),
            "emt.recipe.fusioncrafting",
            "Draconic Evolution Fusion Crafter",
            null,
            RES_PATH_GUI + "basicmachines/FusionCrafter",
            9,
            1,
            1,
            0,
            1,
            "Tier Casing: ",
            1,
            E,
            false,
            false);

    public static void addFusionCraftingRecipe(
            ItemStack[] inputs,
            FluidStack[] fluidinputs,
            ItemStack[] outputs,
            FluidStack[] fluidoutputs,
            int aDuration,
            int aEUt,
            int aTier) {
        sFusionCraftingRecipes.addRecipe(
                true, inputs, outputs, null, fluidinputs, fluidoutputs, aDuration, aEUt, aTier);
    }

    public static void addFusionCraftingRecipeNonOptimized(
            ItemStack[] inputs,
            FluidStack[] fluidinputs,
            ItemStack[] outputs,
            FluidStack[] fluidoutputs,
            int aDuration,
            int aEUt,
            int aTier) {
        sFusionCraftingRecipes.addRecipe(
                false, inputs, outputs, null, fluidinputs, fluidoutputs, aDuration, aEUt, aTier);
    }

    public static void addFusionCraftingRecipe(
            ItemStack[] inputs, ItemStack output, int aDuration, int aEUt, int aTier) {
        addFusionCraftingRecipe(inputs, null, new ItemStack[] {output}, null, aDuration, aEUt, aTier);
    }

    // Use this if you don't want your recipes quantity to be splitted
    public static void addFusionCraftingRecipeNonOptimized(
            ItemStack[] inputs,
            FluidStack fluidinput,
            ItemStack output,
            FluidStack fluidoutput,
            int aDuration,
            int aEUt,
            int aTier) {
        addFusionCraftingRecipeNonOptimized(
                inputs,
                new FluidStack[] {fluidinput},
                new ItemStack[] {output},
                new FluidStack[] {fluidoutput},
                aDuration,
                aEUt,
                aTier);
    }

    public static void Run() {

        // CORES

        addFusionCraftingRecipeNonOptimized(
                new ItemStack[] {
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 4),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Ichorium, 1),
                    ItemList.QuantumEye.get(1L),
                    new ItemStack(EMTItems.itemEMTItems, 0, 16)
                },
                Materials.Sunnarium.getMolten(1440),
                GT_ModHandler.getModItem("DraconicEvolution", "draconicCore", 1, 0),
                GT_Values.NF,
                400,
                500000,
                1);

        addFusionCraftingRecipeNonOptimized(
                new ItemStack[] {
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 8),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 4),
                    GT_ModHandler.getModItem("DraconicEvolution", "draconicCore", 4, 0),
                    ItemList.QuantumStar.get(1L),
                    new ItemStack(EMTItems.itemEMTItems, 0, 17),
                },
                Materials.Neutronium.getMolten(1440),
                GT_ModHandler.getModItem("DraconicEvolution", "wyvernCore", 1, 0),
                GT_Values.NF,
                800,
                2000000,
                2);
        if (Loader.isModLoaded("supersolarpanel")) {
            addFusionCraftingRecipeNonOptimized(
                    new ItemStack[] {
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 12),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 4),
                        GT_ModHandler.getModItem("DraconicEvolution", "wyvernCore", 4, 0),
                        GT_ModHandler.getModItem("supersolarpanel", "enderquantumcomponent", 1, 0),
                        new ItemStack(EMTItems.itemEMTItems, 0, 18)
                    },
                    Materials.Infinity.getMolten(1440),
                    GT_ModHandler.getModItem("DraconicEvolution", "awakenedCore", 1, 0),
                    GT_Values.NF,
                    1600,
                    8000000,
                    3);
        } else {
            addFusionCraftingRecipeNonOptimized(
                    new ItemStack[] {
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 12),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 4),
                        GT_ModHandler.getModItem("DraconicEvolution", "wyvernCore", 4, 0),
                        GT_ModHandler.getModItem("dreamcraft", "item.ManyullynCrystal", 1, 0),
                        new ItemStack(EMTItems.itemEMTItems, 0, 18)
                    },
                    Materials.Infinity.getMolten(1440),
                    GT_ModHandler.getModItem("DraconicEvolution", "awakenedCore", 1, 0),
                    GT_Values.NF,
                    1600,
                    8000000,
                    3);
        }

        addFusionCraftingRecipeNonOptimized(
                new ItemStack[] {
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 16),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 4),
                    GT_ModHandler.getModItem("DraconicEvolution", "awakenedCore", 4, 0),
                    GT_ModHandler.getModItem("DraconicEvolution", "chaosFragment", 2, 2),
                    new ItemStack(EMTItems.itemEMTItems, 0, 19)
                },
                Materials.SpaceTime.getMolten(1440),
                GT_ModHandler.getModItem("DraconicEvolution", "chaoticCore", 1, 0),
                GT_Values.NF,
                3200,
                24000000,
                4);

        // ENERGY CORES

        addFusionCraftingRecipe(
                new ItemStack[] {
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Draconium, 8),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StellarAlloy, 4),
                    GT_ModHandler.getModItem("AdvancedSolarPanel", "asp_crafting_items", 4, 1),
                    GT_ModHandler.getModItem("DraconicEvolution", "draconicCore", 1, 0),
                    new ItemStack(EMTItems.itemEMTItems, 0, 17)
                },
                GT_ModHandler.getModItem("DraconicEvolution", "draconiumEnergyCore", 1, 0),
                1000,
                500000,
                2);

        addFusionCraftingRecipe(
                new ItemStack[] {
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.DraconiumAwakened, 8),
                    GT_ModHandler.getModItem("DraconicEvolution", "draconiumEnergyCore", 4, 0),
                    GT_ModHandler.getModItem("AdvancedSolarPanel", "asp_crafting_items", 4, 4),
                    GT_ModHandler.getModItem("DraconicEvolution", "wyvernCore", 1, 0),
                    new ItemStack(EMTItems.itemEMTItems, 0, 18)
                },
                GT_ModHandler.getModItem("DraconicEvolution", "draconiumEnergyCore", 1, 1),
                2000,
                2000000,
                3);

        // Dragon Blood
        if (Loader.isModLoaded("miscutils")) {

            addFusionCraftingRecipeNonOptimized(
                    new ItemStack[] {
                        new ItemStack(Blocks.dragon_egg, 0),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DraconiumAwakened, 64)
                    },
                    Materials.Radon.getPlasma(144),
                    null,
                    new FluidStack(FluidRegistry.getFluid("molten.dragonblood"), 288),
                    4200,
                    1966080,
                    3);

            addFusionCraftingRecipeNonOptimized(
                    new ItemStack[] {
                        GT_ModHandler.getModItem("witchery", "infinityegg", 0),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DraconiumAwakened, 64)
                    },
                    Materials.Radon.getPlasma(72),
                    null,
                    new FluidStack(FluidRegistry.getFluid("molten.dragonblood"), 432),
                    3600,
                    1966080,
                    3);
        }
    }
}
