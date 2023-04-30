package emt.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;
import emt.EMT;
import emt.item.ItemElectricScribingTools;
import emt.item.ItemIC2Baubles;
import emt.item.ItemMaterials;
import emt.item.ItemOneRing;
import emt.item.armor.boots.ItemElectricBootsTraveller;
import emt.item.armor.boots.ItemNanoBootsTraveller;
import emt.item.armor.boots.ItemQuantumBootsTraveller;
import emt.item.armor.chestplate.ItemInfusedQuantumChestplate;
import emt.item.armor.goggles.ItemElectricGoggles;
import emt.item.armor.goggles.ItemNanoGoggles;
import emt.item.armor.goggles.ItemQuantumGoggles;
import emt.item.armor.goggles.ItemSolarHelmetRevealing;
import emt.item.armor.wings.ItemFeatherWing;
import emt.item.armor.wings.ItemNanoWing;
import emt.item.armor.wings.ItemQuantumWing;
import emt.item.armor.wings.ItemThaumiumReinforcedWing;
import emt.item.focus.*;
import emt.item.tool.ItemElectricHoeGrowth;
import emt.item.tool.ItemElectricThorHammer;
import emt.item.tool.ItemThorHammer;
import emt.item.tool.ItemThorHammerBroken;
import emt.item.tool.chainsaw.ItemDiamondChainsaw;
import emt.item.tool.chainsaw.ItemStreamChainsaw;
import emt.item.tool.chainsaw.ItemThaumiumChainsaw;
import emt.item.tool.drill.ItemRockbreakerDrill;
import emt.item.tool.drill.ItemThaumiumDrill;
import emt.item.tool.omnitool.ItemOmnitoolDiamond;
import emt.item.tool.omnitool.ItemOmnitoolIron;
import emt.item.tool.omnitool.ItemOmnitoolThaumium;
import ic2.core.init.InternalName;
import thaumcraft.api.ThaumcraftApi;

public class EMTItems {

    public static Item onering;
    public static Item materials;
    public static Item craftingComponents;
    public static Item focusChristmas;
    public static Item thaumiumDrill;
    public static Item thaumiumChainsaw;
    public static Item quantumThaumicHelmet;
    public static Item diamondChainsaw;
    public static Item ironOmnitool;
    public static Item diamondOmnitool;
    public static Item thaumiumOmnitool;
    public static Item nanoThaumicHelmet;
    public static Item explosionFocus;
    public static Item christmasFocus;
    public static Item shieldFocus;
    public static Item energyBallFocus;
    public static Item electricGoggles;
    public static Item streamChainsaw;
    public static Item rockbreakerDrill;
    public static Item solarHelmetRevealing;
    public static Item thorHammer;
    public static Item taintedThorHammer;
    public static Item electricThorHammer;
    public static Item electricBootsTraveller;
    public static Item quantumBootsTraveller;
    public static Item nanoBootsTraveller;
    public static Item electricScribingTools;
    public static Item itemEMTItems;
    public static Item electricHoeGrowth;
    public static Item chargeFocus;
    public static Item emtBauble;
    public static Item wandChargeFocus;
    public static Item featherWing;
    public static Item thaumiumWing;
    public static Item nanoWing;
    public static Item quantumWing;
    public static Item quantumArmor;
    public static ItemArmor.ArmorMaterial featherWingMaterial = EnumHelper
            .addArmorMaterial("FEATEHRWiNG", 1, new int[] { 2, 2, 2, 2 }, 6);

