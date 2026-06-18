package emt.item.armor.wings;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;

public class ItemQuantumWing extends ItemNanoWing {

    public static int maxCharge = 10000000;
    public int tier = 4;

    public ItemQuantumWing(ArmorMaterial material, int par3, int par4) {
        super(material, par3, par4);
        this.setMaxStackSize(1);
        this.setMaxDamage(27);
        this.setCreativeTab(EMT.TAB);
        visDiscount = 6;
        transferLimit = 12000;
        energyPerDamage = 20000;
    }

    @Override
    public float getFallDamageMult() {
        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.RESOURCE_PATH + ":armor/wing_quantum");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return EMT.RESOURCE_PATH + ":textures/models/quantumwing.png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        this.useWings(player, stack, world, 0.33f, 0.5f, 0.2f, 7);
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return maxCharge;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 4;
    }

    public double getDamageAbsorptionRatio() {
        return 1D;
    }
}
