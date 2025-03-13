package emt.item.focus;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import emt.entity.EntityEnergyBall;
import ic2.api.item.ElectricItem;
import thaumcraft.api.wands.FocusUpgradeType;

public class ItemEnergyBallFocus extends ItemBaseFocus {

    public ItemEnergyBallFocus() {
        super("energyBall");
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0x0000FF;
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "ENERGYBALL" + super.getSortingHelper(itemstack);
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
        return new FocusUpgradeType[] { FocusUpgradeType.frugal };
    }

    @Override
    public ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer player, MovingObjectPosition mop) {
        if (world.isRemote) {
            return stack;
        }

        ItemStack armor = player.inventory.armorInventory[2];
        if (armor == null) {
            return stack;
        }

        int cost = getCost(stack);
        double val = ElectricItem.manager.discharge(armor, cost, 4, true, false, false);

        if (val < cost) {
            return stack;
        }

        float rotX = (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)) * 100;
        float rotY = (-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI)) * 100;
        float rotZ = (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI)) * 100;
        world.spawnEntityInWorld(new EntityEnergyBall(world, player, rotX, rotY, rotZ));

        return stack;
    }

    /**
     * Base cost of 5120 with a 10% discount per level of frugal.
     */
    private int getCost(ItemStack stack) {
        return (int) (5120 * (1.0 - (0.1 * getUpgradeLevel(stack, FocusUpgradeType.frugal))));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("item.EMT.focus.EU.cost.info"));
        list.add(StatCollector.translateToLocalFormatted("item.EMT.focus.EU.cost.once", getCost(stack)));
        this.addFocusInformation(stack, player, list, par4);
    }
}
