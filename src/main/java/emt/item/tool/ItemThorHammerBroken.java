package emt.item.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.util.EMTTextHelper;
import thaumcraft.api.IRepairable;
import thaumcraft.common.entities.projectile.EntityAlumentum;

public class ItemThorHammerBroken extends ItemSword implements IRepairable {

    public ItemThorHammerBroken() {
        super(ToolMaterial.EMERALD);
        this.setCreativeTab(EMT.TAB);
        this.setMaxDamage(1000);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.TEXTURE_PATH + ":hammer/taintedthorhammer");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        player.swingItem();
        world.spawnEntityInWorld(new EntityAlumentum(world, player.posX + 8, player.posY, player.posZ - 8));
        world.spawnEntityInWorld(new EntityAlumentum(world, player.posX - 8, player.posY, player.posZ + 8));
        world.spawnEntityInWorld(new EntityAlumentum(world, player.posX - 8, player.posY, player.posZ - 8));
        world.spawnEntityInWorld(new EntityAlumentum(world, player.posX + 8, player.posY, player.posZ + 8));
        world.spawnEntityInWorld(new EntityAlumentum(world, player.posX, player.posY + 4, player.posZ));
        world.spawnEntityInWorld(new EntityAlumentum(world, player.posX, player.posY + 8, player.posZ));

        if (!player.capabilities.isCreativeMode) {
            itemstack.damageItem(20, player);
        }
        return itemstack;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker) {
        entityliving.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 2F);
        itemstack.damageItem(1, attacker);
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List<String> list, boolean par4) {
        list.add(EMTTextHelper.localize("tooltip.EMT.hammer.broken.thor"));
        list.add(EMTTextHelper.localize("tooltip.EMT.hammer.broken.danger"));
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            list.add(EMTTextHelper.localize("tooltip.EMT.hammer.broken.plsNoRightClick"));
        } else {
            list.add(EMTTextHelper.localize("tooltip.EMT.hammer.broken.plsRightClick"));
        }
    }
}
