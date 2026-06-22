package emt.gthandler.common.loader;

import static emt.command.CommandOutputs.mkbook;
import static emt.util.EMTRandomHelper.getChargedItem;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static thaumcraft.api.ThaumcraftApi.addInfusionCraftingRecipe;
import static thaumcraft.api.ThaumcraftApi.registerObjectTag;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import emt.init.EMTItems;
import emt.init.EMTRecipes;
import emt.util.EMTCraftingAspects;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import ic2.api.item.IC2Items;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;

public class EMT_GT_Loader {

    public static void runlate() {
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.book),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                        new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE))
                .itemOutputs(mkbook()).duration(6 * SECONDS).eut(TierEU.RECIPE_MV / 2).addTo(assemblerRecipes);

        registerObjectTag(ItemList.ReinforcedGlass.get(1L), new AspectList().add(Aspect.METAL, 2).add(Aspect.COLD, 2));
        registerObjectTag(
                ItemList.FenceIron.get(1L),
                new AspectList().add(Aspect.ENERGY, 1).add(Aspect.METAL, 2).add(Aspect.WEATHER, 1));
        registerObjectTag(ItemList.PadBouncy.get(1L), new AspectList().add(Aspect.AIR, 5));
        registerObjectTag(ItemList.PadSticky.get(1L), new AspectList().add(Aspect.TRAP, 5).add(Aspect.SLIME, 2));

        EMTRecipes.shieldFocus = addInfusionCraftingRecipe(
                "Shield Focus",
                new ItemStack(EMTItems.shieldFocus),
                4,
                EMTCraftingAspects.shieldFocusCrafting,
                new ItemStack(ConfigItems.itemFocusPortableHole, 1),
                new ItemStack[] { ItemList.Block_ReinforcedConcrete.get(1L), ItemList.ReinforcedGlass.get(1L),
                        ItemList.Block_ReinforcedConcrete.get(1L), ItemList.ReinforcedGlass.get(1L),
                        new ItemStack(Blocks.soul_sand), new ItemStack(Blocks.obsidian),
                        new ItemStack(Blocks.obsidian) });

        EMTRecipes.rockbreakerDrill = addInfusionCraftingRecipe(
                "Drill of the Rockbreaker",
                getChargedItem(EMTItems.rockbreakerDrill, 10),
                6,
                EMTCraftingAspects.rockbreakerDrillCrafting,
                new ItemStack(EMTItems.thaumiumDrill, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.flint_and_steel), new ItemStack(Items.fire_charge),
                        new ItemStack(ConfigItems.itemPickElemental), new ItemStack(ConfigItems.itemShovelElemental),
                        new ItemStack(IC2Items.getItem("lapotronCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        ItemList.Block_ReinforcedConcrete.get(1L), IC2Items.getItem("iridiumPlate"),
                        IC2Items.getItem("overclockerUpgrade") });
    }
}
