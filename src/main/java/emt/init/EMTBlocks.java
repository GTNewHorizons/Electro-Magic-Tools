package emt.init;

import static thaumcraft.api.ThaumcraftApi.registerObjectTag;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;
import emt.block.BlockElectricCloud;
import emt.block.BlockEssentiaGenerators;
import emt.block.BlockMachines;
import emt.block.BlockPortableNode;
import emt.block.BlockShield;
import emt.block.BlockSolars;
import emt.item.block.ItemBlockElectricCloud;
import emt.item.block.ItemBlockEssentiaGenerators;
import emt.item.block.ItemBlockMachines;
import emt.item.block.ItemBlockSolars;
import emt.tile.solar.Solars;
import ic2.api.item.IC2Items;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class EMTBlocks {

    public static Block portableNode, essentiaGens, shield, emtMachines, electricCloud;

    public static Block[] solars = new Block[Solars.getCountOfInstances()];

    public static void registerBlocks() {
        portableNode = new BlockPortableNode("portablenode");
        GameRegistry.registerBlock(portableNode, "PortableNode");

        shield = new BlockShield("shield");
        GameRegistry.registerBlock(shield, "ShieldBlock");

        for (int i = 0; i < solars.length; i++) {
            if (i == 0) {
                solars[i] = new BlockSolars("solar", Solars.getCountOfMetas(i), i);
                GameRegistry.registerBlock(solars[i], ItemBlockSolars.class, "EMTSolars");
            } else {
                solars[i] = new BlockSolars("solar" + (i + 1), Solars.getCountOfMetas(i), i);
                GameRegistry.registerBlock(solars[i], ItemBlockSolars.class, "EMTSolars" + (i + 1));
            }
        }

        emtMachines = new BlockMachines("machine");
        GameRegistry.registerBlock(emtMachines, ItemBlockMachines.class, "EMTMachines");

        essentiaGens = new BlockEssentiaGenerators("essentia");
        GameRegistry.registerBlock(essentiaGens, ItemBlockEssentiaGenerators.class, "EssentiaGenerators");

        electricCloud = new BlockElectricCloud("electricCloud");
        GameRegistry.registerBlock(electricCloud, ItemBlockElectricCloud.class, "electricCloud");
    }

    public static void addAspects() {
        registerObjectTag(IC2Items.getItem("rubberTrampoline"), new AspectList().add(Aspect.AIR, 5));
        registerObjectTag(
                IC2Items.getItem("ironFence"),
                new AspectList().add(Aspect.ENERGY, 1).add(Aspect.METAL, 2).add(Aspect.WEATHER, 1));
        registerObjectTag(
                IC2Items.getItem("reinforcedGlass"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.COLD, 2));
        registerObjectTag(IC2Items.getItem("scaffold"), new AspectList().add(Aspect.TREE, 4));
        registerObjectTag(IC2Items.getItem("ironScaffold"), new AspectList().add(Aspect.METAL, 4));
        registerObjectTag(
                IC2Items.getItem("generator"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.ENERGY, 2).add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("waterMill"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.ENERGY, 1).add(Aspect.WATER, 2)
                        .add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("solarPanel"),
                new AspectList().add(Aspect.METAL, 1).add(Aspect.ENERGY, 1).add(Aspect.LIGHT, 2)
                        .add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("windMill"),
                new AspectList().add(Aspect.ENERGY, 1).add(Aspect.WEATHER, 3).add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("batBox"),
                new AspectList().add(Aspect.METAL, 1).add(Aspect.ENERGY, 3).add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("cesuUnit"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.ENERGY, 4).add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("mfeUnit"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.ENERGY, 5).add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("mfsUnit"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.ENERGY, 7).add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("ironFurnace"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.FIRE, 2).add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("electroFurnace"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.ENERGY, 1).add(Aspect.FIRE, 3)
                        .add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("macerator"),
                new AspectList().add(Aspect.METAL, 1).add(Aspect.ENERGY, 2).add(Aspect.MINE, 2)
                        .add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("extractor"),
                new AspectList().add(Aspect.METAL, 2).add(Aspect.ENERGY, 1).add(Aspect.CRAFT, 2)
                        .add(Aspect.MECHANISM, 1));
        registerObjectTag(
                IC2Items.getItem("compressor"),
                new AspectList().add(Aspect.METAL, 1).add(Aspect.ENERGY, 2).add(Aspect.VOID, 3)
                        .add(Aspect.MECHANISM, 1));
    }
}
