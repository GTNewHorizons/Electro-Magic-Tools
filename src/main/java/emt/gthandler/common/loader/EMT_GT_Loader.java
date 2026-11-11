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

import com.ruling_0.materiallib.api.Material;
import com.ruling_0.materiallib.api.MaterialLibAPI;

import emt.init.EMTItems;
import emt.init.EMTRecipes;
import emt.util.EMTCraftingAspects;
import gregtech.api.enums.Circuits;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.StoneType;
import gregtech.api.enums.TierEU;
import gregtech.api.enums.materials.OreShapes;
import gregtech.api.enums.materials.Shapes;
import ic2.api.item.IC2Items;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;

public class EMT_GT_Loader {

    public static ItemStack stoneOre(Material material, int amount) {
        return MaterialLibAPI.getStack(material, OreShapes.ore, OreShapes.variantOf(StoneType.Stone.name()), amount);
    }

    public static ItemStack crushedOre(Material material, int amount) {
        return MaterialLibAPI.getStack(material, Shapes.crushed, amount);
    }

    public static ItemStack plate(Material material, int amount) {
        return MaterialLibAPI.getStack(material, Shapes.plate, amount);
    }

    public static void runlate() {
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.book),
                        Circuits.LV.get(1),
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
