package emt.init;

import cpw.mods.fml.common.registry.GameRegistry;
import emt.gthandler.common.implementations.automation.EssentiaFiller;
import emt.tile.TileElectricCloud;
import emt.tile.TileEntityEtherealMacerator;
import emt.tile.TileEntityIndustrialWandRecharge;
import emt.tile.TileEntityPortableNode;
import emt.tile.generator.TileEntityBaseGenerator;
import emt.tile.solar.TileEntitySolarBase;

public class EMTTiles {

    public static void registerTileEntities() {


        //Essentia Gens
        //legacy support
        GameRegistry.registerTileEntity(TileEntityBaseGenerator.class, "tileentitypotentiagenerator");
        GameRegistry.registerTileEntity(TileEntityBaseGenerator.class, "tileentityignisgenerator");
        GameRegistry.registerTileEntity(TileEntityBaseGenerator.class, "tileentityauramgenerator");
        GameRegistry.registerTileEntity(TileEntityBaseGenerator.class, "tileentityarborgenerator");
        GameRegistry.registerTileEntity(TileEntityBaseGenerator.class, "tileentityaergenerator");
        GameRegistry.registerTileEntity(TileEntityBaseGenerator.class, "tileentitylucrumgenerator");

        //new TEs will be called TileEntityEssentiaGenerator
        GameRegistry.registerTileEntity(TileEntityBaseGenerator.class, "TileEntityEssentiaGenerator");

        //Machinery
        GameRegistry.registerTileEntity(TileEntityIndustrialWandRecharge.class, "tileentityindustrialwandrecharge");
        GameRegistry.registerTileEntity(TileEntityEtherealMacerator.class, "tileentityetherealmacerator");
        GameRegistry.registerTileEntity(EssentiaFiller.class, "EssentiaFillerTE");

        //Solars
        //legacy support
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitycompressedsolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitydoublecompressedsolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitytriplecompressedsolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitywatersolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitydoublewatersolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitytriplewatersolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitydarksolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitydoubledarksolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitytripledarksolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentityordersolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitydoubleordersolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitytripleordersolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentityfiresolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitydoublefiresolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitytriplefiresolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentityairsolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitydoubleairsolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitytripleairsolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentityearthsolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitydoubleearthsolar");
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "tileentitytripleearthsolar");

        //new TEs will be called TileEntityXCompressedSolar
        GameRegistry.registerTileEntity(TileEntitySolarBase.class, "TileEntityXCompressedSolar");

        //Random Stuff
        GameRegistry.registerTileEntity(TileEntityPortableNode.class, "tileentityportablenode");
        GameRegistry.registerTileEntity(TileElectricCloud.class, "tileEntityElectricCloud");
    }
}
