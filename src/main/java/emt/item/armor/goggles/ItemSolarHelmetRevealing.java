package emt.item.armor.goggles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ItemSolarHelmetRevealing extends ItemQuantumGoggles {

    private static final Map potionCost = new HashMap();
    /*private int ticker;
    private int generating;
    private boolean sunIsUp;
    private boolean skyIsVisible;
    private boolean noSunWorld;
    private boolean dampPlace;*/
    private int genDay;
    private int genNight;

    @SuppressWarnings("unchecked")
    public ItemSolarHelmetRevealing(ArmorMaterial material, int par3, int par4) {
        super(material, par3, par4);
        this.setMaxDamage(27);
        this.setMaxStackSize(1);
        this.setCreativeTab(EMT.TAB);
        // this.maxCharge = 20000000; //REALLY?
        this.genDay = 65536;
        this.genNight = 4096;
        maxCharge = 20000000;
        visDiscount = 8;
        tier = 4;
        transferLimit = 18000;
        energyPerDamage = 20000;

        potionCost.put(Integer.valueOf(Potion.poison.id), Integer.valueOf(10000));
        potionCost.put(Integer.valueOf(Potion.wither.id), Integer.valueOf(15000));
        potionCost.put(Integer.valueOf(Potion.confusion.id), Integer.valueOf(5000));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.TEXTURE_PATH + ":armor/goggles_solar");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layerType) {
        return EMT.TEXTURE_PATH + ":textures/models/solarrevealinghelmet.png";
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return 1D;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 4;
    }

    @Override
    public void onArmorTick(World worldObj, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(worldObj, player, itemStack);

        if (worldObj.isRemote) {
            return;
        }

        if (worldObj.canBlockSeeTheSky((int) player.posX, (int) player.posY + 1, (int) player.posZ) && !worldObj.isRaining()) {
            double enerj = (worldObj.isDaytime() ? (double) genDay : (double) genNight) / 12000D;

            for (int i = 0; i < player.inventory.armorInventory.length; i++) {
                if (enerj > 0) {
                    if ((player.inventory.armorInventory[i] != null) && (player.inventory.armorInventory[i].getItem() instanceof IElectricItem)) {
                        double sentPacket = ElectricItem.manager.charge(player.inventory.armorInventory[i], enerj, 4, false, false);
                        enerj -= sentPacket;
                    }
                } else {
                    return;
                }
            }
            for (int j = 0; j < player.inventory.mainInventory.length; j++) {
                if (enerj > 0) {
                    if ((player.inventory.mainInventory[j] != null) && (player.inventory.mainInventory[j].getItem() instanceof IElectricItem)) {
                        double sentPacket = ElectricItem.manager.charge(player.inventory.mainInventory[j], enerj, 4, false, false);
                        enerj -= sentPacket;
                    }
                } else {
                    return;
                }
            }
        }
    }
}
