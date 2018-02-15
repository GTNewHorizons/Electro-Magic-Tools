package emt.tile.generator;

import emt.util.EMTEssentiasOutputs;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;

public class TileEntityLucrumGenerator extends TileEntityBaseGenerator {

	public TileEntityLucrumGenerator() {
		aspect = Aspect.GREED;
		generating = EMTEssentiasOutputs.outputs.get(aspect.getTag());
	}
	
	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile.EMT.essentia.lucrum.name");
	}
}
