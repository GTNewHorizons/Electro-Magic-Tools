package emt.item.armor.boots;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.util.EMTConfigHandler;
import gregtech.api.hazards.Hazard;
import gregtech.api.hazards.IHazardProtector;

@Optional.Interface(iface = "gregtech.api.hazards.IHazardProtector", modid = "gregtech")
public class ItemNanoBootsTraveller extends ItemElectricBootsTraveller implements IHazardProtector {

    public ItemNanoBootsTraveller(ArmorMaterial material, int par3, int par4) {
        super(material, par3, par4);
        maxCharge = 1000000;
        speedBonus = (float) EMTConfigHandler.nanoBootsSpeed;
        jumpBonus = (float) EMTConfigHandler.nanoBootsJump;
        visDiscount = 4;
        transferLimit = 1600;
        energyPerDamage = 5000;
    }

    @Override
    public float getMaxHealthyDropDist() {
        return (float) EMTConfigHandler.nanoBootsMaxDrop;
    }

    @Override
    public float getMinimumDropDist() {
        return (float) EMTConfigHandler.nanoBootsMinDrop;
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return 0.9D;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.TEXTURE_PATH + ":armor/boots_nano");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return EMT.TEXTURE_PATH + ":textures/models/nanobootstravel.png";
    }

    @Override
    public boolean protectsAgainst(ItemStack itemStack, Hazard hazard) {
        return true;
    }
}
