package emt.init;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import emt.EMT;
import emt.util.EMTConfigHandler;
import emt.util.EMTResearchAspects;
import emt.util.EMTResearchItem;
import ic2.api.item.IC2Items;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchPage;

public class EMTResearches {

    public static void register() {
        addResearchTab();
        addResearch();
    }

    public static void addResearchTab() {
        ResourceLocation background = new ResourceLocation(EMT.TEXTURE_PATH, "textures/misc/background.png");
        ResearchCategories
                .registerCategory("EMT", new ResourceLocation(EMT.TEXTURE_PATH, "textures/misc/emt.png"), background);
    }

    public static void addResearch() {
        /** Research that can't be disabled **/
        new EMTResearchItem(
                "Electric Magic Tools",
                0,
                0,
                0,
                new ResourceLocation(EMT.TEXTURE_PATH, "textures/misc/emt.png")).setRound().setAutoUnlock()
                        .registerResearchItem().setPages(new ResearchPage(""));
        new EMTResearchItem("Diamond Chainsaw", 5, -6, 0, new ItemStack(EMTItems.diamondChainsaw)).setRound()
                .setAutoUnlock().registerResearchItem()
                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.diamondChainsaw));
        new EMTResearchItem("Thaumium Plate", 6, -6, 0, new ItemStack(EMTItems.itemEMTItems, 1, 5)).setRound()
                .setAutoUnlock().registerResearchItem()
                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.thaumiumPlate));
        new EMTResearchItem("Macerating Native Ore Clusters", 5, -7, 0, IC2Items.getItem("smallGoldDust")).setRound()
                .setAutoUnlock().registerResearchItem().setPages(new ResearchPage(""));
        new EMTResearchItem("The Legend", 5, -5, 0, new ItemStack(EMTItems.taintedThorHammer)).setRound()
                .setAutoUnlock().registerResearchItem().setPages(new ResearchPage(""));
        new EMTResearchItem("Lightning Summoner", 6, -5, 0, new ItemStack(EMTItems.itemEMTItems, 1, 6)).setRound()
                .setAutoUnlock().registerResearchItem().setPages(new ResearchPage(""));
        new EMTResearchItem("The One Ring", 6, -7, 0, new ItemStack(EMTItems.onering)).setRound().setAutoUnlock()
                .registerResearchItem().setPages(new ResearchPage(""));
        new EMTResearchItem("Electric Cloud", 4, -7, 0, new ItemStack(EMTBlocks.electricCloud)).setRound()
                .setAutoUnlock().registerResearchItem().setPages(new ResearchPage("1"), new ResearchPage("2"));
        new EMTResearchItem("Feather Wings", 0, 2, 0, new ItemStack(EMTItems.featherWing))
                .setParents("Electric Magic Tools").setRound().setAutoUnlock().registerResearchItem().setPages(
                        new ResearchPage(EMTRecipes.cardboard),
                        new ResearchPage(EMTRecipes.featherWing),
                        new ResearchPage(EMTRecipes.featherWings));
        // new EMTResearchItem("ResearchCompleter", EMTResearchAspects.researchCompleter, -4, -2, 0, new
        // ItemStack(GregTech_API.sBlockMachines, 1, 13001)).setParents("Electric Magic
        // Tools").setParentsHidden("WANDPED", "WARDEDARCANA").registerResearchItem().setPages(new ResearchPage(""), new
        // ResearchPage(EMTRecipes.magicalMachineCasing), new ResearchPage(EMTRecipes.researchCompleter));
        /*************************************/

        /** Research that can be disabled **/
        if (!EMTConfigHandler.thaumiumDrillResearch) {
            new EMTResearchItem(
                    "Thaumium Drill",
                    EMTResearchAspects.thaumiumDrillResearch,
                    -1,
                    -2,
                    1,
                    new ItemStack(EMTItems.thaumiumDrill)).setSecondary().setParents("Electric Magic Tools")
                            .setConcealed().registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.thaumiumDrill));
        }

        if (!EMTConfigHandler.thaumiumChainsawResearch) {
            new EMTResearchItem(
                    "Thaumium Chainsaw",
                    EMTResearchAspects.thaumiumChainsawResearch,
                    -2,
                    -2,
                    1,
                    new ItemStack(EMTItems.thaumiumChainsaw)).setSecondary().setParents("Electric Magic Tools")
                            .setConcealed().registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.thaumiumChainsaw));
        }

        if (!EMTConfigHandler.electricGooglesResearch) {
            new EMTResearchItem(
                    "Electric Goggles",
                    EMTResearchAspects.electricGogglesResearch,
                    1,
                    -2,
                    1,
                    new ItemStack(EMTItems.electricGoggles)).setSecondary().setParents("Electric Magic Tools")
                            .setConcealed().registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.electricGoggles));
        }

        if (!EMTConfigHandler.electricGooglesResearch) {
            if (!EMTConfigHandler.nanoGooglesResearch) {
                ;
                new EMTResearchItem(
                        "Nanosuit Goggles of Revealing",
                        EMTResearchAspects.thaumicNanoHelmet,
                        2,
                        -2,
                        2,
                        new ItemStack(EMTItems.nanoThaumicHelmet)).setParents("Electric Goggles").setConcealed()
                                .registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.thaumicNanoHelmet));
            }
        }

        if (!EMTConfigHandler.electricGooglesResearch) {
            if (!EMTConfigHandler.nanoGooglesResearch) {
                if (!EMTConfigHandler.quantumGooglesResearch) {
                    new EMTResearchItem(
                            "Quantum Goggles of Revealing",
                            EMTResearchAspects.thaumicQuantumHelmet,
                            3,
                            -2,
                            3,
                            new ItemStack(EMTItems.quantumThaumicHelmet)).setParents("Nanosuit Goggles of Revealing")
                                    .setConcealed().registerResearchItem()
                                    .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.thaumicQuantumHelmet));
                }
            }
        }

        if (!EMTConfigHandler.ironOmnitoolResearch) {
            new EMTResearchItem("Iron Omnitool", -2, 0, 0, new ItemStack(EMTItems.ironOmnitool))
                    .setParents("Electric Magic Tools").setRound().setAutoUnlock().registerResearchItem()
                    .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.ironOmnitool));
        }

        if (!EMTConfigHandler.ironOmnitoolResearch) {
            if (!EMTConfigHandler.diamondOmnitoolResearch) {
                new EMTResearchItem(
                        "Diamond Omnitool",
                        EMTResearchAspects.diamondOmnitoolResearch,
                        -4,
                        0,
                        1,
                        new ItemStack(EMTItems.diamondOmnitool)).setSecondary().setParents("Iron Omnitool")
                                .setConcealed().registerResearchItem().setPages(
                                        new ResearchPage(""),
                                        new ResearchPage(EMTRecipes.diamondOmnitool),
                                        new ResearchPage(EMTRecipes.ironOmnitoolToDiamond));
            }
        }

        if (!EMTConfigHandler.thaumiumDrillResearch && !EMTConfigHandler.thaumiumChainsawResearch) {
            if (!EMTConfigHandler.thaumiumOmnitoolResearch) {
                new EMTResearchItem(
                        "Thaumium Omnitool",
                        EMTResearchAspects.thaumiumOmnitoolResearch,
                        -6,
                        0,
                        2,
                        new ItemStack(EMTItems.thaumiumOmnitool))
                                .setParentsHidden("Thaumium Drill", "Thaumium Chainsaw").setParents("Diamond Omnitool")
                                .setConcealed().registerResearchItem().setPages(
                                        new ResearchPage(""),
                                        new ResearchPage(EMTRecipes.thaumiumOmnitool),
                                        new ResearchPage(EMTRecipes.diamondOmnitoolToThaumium));
            }
        }

        if (!EMTConfigHandler.explosionFocusResearch) {
            new EMTResearchItem(
                    "Explosion Focus",
                    EMTResearchAspects.laserFocusResearch,
                    2,
                    3,
                    2,
                    new ItemStack(EMTItems.explosionFocus)).setParents("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.explosionFocus));
        }

        if (!EMTConfigHandler.christmasFocusResearch) {
            new EMTResearchItem(
                    "Kris-tmas Focus",
                    EMTResearchAspects.christmasFocusResearch,
                    3,
                    3,
                    1,
                    new ItemStack(EMTItems.christmasFocus)).setSecondary().setParents("Electric Magic Tools")
                            .setConcealed().registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.christmasFocus));
        }

        if (!EMTConfigHandler.shieldFocusResearch) {
            new EMTResearchItem(
                    "Shield Focus",
                    EMTResearchAspects.shieldFocusResearch,
                    4,
                    3,
                    2,
                    new ItemStack(EMTItems.shieldFocus)).setParents("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.shieldFocus));
        }

        if (!EMTConfigHandler.energyBallFocusResearch) {
            new EMTResearchItem(
                    "Energy Ball Focus",
                    EMTResearchAspects.energyBallFocusResearch,
                    1,
                    3,
                    2,
                    new ItemStack(EMTItems.energyBallFocus)).setParents("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.energyBallFocus));
        }

        if (!EMTConfigHandler.shieldFocusResearch) {
            if (!EMTConfigHandler.shieldBlockResearch) {
                new EMTResearchItem(
                        "Shield Block",
                        EMTResearchAspects.shieldBlockResearch,
                        4,
                        5,
                        1,
                        new ItemStack(EMTBlocks.shield)).setSecondary().setParents("Shield Focus").setConcealed()
                                .registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.shieldBlock));
            }
        }

        if (!EMTConfigHandler.potentiaGeneratorResearch) {
            new EMTResearchItem(
                    "Potentia Generator",
                    EMTResearchAspects.potentiaGeneratorResearch,
                    -2,
                    3,
                    3,
                    new ItemStack(EMTBlocks.essentiaGens, 1, 0)).setParents("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.potentiaGenerator));
        }

        if (!EMTConfigHandler.potentiaGeneratorResearch) {
            if (!EMTConfigHandler.ignisGeneratorResearch) {
                new EMTResearchItem(
                        "Ignis Generator",
                        EMTResearchAspects.ignisGeneratorResearch,
                        -3,
                        4,
                        1,
                        new ItemStack(EMTBlocks.essentiaGens, 1, 1)).setSecondary().setParents("Potentia Generator")
                                .setConcealed().registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.ignisGenerator));
            }
        }

        if (!EMTConfigHandler.potentiaGeneratorResearch) {
            if (!EMTConfigHandler.auramGeneratorResearch) {
                new EMTResearchItem(
                        "Auram Generator",
                        EMTResearchAspects.auramGeneratorResearch,
                        -1,
                        4,
                        1,
                        new ItemStack(EMTBlocks.essentiaGens, 1, 2)).setSecondary().setParents("Potentia Generator")
                                .setConcealed().registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.auramGenerator));
            }
        }

        if (!EMTConfigHandler.potentiaGeneratorResearch) {
            if (!EMTConfigHandler.arborGeneratorResearch) {
                new EMTResearchItem(
                        "Arbor Generator",
                        EMTResearchAspects.arborGeneratorResearch,
                        -2,
                        5,
                        1,
                        new ItemStack(EMTBlocks.essentiaGens, 1, 3)).setSecondary().setParents("Potentia Generator")
                                .setConcealed().registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.arborGenerator));
            }
        }

        if (!EMTConfigHandler.potentiaGeneratorResearch) {
            if (!EMTConfigHandler.lucrumGeneratorResearch) {
                new EMTResearchItem(
                        "Lucrum Generator",
                        EMTResearchAspects.lucrumGeneratorResearch,
                        -2,
                        2,
                        1,
                        new ItemStack(EMTBlocks.essentiaGens, 1, 5)).setSecondary().setParents("Potentia Generator")
                                .setConcealed().registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.lucrumGenerator));
            }
        }

        if (!EMTConfigHandler.potentiaGeneratorResearch) {
            if (!EMTConfigHandler.aerGeneratorResearch) {
                new EMTResearchItem(
                        "Aer Generator",
                        EMTResearchAspects.aerGenerator,
                        -2,
                        6,
                        1,
                        new ItemStack(EMTBlocks.essentiaGens, 1, 4)).setSecondary().setParents("Potentia Generator")
                                .setConcealed().registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.aerGenerator));
            }
        }

        if (!EMTConfigHandler.potentiaGeneratorResearch) {
            if (!EMTConfigHandler.wandChargingSationResearch) {
                new EMTResearchItem(
                        "Industrial Wand Charging Station",
                        EMTResearchAspects.wandCharger,
                        -4,
                        2,
                        3,
                        new ItemStack(EMTBlocks.emtMachines, 1, 0)).setParents("Potentia Generator")
                                .registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.wandRecharger));
            }
        }

        if (!EMTConfigHandler.thaumiumChainsawResearch) {
            if (!EMTConfigHandler.streamChainsawResearch) {
                new EMTResearchItem(
                        "Chainsaw of the Stream",
                        EMTResearchAspects.streamChainsawResearch,
                        -3,
                        -4,
                        3,
                        new ItemStack(EMTItems.streamChainsaw)).setParents("Thaumium Chainsaw").registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.streamChainsaw));
            }
        }

        if (!EMTConfigHandler.thaumiumDrillResearch) {
            if (!EMTConfigHandler.rockbreakerDrillResearch) {
                new EMTResearchItem(
                        "Drill of the Rockbreaker",
                        EMTResearchAspects.rockbreakerDrillResearch,
                        0,
                        -4,
                        3,
                        new ItemStack(EMTItems.rockbreakerDrill)).setParents("Thaumium Drill").registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.rockbreakerDrill));
            }
        }

        if (!EMTConfigHandler.tinyUraniumResearch) {
            new EMTResearchItem(
                    "Tiny Uranium",
                    EMTResearchAspects.tinyUraniumResearch,
                    -5,
                    -5,
                    1,
                    IC2Items.getItem("Uran238")).setSecondary().setParentsHidden("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.tinyUranium));
        }

        if (!EMTConfigHandler.thorHammerResearch) {
            new EMTResearchItem(
                    "Mjolnir",
                    EMTResearchAspects.thorHammerResearch,
                    -5,
                    5,
                    3,
                    new ItemStack(EMTItems.thorHammer)).setParentsHidden("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.thorHammer));
        }

        if (!EMTConfigHandler.thorHammerResearch) {
            if (!EMTConfigHandler.superchargedThorHammerResearch) {
                new EMTResearchItem(
                        "Supercharged Mjolnir",
                        EMTResearchAspects.superchargedThorHammerResearch,
                        -6,
                        6,
                        3,
                        new ItemStack(EMTItems.electricThorHammer)).setParents("Mjolnir").registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.superchargedThorHammer));
            }
        }

        if (!EMTConfigHandler.compressedSolarsResearch) {
            new EMTResearchItem(
                    "Compressed Solars",
                    EMTResearchAspects.compressedSolars,
                    -4,
                    -5,
                    2,
                    new ItemStack(EMTBlocks.solars[0])).setParentsHidden("Potentia Generator").setConcealed()
                            .registerResearchItem().setPages(
                                    new ResearchPage(""),
                                    new ResearchPage(EMTRecipes.compressedSolar),
                                    new ResearchPage(EMTRecipes.doubleCompressedSolar),
                                    new ResearchPage(EMTRecipes.tripleCompressedSolar));
        }

        if (!EMTConfigHandler.compressedSolarsResearch) {
            if (!EMTConfigHandler.solarHelmetResearch) {
                new EMTResearchItem(
                        "Solar Helmet of Revealing",
                        EMTResearchAspects.solarHelmetRevealing,
                        -2,
                        -5,
                        1,
                        new ItemStack(EMTItems.solarHelmetRevealing)).setSecondary().setParents("Compressed Solars")
                                .setParentsHidden("Quantum Goggles of Revealing").registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.solarHelmetRevealing));
            }
        }

        if (!EMTConfigHandler.electricBootsResearch) {
            new EMTResearchItem(
                    "Electric Boots of the Traveller",
                    EMTResearchAspects.electricBootsTravel,
                    2,
                    0,
                    1,
                    new ItemStack(EMTItems.electricBootsTraveller)).setSecondary().setParents("Electric Magic Tools")
                            .setConcealed().registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.electricBootsTravel));
        }

        if (!EMTConfigHandler.electricBootsResearch) {
            if (!EMTConfigHandler.nanoBootsResearch) {
                new EMTResearchItem(
                        "Nano Boots of the Traveller",
                        EMTResearchAspects.nanoBootsTravel,
                        4,
                        0,
                        2,
                        new ItemStack(EMTItems.nanoBootsTraveller)).setParents("Electric Boots of the Traveller")
                                .setConcealed().registerResearchItem()
                                .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.nanoBootsTravel));
            }
        }

        if (!EMTConfigHandler.electricBootsResearch) {
            if (!EMTConfigHandler.nanoBootsResearch) {
                if (!EMTConfigHandler.quantumBootsResearch) {
                    new EMTResearchItem(
                            "Quantum Boots of the Traveller",
                            EMTResearchAspects.quantumBootsTravel,
                            6,
                            0,
                            3,
                            new ItemStack(EMTItems.quantumBootsTraveller)).setParents("Nano Boots of the Traveller")
                                    .setConcealed().registerResearchItem()
                                    .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.quantumBootsTravel));
                }
            }
        }

        if (!EMTConfigHandler.electricScribingToolsResearch) {
            new EMTResearchItem(
                    "Electric Scribing Tools",
                    EMTResearchAspects.electricScribingTools,
                    -6,
                    -5,
                    1,
                    new ItemStack(EMTItems.electricScribingTools)).setSecondary()
                            .setParentsHidden("Electric Magic Tools").setConcealed().registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.electricScribingTools));
        }

        if (!EMTConfigHandler.etherealProcessorResearch) {
            new EMTResearchItem(
                    "Etheral Processor",
                    EMTResearchAspects.etherealProcessor,
                    -1,
                    2,
                    2,
                    new ItemStack(EMTBlocks.emtMachines, 1, 1)).setParents("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.etheralProcessor));
        }

        if (!EMTConfigHandler.compressedSolarsResearch) {
            if (!EMTConfigHandler.waterSolarsResearch) {
                new EMTResearchItem(
                        "Water Infused Solar Panels",
                        EMTResearchAspects.waterSolars,
                        -7,
                        -7,
                        1,
                        new ItemStack(EMTBlocks.solars[0], 1, 3)).setSecondary().setParents("Compressed Solars")
                                .registerResearchItem().setPages(
                                        new ResearchPage(""),
                                        new ResearchPage(EMTRecipes.waterSolar),
                                        new ResearchPage(EMTRecipes.doubleWaterSolar),
                                        new ResearchPage(EMTRecipes.tripleWaterSolar));
            }
        }

        if (!EMTConfigHandler.compressedSolarsResearch) {
            if (!EMTConfigHandler.darkSolarsResearch) {
                new EMTResearchItem(
                        "Entropy Infused Solar Panels",
                        EMTResearchAspects.darkSolars,
                        -8,
                        -6,
                        1,
                        new ItemStack(EMTBlocks.solars[0], 1, 6)).setSecondary().setParents("Compressed Solars")
                                .registerResearchItem().setPages(
                                        new ResearchPage(""),
                                        new ResearchPage(EMTRecipes.darkSolar),
                                        new ResearchPage(EMTRecipes.doubleDarkSolar),
                                        new ResearchPage(EMTRecipes.tripleDarkSolar));
            }
        }

        if (!EMTConfigHandler.compressedSolarsResearch) {
            if (!EMTConfigHandler.orderSolarsResearch) {
                new EMTResearchItem(
                        "Order Infused Solar Panels",
                        EMTResearchAspects.orderSolars,
                        -6,
                        -8,
                        1,
                        new ItemStack(EMTBlocks.solars[0], 1, 9)).setSecondary().setParents("Compressed Solars")
                                .registerResearchItem().setPages(
                                        new ResearchPage(""),
                                        new ResearchPage(EMTRecipes.orderSolar),
                                        new ResearchPage(EMTRecipes.doubleOrderSolar),
                                        new ResearchPage(EMTRecipes.tripleOrderSolar));
            }
        }

        if (!EMTConfigHandler.compressedSolarsResearch) {
            if (!EMTConfigHandler.fireSolarsResearch) {
                new EMTResearchItem(
                        "Fire Infused Solar Panels",
                        EMTResearchAspects.fireSolars,
                        -5,
                        -7,
                        1,
                        new ItemStack(EMTBlocks.solars[0], 1, 12)).setSecondary().setParents("Compressed Solars")
                                .registerResearchItem().setPages(
                                        new ResearchPage(""),
                                        new ResearchPage(EMTRecipes.fireSolar),
                                        new ResearchPage(EMTRecipes.doubleFireSolar),
                                        new ResearchPage(EMTRecipes.tripleFireSolar));
            }
        }

        if (!EMTConfigHandler.compressedSolarsResearch) {
            if (!EMTConfigHandler.airSolarsResearch) {
                new EMTResearchItem(
                        "Air Infused Solar Panels",
                        EMTResearchAspects.airSolars,
                        -4,
                        -6,
                        1,
                        new ItemStack(EMTBlocks.solars[0], 1, 15)).setSecondary().setParents("Compressed Solars")
                                .registerResearchItem().setPages(
                                        new ResearchPage(""),
                                        new ResearchPage(EMTRecipes.airSolar),
                                        new ResearchPage(EMTRecipes.doubleAirSolar),
                                        new ResearchPage(EMTRecipes.tripleAirSolar));
            }
        }

        if (!EMTConfigHandler.compressedSolarsResearch) {
            if (!EMTConfigHandler.earthSolarsResearch) {
                new EMTResearchItem(
                        "Earth Infused Solar Panels",
                        EMTResearchAspects.earthSolars,
                        -6,
                        -7,
                        1,
                        new ItemStack(EMTBlocks.solars[1], 1, 2)).setSecondary().setParents("Compressed Solars")
                                .registerResearchItem().setPages(
                                        new ResearchPage(""),
                                        new ResearchPage(EMTRecipes.earthSolar),
                                        new ResearchPage(EMTRecipes.doubleEarthSolar),
                                        new ResearchPage(EMTRecipes.tripleEarthSolar));
            }
        }

        if (!EMTConfigHandler.uuMInfusionResearch) {
            new EMTResearchItem(
                    "UU-Matter Infusion",
                    EMTResearchAspects.uuMInfusion,
                    5,
                    5,
                    3,
                    new ItemStack(EMTItems.itemEMTItems, 1, 15)).setParentsHidden("Electric Magic Tools").setConcealed()
                            .registerResearchItem().setPages(
                                    new ResearchPage(""),
                                    new ResearchPage(EMTRecipes.uuMCrystal),
                                    new ResearchPage(EMTRecipes.charcoalToCoal),
                                    new ResearchPage(EMTRecipes.glowstoneDustToBlock),
                                    new ResearchPage(EMTRecipes.stoneBricksToIronOre),
                                    new ResearchPage(EMTRecipes.arcaneStoneToCopperOre),
                                    new ResearchPage(EMTRecipes.arcaneStoneBricksToTinOre),
                                    new ResearchPage(EMTRecipes.amberBlockToLeadOre),
                                    new ResearchPage(EMTRecipes.amberBricksToUraniumOre),
                                    new ResearchPage(EMTRecipes.shardToResin),
                                    new ResearchPage(EMTRecipes.shardToRedstone),
                                    new ResearchPage(EMTRecipes.shardToLapis),
                                    new ResearchPage(EMTRecipes.ironToGold),
                                    new ResearchPage(EMTRecipes.goldToDiamond),
                                    new ResearchPage(EMTRecipes.diamondToUranium),
                                    new ResearchPage(EMTRecipes.uraniumToIridium));
        }

        if (!EMTConfigHandler.portableNodeResarch) {
            new EMTResearchItem(
                    "Portable Node",
                    EMTResearchAspects.portableNode,
                    2,
                    5,
                    3,
                    new ItemStack(EMTBlocks.portableNode)).setParents("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.portableNode));
        }

        if (!EMTConfigHandler.electricHoeGrowthResearch) {
            new EMTResearchItem(
                    "Electric Hoe of Growth",
                    EMTResearchAspects.electricHoeGrowth,
                    -1,
                    -4,
                    2,
                    new ItemStack(EMTItems.electricHoeGrowth)).setParents("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.electricHoeGrowth));
        }

        if (!EMTConfigHandler.chargeFocusResearch) {
            new EMTResearchItem(
                    "Wand Focus: Charging",
                    EMTResearchAspects.chargeFocus,
                    5,
                    3,
                    2,
                    new ItemStack(EMTItems.chargeFocus)).setParents("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.chargeFocus));
        }

        if (!EMTConfigHandler.wandChargeFocusResearch) {
            new EMTResearchItem(
                    "Wand Focus: Wand Charging",
                    EMTResearchAspects.wandChargeFocus,
                    7,
                    3,
                    3,
                    new ItemStack(EMTItems.wandChargeFocus)).setParents("Wand Focus: Charging").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.wandChargeFocus));
        }

        if (!EMTConfigHandler.inventoryChargingRing) {
            new EMTResearchItem(
                    "Inventory Charging Ring",
                    EMTResearchAspects.inventoryChargingRing,
                    0,
                    -5,
                    3,
                    new ItemStack(EMTItems.emtBauble, 1, 1)).setParentsHidden("Electric Magic Tools").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.inventoryChargingRing));
        }

        if (!EMTConfigHandler.armorChargingRing) {
            new EMTResearchItem(
                    "Armor Charging Ring",
                    EMTResearchAspects.armorChargingRing,
                    0,
                    -7,
                    3,
                    new ItemStack(EMTItems.emtBauble, 1, 0)).setSecondary().setParents("Inventory Charging Ring")
                            .setConcealed().registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.armorChargingRing));
        }

        if (!EMTConfigHandler.thaumiumWingResearch) {
            new EMTResearchItem(
                    "Thaumium Reinforced Wings",
                    EMTResearchAspects.thaumiumWing,
                    0,
                    4,
                    0,
                    new ItemStack(EMTItems.thaumiumWing)).setSecondary().setParents("Feather Wings").setConcealed()
                            .registerResearchItem().setPages(
                                    new ResearchPage(""),
                                    new ResearchPage(EMTRecipes.thaumiumWing),
                                    new ResearchPage(EMTRecipes.thaumiumWings));
        }

        if (!EMTConfigHandler.nanoWingResearch && !EMTConfigHandler.thaumiumWingResearch) {
            new EMTResearchItem(
                    "Nanosuit Wings",
                    EMTResearchAspects.nanoWing,
                    0,
                    6,
                    2,
                    new ItemStack(EMTItems.nanoWing)).setParents("Thaumium Reinforced Wings").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.nanoWings));
        }

        if (!EMTConfigHandler.quantumWingsResearch && !EMTConfigHandler.thaumiumWingResearch
                && !EMTConfigHandler.nanoWingResearch) {
            new EMTResearchItem(
                    "Quantum Wings",
                    EMTResearchAspects.quantumWing,
                    0,
                    8,
                    3,
                    new ItemStack(EMTItems.quantumWing)).setParents("Nanosuit Wings").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.quantumWings));
        }

        if (!EMTConfigHandler.infusedQuantumChestplate && !EMTConfigHandler.thaumiumWingResearch
                && !EMTConfigHandler.nanoWingResearch
                && !EMTConfigHandler.quantumWingsResearch) {
            new EMTResearchItem(
                    "Infused Quantum Armor",
                    EMTResearchAspects.quantumWing,
                    0,
                    10,
                    4,
                    new ItemStack(EMTItems.quantumArmor)).setParents("Quantum Wings").setConcealed()
                            .registerResearchItem()
                            .setPages(new ResearchPage(""), new ResearchPage(EMTRecipes.infusedQuantumArmor));
        }
        /***********************************/
    }
}
