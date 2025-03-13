package emt.item.focus;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;

public abstract class ItemBaseFocus extends ItemFocusBasic {

    public IIcon ornament;
    String textureName = "";

    public ItemBaseFocus(String unlocName) {
        super();
        setUnlocalizedName(EMT.MOD_ID + ".focus." + unlocName);
        setCreativeTab(EMT.TAB);
        setMaxDamage(1);
        setNoRepair();
        setMaxStackSize(1);
        this.textureName = unlocName;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icon = ir.registerIcon(EMT.TEXTURE_PATH + ":" + "focus_" + textureName);
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.rare;
    }

    @Override
    public IIcon getOrnament(ItemStack focusstack) {
        return ornament;
    }

    @Override
    public AspectList getVisCost(ItemStack focusstack) {
        return new AspectList();
    }

    @Override
    public ItemStack onFocusRightClick(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer,
            MovingObjectPosition paramMovingObjectPosition) {
        if (isVisCostPerTick(paramItemStack)) paramEntityPlayer.setItemInUse(paramItemStack, Integer.MAX_VALUE);
        return paramItemStack;
    }

    @Override
    public void onUsingFocusTick(ItemStack itemstack, EntityPlayer player, int count) {}

    @Override
    public void onPlayerStoppedUsingFocus(ItemStack itemstack, World world, EntityPlayer player, int count) {}

    @Override
    public boolean onFocusBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        return false;
    }

}
