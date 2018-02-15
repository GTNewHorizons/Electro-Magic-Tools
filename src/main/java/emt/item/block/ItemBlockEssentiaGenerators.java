package emt.item.block;

import java.util.List;

import org.lwjgl.input.Keyboard;

import emt.util.EMTConfigHandler;
import emt.util.EMTEssentiasOutputs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;

public class ItemBlockEssentiaGenerators extends ItemBlock {

	public ItemBlockEssentiaGenerators(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
		case 0:
			name = "potentia";
			break;
		case 1:
			name = "ignis";
			break;
		case 2:
			name = "auram";
			break;
		case 3:
			name = "arbor";
			break;
		case 4:
			name = "aer";
			break;
		case 5:
			name = "lucrum";
			break;
		default:
			name = "nothing";
		}
		return getUnlocalizedName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
        	switch (is.getItemDamage()) {
        case 0:
            list.add(StatCollector.translateToLocal("emt.Output")+Integer.toString((int)(EMTEssentiasOutputs.outputs.get(Aspect.ENERGY.getTag())/20/20)) +" EU/t");
        	list.add(StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")+": "+Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.ENERGY.getTag()))) +" EU");
            
        	break;
        case 1:
        	list.add(StatCollector.translateToLocal("emt.Output")+Integer.toString((int)(EMTEssentiasOutputs.outputs.get(Aspect.FIRE.getTag())/20/20)) +" EU/t");
        	list.add(StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")+": "+Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.FIRE.getTag()))) +" EU");
        	break;
        case 2:
        	list.add(StatCollector.translateToLocal("emt.Output")+Integer.toString((int)(EMTEssentiasOutputs.outputs.get(Aspect.AURA.getTag())/20/20)) +" EU/t");
        	list.add(StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")+": "+Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.AURA.getTag()))) +" EU");
        	
        	break;
        case 3:
        	list.add(StatCollector.translateToLocal("emt.Output")+Integer.toString((int)(EMTEssentiasOutputs.outputs.get(Aspect.TREE.getTag())/20/20)) +" EU/t");
        	list.add(StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")+": "+Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.FIRE.getTag()))) +" EU");
        	
        	break;
        case 4:
        	list.add(StatCollector.translateToLocal("emt.Output")+Integer.toString((int)(EMTEssentiasOutputs.outputs.get(Aspect.AIR.getTag())/20/20)) +" EU/t");
        	list.add(StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")+": "+Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.AIR.getTag()))) +" EU");
        	
        	break;
        case 5:
        	list.add(StatCollector.translateToLocal("emt.Output")+Integer.toString((int)(EMTEssentiasOutputs.outputs.get(Aspect.GREED.getTag())/20/20)) +" EU/t");
        	list.add(StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")+": "+Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.GREED.getTag()))) +" EU");
        	
        	break;
        default:
        	break;
        	}
            list.add(StatCollector.translateToLocal("emt.Storage")+Double.toString(EMTConfigHandler.EssentiaGeneratorStorage)+" EU");
        } else 
            list.add(StatCollector.translateToLocal("emt.PressShift"));
    }
}
