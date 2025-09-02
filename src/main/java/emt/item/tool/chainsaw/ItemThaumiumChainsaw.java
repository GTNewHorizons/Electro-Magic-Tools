package emt.item.tool.chainsaw;

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
import ic2.api.item.ElectricItem;

public class ItemThaumiumChainsaw extends ItemDiamondChainsaw {

    public ItemThaumiumChainsaw() {
        this.efficiencyOnProperMaterial = 21F;
        this.setCreativeTab(EMT.TAB);
        this.setMaxDamage(27);
        this.setMaxStackSize(1);
        maxCharge = 100000;
        cost = 250;
        hitCost = 350;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.RESOURCE_PATH + ":tools/chainsaw_thaumium");
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int par4, int par5, int par6,
            EntityLivingBase entityLiving) {
        ElectricItem.manager.use(stack, cost, entityLiving);
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker) {
        if (ElectricItem.manager.use(itemstack, hitCost, attacker)) {
            entityliving.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 12F);
        }
        return false;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 600;
    }
}
