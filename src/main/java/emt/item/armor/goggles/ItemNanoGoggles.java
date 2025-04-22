package emt.item.armor.goggles;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import gregtech.api.hazards.Hazard;
import gregtech.api.hazards.IHazardProtector;
import ic2.api.item.IC2Items;

@Optional.Interface(iface = "gregtech.api.hazards.IHazardProtector", modid = "gregtech_nh")
public class ItemNanoGoggles extends ItemElectricGoggles implements IHazardProtector {

    public ItemNanoGoggles(ArmorMaterial material, int renderIndex, int armorType) {
        super(material, renderIndex, armorType);
        this.setCreativeTab(EMT.TAB);
        this.setMaxDamage(27);
        this.setMaxStackSize(1);
        visDiscount = 6;
        tier = 3;
        maxCharge = 1000000;
        energyPerDamage = 5000;
        transferLimit = 1600;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.TEXTURE_PATH + ":armor/goggles_nano");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return EMT.TEXTURE_PATH + ":textures/models/thaumicnanohelmet.png";
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return 0.9D;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 3;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        IC2Items.getItem("nightvisionGoggles").getItem().onArmorTick(world, player, itemStack);
    }

    @Override
    @Optional.Method(modid = "gregtech_nh")
    public boolean protectsAgainst(ItemStack itemStack, Hazard hazard) {
        return true;
    }
}
