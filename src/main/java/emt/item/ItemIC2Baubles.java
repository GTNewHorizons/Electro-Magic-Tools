package emt.item;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.util.EMTConfigHandler;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import thaumcraft.api.IRunicArmor;

public class ItemIC2Baubles extends ItemBase implements IBauble, IRunicArmor {

    public static int wornTick;
    public IIcon[] icon = new IIcon[16];
    public Random random = new Random();

    public ItemIC2Baubles() {
        super("bauble");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);

        wornTick = 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        String name = "";
        switch (itemstack.getItemDamage()) {
            case 0: {
                name = "euMaker.armor";
                break;
            }
            case 1: {
                name = "euMaker.inventory";
                break;
            }
            default:
                name = "nothing";
                break;
        }
        return getUnlocalizedName() + "." + name;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.icon[0] = ri.registerIcon(EMT.TEXTURE_PATH + ":armoreumaker");
        this.icon[1] = ri.registerIcon(EMT.TEXTURE_PATH + ":inventoryeumaker");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon[meta];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> list) {
        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 1));
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        if (stack.getItemDamage() <= 1) {
            return BaubleType.RING;
        } else {
            return null;
        }
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            if (stack != null) {
                if (stack.getItemDamage() == 0) {
                    if (player instanceof EntityPlayer) {
                        int energyLeft = EMTConfigHandler.armorBaubleProduction;
                        for (int i = 0; i < ((EntityPlayer) player).inventory.armorInventory.length; i++) {
                            if (energyLeft > 0) {
                                if ((((EntityPlayer) player).inventory.armorInventory[i] != null)
                                        && (((EntityPlayer) player).inventory.armorInventory[i]
                                                .getItem() instanceof IElectricItem)) {
                                    double sentPacket = ElectricItem.manager.charge(
                                            ((EntityPlayer) player).inventory.armorInventory[i],
                                            energyLeft,
                                            4,
                                            false,
                                            false);
                                    energyLeft -= sentPacket;
                                }
                            }
                        }
                    }
                }
            }
            if (stack != null) {
                if (stack.getItemDamage() == 1) {
                    if (player instanceof EntityPlayer) {
                        int energyLeft = EMTConfigHandler.inventoryBaubleProdution;
                        for (int i = 0; i < ((EntityPlayer) player).inventory.mainInventory.length; i++) {
                            if (energyLeft > 0) {
                                if ((((EntityPlayer) player).inventory.mainInventory[i] != null)
                                        && (((EntityPlayer) player).inventory.mainInventory[i]
                                                .getItem() instanceof IElectricItem)) {
                                    double sentPacket = ElectricItem.manager.charge(
                                            ((EntityPlayer) player).inventory.mainInventory[i],
                                            energyLeft,
                                            4,
                                            false,
                                            false);
                                    energyLeft -= sentPacket;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {}

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {}

    @Override
    public boolean canEquip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    @Override
    public int getRunicCharge(ItemStack itemStack) {
        return 0;
    }
}
