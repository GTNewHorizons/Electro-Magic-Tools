package emt.item.focus;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import emt.entity.EntityShield;
import ic2.core.IC2;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemShieldFocus extends ItemBaseFocus {

    private static final AspectList visCost = new AspectList().add(Aspect.ORDER, 5).add(Aspect.WATER, 5)
            .add(Aspect.AIR, 5);

    public ItemShieldFocus() {
        super("shield");
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0x1E90FF;
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        return visCost;
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "SHIELD" + super.getSortingHelper(itemstack);
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
        return new FocusUpgradeType[] { FocusUpgradeType.frugal };
    }

    @Override
    public boolean isVisCostPerTick(ItemStack stack) {
        return true;
    }

    @Override
    public void onUsingFocusTick(ItemStack itemstack, EntityPlayer player, int count) {
        ItemWandCasting wand = (ItemWandCasting) itemstack.getItem();
        List<PotionEffect> potionEffects = new ArrayList<>(player.getActivePotionEffects());
        for (PotionEffect effect : potionEffects) {
            IC2.platform.removePotion(player, effect.getPotionID());
        }
        if (!player.capabilities.isCreativeMode
                && !wand.consumeAllVis(itemstack, player, getVisCost(itemstack), true, false)) {
            player.stopUsingItem();
        }
    }

    @Override
    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player,
            MovingObjectPosition paramMovingObjectPosition) {
        ItemWandCasting wand = (ItemWandCasting) itemstack.getItem();
        player.setItemInUse(itemstack, Integer.MAX_VALUE);
        if (!world.isRemote && (player.capabilities.isCreativeMode
                || wand.consumeAllVis(itemstack, player, getVisCost(itemstack), true, false))) {
            EntityShield shield = new EntityShield(world, player);
            world.spawnEntityInWorld(shield);
        }
        return itemstack;
    }
}
