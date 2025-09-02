package emt.item.tool.omnitool;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.util.EMTConfigHandler;
import ic2.api.item.ElectricItem;

public class ItemOmnitoolDiamond extends ItemOmnitoolIron {

    public ItemOmnitoolDiamond() {
        super();
        this.efficiencyOnProperMaterial = 16F;
        this.setCreativeTab(EMT.TAB);
        this.setMaxStackSize(1);
        if (!EMTConfigHandler.toolsInBore) {
            this.setMaxDamage(27);
        } else {
            this.setMaxDamage(350);
        }
        maxCharge = 100000;
        hitCost = 250;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.RESOURCE_PATH + ":tools/omnitool_diamond");
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int par4, int par5, int par6,
            EntityLivingBase entityLiving) {
        if (!EMTConfigHandler.toolsInBore) {
            cost = 200;
        } else {
            cost = 1;
        }
        ElectricItem.manager.use(stack, cost, entityLiving);
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker) {
        if (ElectricItem.manager.use(itemstack, hitCost, attacker)) {
            entityliving.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 10F);
        }
        return false;
    }

    /* IC2 API METHODS */
    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 400;
    }
}
