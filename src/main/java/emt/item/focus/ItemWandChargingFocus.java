package emt.item.focus;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import emt.util.EMTConfigHandler;
import ic2.api.item.ElectricItem;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemWandChargingFocus extends ItemBaseFocus {

    AspectList visCost = new AspectList().add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.AIR, 10)
            .add(Aspect.EARTH, 10).add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10);

    public ItemWandChargingFocus() {
        super("chargeWand");
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0xFFFF450;
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "ELECTRICCHARGING" + super.getSortingHelper(itemstack);
    }

    @Override
    public boolean isVisCostPerTick(ItemStack focusstack) {
        return true;
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        AspectList actualCost = new AspectList();
        for (Entry<Aspect, Integer> e : visCost.aspects.entrySet()) actualCost.add(
                e.getKey(),
                (int) (e.getValue() * Math.pow(1.1, getUpgradeLevel(stack, FocusUpgradeType.potency))));
        return actualCost;
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
        return new FocusUpgradeType[] { FocusUpgradeType.potency, FocusUpgradeType.frugal };
    }

    @Override
    public void onUsingFocusTick(ItemStack itemstack, EntityPlayer player, int integer) {

        if (!player.worldObj.isRemote) {
            ItemWandCasting wandItem = (ItemWandCasting) itemstack.getItem();
            if (wandIsFull(itemstack, wandItem)) {
                return;
            }

            int cost = getCost(itemstack) / 4;
            for (ItemStack stack : player.inventory.armorInventory) {
                if (stack == null || ElectricItem.manager.getCharge(stack) < cost) {
                    return;
                }
            }
            for (ItemStack stack : player.inventory.armorInventory) {
                ElectricItem.manager.use(stack, cost, player);
            }
            int amount = (int) (100 * Math.pow(1.1, getUpgradeLevel(itemstack, FocusUpgradeType.potency)));
            wandItem.addRealVis(itemstack, Aspect.ORDER, amount, true);
            wandItem.addRealVis(itemstack, Aspect.FIRE, amount, true);
            wandItem.addRealVis(itemstack, Aspect.ENTROPY, amount, true);
            wandItem.addRealVis(itemstack, Aspect.WATER, amount, true);
            wandItem.addRealVis(itemstack, Aspect.EARTH, amount, true);
            wandItem.addRealVis(itemstack, Aspect.AIR, amount, true);
        }
    }

    private boolean wandIsFull(ItemStack itemstack, ItemWandCasting wandItem) {
        int size = wandItem.getMaxVis(itemstack);
        for (Aspect a : Aspect.getPrimalAspects()) {
            if (wandItem.getVis(itemstack, a) < size) {
                return false;
            }
        }
        return true;
    }

    /**
     * Base cost from config with a 10% discount per level of frugal.
     */
    private int getCost(ItemStack stack) {
        return (int) (EMTConfigHandler.wandChargeFocusCost
                * (1.0 - (0.1 * getUpgradeLevel(stack, FocusUpgradeType.frugal))));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("item.EMT.focus.EU.cost.info"));
        list.add(StatCollector.translateToLocalFormatted("item.EMT.focus.EU.cost.tick", getCost(stack)));
        super.addInformation(stack, player, list, par4);
    }
}
