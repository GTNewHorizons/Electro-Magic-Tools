package emt.item.block;

import static emt.util.EMTTextHelper.localize;
import static gregtech.api.util.GT_Utility.formatNumbers;

import emt.EMT;
import emt.block.BlockBaseContainer;
import emt.tile.solar.Solars;
import emt.tile.solar.TileEntitySolarBase;
import emt.util.EMTConfigHandler;
import emt.util.EMTTextHelper;
import java.io.StringReader;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import org.apache.commons.io.input.ReaderInputStream;
import org.lwjgl.input.Keyboard;

public class ItemBlockSolars extends ItemBlock {

    int instance;

    public ItemBlockSolars(Block block) {
        super(block);
        setHasSubtypes(true);
        instance = ((BlockBaseContainer) block).instance;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        final String solarName = Solars.getNameFromSolar(this.instance, itemstack.getItemDamage());
        final String unlocalizedName =
                "tile." + EMT.MOD_ID + "." + Solars.getUnlocalizedName(this.instance, itemstack.getItemDamage());
        final String unlocalizedKey = unlocalizedName + ".name";
        if (!StatCollector.canTranslate(unlocalizedKey))
            StringTranslate.inject(new ReaderInputStream(new StringReader(unlocalizedKey + "=" + solarName)));
        return unlocalizedName;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advancedItemTooltips) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            TileEntitySolarBase te = Solars.getTileEntitySolarBase(instance, itemstack.getItemDamage());
            if (instance == 0) {
                switch (itemstack.getItemDamage()) {
                    case 0: {
                        list.add(formatNumbers(EMTConfigHandler.compressedSolarOutput)
                                + localize("tooltip.EMT.euPerTick"));
                        break;
                    }
                    case 1: {
                        list.add(formatNumbers(EMTConfigHandler.doubleCompressedSolarOutput)
                                + localize("tooltip.EMT.euPerTick"));
                        break;
                    }
                    case 2: {
                        list.add(formatNumbers(EMTConfigHandler.tripleCompressedSolarOutput)
                                + localize("tooltip.EMT.euPerTick"));
                        break;
                    }
                    case 3: { // aqua
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.compressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(2F * EMTConfigHandler.compressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.underwater"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(3F * EMTConfigHandler.compressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.raining"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(6F * EMTConfigHandler.compressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.thunder"));
                        break;
                    }
                    case 4: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.doubleCompressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(2F * EMTConfigHandler.doubleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.underwater"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(3F * EMTConfigHandler.doubleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.raining"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(6F * EMTConfigHandler.doubleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.thunder"));
                        break;
                    }
                    case 5: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.tripleCompressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(2F * EMTConfigHandler.tripleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.underwater"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(3F * EMTConfigHandler.tripleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.raining"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(6F * EMTConfigHandler.tripleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.thunder"));
                        break;
                    }

                    case 6: { // dark
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(0.75F * EMTConfigHandler.compressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.day"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(2F * EMTConfigHandler.compressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
                        break;
                    }
                    case 7: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(0.75F * EMTConfigHandler.doubleCompressedSolarOutput)
                                + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.day"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(2F * EMTConfigHandler.doubleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
                        break;
                    }
                    case 8: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(0.75F * EMTConfigHandler.tripleCompressedSolarOutput)
                                + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.day"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(2F * EMTConfigHandler.tripleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
                        break;
                    }
                    case 9: { // ordo
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(2F * EMTConfigHandler.compressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.day"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(0.75F * EMTConfigHandler.compressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
                        break;
                    }
                    case 10: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(2F * EMTConfigHandler.doubleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.day"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(0.75F * EMTConfigHandler.doubleCompressedSolarOutput)
                                + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
                        break;
                    }

                    case 11: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(2F * EMTConfigHandler.tripleCompressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.day"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY
                                + formatNumbers(0.75F * EMTConfigHandler.tripleCompressedSolarOutput)
                                + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
                        break;
                    }
                    case 12: { // fire
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.compressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.triple") + " "
                                + localize("tooltip.EMT.output.double.lava"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double") + " "
                                + localize("tooltip.EMT.output.double.nether"));
                        break;
                    }
                    case 13: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.doubleCompressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.triple") + " "
                                + localize("tooltip.EMT.output.double.lava"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double") + " "
                                + localize("tooltip.EMT.output.double.nether"));
                        break;
                    }
                    case 14: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.tripleCompressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.triple") + " "
                                + localize("tooltip.EMT.output.double.lava"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double") + " "
                                + localize("tooltip.EMT.output.double.nether"));
                        break;
                    }
                    case 15: { // aer
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.compressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half.height"));
                        break;
                    }
                    default: {
                        list.add(localize("tooltip.EMT.forget"));
                        break;
                    }
                }
            } else if (instance == 1) {
                switch (itemstack.getItemDamage()) {
                    case 0: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.doubleCompressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half.height"));
                        break;
                    }
                    case 1: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.tripleCompressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half.height"));
                        break;
                    }
                    case 2: { // terra
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.compressedSolarOutput) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half.low"));
                        break;
                    }
                    case 3: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.doubleCompressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half.low"));
                        break;
                    }
                    case 4: {
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(EMTConfigHandler.tripleCompressedSolarOutput)
                                + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half.low"));
                        break;
                    }
                    default: {
                        list.add(localize("tooltip.EMT.forget"));
                        break;
                    }
                }
            } else {
                switch (te.aspect) {
                    case 1:
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(2F * te.output) + " "
                                + localize("tooltip.EMT.euPerTick")
                                + " " + localize("tooltip.EMT.day"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(0.75F * te.output) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
                        break;
                    case 2:
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(0.75F * te.output) + " "
                                + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.day"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(2F * te.output) + " "
                                + localize("tooltip.EMT.euPerTick")
                                + " " + localize("tooltip.EMT.night"));
                        break;
                    case 3:
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(te.output) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half.height"));
                        break;
                    case 4:
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(te.output) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half.low"));
                        break;
                    case 5:
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(te.output) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(2F * te.output) + " "
                                + localize("tooltip.EMT.euPerTick")
                                + " " + localize("tooltip.EMT.underwater"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(3F * te.output) + " "
                                + localize("tooltip.EMT.euPerTick")
                                + " " + localize("tooltip.EMT.raining"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(6F * te.output) + " "
                                + localize("tooltip.EMT.euPerTick")
                                + " " + localize("tooltip.EMT.thunder"));
                        break;
                    case 6:
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(te.output) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.triple") + " "
                                + localize("tooltip.EMT.output.double.lava"));
                        list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": "
                                + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double") + " "
                                + localize("tooltip.EMT.output.double.nether"));
                        break;
                    default:
                        list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": "
                                + EMTTextHelper.LIGHT_GRAY + formatNumbers(te.output) + " "
                                + localize("tooltip.EMT.euPerTick"));
                        break;
                }
            }
            list.add(EMTTextHelper.BRIGHT_BLUE + StatCollector.translateToLocal("emt.OutputMax") + " "
                    + EMTTextHelper.LIGHT_GRAY + formatNumbers(te.calculateMaxVoltAmp()[0]) + "EU/t @ "
                    + formatNumbers(te.calculateMaxVoltAmp()[1])
                    + "A");
        } else list.add(StatCollector.translateToLocal("emt.PressShift"));
    }
}
