package emt.item.block;

import static emt.util.EMTTextHelper.localize;

import java.util.List;

import emt.block.BlockBaseContainer;
import emt.util.EMTConfigHandler;
import emt.util.EMTTextHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSolars extends ItemBlock {

	int instance;

	public ItemBlockSolars(Block block) {
		super(block);
		setHasSubtypes(true);
		instance = ((BlockBaseContainer) block).instance;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String name = "";

		if (instance == 0) {
			switch (itemstack.getItemDamage()) {
				case 0: {
					name = "compressed";
					break;
				}
				case 1: {
					name = "doublecompressed";
					break;
				}
				case 2: {
					name = "triplecompressed";
					break;
				}
				case 3: {
					name = "water";
					break;
				}
				case 4: {
					name = "doublewater";
					break;
				}
				case 5: {
					name = "triplewater";
					break;
				}
				case 6: {
					name = "dark";
					break;
				}
				case 7: {
					name = "doubledark";
					break;
				}
				case 8: {
					name = "tripledark";
					break;
				}
				case 9: {
					name = "order";
					break;
				}
				case 10: {
					name = "doubleorder";
					break;
				}
				case 11: {
					name = "tripleorder";
					break;
				}
				case 12: {
					name = "fire";
					break;
				}
				case 13: {
					name = "doublefire";
					break;
				}
				case 14: {
					name = "triplefire";
					break;
				}
				case 15: {
					name = "air";
					break;
				}
				default:
					name = "nothing";
					break;
			}
		}

		if (instance == 1) {
			switch (itemstack.getItemDamage()) {
				case 0: {
					name = "doubleair";
					break;
				}
				case 1: {
					name = "tripleair";
					break;
				}
				case 2: {
					name = "earth";
					break;
				}
				case 3: {
					name = "doubleearth";
					break;
				}
				case 4: {
					name = "tripleearth";
					break;
				}
				default:
					name = "nothing";
					break;
			}
		}

		return getUnlocalizedName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if (instance == 0) {
			switch (itemstack.getItemDamage()) {
				case 0: {
					list.add(EMTConfigHandler.compressedSolarOutput + localize("tooltip.EMT.euPerTick"));
					break;
				}
				case 1: {
					list.add(EMTConfigHandler.doubleCompressedSolarOutput + localize("tooltip.EMT.euPerTick"));
					break;
				}
				case 2: {
					list.add(EMTConfigHandler.tripleCompressedSolarOutput + localize("tooltip.EMT.euPerTick"));
					break;
				}
				case 3: {
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.compressedSolarOutput + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.raining"));
					break;
				}
				case 4: {
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.doubleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.raining"));
					break;
				}
				case 5: {
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.tripleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.raining"));
					break;
				}
				case 6: {
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.compressedSolarOutput + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
					break;
				}
				case 7: {
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.doubleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
					break;
				}
				case 8: {
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.tripleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick") + " " + localize("tooltip.EMT.night"));
					break;
				}
				case 9: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.compressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.triple"));
					break;
				}
				case 10: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.doubleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.triple"));
					break;
				}

				case 11: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.tripleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.triple"));
					break;
				}
				case 12: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.compressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double") + " " + localize("tooltip.EMT.output.double.nether"));
					break;
				}
				case 13: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.doubleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double") + " " + localize("tooltip.EMT.output.double.nether"));
					break;
				}
				case 14: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.tripleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double") + " " + localize("tooltip.EMT.output.double.lava"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double") + " " + localize("tooltip.EMT.output.double.nether"));
					break;
				}
				case 15: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.compressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half") + " " + localize("tooltip.EMT.output.double.half.height"));
					break;
				}
				default: {
					list.add(localize("tooltip.EMT.forget"));
					break;
				}
			}
		}

		if (instance == 1) {
			switch (itemstack.getItemDamage()) {
				case 0: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.doubleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half") + " " + localize("tooltip.EMT.output.double.half.height"));
					break;
				}
				case 1: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.tripleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half") + " " + localize("tooltip.EMT.output.double.half.height"));
					break;
				}
				case 2: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.compressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half") + " " + localize("tooltip.EMT.output.double.half.low"));
					break;
				}
				case 3: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.doubleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half") + " " + localize("tooltip.EMT.output.double.half.low"));
					break;
				}
				case 4: {
					list.add(EMTTextHelper.BRIGHT_BLUE + localize("tooltip.EMT.normalConditions") + ": " + EMTTextHelper.LIGHT_GRAY + EMTConfigHandler.tripleCompressedSolarOutput + " " + localize("tooltip.EMT.euPerTick"));
					list.add(EMTTextHelper.BRIGHT_GREEN + localize("tooltip.EMT.specialEffect") + ": " + EMTTextHelper.LIGHT_GRAY + localize("tooltip.EMT.output.double.half") + " " + localize("tooltip.EMT.output.double.half.low"));
					break;
				}
				default: {
					list.add(localize("tooltip.EMT.forget"));
					break;
				}
			}
		}
	}
}
