package emt.init;

import static thaumcraft.api.aspects.Aspect.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import emt.EMT;
import emt.util.EMTResearchItem;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class EMT_GTNH_Recipes_And_Researches implements Runnable {

    private static LinkedHashMap<String, IArcaneRecipe> arcaneRecipeLinkedHashMap = new LinkedHashMap<>();
    private static LinkedHashMap<String, InfusionRecipe> infusionRecipeLinkedHashMap = new LinkedHashMap<>();
    private static LinkedHashMap<String, CrucibleRecipe> cruciblRecipeLinkedHashMap = new LinkedHashMap<>();

    private static AspectList getPrimals(int amount) {
        return new AspectList().add(ORDER, amount).add(ENTROPY, amount).add(AIR, amount).add(EARTH, amount)
                .add(WATER, amount).add(FIRE, amount);
    }

    @Override
    public void run() {

        EMTResearches.addResearchTab();
        runArcaneRecipes();
        runResreaches();
    }

    private void runInfusionRecipes() {}

    private void runKettleRecipes() {}

    private void runArcaneRecipes() {
        arcaneRecipeLinkedHashMap.put(
                "DiamondChainsaw",
                new ShapedArcaneRecipe(
                        "DiamondChainsaw",
                        new ItemStack(EMTItems.diamondChainsaw),
                        getPrimals(35),
                        "sts",
                        "pmp",
                        "pbp",
                        's',
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Diamond, 1),
                        'p',
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1),
                        't',
                        GT_OreDictUnificator.get(OrePrefixes.toolHeadChainsaw, Materials.Thaumium, 1),
                        'b',
                        ItemList.Battery_RE_LV_Sodium.get(1),
                        'm',
                        ItemList.Electric_Motor_MV.get(1)));
        arcaneRecipeLinkedHashMap.put(
                "ThaumiumChainsaw",
                new ShapedArcaneRecipe(
                        "ThaumiumChainsaw",
                        new ItemStack(EMTItems.thaumiumChainsaw),
                        getPrimals(35),
                        "sps",
                        "pmp",
                        "ppp",
                        's',
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Thaumium, 1),
                        'p',
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Thaumium, 1),
                        'b',
                        ItemList.Battery_RE_LV_Sodium.get(1),
                        'm',
                        new ItemStack(EMTItems.diamondChainsaw)));

        for (IArcaneRecipe recipe : arcaneRecipeLinkedHashMap.values()) {
            ThaumcraftApi.getCraftingRecipes().add(recipe);
        }
    }

    private void runResreaches() {
        HashSet<ResearchItem> researches = new HashSet<>();
        researches.add(
                new EMTResearchItem(
                        "ElectricMagicTools",
                        0,
                        0,
                        0,
                        new ResourceLocation(EMT.TEXTURE_PATH, "textures/misc/emt.png")).setSpecial().setAutoUnlock()
                                .setPages(new ResearchPage("")));
        researches.add(
                new EMTResearchItem(
                        "DiamondChainsaw",
                        new AspectList().add(GREED, 12).add(FIRE, 9).add(METAL, 6).add(EARTH, 3),
                        0,
                        -2,
                        2,
                        new ItemStack(EMTItems.diamondChainsaw))
                                .setPages(new ResearchPage(arcaneRecipeLinkedHashMap.get("DiamondChainsaw"))).setRound()
                                .setParentsHidden("ElectricMagicTools"));
        // researches.add(new EMTResearchItem("DiamondChainsaw",new
        // AspectList().add(GREED,12).add(FIRE,9).add(METAL,6).add(EARTH,3),0,-2,2,new
        // ItemStack(EMTItems.diamondChainsaw)).setRound());
        researches.forEach(ResearchItem::registerResearchItem);
    }

    class ShapedArcaneRecipeIgnoresNBT extends ShapedArcaneRecipe {

        protected String research;
        protected ItemStack result;
        protected AspectList aspects;
        protected Object[] recipe;
        protected LinkedHashMap<Character, Object> translation = new LinkedHashMap<>();
        int[] rowcolm;

        public ShapedArcaneRecipeIgnoresNBT(String research, ItemStack result, AspectList aspects, Object... recipe) {
            super(research, result, aspects, recipe);
            this.research = research;
            this.result = result;
            this.aspects = aspects;
            this.recipe = recipe;
            int rows = 0;
            int colums = 0;
            if (!(recipe[0] instanceof String)) throw new UnsupportedOperationException(
                    "The Recipe must start with at least one String for the first row!");
            int i = 0;
            rowcolm = new int[3];
            do {
                rows++;
                colums = Math.max(colums, ((String) recipe[i]).length());
                rowcolm[i] = colums;
                i = rows;
            } while (recipe[i] instanceof String);
            if (rows > 3 || colums > 3)
                throw new UnsupportedOperationException("The Recipe must fit in a 3x3 crafting field!");
            rowcolm = Arrays.copyOf(rowcolm, rows);

            int k = 0;
            for (int j = i; j < recipe.length; j += 2) {
                k++;
                if (recipe[j] instanceof Character) translation.put((Character) recipe[j], recipe[j + 1]);
            }
        }

        @Override
        public boolean matches(IInventory inv, World world, EntityPlayer player) {
            if (this.research.length() > 0
                    && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research))
                return false;
            else {
                for (int y = 0; y < rowcolm.length; y++) {
                    String rowAsString = ((String) recipe[y]);
                    for (int x = 0; x < rowcolm[y]; x++) {
                        Object itemReplacement = translation.get(rowAsString.toCharArray()[x]);
                        ItemStack slot = ThaumcraftApiHelper.getStackInRowAndColumn(inv, x, y);
                        if (itemReplacement != null && slot == null) {
                            return false;
                        } else if (itemReplacement instanceof String) {
                            if (!GT_Utility.areStacksEqual(
                                    GT_OreDictUnificator.getFirstOre(itemReplacement, 1),
                                    GT_OreDictUnificator.get(slot),
                                    true))
                                if (!OreDictionary.getOres((String) itemReplacement).contains(slot)) return false;
                        } else if (itemReplacement instanceof ItemStack) {
                            if (!GT_Utility.isStackValid(itemReplacement)) {
                                return false;
                            }
                            if (!GT_Utility.areStacksEqual((ItemStack) itemReplacement, slot, true))
                                if (!GT_Utility.areUnificationsEqual((ItemStack) itemReplacement, slot, true))
                                    if (!(((ItemStack) itemReplacement).getItem().equals(slot.getItem())
                                            && ((ItemStack) itemReplacement).getItemDamage()
                                                    == OreDictionary.WILDCARD_VALUE))
                                        return false;
                        } else if (itemReplacement instanceof Collection) {
                            boolean hit = false;
                            for (Object o : (Collection) itemReplacement) {
                                if (GT_Utility.areStacksEqual((ItemStack) o, slot, true)
                                        || GT_Utility.areUnificationsEqual((ItemStack) o, slot, true)
                                        || (((ItemStack) o).getItem().equals(slot.getItem())
                                                && ((ItemStack) itemReplacement).getItemDamage()
                                                        == OreDictionary.WILDCARD_VALUE))
                                    hit = true;
                            }
                            if (!hit) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }

        @Override
        public ItemStack getCraftingResult(IInventory iInventory) {
            return result;
        }

        @Override
        public int getRecipeSize() {
            return recipe.length;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return result;
        }

        @Override
        public AspectList getAspects() {
            return aspects;
        }

        @Override
        public AspectList getAspects(IInventory iInventory) {
            return aspects;
        }

        @Override
        public String getResearch() {
            return research;
        }
    }
}
