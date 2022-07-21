package emt.nei;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_Recipe;
import gregtech.nei.GT_NEI_DefaultHandler;

import static emt.nei.NEI_Config.isAdded;

public class FusionCraftingHandler extends GT_NEI_DefaultHandler {
    // TODO: BETTER GUI <3
    public FusionCraftingHandler(GT_Recipe.GT_Recipe_Map aRecipeMap) {
        super(aRecipeMap);
        if (!isAdded) {
            FMLInterModComms.sendRuntimeMessage(
                GT_Values.GT,
                "NEIPlugins",
                "register-crafting-handler",
                "emt@" + this.getRecipeName() + "@" + this.getOverlayIdentifier());
            GuiCraftingRecipe.craftinghandlers.add(this);
            GuiUsageRecipe.usagehandlers.add(this);
        }
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return new FusionCraftingHandler(this.mRecipeMap);
    }
}
