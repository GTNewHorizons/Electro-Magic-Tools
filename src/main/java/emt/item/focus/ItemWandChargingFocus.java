package emt.item.focus;

import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
        return "ELECTRICCHARGING";
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
        return visCost;
    }

    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
        return new FocusUpgradeType[] { FocusUpgradeType.potency, FocusUpgradeType.frugal };
    }

    public boolean canApplyUpgrade(ItemStack focusstack, EntityPlayer player, FocusUpgradeType type, int rank) {
        return true;
    }

    @Override
    public void onUsingFocusTick(ItemStack itemstack, EntityPlayer player, int integer) {

        if (!player.worldObj.isRemote) {
            ItemWandCasting wandItem = (ItemWandCasting) itemstack.getItem();

            ItemStack armor = player.inventory.armorInventory[1];
            if (armor != null) {
                if ((ElectricItem.manager.use(armor, EMTConfigHandler.wandChargeFocusCost / 4, player)
                        && (ElectricItem.manager.use(armor, EMTConfigHandler.wandChargeFocusCost / 4, player))
                        && (ElectricItem.manager.use(armor, EMTConfigHandler.wandChargeFocusCost / 4, player))
                        && (ElectricItem.manager.use(armor, EMTConfigHandler.wandChargeFocusCost / 4, player)))) {
                    int amount = (int) (100 * Math.pow(1.1, getUpgradeLevel(itemstack, FocusUpgradeType.potency)));
                    wandItem.addRealVis(itemstack, Aspect.ORDER, amount, true);
                    wandItem.addRealVis(itemstack, Aspect.FIRE, amount, true);
                    wandItem.addRealVis(itemstack, Aspect.ENTROPY, amount, true);
                    wandItem.addRealVis(itemstack, Aspect.WATER, amount, true);
                    wandItem.addRealVis(itemstack, Aspect.EARTH, amount, true);
                    wandItem.addRealVis(itemstack, Aspect.AIR, amount, true);
                }
            }
        }
    }
}
