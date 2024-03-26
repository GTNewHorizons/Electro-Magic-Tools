package emt.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

/**
 * TODO: Need to rewrite...
 **/
public class EMTConfigHandler {

    /**
     * Sections
     **/
    public static final String RANDOM = "Random Configs";

    public static final String RESEARCH = "Research";
    public static final String VALUES = "Numeric Values";
    public static final String OUTPUTS = "Essentia Generator Outputs";
    public static Configuration config;
    public static int etherealProcessorBaseSpeed;
    public static int etherealProcessorBonus;
    public static int chargeFocusProduction;
    public static int armorBaubleProduction;
    public static int inventoryBaubleProdution;
    public static int wandChargeFocusCost;

    /**
     * Values
     **/
    public static double wandChargerConsumption;

    public static double compressedSolarOutput;
    public static double doubleCompressedSolarOutput;
    public static double nanoBootsSpeed;
    public static double quantumBootsSpeed;
    public static double nanoBootsJump;
    public static double quantumBootsJump;
    public static double tripleCompressedSolarOutput;
    public static double quadrupleCompressedSolarOutput;
    public static double quintupleCompressedSolarOutput;
    public static double sextupleCompressedSolarOutput;
    public static double septupleCompressedSolarOutput;
    public static double octupleCompressedSolarOutput;

    public static double nanoBootsMinDrop;
    public static double nanoBootsMaxDrop;
    public static double quantumBootsMinDrop;
    public static double quantumBootsMaxDrop;

    public static double EssentiaGeneratorStorage;
    public static int aIDoffset;

    /**
     * Essentias
     **/
    public static double fireOutput;

    public static double waterOutput;
    public static double airOutput;
    public static double earthOutput;
    public static double orderOutput;
    public static double entropyOutput;
    public static double outputCap;

    /**
     * random stuff
     **/
    public static boolean impactOfRain;

    public static boolean capesOn;
    public static boolean toolsInBore;
    public static boolean nightVisionOff;
    public static boolean enchanting;
    public static boolean smoke;
    public static boolean oneRingSpawn;
    public static boolean removeAmberAndCinnabarMacerating;
    public static List<String> etherealMaceratorWhiteList = new ArrayList<>();

    /**
     * Loot chance
     **/
    public static int chanceOneRing;

    public static int chanceTaintedMjolnir;

    /**
     * Researches
     **/
    public static boolean thaumiumDrillResearch;

