package emt.gthandler.common.loader;

import static emt.command.CommandOutputs.mkbook;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import emt.tile.GT_MetaTileEntity_ResearchCompleter;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MetaTileEntityIDs;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import thaumcraft.common.config.ConfigItems;

public class EMT_GT_Loader {

    public static void run() {
        ItemList.ResearchCompleter.set(
                new GT_MetaTileEntity_ResearchCompleter(
                        MetaTileEntityIDs.ResearchCompleter.ID,
                        "Research Completer",
                        "Research Completer").getStackForm(1L));
    }

    public static void runlate() {
        GTValues.RA.stdBuilder()
                .itemInputs(
                        new ItemStack(Items.book),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),
                        new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE))
                .itemOutputs(mkbook()).duration(6 * SECONDS).eut(TierEU.RECIPE_MV / 2).addTo(assemblerRecipes);
    }
}
