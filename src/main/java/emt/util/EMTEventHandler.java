package emt.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import baubles.api.BaublesApi;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import emt.EMT;
import emt.init.EMTBlocks;
import emt.init.EMTItems;
import emt.item.armor.goggles.ItemElectricGoggles;
import emt.item.armor.wings.ItemFeatherWing;
import ic2.api.item.ElectricItem;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.monster.EntityTaintChicken;

public class EMTEventHandler {

    /**
     * Intercepts damage events for any living entity to apply custom effects for players wearing specific armor. This
     * method is a central handler for armor-based damage mitigation that cannot be handled by the ISpecialArmor
     * interface.
     * <p>
     * It triggers whenever an entity is about to be hurt and contains two primary logic blocks:
     * <ol>
     * <li>Reduces fall damage for players wearing Feather Wings.</li>
     * <li>Absorbs anvil/falling block damage for players wearing Electric Goggles, preventing the item from breaking
     * due to a hardcoded vanilla mechanic.</li>
     * </ol>
     *
     * @param e The LivingHurtEvent, containing information about the damage source, amount, and the entity being hurt.
     */
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent e) {
        if (!(e.entityLiving instanceof EntityPlayer player)) {
            return;
        }

        if (e.source == DamageSource.fall) {
            ItemStack chestplate = player.inventory.armorInventory[2];
            if (chestplate != null && chestplate.getItem() instanceof ItemFeatherWing) {
                e.ammount *= ((ItemFeatherWing) chestplate.getItem()).getFallDamageMult();
            }
        }

        else if (e.source == DamageSource.anvil || e.source == DamageSource.fallingBlock) {
            ItemStack helmet = player.getEquipmentInSlot(4); // 4 for Helmet

            if (helmet != null && helmet.getItem() instanceof ItemElectricGoggles goggles) {
                double energyRequired = e.ammount * goggles.getEnergyPerDamage();
                double currentEnergy = ElectricItem.manager.getCharge(helmet);

                if (currentEnergy >= energyRequired) {
                    ElectricItem.manager.discharge(helmet, energyRequired, Integer.MAX_VALUE, true, false, false);
                    e.setCanceled(true);
                } else if (currentEnergy > 0) {
                    float damageAbsorbed = (float) (currentEnergy / goggles.getEnergyPerDamage());
                    ElectricItem.manager.discharge(helmet, currentEnergy, Integer.MAX_VALUE, true, false, false);
                    e.ammount -= damageAbsorbed;
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent e) {

        ItemStack s = e.player.inventory.armorInventory[2];

        if (s == null || !(s.getItem() instanceof ItemFeatherWing) || e.phase == Phase.START) {
            return;
        }

        if (e.player.motionY > 0) {
            if (e.player.fallDistance > 0) e.player.fallDistance -= e.player.motionY;
        }
    }

    @SubscribeEvent
    public void onEntityLivingDrops(LivingDropsEvent event) {
        if (event.entityLiving instanceof EntityCreeper) {
            EntityCreeper creeper = (EntityCreeper) event.entityLiving;
            if (creeper.getPowered()) {
                event.entityLiving.entityDropItem(new ItemStack(EMTItems.itemEMTItems, 1, 6), 1);
            }
        }
        if (event.entityLiving instanceof EntityTaintChicken) {
            event.entityLiving.entityDropItem(
                    new ItemStack(EMTItems.itemEMTItems, event.entityLiving.worldObj.rand.nextInt(3), 13),
                    1);
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(EMT.MOD_ID)) {
            EMTConfigHandler.syncConfig();
            EMT.LOGGER.info("Refreshing configuration file.");
        }
    }

    @SubscribeEvent
    public void onSetEntityAttack(LivingSetAttackTargetEvent e) {
        if (e.entityLiving instanceof EntityLiving && e.target instanceof EntityPlayer) {
            IInventory inventory = BaublesApi.getBaubles((EntityPlayer) e.target);

            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);

                if (stack != null && stack.getItem() == EMTItems.onering) {
                    ((EntityLiving) e.entityLiving).setAttackTarget(null);
                }
            }
        }
    }

    @SubscribeEvent
    public void createCloud(PlayerInteractEvent e) {
        if (e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || e.world.isRemote) {
            return;
        }

        Block block = e.world.getBlock(e.x, e.y, e.z);
        int meta = e.world.getBlockMetadata(e.x, e.y, e.z);

        if (block != ConfigBlocks.blockAiry || meta != 1) {
            return;
        }

        ItemStack currentStack = e.entityPlayer.inventory.getCurrentItem();
        if (currentStack == null) {
            return;
        }

        double val = ElectricItem.manager.discharge(currentStack, 256, 4, false, true, false);
        if (val < 256) {
            return;
        }

        e.world.setBlock(e.x, e.y, e.z, EMTBlocks.electricCloud);
    }
}
