package emt.tile.generator;

import emt.util.EMTEssentiasOutputs;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;

public class TileEntityArborGenerator extends TileEntityBaseGenerator {

	public TileEntityArborGenerator() {
		aspect = Aspect.TREE;
		generating = EMTEssentiasOutputs.outputs.get(aspect.getTag());
	}
	
	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile.EMT.essentia.arbor.name");
	}
}
