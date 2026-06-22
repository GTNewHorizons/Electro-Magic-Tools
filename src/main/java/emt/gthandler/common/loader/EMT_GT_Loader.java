package emt.gthandler.common.loader;

import static emt.command.CommandOutputs.mkbook;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static thaumcraft.api.ThaumcraftApi.registerObjectTag;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
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
    }
}
