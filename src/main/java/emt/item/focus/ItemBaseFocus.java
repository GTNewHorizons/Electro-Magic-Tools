package emt.item.focus;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import thaumcraft.api.aspects.Aspect;
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

    boolean hasDepth() {
        return false;
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean isItemTool(ItemStack par1ItemStack) {
        return true;
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

    public boolean isUseItem(ItemStack stack) {
        return isVisCostPerTick(stack);
    }

    public boolean isVisCostPerTick() {
        return false;
    }

    @Override
    public ItemStack onFocusRightClick(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer,
            MovingObjectPosition paramMovingObjectPosition) {
        if (isUseItem(paramItemStack)) paramEntityPlayer.setItemInUse(paramItemStack, Integer.MAX_VALUE);
        return paramItemStack;
    }

    @Override
    public void onUsingFocusTick(ItemStack itemstack, EntityPlayer player, int count) {}

    @Override
    public void onPlayerStoppedUsingFocus(ItemStack itemstack, World world, EntityPlayer player, int count) {}

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "00";
    }

    @Override
    public boolean onFocusBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 5;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        AspectList cost = getVisCost(stack);
        if (cost != null && cost.size() > 0) {
            list.add(StatCollector.translateToLocal(isVisCostPerTick() ? "item.Focus.cost2" : "item.Focus.cost1"));
            for (Aspect aspect : cost.getAspectsSorted()) {
                float amount = cost.getAmount(aspect) / 100.0F;
                list.add(" " + '\u00a7' + aspect.getChatcolor() + aspect.getName() + '\u00a7' + "r x " + amount);
            }
        }
    }
}
