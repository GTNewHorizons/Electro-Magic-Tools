package emt.tile.generator;

import emt.util.EMTEssentiasOutputs;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;

public class TileEntityIgnisGenerator extends TileEntityBaseGenerator {

	public TileEntityIgnisGenerator() {
		aspect = Aspect.FIRE;
		generating = EMTEssentiasOutputs.outputs.get(aspect.getTag());
	}
	
	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile.EMT.essentia.ignis.name");
	}
}
