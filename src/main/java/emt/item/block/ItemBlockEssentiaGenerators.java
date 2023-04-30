package emt.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import emt.util.EMTConfigHandler;
import emt.util.EMTEssentiasOutputs;
import emt.util.EMTTextHelper;
import gregtech.api.enums.GT_Values;
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

    private long getOutputAmperage(double EU) {
        return (long) (1L + (EU / 20.0D / 20.0D / GT_Values.V[2]));
    }

    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            switch (is.getItemDamage()) {
                case 0:
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Generating")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Integer.toString(
                                            (int) (EMTEssentiasOutputs.outputs.get(Aspect.ENERGY.getTag()) / 20 / 20))
                                    + " EU/t");
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Output")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + "128 EU/t @ "
                                    + getOutputAmperage(EMTEssentiasOutputs.outputs.get(Aspect.ENERGY.getTag()))
                                    + " A");
                    list.add(
                            EMTTextHelper.BRIGHT_GREEN
                                    + StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")
                                    + ": "
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.ENERGY.getTag())))
                                    + " EU");
                    break;

                case 1:
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Generating")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Integer.toString(
                                            (int) (EMTEssentiasOutputs.outputs.get(Aspect.FIRE.getTag()) / 20 / 20))
                                    + " EU/t");
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Output")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + "128 EU/t @ "
                                    + getOutputAmperage(EMTEssentiasOutputs.outputs.get(Aspect.FIRE.getTag()))
                                    + " A");
                    list.add(
                            EMTTextHelper.BRIGHT_GREEN
                                    + StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")
                                    + ": "
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.FIRE.getTag())))
                                    + " EU");
                    break;

                case 2:
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Generating")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Integer.toString(
                                            (int) (EMTEssentiasOutputs.outputs.get(Aspect.AURA.getTag()) / 20 / 20))
                                    + " EU/t");
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Output")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + "128 EU/t @ "
                                    + getOutputAmperage(EMTEssentiasOutputs.outputs.get(Aspect.AURA.getTag()))
                                    + " A");
                    list.add(
                            EMTTextHelper.BRIGHT_GREEN
                                    + StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")
                                    + ": "
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.AURA.getTag())))
                                    + " EU");
                    break;

                case 3:
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Generating")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Integer.toString(
                                            (int) (EMTEssentiasOutputs.outputs.get(Aspect.TREE.getTag()) / 20 / 20))
                                    + " EU/t");
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Output")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + "128 EU/t @ "
                                    + getOutputAmperage(EMTEssentiasOutputs.outputs.get(Aspect.TREE.getTag()))
                                    + " A");
                    list.add(
                            EMTTextHelper.BRIGHT_GREEN
                                    + StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")
                                    + ": "
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.FIRE.getTag())))
                                    + " EU");
                    break;

                case 4:
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Generating")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Integer.toString(
                                            (int) (EMTEssentiasOutputs.outputs.get(Aspect.AIR.getTag()) / 20 / 20))
                                    + " EU/t");
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Output")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + "128 EU/t @ "
                                    + getOutputAmperage(EMTEssentiasOutputs.outputs.get(Aspect.AIR.getTag()))
                                    + " A");
                    list.add(
                            EMTTextHelper.BRIGHT_GREEN
                                    + StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")
                                    + ": "
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.AIR.getTag())))
                                    + " EU");
                    break;

                case 5:
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Generating")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Integer.toString(
                                            (int) (EMTEssentiasOutputs.outputs.get(Aspect.GREED.getTag()) / 20 / 20))
                                    + " EU/t");
                    list.add(
                            EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.Output")
                                    + EMTTextHelper.LIGHT_GRAY
                                    + "128 EU/t @ "
                                    + getOutputAmperage(EMTEssentiasOutputs.outputs.get(Aspect.GREED.getTag()))
                                    + " A");
                    list.add(
                            EMTTextHelper.BRIGHT_GREEN
                                    + StatCollector.translateToLocal("gui.EMT.book.aspect.output.essentia.eu")
                                    + ": "
                                    + EMTTextHelper.LIGHT_GRAY
                                    + Double.toString((EMTEssentiasOutputs.outputs.get(Aspect.GREED.getTag())))
                                    + " EU");
                    break;

                default:
                    break;
            }
            list.add(
                    EMTTextHelper.YELLOW + StatCollector.translateToLocal("emt.Storage")
                            + EMTTextHelper.LIGHT_GRAY
                            + Double.toString(EMTConfigHandler.EssentiaGeneratorStorage)
                            + " EU");
        } else list.add(StatCollector.translateToLocal("emt.PressShift"));
    }
}
