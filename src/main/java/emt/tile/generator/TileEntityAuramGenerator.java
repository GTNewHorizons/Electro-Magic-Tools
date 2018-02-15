package emt.tile.generator;

import emt.util.EMTEssentiasOutputs;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;

public class TileEntityAuramGenerator extends TileEntityBaseGenerator {

	public TileEntityAuramGenerator() {
		aspect = Aspect.AURA;
		generating = EMTEssentiasOutputs.outputs.get(aspect.getTag());
	}
	
	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile.EMT.essentia.auram.name");
	}
}