    public static void registerItems() {
        itemEMTItems = new ItemMaterials();
        GameRegistry.registerItem(itemEMTItems, "EMTItems");
        OreDictionary.registerOre("plateThaumium", new ItemStack(itemEMTItems, 1, 5));
        OreDictionary.registerOre("slimeball", new ItemStack(itemEMTItems, 1, 8));

        emtBauble = new ItemIC2Baubles().setUnlocalizedName(EMT.MOD_ID + ".bauble");
        GameRegistry.registerItem(emtBauble, "EMTBaubles");

        onering = new ItemOneRing();
        GameRegistry.registerItem(onering, "BaseBaubles");

        quantumArmor = new ItemInfusedQuantumChestplate(InternalName.itemArmorQuantumChestplate, 1)
                .setUnlocalizedName(EMT.MOD_ID + ".quantum");

        electricGoggles = new ItemElectricGoggles(ItemArmor.ArmorMaterial.IRON, 3, 0)
                .setUnlocalizedName(EMT.MOD_ID + ".goggles.electric");
        GameRegistry.registerItem(electricGoggles, "ElectricGogglesRevealing");

        nanoThaumicHelmet = new ItemNanoGoggles(ItemArmor.ArmorMaterial.DIAMOND, 3, 0)
                .setUnlocalizedName(EMT.MOD_ID + ".goggles.nano");
        GameRegistry.registerItem(nanoThaumicHelmet, "NanosuitGogglesRevealing");

        quantumThaumicHelmet = new ItemQuantumGoggles(ItemArmor.ArmorMaterial.DIAMOND, 3, 0)
                .setUnlocalizedName(EMT.MOD_ID + ".goggles.quantum");
        GameRegistry.registerItem(quantumThaumicHelmet, "QuantumGogglesRevealing");

        solarHelmetRevealing = new ItemSolarHelmetRevealing(ItemArmor.ArmorMaterial.DIAMOND, 3, 0)
                .setUnlocalizedName(EMT.MOD_ID + ".goggles.solar");
        GameRegistry.registerItem(solarHelmetRevealing, "SolarHelmetRevealing");

        electricBootsTraveller = new ItemElectricBootsTraveller(ItemArmor.ArmorMaterial.IRON, 3, 3)
                .setUnlocalizedName(EMT.MOD_ID + ".boots.traveller.electric");
        GameRegistry.registerItem(electricBootsTraveller, "ElectricBootsTraveller");

        nanoBootsTraveller = new ItemNanoBootsTraveller(ItemArmor.ArmorMaterial.DIAMOND, 3, 3)
                .setUnlocalizedName(EMT.MOD_ID + ".boots.traveller.nano");
        GameRegistry.registerItem(nanoBootsTraveller, "NanoBootsTraveller");

        quantumBootsTraveller = new ItemQuantumBootsTraveller(ItemArmor.ArmorMaterial.DIAMOND, 3, 3)
                .setUnlocalizedName(EMT.MOD_ID + ".boots.traveller.quantum");
        GameRegistry.registerItem(quantumBootsTraveller, "QuantumBootsTraveller");

        electricScribingTools = new ItemElectricScribingTools()
                .setUnlocalizedName(EMT.MOD_ID + ".scribingtools.electric");
        GameRegistry.registerItem(electricScribingTools, "ElectricScribingTools");

        featherWing = new ItemFeatherWing(featherWingMaterial, 7, 1).setUnlocalizedName(EMT.MOD_ID + ".wing.feather");
        GameRegistry.registerItem(featherWing, "FeatherWing");

        thaumiumWing = new ItemThaumiumReinforcedWing(ThaumcraftApi.armorMatThaumium, 7, 1)
                .setUnlocalizedName(EMT.MOD_ID + ".wing.thaumium");
        GameRegistry.registerItem(thaumiumWing, "ThaumiumWing");

        nanoWing = new ItemNanoWing(ItemArmor.ArmorMaterial.DIAMOND, 7, 1)
                .setUnlocalizedName(EMT.MOD_ID + ".wing.nano");
        GameRegistry.registerItem(nanoWing, "NanosuitWing");

        quantumWing = new ItemQuantumWing(ItemArmor.ArmorMaterial.DIAMOND, 7, 1)
                .setUnlocalizedName(EMT.MOD_ID + ".wing.quantum");
        GameRegistry.registerItem(quantumWing, "QuantumWing");

        thorHammer = new ItemThorHammer().setUnlocalizedName(EMT.MOD_ID + ".hammer");
        GameRegistry.registerItem(thorHammer, "Mjolnir");

        taintedThorHammer = new ItemThorHammerBroken().setUnlocalizedName(EMT.MOD_ID + ".hammer.broken");
        GameRegistry.registerItem(taintedThorHammer, "TaintedMjolnir");

        electricThorHammer = new ItemElectricThorHammer().setUnlocalizedName(EMT.MOD_ID + ".hammer.electric");
        GameRegistry.registerItem(electricThorHammer, "SuperchargedMjolnir");

        thaumiumDrill = new ItemThaumiumDrill().setUnlocalizedName(EMT.MOD_ID + ".drill.thaumium");
        GameRegistry.registerItem(thaumiumDrill, "ThaumiumDrill");

        diamondChainsaw = new ItemDiamondChainsaw().setUnlocalizedName(EMT.MOD_ID + ".chainsaw.diamond");
        GameRegistry.registerItem(diamondChainsaw, "DiamondChainsaw");

        thaumiumChainsaw = new ItemThaumiumChainsaw().setUnlocalizedName(EMT.MOD_ID + ".chainsaw.thaumium");
        GameRegistry.registerItem(thaumiumChainsaw, "ThaumiumChainsaw");

        ironOmnitool = new ItemOmnitoolIron().setUnlocalizedName(EMT.MOD_ID + ".omnitool.iron");
        GameRegistry.registerItem(ironOmnitool, "Omnitool");

        diamondOmnitool = new ItemOmnitoolDiamond().setUnlocalizedName(EMT.MOD_ID + ".omnitool.diamond");
        GameRegistry.registerItem(diamondOmnitool, "Diamond Omnitool");

        thaumiumOmnitool = new ItemOmnitoolThaumium().setUnlocalizedName(EMT.MOD_ID + ".omnitool.thaumium");
        GameRegistry.registerItem(thaumiumOmnitool, "ThaumiumOmnitool");

        streamChainsaw = new ItemStreamChainsaw().setUnlocalizedName(EMT.MOD_ID + ".chainsaw.stream");
        GameRegistry.registerItem(streamChainsaw, "ChainsawStream");

        rockbreakerDrill = new ItemRockbreakerDrill().setUnlocalizedName(EMT.MOD_ID + ".drill.rockbreaker");
        GameRegistry.registerItem(rockbreakerDrill, "DrillRockbreaker");

        electricHoeGrowth = new ItemElectricHoeGrowth().setUnlocalizedName(EMT.MOD_ID + ".hoeofgrowth.electric");
        GameRegistry.registerItem(electricHoeGrowth, "ElectricHoeGrowth");

        explosionFocus = new ItemExplosionFocus();
        GameRegistry.registerItem(explosionFocus, "ExplosionFocus");

        christmasFocus = new ItemChristmasFocus();
        GameRegistry.registerItem(christmasFocus, "ChristmasFocus");

        shieldFocus = new ItemShieldFocus();
        GameRegistry.registerItem(shieldFocus, "ShieldFocus");

        chargeFocus = new ItemChargeFocus();
        GameRegistry.registerItem(chargeFocus, "ChargingFocus");

        wandChargeFocus = new ItemWandChargingFocus();
        GameRegistry.registerItem(wandChargeFocus, "WandChargingFocus");

        energyBallFocus = new ItemEnergyBallFocus();
        GameRegistry.registerItem(energyBallFocus, "EnergyBallFocus");
    }
}
