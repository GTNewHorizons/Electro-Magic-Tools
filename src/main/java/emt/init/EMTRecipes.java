package emt.init;

import static emt.util.EMTRandomHelper.getChargedItem;
import static thaumcraft.api.ThaumcraftApi.*;

import java.util.Iterator;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;
import emt.EMT;
import emt.util.EMTConfigHandler;
import emt.util.EMTCraftingAspects;
import emt.util.EMTResearchAspects;
import ic2.api.item.IC2Items;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import ic2.core.BasicMachineRecipeManager;
import ic2.core.Ic2Items;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.common.blocks.ItemJarNode;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class EMTRecipes {

    public static ItemStack uuMCell = new ItemStack(EMTItems.itemEMTItems, 1, 15);
    public static IRecipe ironOmnitool;
    public static IRecipe diamondChainsaw;
    public static IRecipe ductTape;
    public static IRecipe rubberBall;
    public static IRecipe featherMesh;
    public static IRecipe cardboard;
    public static IRecipe featherWing;
    public static IRecipe featherWings;
    public static IRecipe taintedFeathers;
    public static InfusionRecipe uraniumToIridium;
    public static InfusionRecipe diamondToUranium;
    public static InfusionRecipe goldToDiamond;
    public static InfusionRecipe ironToGold;
    public static InfusionRecipe stoneBricksToIronOre;
    public static InfusionRecipe arcaneStoneToCopperOre;
    public static InfusionRecipe arcaneStoneBricksToTinOre;
    public static InfusionRecipe amberBlockToLeadOre;
    public static InfusionRecipe amberBricksToUraniumOre;
    public static InfusionRecipe charcoalToCoal;
    public static InfusionRecipe shardToResin;
    public static InfusionRecipe shardToRedstone;
    public static InfusionRecipe shardToLapis;
    public static InfusionRecipe glowstoneDustToBlock;
    public static InfusionRecipe infusedQuantumArmor;
    public static InfusionRecipe thaumiumDrill;
    public static InfusionRecipe thaumiumChainsaw;
    public static InfusionRecipe thaumicQuantumHelmet;
    public static InfusionRecipe thaumiumOmnitool;
    public static InfusionRecipe thaumicNanoHelmet;
    public static InfusionRecipe explosionFocus;
    public static InfusionRecipe shieldFocus;
    public static InfusionRecipe potentiaGenerator;
    public static InfusionRecipe streamChainsaw;
    public static InfusionRecipe rockbreakerDrill;
    public static InfusionRecipe thorHammer;
    public static InfusionRecipe superchargedThorHammer;
    public static InfusionRecipe wandRecharger;
    public static InfusionRecipe solarHelmetRevealing;
    public static InfusionRecipe electricBootsTravel;
    public static InfusionRecipe nanoBootsTravel;
    public static InfusionRecipe quantumBootsTravel;
    public static InfusionRecipe diamondOmnitoolToThaumium;
    public static InfusionRecipe etheralProcessor;
    public static InfusionRecipe tripleCompressedSolar;
    public static InfusionRecipe electricHoeGrowth;
    public static InfusionRecipe chargeFocus;
    public static InfusionRecipe wandChargeFocus;
    public static InfusionRecipe energyBallFocus;
    public static InfusionRecipe inventoryChargingRing;
    public static InfusionRecipe armorChargingRing;
    public static InfusionRecipe nanoWings;
    public static InfusionRecipe quantumWings;
    public static InfusionRecipe lucrumGenerator;
    public static ShapelessArcaneRecipe diamondOmnitool;
    public static ShapelessArcaneRecipe tinyUranium;
    public static ShapedArcaneRecipe christmasFocus;
    public static ShapedArcaneRecipe electricGoggles;
    public static ShapedArcaneRecipe electricGoggles2;
    public static ShapedArcaneRecipe shieldBlock;
    public static ShapedArcaneRecipe compressedSolar;
    public static ShapedArcaneRecipe doubleCompressedSolar;
    public static ShapedArcaneRecipe ironOmnitoolToDiamond;
    public static ShapedArcaneRecipe electricScribingTools;
    public static ShapedArcaneRecipe thaumiumWing;
    public static ShapedArcaneRecipe thaumiumWings;
    public static ShapedArcaneRecipe researchCompleter;
    public static ShapedArcaneRecipe magicalMachineCasing;
    public static CrucibleRecipe ignisGenerator;
    public static CrucibleRecipe auramGenerator;
    public static CrucibleRecipe arborGenerator;
    public static CrucibleRecipe aerGenerator;
    public static CrucibleRecipe waterSolar;
    public static CrucibleRecipe doubleWaterSolar;
    public static CrucibleRecipe tripleWaterSolar;
    public static CrucibleRecipe darkSolar;
    public static CrucibleRecipe doubleDarkSolar;
    public static CrucibleRecipe tripleDarkSolar;
    public static CrucibleRecipe orderSolar;
    public static CrucibleRecipe doubleOrderSolar;
    public static CrucibleRecipe tripleOrderSolar;
    public static CrucibleRecipe fireSolar;
    public static CrucibleRecipe doubleFireSolar;
    public static CrucibleRecipe tripleFireSolar;
    public static CrucibleRecipe airSolar;
    public static CrucibleRecipe doubleAirSolar;
    public static CrucibleRecipe tripleAirSolar;
    public static CrucibleRecipe earthSolar;
    public static CrucibleRecipe doubleEarthSolar;
    public static CrucibleRecipe tripleEarthSolar;
    public static CrucibleRecipe portableNode;
    public static CrucibleRecipe uuMCrystal;
    public static IRecipe thaumiumPlate;

    private static void registerShapedRecipes() {

        /** Crafting Recipes **/
        ironOmnitool = GameRegistry.addShapedRecipe(
                getChargedItem(EMTItems.ironOmnitool, 10),
                "X",
                "Z",
                "Y",
                'X',
                new ItemStack(IC2Items.getItem("chainsaw").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                'Y',
                new ItemStack(IC2Items.getItem("miningDrill").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                'Z',
                IC2Items.getItem("plateiron"));
        diamondChainsaw = GameRegistry.addShapedRecipe(
                getChargedItem(EMTItems.diamondChainsaw, 10),
                " X ",
                "XYX",
                'X',
                new ItemStack(Items.diamond),
                'Y',
                new ItemStack(IC2Items.getItem("chainsaw").getItem(), 1, OreDictionary.WILDCARD_VALUE));
        ductTape = GameRegistry.addShapedRecipe(
                new ItemStack(EMTItems.itemEMTItems, 1, 9),
                "XXX",
                "YYY",
                "XXX",
                'X',
                new ItemStack(EMTItems.itemEMTItems, 1, 8),
                'Y',
                new ItemStack(Items.paper));
        featherMesh = GameRegistry.addShapedRecipe(
                new ItemStack(EMTItems.itemEMTItems, 1, 7),
                "XYX",
                "YXY",
                "XYX",
                'X',
                new ItemStack(Items.feather),
                'Y',
                new ItemStack(EMTItems.itemEMTItems, 1, 9));
        featherWing = GameRegistry.addShapedRecipe(
                new ItemStack(EMTItems.itemEMTItems, 1, 12),
                "XYZ",
                "XYZ",
                "XYZ",
                'X',
                new ItemStack(EMTItems.itemEMTItems, 1, 7),
                'Y',
                new ItemStack(EMTItems.itemEMTItems, 1, 8),
                'Z',
                new ItemStack(EMTItems.itemEMTItems, 1, 11));
        featherWings = GameRegistry.addShapedRecipe(
                new ItemStack(EMTItems.featherWing),
                "XX",
                'X',
                new ItemStack(EMTItems.itemEMTItems, 1, 12));
        taintedFeathers = GameRegistry.addShapedRecipe(
                new ItemStack(EMTItems.itemEMTItems, 1, 13),
                " X ",
                "XYX",
                " X ",
                'X',
                new ItemStack(ConfigItems.itemResource, 1, 12),
                'Y',
                new ItemStack(Items.feather));

        GameRegistry.addRecipe(
                IC2Items.getItem("nanoHelmet"),
                "XYX",
                "XZX",
                'X',
                IC2Items.getItem("carbonPlate"),
                'Y',
                new ItemStack(IC2Items.getItem("energyCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                'Z',
                new ItemStack(IC2Items.getItem("nightvisionGoggles").getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapedRecipe(
                IC2Items.getItem("generator"),
                "X",
                "Y",
                "Z",
                'X',
                new ItemStack(IC2Items.getItem("chargedReBattery").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                'Y',
                IC2Items.getItem("machine"),
                'Z',
                new ItemStack(Blocks.furnace));
        GameRegistry.addShapedRecipe(
                IC2Items.getItem("generator"),
                "X",
                "Y",
                "Z",
                'X',
                IC2Items.getItem("reBattery"),
                'Y',
                IC2Items.getItem("machine"),
                'Z',
                new ItemStack(Blocks.furnace));

        /** OreDict Crafting Recipes **/
        for (int i = 0; i < OreDictionary.getOres("logWood").size(); i++) {
            cardboard = GameRegistry.addShapedRecipe(
                    new ItemStack(EMTItems.itemEMTItems, 1, 11),
                    "XXX",
                    "YYY",
                    "XXX",
                    'X',
                    new ItemStack(Items.paper),
                    'Y',
                    OreDictionary.getOres("logWood").get(i));
        }
        for (int i = 0; i < OreDictionary.getOres("itemRubber").size(); i++) {
            rubberBall = GameRegistry.addShapedRecipe(
                    new ItemStack(EMTItems.itemEMTItems, 1, 10),
                    "XX",
                    "XX",
                    'X',
                    OreDictionary.getOres("itemRubber").get(i));
        }

        /** Smelting Recipes **/
        GameRegistry.addSmelting(
                new ItemStack(EMTItems.itemEMTItems, 1, 1),
                new ItemStack(ConfigItems.itemResource, 1, 6),
                0.0F);
        GameRegistry.addSmelting(
                new ItemStack(EMTItems.itemEMTItems, 1, 2),
                new ItemStack(ConfigItems.itemResource, 1, 6),
                0.0F);
        GameRegistry.addSmelting(
                new ItemStack(EMTItems.itemEMTItems, 1, 3),
                new ItemStack(ConfigItems.itemResource, 1, 3),
                0.0F);
        GameRegistry.addSmelting(
                new ItemStack(EMTItems.itemEMTItems, 1, 4),
                new ItemStack(ConfigItems.itemResource, 1, 3),
                0.0F);
        GameRegistry.addSmelting(
                new ItemStack(EMTItems.itemEMTItems, 1, 10),
                new ItemStack(EMTItems.itemEMTItems, 2, 8),
                0.0F);
    }

    private static void registerShaplessRecipes() {}

    private static void registerSmeltingRecipes() {}

    public static void registerEarlyRecipes() {
        registerShapedRecipes();
        registerShaplessRecipes();
        registerSmeltingRecipes();
    }

    public static void registerUUMInfusionRecipes() {

        ItemStack iridium = IC2Items.getItem("iridiumOre").copy();
        iridium.stackSize = 2;
        uraniumToIridium = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                iridium,
                4,
                EMTCraftingAspects.uuMatterInfusions,
                IC2Items.getItem("Uran238"),
                new ItemStack[] { uuMCell, uuMCell, uuMCell, uuMCell, uuMCell, uuMCell, uuMCell, uuMCell, uuMCell,
                        uuMCell, uuMCell, uuMCell });

        ItemStack uranium = IC2Items.getItem("Uran238").copy();
        uranium.stackSize = 2;
        diamondToUranium = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                uranium,
                4,
                EMTCraftingAspects.lesserUUMInfusions,
                new ItemStack(Items.diamond),
                new ItemStack[] { uuMCell, uuMCell, uuMCell, uuMCell, uuMCell, uuMCell, uuMCell, uuMCell });

        ItemStack diamond = new ItemStack(Items.diamond).copy();
        diamond.stackSize = 1;
        goldToDiamond = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                diamond,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(Items.gold_ingot),
                new ItemStack[] { uuMCell, uuMCell, uuMCell, uuMCell });

        ItemStack gold = new ItemStack(Items.gold_ingot).copy();
        gold.stackSize = 2;
        ironToGold = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                gold,
                4,
                EMTCraftingAspects.cheapestUUMInfusions,
                new ItemStack(Items.iron_ingot),
                new ItemStack[] { uuMCell, uuMCell, uuMCell });

        ItemStack ironOre = new ItemStack(Blocks.iron_ore).copy();
        ironOre.stackSize = 16;
        stoneBricksToIronOre = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                ironOre,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(Blocks.stonebrick),
                new ItemStack[] { uuMCell, uuMCell });

        /**
         * ItemStack copperOre = Items.getItem("copperOre").copy(); BUGGED IN 1.6 IF IC2 OREGEN IS DISABLED
         **/
        ItemStack copperOre = IC2Items.getItem("crushedCopperOre").copy();
        copperOre.stackSize = 32;
        arcaneStoneToCopperOre = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                copperOre,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
                new ItemStack[] { uuMCell, uuMCell });

        /**
         * ItemStack tinOre = Items.getItem("tinOre").copy(); BUGGED IN 1.6 IF IC2 OREGEN IS DISABLED
         **/
        ItemStack tinOre = IC2Items.getItem("crushedTinOre").copy();
        tinOre.stackSize = 32;
        arcaneStoneBricksToTinOre = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                tinOre,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7),
                new ItemStack[] { uuMCell, uuMCell });

        /**
         * ItemStack leadOre = Items.getItem("leadOre").copy(); BUGGED IN 1.6 IF IC2 OREGEN IS DISABLED
         **/
        ItemStack leadOre = IC2Items.getItem("crushedLeadOre").copy();
        leadOre.stackSize = 32;
        amberBlockToLeadOre = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                leadOre,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 0),
                new ItemStack[] { uuMCell, uuMCell });

        ItemStack uraniumOre = IC2Items.getItem("uraniumOre").copy();
        uraniumOre.stackSize = 8;
        amberBricksToUraniumOre = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                uraniumOre,
                4,
                EMTCraftingAspects.lesserUUMInfusions,
                new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 1),
                new ItemStack[] { uuMCell, uuMCell, uuMCell });

        ItemStack coal = new ItemStack(Items.coal, 1, 0).copy();
        coal.stackSize = 16;
        charcoalToCoal = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                coal,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(Items.coal, 1, 1),
                new ItemStack[] { uuMCell, uuMCell });

        ItemStack stickyResin = IC2Items.getItem("resin").copy();
        stickyResin.stackSize = 21;
        shardToResin = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                stickyResin,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { uuMCell, uuMCell, uuMCell });

        ItemStack redstone = new ItemStack(Items.redstone).copy();
        redstone.stackSize = 24;
        shardToRedstone = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                redstone,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { uuMCell, uuMCell, uuMCell });

        ItemStack lapisLazuli = new ItemStack(Items.dye, 1, 4).copy();
        lapisLazuli.stackSize = 8;
        shardToLapis = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                lapisLazuli,
                4,
                EMTCraftingAspects.cheapUUMInfusions,
                new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { uuMCell, uuMCell, uuMCell });

        ItemStack glowstone = new ItemStack(Blocks.glowstone).copy();
        glowstone.stackSize = 1;
        glowstoneDustToBlock = addInfusionCraftingRecipe(
                "UU-Matter Infusion",
                glowstone,
                4,
                EMTCraftingAspects.cheapestUUMInfusions,
                new ItemStack(Items.glowstone_dust),
                new ItemStack[] { uuMCell });
    }

    public static void registerLateRecipes() {
        /** Infusion Recipes **/
        lucrumGenerator = addInfusionCraftingRecipe(
                "Lucrum Generator",
                new ItemStack(EMTBlocks.essentiaGens, 1, 5),
                10,
                new AspectList().add(Aspect.GREED, 64).add(Aspect.EXCHANGE, 128),
                new ItemStack(EMTBlocks.essentiaGens, 1, 0),
                new ItemStack[] { new ItemStack(Blocks.gold_block), new ItemStack(Blocks.gold_block),
                        new ItemStack(Blocks.gold_block), new ItemStack(Blocks.gold_block),
                        new ItemStack(Blocks.gold_block), new ItemStack(Blocks.gold_block) });

        thaumiumDrill = addInfusionCraftingRecipe(
                "Thaumium Drill",
                getChargedItem(EMTItems.thaumiumDrill, 10),
                5,
                EMTCraftingAspects.thaumiumDrillCrafting,
                new ItemStack(IC2Items.getItem("diamondDrill").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.diamond), new ItemStack(Items.diamond),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), new ItemStack(EMTItems.itemEMTItems, 1, 5),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), IC2Items.getItem("denseplateiron") });

        thaumiumChainsaw = addInfusionCraftingRecipe(
                "Thaumium Chainsaw",
                getChargedItem(EMTItems.thaumiumChainsaw, 10),
                5,
                EMTCraftingAspects.thaumiumChainsawCrafting,
                new ItemStack(EMTItems.diamondChainsaw, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.diamond), new ItemStack(Items.diamond),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), new ItemStack(EMTItems.itemEMTItems, 1, 5),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), IC2Items.getItem("denseplateiron") });

        thaumicQuantumHelmet = addInfusionCraftingRecipe(
                "Quantum Goggles of Revealing",
                getChargedItem(EMTItems.quantumThaumicHelmet, 10),
                6,
                EMTCraftingAspects.thaumicQuantumHelmetCrafting,
                new ItemStack(EMTItems.nanoThaumicHelmet, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.diamond), new ItemStack(Items.milk_bucket),
                        new ItemStack(IC2Items.getItem("quantumHelmet").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), new ItemStack(EMTItems.itemEMTItems, 1, 5),
                        IC2Items.getItem("iridiumPlate"), IC2Items.getItem("advancedCircuit") });

        thaumicNanoHelmet = addInfusionCraftingRecipe(
                "Nanosuit Goggles of Revealing",
                getChargedItem(EMTItems.nanoThaumicHelmet, 10),
                5,
                EMTCraftingAspects.thaumicNanoHelmetCrafting,
                new ItemStack(EMTItems.electricGoggles, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.diamond), new ItemStack(Items.gold_ingot),
                        new ItemStack(IC2Items.getItem("nanoHelmet").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), new ItemStack(EMTItems.itemEMTItems, 1, 5),
                        IC2Items.getItem("carbonPlate"), IC2Items.getItem("electronicCircuit") });

        thaumiumOmnitool = addInfusionCraftingRecipe(
                "Thaumium Omnitool",
                getChargedItem(EMTItems.thaumiumOmnitool, 10),
                6,
                EMTCraftingAspects.thaumiumOmnitoolCrafting,
                new ItemStack(EMTItems.thaumiumChainsaw, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(EMTItems.thaumiumDrill, 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), new ItemStack(EMTItems.itemEMTItems, 1, 5),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), IC2Items.getItem("carbonPlate"),
                        IC2Items.getItem("plateobsidian") });

        diamondOmnitoolToThaumium = addInfusionCraftingRecipe(
                "Thaumium Omnitool",
                new ItemStack(EMTItems.thaumiumOmnitool),
                6,
                EMTCraftingAspects.thaumiumOmnitoolCrafting,
                new ItemStack(EMTItems.diamondOmnitool, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Blocks.diamond_block),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 9),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 9),
                        new ItemStack(IC2Items.getItem("energyCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("carbonPlate"), IC2Items.getItem("advancedCircuit") });

        explosionFocus = addInfusionCraftingRecipe(
                "Explosion Focus",
                new ItemStack(EMTItems.explosionFocus),
                6,
                EMTCraftingAspects.laserFocusCrafting,
                new ItemStack(ConfigItems.itemFocusHellbat, 1),
                new ItemStack[] {
                        new ItemStack(IC2Items.getItem("miningLaser").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(Items.arrow), new ItemStack(Items.gunpowder),
                        new ItemStack(Items.firework_charge), new ItemStack(ConfigItems.itemResource, 1, 1) });

        shieldFocus = addInfusionCraftingRecipe(
                "Shield Focus",
                new ItemStack(EMTItems.shieldFocus),
                4,
                EMTCraftingAspects.shieldFocusCrafting,
                new ItemStack(ConfigItems.itemFocusPortableHole, 1),
                new ItemStack[] { IC2Items.getItem("reinforcedStone"), IC2Items.getItem("reinforcedGlass"),
                        IC2Items.getItem("reinforcedStone"), IC2Items.getItem("reinforcedGlass"),
                        new ItemStack(Blocks.soul_sand), new ItemStack(Blocks.obsidian),
                        new ItemStack(Blocks.obsidian) });

        potentiaGenerator = addInfusionCraftingRecipe(
                "Potentia Generator",
                new ItemStack(EMTBlocks.essentiaGens, 1, 0),
                6,
                EMTCraftingAspects.potentiaGeneratorCrafting,
                IC2Items.getItem("semifluidGenerator"),
                new ItemStack[] { new ItemStack(Items.diamond), new ItemStack(ConfigItems.itemFocusTrade),
                        new ItemStack(Blocks.hopper), new ItemStack(ConfigBlocks.blockJar),
                        IC2Items.getItem("mvTransformer"), IC2Items.getItem("advancedMachine"),
                        IC2Items.getItem("orewashingplant"), IC2Items.getItem("scrap") });

        streamChainsaw = addInfusionCraftingRecipe(
                "Chainsaw of the Stream",
                getChargedItem(EMTItems.streamChainsaw, 10),
                6,
                EMTCraftingAspects.streamChaisnawCrafting,
                new ItemStack(EMTItems.thaumiumChainsaw, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.water_bucket), new ItemStack(ConfigItems.itemAxeElemental),
                        new ItemStack(ConfigBlocks.blockMagicalLog),
                        new ItemStack(IC2Items.getItem("lapotronCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("iridiumPlate"), IC2Items.getItem("overclockerUpgrade") });

        rockbreakerDrill = addInfusionCraftingRecipe(
                "Drill of the Rockbreaker",
                getChargedItem(EMTItems.rockbreakerDrill, 10),
                6,
                EMTCraftingAspects.rockbreakerDrillCrafting,
                new ItemStack(EMTItems.thaumiumDrill, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.flint_and_steel), new ItemStack(Items.fire_charge),
                        new ItemStack(ConfigItems.itemPickElemental), new ItemStack(ConfigItems.itemShovelElemental),
                        new ItemStack(IC2Items.getItem("lapotronCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("reinforcedStone"), IC2Items.getItem("iridiumPlate"),
                        IC2Items.getItem("overclockerUpgrade") });

        thorHammer = addInfusionCraftingRecipe(
                "Mjolnir",
                new ItemStack(EMTItems.thorHammer),
                7,
                EMTCraftingAspects.thorHammerCrafting,
                new ItemStack(EMTItems.taintedThorHammer, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(EMTItems.itemEMTItems, 1, 6),
                        new ItemStack(EMTItems.itemEMTItems, 1, 6), new ItemStack(ConfigItems.itemSwordElemental),
                        new ItemStack(ConfigItems.itemResource, 1, 1), new ItemStack(ConfigItems.itemFocusShock),
                        IC2Items.getItem("rubber") });

        superchargedThorHammer = addInfusionCraftingRecipe(
                "Supercharged Mjolnir",
                getChargedItem(EMTItems.electricThorHammer, 10),
                10,
                EMTCraftingAspects.superchargedThorHammerCrafting,
                new ItemStack(EMTItems.thorHammer, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(EMTItems.itemEMTItems, 1, 6),
                        new ItemStack(EMTItems.itemEMTItems, 1, 6), new ItemStack(Blocks.web),
                        new ItemStack(ConfigItems.itemFocusHellbat), new ItemStack(ConfigItems.itemSwordElemental),
                        new ItemStack(IC2Items.getItem("lapotronCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("iridiumPlate"), IC2Items.getItem("iridiumPlate"),
                        new ItemStack(IC2Items.getItem("nanoSaber").getItem(), 1, OreDictionary.WILDCARD_VALUE) });

        wandRecharger = addInfusionCraftingRecipe(
                "Industrial Wand Charging Station",
                new ItemStack(EMTBlocks.emtMachines, 1, 0),
                6,
                EMTCraftingAspects.wandCharger,
                new ItemStack(ConfigBlocks.blockStoneDevice, 1, 5),
                new ItemStack[] { IC2Items.getItem("replicator"), IC2Items.getItem("iridiumPlate"),
                        new ItemStack(Blocks.diamond_block), new ItemStack(ConfigItems.itemShard, 1, 5),
                        new ItemStack(ConfigBlocks.blockJar) });

        solarHelmetRevealing = addInfusionCraftingRecipe(
                "Solar Helmet of Revealing",
                getChargedItem(EMTItems.solarHelmetRevealing, 10),
                5,
                EMTCraftingAspects.solarHelmetRevealing,
                new ItemStack(EMTItems.quantumThaumicHelmet, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(EMTBlocks.solars[0], 1, 1), IC2Items.getItem("glassFiberCableItem"),
                        new ItemStack(IC2Items.getItem("lapotronCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(ConfigItems.itemShard, 1, 4) });

        electricBootsTravel = addInfusionCraftingRecipe(
                "Electric Boots of the Traveller",
                getChargedItem(EMTItems.electricBootsTraveller, 10),
                2,
                EMTCraftingAspects.electricBootsTravel,
                new ItemStack(ConfigItems.itemBootsTraveller),
                new ItemStack[] { new ItemStack(Items.diamond), IC2Items.getItem("elemotor"), IC2Items.getItem("coil"),
                        IC2Items.getItem("hazmatBoots"),
                        new ItemStack(IC2Items.getItem("advBattery").getItem(), 1, OreDictionary.WILDCARD_VALUE) });

        nanoBootsTravel = addInfusionCraftingRecipe(
                "Nano Boots of the Traveller",
                getChargedItem(EMTItems.nanoBootsTraveller, 10),
                2,
                EMTCraftingAspects.nanoBootsTravel,
                new ItemStack(EMTItems.electricBootsTraveller, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.diamond),
                        new ItemStack(IC2Items.getItem("nanoBoots").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(IC2Items.getItem("energyCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE) });

        quantumBootsTravel = addInfusionCraftingRecipe(
                "Quantum Boots of the Traveller",
                getChargedItem(EMTItems.quantumBootsTraveller, 10),
                2,
                EMTCraftingAspects.quantumBootsTravel,
                new ItemStack(EMTItems.nanoBootsTraveller, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Blocks.diamond_block),
                        new ItemStack(IC2Items.getItem("quantumBoots").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(IC2Items.getItem("lapotronCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("iridiumPlate") });

        etheralProcessor = addInfusionCraftingRecipe(
                "Etheral Processor",
                new ItemStack(EMTBlocks.emtMachines, 1, 1),
                3,
                EMTCraftingAspects.etherealProcessor,
                IC2Items.getItem("macerator"),
                new ItemStack[] { IC2Items.getItem("electroFurnace"),
                        new ItemStack(ConfigBlocks.blockMetalDevice, 1, 9), new ItemStack(Blocks.end_stone),
                        new ItemStack(Blocks.end_stone), new ItemStack(Blocks.iron_block),
                        new ItemStack(Blocks.iron_block), new ItemStack(EMTItems.itemEMTItems, 1, 5),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5) });

        tripleCompressedSolar = addInfusionCraftingRecipe(
                "Compressed Solars",
                new ItemStack(EMTBlocks.solars[0], 1, 2),
                2,
                EMTCraftingAspects.compressedSolars,
                new ItemStack(EMTBlocks.solars[0], 1, 1),
                new ItemStack[] { new ItemStack(EMTBlocks.solars[0], 1, 1), new ItemStack(EMTBlocks.solars[0], 1, 1),
                        new ItemStack(EMTBlocks.solars[0], 1, 1), new ItemStack(EMTBlocks.solars[0], 1, 1),
                        new ItemStack(EMTBlocks.solars[0], 1, 1), new ItemStack(EMTBlocks.solars[0], 1, 1),
                        new ItemStack(EMTBlocks.solars[0], 1, 1), new ItemStack(EMTBlocks.solars[0], 1, 1) });

        electricHoeGrowth = addInfusionCraftingRecipe(
                "Electric Hoe of Growth",
                getChargedItem(EMTItems.electricHoeGrowth, 10),
                4,
                EMTCraftingAspects.electricHoeGrowth,
                new ItemStack(ConfigItems.itemHoeElemental),
                new ItemStack[] {
                        new ItemStack(IC2Items.getItem("lapotronCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(IC2Items.getItem("electricHoe").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(Items.dye, 1, 15), IC2Items.getItem("elemotor"), IC2Items.getItem("coil"),
                        new ItemStack(Blocks.sapling, 1, OreDictionary.WILDCARD_VALUE) });

        chargeFocus = addInfusionCraftingRecipe(
                "Wand Focus: Charging",
                new ItemStack(EMTItems.chargeFocus),
                4,
                EMTCraftingAspects.chargeFocus,
                new ItemStack(IC2Items.getItem("advBattery").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { IC2Items.getItem("generator"), IC2Items.getItem("batBox"),
                        new ItemStack(EMTItems.itemEMTItems, 1, 5), new ItemStack(EMTItems.itemEMTItems, 1, 5),
                        new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE) });

        wandChargeFocus = addInfusionCraftingRecipe(
                "Wand Focus: Wand Charging",
                new ItemStack(EMTItems.wandChargeFocus),
                5,
                EMTCraftingAspects.wandChargeFocus,
                new ItemStack(EMTBlocks.emtMachines, 1, 0),
                new ItemStack[] { new ItemStack(EMTItems.chargeFocus),
                        new ItemStack(IC2Items.getItem("energyPack").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("iridiumPlate"), IC2Items.getItem("iridiumPlate") });

        energyBallFocus = addInfusionCraftingRecipe(
                "Wand Focus: Energy Ball",
                new ItemStack(EMTItems.energyBallFocus),
                4,
                EMTResearchAspects.energyBallFocusResearch,
                new ItemStack(ConfigItems.itemFocusShock),
                new ItemStack[] { new ItemStack(Blocks.tnt), new ItemStack(IC2Items.getItem("advBattery").getItem()),
                        new ItemStack(IC2Items.getItem("advBattery").getItem()),
                        new ItemStack(IC2Items.getItem("advBattery").getItem()) });

        inventoryChargingRing = addInfusionCraftingRecipe(
                "Inventory Charging Ring",
                new ItemStack(EMTItems.emtBauble, 1, 1),
                6,
                EMTCraftingAspects.inventoryChargingRing,
                new ItemStack(ConfigItems.itemBaubleBlanks, 1, 1),
                new ItemStack[] { new ItemStack(EMTItems.thaumiumDrill, 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("generator"), IC2Items.getItem("geothermalGenerator"),
                        IC2Items.getItem("solarPanel"), ic2.core.Ic2Items.WindKineticGenerator,
                        ic2.core.Ic2Items.WaterKineticGenerator, IC2Items.getItem("nuclearReactor") });

        armorChargingRing = addInfusionCraftingRecipe(
                "Armor Charging Ring",
                new ItemStack(EMTItems.emtBauble, 1, 0),
                6,
                EMTCraftingAspects.armorChargingRing,
                new ItemStack(ConfigItems.itemBaubleBlanks, 1, 1),
                new ItemStack[] { new ItemStack(EMTItems.electricBootsTraveller, 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("generator"), IC2Items.getItem("geothermalGenerator"),
                        IC2Items.getItem("solarPanel"), ic2.core.Ic2Items.WindKineticGenerator,
                        ic2.core.Ic2Items.WaterKineticGenerator, IC2Items.getItem("nuclearReactor") });

        nanoWings = addInfusionCraftingRecipe(
                "Nanosuit Wings",
                getChargedItem(EMTItems.nanoWing, 10),
                4,
                EMTCraftingAspects.nanoWing,
                new ItemStack(EMTItems.thaumiumWing),
                new ItemStack[] { IC2Items.getItem("carbonPlate"), IC2Items.getItem("carbonPlate"),
                        IC2Items.getItem("carbonPlate"),
                        new ItemStack(IC2Items.getItem("nanoBodyarmor").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(ConfigItems.itemResource, 1, 1) });

        quantumWings = addInfusionCraftingRecipe(
                "Quantum Wings",
                getChargedItem(EMTItems.quantumWing, 10),
                6,
                EMTCraftingAspects.quantumWing,
                new ItemStack(EMTItems.nanoWing, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { IC2Items.getItem("iridiumPlate"), IC2Items.getItem("iridiumPlate"),
                        IC2Items.getItem("iridiumPlate"),
                        new ItemStack(IC2Items.getItem("quantumBodyarmor").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(ConfigItems.itemResource, 1, 1) });

        infusedQuantumArmor = addInfusionCraftingRecipe(
                "Infused Quantum Armor",
                getChargedItem(EMTItems.quantumArmor, 10),
                5,
                EMTCraftingAspects.quantumWing,
                new ItemStack(IC2Items.getItem("quantumBodyarmor").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack[] { new ItemStack(Items.diamond), IC2Items.getItem("iridiumPlate"),
                        IC2Items.getItem("iridiumPlate"), IC2Items.getItem("iridiumPlate"),
                        new ItemStack(EMTBlocks.shield), new ItemStack(EMTBlocks.shield) });

        /** Arcane Worktable Recipes **/
        diamondOmnitool = addShapelessArcaneCraftingRecipe(
                "Diamond Omnitool",
                getChargedItem(EMTItems.diamondOmnitool, 10),
                EMTCraftingAspects.diamondOmnitoolCrafting,
                new ItemStack(EMTItems.diamondChainsaw, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(IC2Items.getItem("diamondDrill").getItem(), 1, OreDictionary.WILDCARD_VALUE));

        ironOmnitoolToDiamond = addArcaneCraftingRecipe(
                "Diamond Omnitool",
                new ItemStack(EMTItems.diamondOmnitool),
                EMTCraftingAspects.diamondOmnitoolCrafting,
                "XAX",
                "XBX",
                "XAX",
                'X',
                new ItemStack(Items.diamond),
                'A',
                IC2Items.getItem("advancedCircuit"),
                'B',
                new ItemStack(EMTItems.ironOmnitool, 1, OreDictionary.WILDCARD_VALUE));

        christmasFocus = addArcaneCraftingRecipe(
                "Kris-tmas Focus",
                new ItemStack(EMTItems.christmasFocus),
                EMTCraftingAspects.christmasFocusCrafting,
                "XYX",
                "YZY",
                "XYX",
                'X',
                new ItemStack(Blocks.snow),
                'Y',
                new ItemStack(Blocks.pumpkin),
                'Z',
                new ItemStack(ConfigItems.itemFocusFrost));

        electricGoggles = addArcaneCraftingRecipe(
                "Electric Goggles",
                getChargedItem(EMTItems.electricGoggles, 10),
                EMTCraftingAspects.electricGogglesCrafting,
                " Y ",
                "AZA",
                "BXB",
                'Z',
                new ItemStack(ConfigItems.itemGoggles),
                'X',
                IC2Items.getItem("electronicCircuit"),
                'Y',
                new ItemStack(Items.diamond_helmet),
                'A',
                new ItemStack(IC2Items.getItem("reBattery").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                'B',
                Items.repeater);

        electricGoggles2 = addArcaneCraftingRecipe(
                "Electric Goggles",
                getChargedItem(EMTItems.electricGoggles, 10),
                EMTCraftingAspects.electricGogglesCrafting,
                " Y ",
                "AZA",
                "BXB",
                'Z',
                new ItemStack(ConfigItems.itemGoggles),
                'X',
                IC2Items.getItem("electronicCircuit"),
                'Y',
                new ItemStack(Items.diamond_helmet),
                'A',
                new ItemStack(IC2Items.getItem("chargedReBattery").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                'B',
                Items.repeater);

        shieldBlock = addArcaneCraftingRecipe(
                "Shield Block",
                new ItemStack(EMTBlocks.shield, 6),
                EMTCraftingAspects.shieldBlockCrafting,
                "XYX",
                "X X",
                "XYX",
                'X',
                new ItemStack(Blocks.glass),
                'Y',
                new ItemStack(Blocks.obsidian));

        tinyUranium = addShapelessArcaneCraftingRecipe(
                "Tiny Uranium",
                new ItemStack(IC2Items.getItem("smallUran235").getItem(), 7),
                EMTCraftingAspects.tinyUraniumCrafting,
                IC2Items.getItem("Uran238"));

        compressedSolar = addArcaneCraftingRecipe(
                "Compressed Solars",
                new ItemStack(EMTBlocks.solars[0], 1, 0),
                EMTCraftingAspects.compressedSolars,
                "XXX",
                "XXX",
                "XXX",
                'X',
                IC2Items.getItem("solarPanel"));

        doubleCompressedSolar = addArcaneCraftingRecipe(
                "Compressed Solars",
                new ItemStack(EMTBlocks.solars[0], 1, 1),
                EMTCraftingAspects.compressedSolars,
                "XXX",
                "XXX",
                "XXX",
                'X',
                new ItemStack(EMTBlocks.solars[0], 1, 0));

        electricScribingTools = addArcaneCraftingRecipe(
                "Electric Scribing Tools",
                getChargedItem(EMTItems.electricScribingTools, 10),
                EMTCraftingAspects.electricScribingTools,
                "YXY",
                "XZX",
                "YXY",
                'Y',
                IC2Items.getItem("electronicCircuit"),
                'X',
                new ItemStack(ConfigItems.itemInkwell),
                'Z',
                new ItemStack(IC2Items.getItem("energyCrystal").getItem(), 1, OreDictionary.WILDCARD_VALUE));

        thaumiumWing = addArcaneCraftingRecipe(
                "Thaumium Reinforced Wings",
                new ItemStack(EMTItems.itemEMTItems, 1, 14),
                EMTCraftingAspects.thaumiumWing,
                "XYZ",
                "XYZ",
                "XYZ",
                'X',
                new ItemStack(EMTItems.itemEMTItems, 1, 13),
                'Y',
                new ItemStack(EMTItems.itemEMTItems, 1, 5),
                'Z',
                IC2Items.getItem("plateiron"));

        thaumiumWings = addArcaneCraftingRecipe(
                "Thaumium Reinforced Wings",
                new ItemStack(EMTItems.thaumiumWing),
                EMTCraftingAspects.thaumiumWing,
                "XX",
                'X',
                new ItemStack(EMTItems.itemEMTItems, 1, 14));

        // magicalMachineCasing = addArcaneCraftingRecipe("ResearchCompleter", new
        // ItemStack(GregTech_API.sBlockCasings8, 1, 6), EMTCraftingAspects.magicalMachineCasing, "XYX", "XZX", "XYX",
        // 'X', GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Thaumium, 1), 'Y', new
        // ItemStack(ConfigItems.itemResource, 1, 14) , 'Z', GT_OreDictUnificator.get(OrePrefixes.frameGt,
        // Materials.Thaumium, 1));

        // researchCompleter = addArcaneCraftingRecipe("ResearchCompleter", new ItemStack(GregTech_API.sBlockMachines,
        // 1, 13001), EMTCraftingAspects.researchCompleter, "XYX", "YZY", "XAX", 'X',
        // GT_OreDictUnificator.get(OrePrefixes.circuit.get(Materials.Good), 1L), 'Y', new
        // ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 2), 'Z', new ItemStack(GregTech_API.sBlockCasings8, 1, 6),
        // 'A', new ItemStack(ConfigBlocks.blockStoneDevice, 1, 5));

        /** Crucible Recipes **/
        ignisGenerator = addCrucibleRecipe(
                "Ignis Generator",
                new ItemStack(EMTBlocks.essentiaGens, 1, 1),
                new ItemStack(EMTBlocks.essentiaGens, 1, 0),
                EMTCraftingAspects.ignisGeneratorCrafting);

        auramGenerator = addCrucibleRecipe(
                "Auram Generator",
                new ItemStack(EMTBlocks.essentiaGens, 1, 2),
                new ItemStack(EMTBlocks.essentiaGens, 1, 0),
                EMTCraftingAspects.auramGeneratorCrafting);

        arborGenerator = addCrucibleRecipe(
                "Arbor Generator",
                new ItemStack(EMTBlocks.essentiaGens, 1, 3),
                new ItemStack(EMTBlocks.essentiaGens, 1, 0),
                EMTCraftingAspects.arborGeneratorCrafting);

        aerGenerator = addCrucibleRecipe(
                "Aer Generator",
                new ItemStack(EMTBlocks.essentiaGens, 1, 4),
                new ItemStack(EMTBlocks.essentiaGens, 1, 0),
                EMTCraftingAspects.aerGenerator);

        waterSolar = addCrucibleRecipe(
                "Water Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 3),
                new ItemStack(EMTBlocks.solars[0], 1, 0),
                EMTCraftingAspects.waterSolars);

        doubleWaterSolar = addCrucibleRecipe(
                "Water Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 4),
                new ItemStack(EMTBlocks.solars[0], 1, 1),
                EMTCraftingAspects.waterSolars);

        tripleWaterSolar = addCrucibleRecipe(
                "Water Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 5),
                new ItemStack(EMTBlocks.solars[0], 1, 2),
                EMTCraftingAspects.waterSolars);

        darkSolar = addCrucibleRecipe(
                "Entropy Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 6),
                new ItemStack(EMTBlocks.solars[0], 1, 0),
                EMTCraftingAspects.darkSolars);

        doubleDarkSolar = addCrucibleRecipe(
                "Entropy Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 7),
                new ItemStack(EMTBlocks.solars[0], 1, 1),
                EMTCraftingAspects.darkSolars);

        tripleDarkSolar = addCrucibleRecipe(
                "Entropy Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 8),
                new ItemStack(EMTBlocks.solars[0], 1, 2),
                EMTCraftingAspects.darkSolars);

        orderSolar = addCrucibleRecipe(
                "Order Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 9),
                new ItemStack(EMTBlocks.solars[0], 1, 0),
                EMTCraftingAspects.orderSolars);

        doubleOrderSolar = addCrucibleRecipe(
                "Order Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 10),
                new ItemStack(EMTBlocks.solars[0], 1, 1),
                EMTCraftingAspects.orderSolars);

        tripleOrderSolar = addCrucibleRecipe(
                "Order Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 11),
                new ItemStack(EMTBlocks.solars[0], 1, 2),
                EMTCraftingAspects.orderSolars);

        fireSolar = addCrucibleRecipe(
                "Fire Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 12),
                new ItemStack(EMTBlocks.solars[0], 1, 0),
                EMTCraftingAspects.fireSolars);

        doubleFireSolar = addCrucibleRecipe(
                "Fire Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 13),
                new ItemStack(EMTBlocks.solars[0], 1, 1),
                EMTCraftingAspects.fireSolars);

        tripleFireSolar = addCrucibleRecipe(
                "Fire Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 14),
                new ItemStack(EMTBlocks.solars[0], 1, 2),
                EMTCraftingAspects.fireSolars);

        airSolar = addCrucibleRecipe(
                "Air Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[0], 1, 15),
                new ItemStack(EMTBlocks.solars[0], 1, 0),
                EMTCraftingAspects.airSolars);

        doubleAirSolar = addCrucibleRecipe(
                "Air Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[1], 1, 0),
                new ItemStack(EMTBlocks.solars[0], 1, 1),
                EMTCraftingAspects.airSolars);

        tripleAirSolar = addCrucibleRecipe(
                "Air Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[1], 1, 1),
                new ItemStack(EMTBlocks.solars[0], 1, 2),
                EMTCraftingAspects.airSolars);

        earthSolar = addCrucibleRecipe(
                "Earth Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[1], 1, 2),
                new ItemStack(EMTBlocks.solars[0], 1, 0),
                EMTCraftingAspects.earthSolars);

        doubleEarthSolar = addCrucibleRecipe(
                "Earth Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[1], 1, 3),
                new ItemStack(EMTBlocks.solars[0], 1, 1),
                EMTCraftingAspects.earthSolars);

        tripleEarthSolar = addCrucibleRecipe(
                "Earth Infused Solar Panels",
                new ItemStack(EMTBlocks.solars[1], 1, 4),
                new ItemStack(EMTBlocks.solars[0], 1, 2),
                EMTCraftingAspects.earthSolars);

        ItemStack itemStack = new ItemStack(ConfigItems.itemJarNode);
        ((ItemJarNode) itemStack.getItem()).setAspects(itemStack, new AspectList());
        portableNode = addCrucibleRecipe(
                "Portable Node",
                new ItemStack(EMTBlocks.portableNode),
                itemStack,
                EMTCraftingAspects.portableNode);

        uuMCrystal = addCrucibleRecipe(
                "UU-Matter Infusion",
                new ItemStack(EMTItems.itemEMTItems, 1, 15),
                Ic2Items.uuMatterCell.copy(),
                EMTCraftingAspects.uuMatterCrystal);

        /** IC2 Stuff related recipes **/

        /* Ore Clusters Macerator Recipes */
        ItemStack ironClusterRecipe = IC2Items.getItem("smallIronDust").copy();
        ironClusterRecipe.stackSize = 22;
        ((BasicMachineRecipeManager) Recipes.macerator).addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigItems.itemNugget, 1, 16)),
                null,
                true,
                ironClusterRecipe);

        ItemStack goldClusterRecipe = IC2Items.getItem("smallGoldDust").copy();
        goldClusterRecipe.stackSize = 22;
        ((BasicMachineRecipeManager) Recipes.macerator).addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigItems.itemNugget, 1, 31)),
                null,
                true,
                goldClusterRecipe);

        ItemStack copperClusterRecipe = IC2Items.getItem("smallCopperDust").copy();
        copperClusterRecipe.stackSize = 22;
        ((BasicMachineRecipeManager) Recipes.macerator).addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigItems.itemNugget, 1, 17)),
                null,
                true,
                copperClusterRecipe);

        ItemStack tinClusterRecipe = IC2Items.getItem("smallTinDust").copy();
        tinClusterRecipe.stackSize = 22;
        ((BasicMachineRecipeManager) Recipes.macerator).addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigItems.itemNugget, 1, 18)),
                null,
                true,
                tinClusterRecipe);

        ItemStack silverClusterRecipe = IC2Items.getItem("smallSilverDust").copy();
        silverClusterRecipe.stackSize = 22;
        ((BasicMachineRecipeManager) Recipes.macerator).addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigItems.itemNugget, 1, 19)),
                null,
                true,
                silverClusterRecipe);

        ItemStack leadClusterRecipe = IC2Items.getItem("smallLeadDust").copy();
        leadClusterRecipe.stackSize = 22;
        ((BasicMachineRecipeManager) Recipes.macerator).addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigItems.itemNugget, 1, 20)),
                null,
                true,
                leadClusterRecipe);

        /* Thaumium Plates Recipes */
        thaumiumPlate = GameRegistry.addShapedRecipe(
                new ItemStack(EMTItems.itemEMTItems, 1, 5),
                "X",
                "Y",
                "Z",
                'Y',
                new ItemStack(ConfigItems.itemResource, 1, 2),
                'X',
                new ItemStack(IC2Items.getItem("ForgeHammer").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                'Z',
                new ItemStack(Blocks.obsidian));
        Recipes.metalformerRolling.addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigItems.itemResource, 1, 2)),
                null,
                new ItemStack(EMTItems.itemEMTItems, 1, 5));

        /* Ore Processing for Amber and Cinnabar */
        if (EMTConfigHandler.removeAmberAndCinnabarMacerating) {
            for (Iterator<IRecipeInput> it = Recipes.macerator.getRecipes().keySet().iterator(); it.hasNext();) {
                IRecipeInput input = it.next();
                if (input.matches(new ItemStack(ConfigBlocks.blockCustomOre, 1, 7))) {
                    it.remove();
                    EMT.LOGGER.info("Removing conflicting amber macerating recipe(s)");
                }
            }

            for (Iterator<IRecipeInput> it = Recipes.macerator.getRecipes().keySet().iterator(); it.hasNext();) {
                IRecipeInput input = it.next();
                if (input.matches(new ItemStack(ConfigBlocks.blockCustomOre, 1, 0))) {
                    it.remove();
                    EMT.LOGGER.info("Removing Conflicting cinnabar macerating recipe(s)");
                }
            }
        }
        ItemStack crushedAmberRecipe = new ItemStack(EMTItems.itemEMTItems, 1, 1);
        crushedAmberRecipe.stackSize = 2;
        Recipes.macerator.addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigBlocks.blockCustomOre, 1, 7)),
                null,
                crushedAmberRecipe);

        ItemStack crushedCinnabarRecipe = new ItemStack(EMTItems.itemEMTItems, 1, 3);
        crushedCinnabarRecipe.stackSize = 2;
        Recipes.macerator.addRecipe(
                new RecipeInputItemStack(new ItemStack(ConfigBlocks.blockCustomOre, 1, 0)),
                null,
                crushedCinnabarRecipe);

        NBTTagCompound waterAmount = new NBTTagCompound();
        waterAmount.setInteger("amount", 1000);

        ItemStack smallCopperDust = IC2Items.getItem("smallCopperDust");
        smallCopperDust.stackSize = 2;
        ItemStack smallTinDust = IC2Items.getItem("smallTinDust");
        smallCopperDust.stackSize = 2;

        Recipes.oreWashing.addRecipe(
                new RecipeInputItemStack(new ItemStack(EMTItems.itemEMTItems, 1, 1)),
                waterAmount,
                new ItemStack(EMTItems.itemEMTItems, 1, 2),
                smallCopperDust,
                IC2Items.getItem("stoneDust"));
        Recipes.oreWashing.addRecipe(
                new RecipeInputItemStack(new ItemStack(EMTItems.itemEMTItems, 1, 3)),
                waterAmount,
                new ItemStack(EMTItems.itemEMTItems, 1, 4),
                smallTinDust,
                IC2Items.getItem("stoneDust"));

        NBTTagCompound heatAmount = new NBTTagCompound();
        heatAmount.setInteger("minHeat", 1000);

        Recipes.centrifuge.addRecipe(
                new RecipeInputItemStack(new ItemStack(EMTItems.itemEMTItems, 1, 2)),
                heatAmount,
                IC2Items.getItem("smallCopperDust"),
                new ItemStack(ConfigItems.itemResource, 1, 6));
        Recipes.centrifuge.addRecipe(
                new RecipeInputItemStack(new ItemStack(EMTItems.itemEMTItems, 1, 4)),
                heatAmount,
                IC2Items.getItem("smallTinDust"),
                new ItemStack(ConfigItems.itemResource, 1, 3));
    }
}