    public static boolean thaumiumChainsawResearch;
    public static boolean thaumiumOmnitoolResearch;
    public static boolean rockbreakerDrillResearch;
    public static boolean streamChainsawResearch;
    public static boolean electricGooglesResearch;
    public static boolean nanoGooglesResearch;
    public static boolean quantumGooglesResearch;
    public static boolean ironOmnitoolResearch;
    public static boolean diamondOmnitoolResearch;
    public static boolean explosionFocusResearch;
    public static boolean christmasFocusResearch;
    public static boolean energyBallFocusResearch;
    public static boolean shieldFocusResearch;
    public static boolean maintenanceFocusResearch;
    public static boolean shieldBlockResearch;
    public static boolean potentiaGeneratorResearch;
    public static boolean ignisGeneratorResearch;
    public static boolean auramGeneratorResearch;
    public static boolean arborGeneratorResearch;
    public static boolean lucrumGeneratorResearch;
    public static boolean wandChargingSationResearch;
    public static boolean tinyUraniumResearch;
    public static boolean thorHammerResearch;
    public static boolean superchargedThorHammerResearch;
    public static boolean compressedSolarsResearch;
    public static boolean solarHelmetResearch;
    public static boolean electricBootsResearch;
    public static boolean nanoBootsResearch;
    public static boolean quantumBootsResearch;
    public static boolean electricScribingToolsResearch;
    public static boolean etherealProcessorResearch;
    public static boolean waterSolarsResearch;
    public static boolean darkSolarsResearch;
    public static boolean orderSolarsResearch;
    public static boolean fireSolarsResearch;
    public static boolean airSolarsResearch;
    public static boolean earthSolarsResearch;
    public static boolean saxumGenResearch;
    public static boolean uuMInfusionResearch;
    public static boolean portableNodeResarch;
    public static boolean electricHoeGrowthResearch;
    public static boolean chargeFocusResearch;
    public static boolean wandChargeFocusResearch;
    public static boolean inventoryChargingRing;
    public static boolean armorChargingRing;
    public static boolean thaumiumWingResearch;
    public static boolean nanoWingResearch;
    public static boolean quantumWingsResearch;
    public static boolean infusedQuantumChestplate;
    public static boolean aerGeneratorResearch;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {

        config.addCustomCategoryComment(VALUES, "The only way to change some numbers that my machines uses.");
        config.addCustomCategoryComment(
                RESEARCH,
                "The only way to disable some researches. " + "Be careful, if you disable some researches, "
                        + "all researches linked to it will be removed too. "
                        + "Really, don't touch that unless you know exactly what you are doing.");
        config.addCustomCategoryComment(OUTPUTS, "Change outputs linked to different Essentias here");
        config.addCustomCategoryComment(RANDOM, "Options that don't fit into the other categories.");

        capesOn = config.get(
                RANDOM,
                "Enable capes",
                true,
                "This config option is to enable or disable capes for people who have them. "
                        + "Seriously, you shouldn't touch that unless you have another cape and "
                        + "it conflicts with it.")
                .getBoolean(capesOn);
        toolsInBore = config.get(
                RANDOM,
                "Tools for Arcane Bore",
                false,
                "This will augment the durablilty of the tools, and will also decrease the EU cost to 1. "
                        + "The tools should have the same number of uses than before.")
                .getBoolean(toolsInBore);
        nightVisionOff = config.get(
                RANDOM,
                "Disable nightvision helmets",
                false,
                "This was added because of mods making you totally blind if using nightvision. For example, enable that when in the Deep Dark.")
                .getBoolean(nightVisionOff);
        enchanting = config.get(RANDOM, "Enable enchanting tools", false, "Warning: the enchantability is low.")
                .getBoolean(enchanting);
        smoke = config.get(
                RANDOM,
                "Disable smoke effect for boots",
                false,
                "This effect only appears when the player isn't on the ground").getBoolean(smoke);
        oneRingSpawn = config
                .get(RANDOM, "Disable One Ring in dungeon chests", true, "There is a recipe to get this item.")
                .getBoolean(oneRingSpawn);
        chanceTaintedMjolnir = config.get(
                RANDOM,
                "Tainted Mjolnir spawning change",
                25,
                "If you have a lot of mods adding dungeon loot, you should definetely increase this").getInt();
        chanceOneRing = config.get(
                RANDOM,
                "One Ring spawning chance",
                15,
                "If you have a lot of mods adding dungeon loot, you should definetely increase this").getInt();
        removeAmberAndCinnabarMacerating = config
                .get(
                        RANDOM,
                        "Clear Amber/Cinnabar Macerating",
                        true,
                        "This is here because of conflicts between mods that add the same input and different outputs")
                .getBoolean(removeAmberAndCinnabarMacerating);
        impactOfRain = config.get(RANDOM, "Impact of rain", true, "The impact of rain on all wings")
                .getBoolean(impactOfRain);
        etherealMaceratorWhiteList.addAll(
                Arrays.asList(
                        config.get(
                                RANDOM,
                                "White List for Ethereal Processor",
                                new String[] { "ore", "cluster" },
                                "The allowed ore dictionary prefix for Ethereal Processor.").getStringList()));

        // DEFAULTS - Aer - 15,000 Aqua - 5,000 Ignis - 20,000 Ordo - 16,000 Perditio - 10,000 Terra - 2,000
        airOutput = config.get(OUTPUTS, "Aer Output", 15000).getDouble(airOutput);
        waterOutput = config.get(OUTPUTS, "Aqua Output", 5000).getDouble(waterOutput);
        fireOutput = config.get(OUTPUTS, "Ignis Output", 20000).getDouble(fireOutput);
        orderOutput = config.get(OUTPUTS, "Ordo Output", 16000).getDouble(orderOutput);
        entropyOutput = config.get(OUTPUTS, "Perditio Output", 10000).getDouble(entropyOutput);
        earthOutput = config.get(OUTPUTS, "Terra Output", 2000).getDouble(earthOutput);
        outputCap = config.get(OUTPUTS, "Output Cap", -1).getDouble(outputCap);

        EssentiaGeneratorStorage = config.get(
                VALUES,
                "Essentia Generator Storage",
                1000000,
                "This is the number you have to modify if you want to change the size of the "
                        + "internal buffer of the Essentia Generator")
                .getDouble(EssentiaGeneratorStorage);
        aIDoffset = config.get(
                VALUES,
                "aID offset for Gregetch Machinery",
                12983,
                "This Mod will use 17 IDs, do not change if you dont know what you are doing!").getInt();

        wandChargerConsumption = config
                .get(
                        VALUES,
                        "Wand Charging Station Consumption",
                        50000,
                        "This is the number you have to modify if you want to make the Industrial Wand Charging "
                                + "Station use more or less EU to charge 1 unit of Vis on your wand.")
                .getDouble(wandChargerConsumption);
        compressedSolarOutput = config.get(
                VALUES,
                "Compressed Solar Panel Output",
                10,
                "This is the number you have to modify if you want to make the Compressed Solar Panel yield "
                        + "more or less EU per tick. Really, you shouldn't touch that, "
                        + "since it's pretty balanced as it is.")
                .getDouble(compressedSolarOutput);
        doubleCompressedSolarOutput = config.get(
                VALUES,
                "Double Compressed Solar Panel Output",
                100,
                "This is the number you have to modify if you want to make the Double Compressed Solar Panel "
                        + "yield more or less EU per tick. Really, you shouldn't touch that, "
                        + "since it's pretty balanced as it is.")
                .getDouble(doubleCompressedSolarOutput);
        tripleCompressedSolarOutput = config.get(
                VALUES,
                "Triple Compressed Solar Panel Output",
                1000,
                "This is the number you have to modify if you want to make the Triple Compressed Solar Panel "
                        + "yield more or less EU per tick. Really, you shouldn't touch that, "
                        + "since it's pretty balanced as it is.")
                .getDouble(tripleCompressedSolarOutput);
        quadrupleCompressedSolarOutput = config.get(
                VALUES,
                "Quadruple Compressed Solar Panel Output",
                10000,
                "This is the number you have to modify if you want to make the Quadruple Compressed Solar Panel "
                        + "yield more or less EU per tick. Really, you shouldn't touch that, "
                        + "since it's pretty balanced as it is.")
                .getDouble(quadrupleCompressedSolarOutput);
        quintupleCompressedSolarOutput = config.get(
                VALUES,
                "Quintouple Compressed Solar Panel Output",
                100000,
                "This is the number you have to modify if you want to make the Quintouple Compressed Solar Panel "
                        + "yield more or less EU per tick. Really, you shouldn't touch that, "
                        + "since it's pretty balanced as it is.")
                .getDouble(quintupleCompressedSolarOutput);
        sextupleCompressedSolarOutput = config.get(
                VALUES,
                "Sextouple Compressed Solar Panel Output",
                1000000,
                "This is the number you have to modify if you want to make the Sextouple Compressed Solar Panel "
                        + "yield more or less EU per tick. Really, you shouldn't touch that, "
                        + "since it's pretty balanced as it is.")
                .getDouble(sextupleCompressedSolarOutput);
        septupleCompressedSolarOutput = config.get(
                VALUES,
                "Septouple Compressed Solar Panel Output",
                10000000,
                "This is the number you have to modify if you want to make the Septouple Compressed Solar Panel "
                        + "yield more or less EU per tick. Really, you shouldn't touch that, "
                        + "since it's pretty balanced as it is.")
                .getDouble(septupleCompressedSolarOutput);
        octupleCompressedSolarOutput = config.get(
                VALUES,
                "Octtouple Compressed Solar Panel Output",
                100000000,
                "This is the number you have to modify if you want to make the Octtouple Compressed Solar Panel "
                        + "yield more or less EU per tick. Really, you shouldn't touch that, "
                        + "since it's pretty balanced as it is.")
                .getDouble(octupleCompressedSolarOutput);

        nanoBootsSpeed = config
                .get(VALUES, "Nano Boots of the Traveller Speed", 0.25, "Watch out, that goes up REALLY quickly.")
                .getDouble(nanoBootsSpeed);
        quantumBootsSpeed = config
                .get(VALUES, "Quantum Boots of the Traveller Speed", 0.5, "Watch out, that goes up REALLY quickly.")
                .getDouble(quantumBootsSpeed);
        nanoBootsJump = config
                .get(VALUES, "Nano Boots of the Traveller jump", 0.6, "Watch out, that goes up REALLY quickly.")
                .getDouble(nanoBootsJump);
        quantumBootsJump = config
                .get(VALUES, "Quantum Boots of the Traveller jump", 0.9, "Watch out, that goes up REALLY quickly.")
                .getDouble(nanoBootsJump);
        etherealProcessorBaseSpeed = config
                .get(VALUES, "Etheral Processor speed", 400, "Default is 400, the double of a regular furnace")
                .getInt();
        etherealProcessorBonus = config.get(
                VALUES,
                "Ethereal Processor Bonus",
                10,
                "This number is the chance of getting a Thaumium Ingot as a bonus when "
                        + "processing an item in the machine. It is 1 out of the number you will enter. "
                        + "The default is 1/10 chance.")
                .getInt();
        chargeFocusProduction = config.get(VALUES, "Wand Focus: Charge production", 64, "Default is 64").getInt();
        armorBaubleProduction = config.get(VALUES, "Armor Charging Ring production", 32, "Default is 32").getInt();
        inventoryBaubleProdution = config.get(VALUES, "Inventory Charging Ring production", 32, "Default is 32")
                .getInt();
        wandChargeFocusCost = config.get(VALUES, "Wand Focus: Wand Charging Cost", 40000, "Default is 40000").getInt();

        nanoBootsMinDrop = config.get(
                VALUES,
                "Nano Boots of the Traveller minimum drop",
                6.0,
                "The distance allowed to fall without any damage").getDouble(nanoBootsMinDrop);
        nanoBootsMaxDrop = config
                .get(
                        VALUES,
                        "Nano Boots of the Traveller maximum healthy drop",
                        35.0,
                        "Energy amount drained will tripled if player drops more than this amount of blocks")
                .getDouble(nanoBootsMaxDrop);
        quantumBootsMinDrop = config.get(
                VALUES,
                "Quantum Boots of the Traveller minimum drop",
                10.0,
                "The distance allowed to fall without any damage").getDouble(quantumBootsMinDrop);
        quantumBootsMaxDrop = config
                .get(
                        VALUES,
                        "Quantum Boots of the Traveller maximum healthy drop",
                        100.0,
                        "Energy amount drained will tripled if player drops more than this amount of blocks")
                .getDouble(quantumBootsMaxDrop);

        thaumiumDrillResearch = config.get(RESEARCH, "Thaumium Drill", false).getBoolean(thaumiumDrillResearch);
        thaumiumChainsawResearch = config.get(RESEARCH, "Thaumium Chainsaw", false)
                .getBoolean(thaumiumChainsawResearch);
        thaumiumDrillResearch = config.get(RESEARCH, "Thaumium Omnitool", false).getBoolean(thaumiumOmnitoolResearch);
        rockbreakerDrillResearch = config.get(RESEARCH, "Drill of the Rockbreaker", false)
                .getBoolean(rockbreakerDrillResearch);
        streamChainsawResearch = config.get(RESEARCH, "Chainsaw of the Stream", false)
                .getBoolean(streamChainsawResearch);
        electricGooglesResearch = config.get(RESEARCH, "Electric Googles of Revealing", false)
                .getBoolean(electricGooglesResearch);
        nanoGooglesResearch = config.get(RESEARCH, "Nanosuit Googles of Revealing", false)
                .getBoolean(nanoGooglesResearch);
        quantumGooglesResearch = config.get(RESEARCH, "Quantum Googles of Revealing", false)
                .getBoolean(quantumGooglesResearch);
        ironOmnitoolResearch = config.get(RESEARCH, "Omnitool", false).getBoolean(ironOmnitoolResearch);
        diamondOmnitoolResearch = config.get(RESEARCH, "Diamond Omnitool", false).getBoolean(diamondOmnitoolResearch);
        explosionFocusResearch = config.get(RESEARCH, "Explosion Focus", false).getBoolean(explosionFocusResearch);
        christmasFocusResearch = config.get(RESEARCH, "Kris-tmas Focus", false).getBoolean(christmasFocusResearch);
        energyBallFocusResearch = config.get(RESEARCH, "EnergyBall Focus", false).getBoolean(christmasFocusResearch);
        shieldFocusResearch = config.get(RESEARCH, "Shield Focus", false).getBoolean(shieldFocusResearch);
        maintenanceFocusResearch = config.get(RESEARCH, "Maintenance Focus", false)
                .getBoolean(maintenanceFocusResearch);
        shieldBlockResearch = config.get(RESEARCH, "Shield Block", false).getBoolean(shieldBlockResearch);
        potentiaGeneratorResearch = config.get(RESEARCH, "Potentia Generator", false)
                .getBoolean(potentiaGeneratorResearch);
        ignisGeneratorResearch = config.get(RESEARCH, "Ignis Generator", false).getBoolean(ignisGeneratorResearch);
        auramGeneratorResearch = config.get(RESEARCH, "Auram Generator", false).getBoolean(auramGeneratorResearch);
        arborGeneratorResearch = config.get(RESEARCH, "Arbor Generator", false).getBoolean(arborGeneratorResearch);
        lucrumGeneratorResearch = config.get(RESEARCH, "Lucrum Generator", false).getBoolean(lucrumGeneratorResearch);
        wandChargingSationResearch = config.get(RESEARCH, "Industrial Wand Charging Station", false)
                .getBoolean(wandChargingSationResearch);
        tinyUraniumResearch = config.get(RESEARCH, "Tiny Piles of Uranium", false).getBoolean(tinyUraniumResearch);
        thorHammerResearch = config.get(RESEARCH, "Mjolnir", false).getBoolean(thorHammerResearch);
        superchargedThorHammerResearch = config.get(RESEARCH, "Supercharged Mjolnir", false)
                .getBoolean(superchargedThorHammerResearch);
        compressedSolarsResearch = config.get(RESEARCH, "Compressed Solars", false)
                .getBoolean(compressedSolarsResearch);
        solarHelmetResearch = config.get(RESEARCH, "Solar Helmet of Revealing", false).getBoolean(solarHelmetResearch);
        electricBootsResearch = config.get(RESEARCH, "Electric Boots of the Traveller", false)
                .getBoolean(electricBootsResearch);
        nanoBootsResearch = config.get(RESEARCH, "Nano Boots of the Traveller", false).getBoolean(nanoBootsResearch);
        quantumBootsResearch = config.get(RESEARCH, "Quantum Boots of the Traveller", false)
                .getBoolean(quantumBootsResearch);
        electricScribingToolsResearch = config.get(RESEARCH, "Electric Scribing Tools", false)
                .getBoolean(electricScribingToolsResearch);
        etherealProcessorResearch = config.get(RESEARCH, "Ethereal Processor", false)
                .getBoolean(etherealProcessorResearch);
        waterSolarsResearch = config.get(RESEARCH, "Water Solar Panels", false).getBoolean(waterSolarsResearch);
        darkSolarsResearch = config.get(RESEARCH, "Entropy Solar Panels", false).getBoolean(darkSolarsResearch);
        orderSolarsResearch = config.get(RESEARCH, "Order Solar Panels", false).getBoolean(orderSolarsResearch);
        fireSolarsResearch = config.get(RESEARCH, "Fire Solar Panels", false).getBoolean(fireSolarsResearch);
        airSolarsResearch = config.get(RESEARCH, "Air Solar Panels", false).getBoolean(airSolarsResearch);
        earthSolarsResearch = config.get(RESEARCH, "Earth Solar Panels", false).getBoolean(earthSolarsResearch);
        saxumGenResearch = config.get(RESEARCH, "Saxum Generator", false).getBoolean(saxumGenResearch);
        uuMInfusionResearch = config.get(RESEARCH, "UU-Mater Infusion", false).getBoolean(uuMInfusionResearch);
        portableNodeResarch = config.get(RESEARCH, "Portable Node", false).getBoolean(portableNodeResarch);
        electricHoeGrowthResearch = config.get(RESEARCH, "Electric Hoe of Growth", false)
                .getBoolean(electricHoeGrowthResearch);
        chargeFocusResearch = config.get(RESEARCH, "Wand Focus: Charging", false).getBoolean(chargeFocusResearch);
        wandChargeFocusResearch = config.get(RESEARCH, "Wand Focus: Wand Charging", false)
                .getBoolean(wandChargeFocusResearch);
        inventoryChargingRing = config.get(RESEARCH, "Inventory Charging Ring", false)
                .getBoolean(inventoryChargingRing);
        armorChargingRing = config.get(RESEARCH, "Armor Charging Ring", false).getBoolean(armorChargingRing);
        thaumiumWingResearch = config.get(RESEARCH, "Thaumium Reinforced Wings", false)
                .getBoolean(thaumiumWingResearch);
        nanoWingResearch = config.get(RESEARCH, "Nanosuit Wings", false).getBoolean(nanoWingResearch);
        quantumWingsResearch = config.get(RESEARCH, "Quantum Wings", false).getBoolean(quantumWingsResearch);
        infusedQuantumChestplate = config.get(RESEARCH, "Infused Quantum Chestplate", false)
                .getBoolean(infusedQuantumChestplate);
        aerGeneratorResearch = config.get(RESEARCH, "Aer Generator", false).getBoolean(aerGeneratorResearch);

        if (config.hasChanged()) {
            config.save();
        }
    }
}
