package emt.tile.generator;

import emt.util.EMTEssentiasOutputs;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;

public class TileEntityPotentiaGenerator extends TileEntityBaseGenerator {

	public TileEntityPotentiaGenerator() {
		aspect = Aspect.ENERGY;
		generating = EMTEssentiasOutputs.outputs.get(aspect.getTag());
	}
	
	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile.EMT.essentia.potentia.name");
	}
}
