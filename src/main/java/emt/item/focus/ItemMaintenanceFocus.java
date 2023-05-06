package emt.item.focus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.items.wands.ItemWandCasting;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Maintenance;

public class ItemMaintenanceFocus extends ItemBaseFocus {

    private static final AspectList visCost = new AspectList().add(Aspect.FIRE, 2500).add(Aspect.EARTH, 2500)
            .add(Aspect.ORDER, 2500);

    public ItemMaintenanceFocus() {
        super("maintenance");
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0xC0C0C0;
    }

    @Override
    public AspectList getVisCost(ItemStack focusstack) {
        return visCost;
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "MAINTENANCE";
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
        return new FocusUpgradeType[] { FocusUpgradeType.frugal };
    }

    @Override
    public ItemStack onFocusRightClick(ItemStack itemStack, World world, EntityPlayer player,
            MovingObjectPosition mop) {

        ItemWandCasting wand = (ItemWandCasting) itemStack.getItem();

        if (!world.isRemote) {
            if (!player.isSneaking()) {
                return itemStack;
            }
            TileEntity hatchCandidateTile = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            if (hatchCandidateTile instanceof BaseMetaTileEntity) {
                IMetaTileEntity hatchCandidateMetaTile = ((BaseMetaTileEntity) hatchCandidateTile).getMetaTileEntity();
                if (hatchCandidateMetaTile instanceof GT_MetaTileEntity_Hatch_Maintenance) {
                    GT_MetaTileEntity_Hatch_Maintenance hatch = (GT_MetaTileEntity_Hatch_Maintenance) hatchCandidateMetaTile;
                    if (player.capabilities.isCreativeMode
                            || wand.consumeAllVis(itemStack, player, getVisCost(itemStack), true, true)) {
                        hatch.mCrowbar = true;
                        hatch.mScrewdriver = true;
                        hatch.mHardHammer = true;
                        hatch.mSolderingTool = true;
                        hatch.mWrench = true;
                        hatch.mSoftHammer = true;
                    }
                }
            }
        }
        player.swingItem();
        return itemStack;
    }
}
