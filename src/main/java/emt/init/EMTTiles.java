package emt.init;

import cpw.mods.fml.common.registry.GameRegistry;
import emt.tile.TileElectricCloud;
import emt.tile.TileEntityEtherealMacerator;
import emt.tile.TileEntityIndustrialWandRecharge;
import emt.tile.TileEntityPortableNode;
import emt.tile.generator.TileEntityAerGenerator;
import emt.tile.generator.TileEntityArborGenerator;
import emt.tile.generator.TileEntityAuramGenerator;
import emt.tile.generator.TileEntityIgnisGenerator;
import emt.tile.generator.TileEntityLucrumGenerator;
import emt.tile.generator.TileEntityPotentiaGenerator;
import emt.tile.solar.air.TileEntityAirSolar;
import emt.tile.solar.air.TileEntityDoubleAirSolar;
import emt.tile.solar.air.TileEntityTripleAirSolar;
import emt.tile.solar.compressed.TileEntityCompressedSolar;
import emt.tile.solar.compressed.TileEntityDoubleCompressedSolar;
import emt.tile.solar.compressed.TileEntityTripleCompressedSolar;
import emt.tile.solar.dark.TileEntityDarkSolar;
import emt.tile.solar.dark.TileEntityDoubleDarkSolar;
import emt.tile.solar.dark.TileEntityTripleDarkSolar;
import emt.tile.solar.earth.TileEntityDoubleEarthSolar;
import emt.tile.solar.earth.TileEntityEarthSolar;
import emt.tile.solar.earth.TileEntityTripleEarthSolar;
import emt.tile.solar.fire.TileEntityDoubleFireSolar;
import emt.tile.solar.fire.TileEntityFireSolar;
import emt.tile.solar.fire.TileEntityTripleFireSolar;
import emt.tile.solar.order.TileEntityDoubleOrderSolar;
import emt.tile.solar.order.TileEntityOrderSolar;
import emt.tile.solar.order.TileEntityTripleOrderSolar;
import emt.tile.solar.water.TileEntityDoubleWaterSolar;
import emt.tile.solar.water.TileEntityTripleWaterSolar;
import emt.tile.solar.water.TileEntityWaterSolar;

public class EMTTiles {

	public static void registerTileEntities() {
		
		//Essentia Gens
		GameRegistry.registerTileEntity(TileEntityPotentiaGenerator.class, "tileentitypotentiagenerator");
		GameRegistry.registerTileEntity(TileEntityIgnisGenerator.class, "tileentityignisgenerator");
		GameRegistry.registerTileEntity(TileEntityAuramGenerator.class, "tileentityauramgenerator");
		GameRegistry.registerTileEntity(TileEntityArborGenerator.class, "tileentityarborgenerator");
		GameRegistry.registerTileEntity(TileEntityAerGenerator.class, "tileentityaergenerator");
		GameRegistry.registerTileEntity(TileEntityLucrumGenerator.class, "tileentityperditiogenerator");
		
		//Machinery
		GameRegistry.registerTileEntity(TileEntityIndustrialWandRecharge.class, "tileentityindustrialwandrecharge");
		GameRegistry.registerTileEntity(TileEntityEtherealMacerator.class, "tileentityetherealmacerator");
		
		//Solars
		GameRegistry.registerTileEntity(TileEntityCompressedSolar.class, "tileentitycompressedsolar");
		GameRegistry.registerTileEntity(TileEntityDoubleCompressedSolar.class, "tileentitydoublecompressedsolar");
		GameRegistry.registerTileEntity(TileEntityTripleCompressedSolar.class, "tileentitytriplecompressedsolar");
		GameRegistry.registerTileEntity(TileEntityWaterSolar.class, "tileentitywatersolar");
		GameRegistry.registerTileEntity(TileEntityDoubleWaterSolar.class, "tileentitydoublewatersolar");
		GameRegistry.registerTileEntity(TileEntityTripleWaterSolar.class, "tileentitytriplewatersolar");
		GameRegistry.registerTileEntity(TileEntityDarkSolar.class, "tileentitydarksolar");
		GameRegistry.registerTileEntity(TileEntityDoubleDarkSolar.class, "tileentitydoubledarksolar");
		GameRegistry.registerTileEntity(TileEntityTripleDarkSolar.class, "tileentitytripledarksolar");
		GameRegistry.registerTileEntity(TileEntityOrderSolar.class, "tileentityordersolar");
		GameRegistry.registerTileEntity(TileEntityDoubleOrderSolar.class, "tileentitydoubleordersolar");
		GameRegistry.registerTileEntity(TileEntityTripleOrderSolar.class, "tileentitytripleordersolar");
		GameRegistry.registerTileEntity(TileEntityFireSolar.class, "tileentityfiresolar");
		GameRegistry.registerTileEntity(TileEntityDoubleFireSolar.class, "tileentitydoublefiresolar");
		GameRegistry.registerTileEntity(TileEntityTripleFireSolar.class, "tileentitytriplefiresolar");
		GameRegistry.registerTileEntity(TileEntityAirSolar.class, "tileentityairsolar");
		GameRegistry.registerTileEntity(TileEntityDoubleAirSolar.class, "tileentitydoubleairsolar");
		GameRegistry.registerTileEntity(TileEntityTripleAirSolar.class, "tileentitytripleairsolar");
		GameRegistry.registerTileEntity(TileEntityEarthSolar.class, "tileentityearthsolar");
		GameRegistry.registerTileEntity(TileEntityDoubleEarthSolar.class, "tileentitydoubleearthsolar");
		GameRegistry.registerTileEntity(TileEntityTripleEarthSolar.class, "tileentitytripleearthsolar");
		
		//Random Stuff
		GameRegistry.registerTileEntity(TileEntityPortableNode.class, "tileentityportablenode");
		GameRegistry.registerTileEntity(TileElectricCloud.class, "tileEntityElectricCloud");
	}
}
