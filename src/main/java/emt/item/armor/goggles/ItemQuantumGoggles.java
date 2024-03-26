package emt.item.armor.goggles;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import ic2.api.item.IC2Items;
import ic2.core.IC2;
import ic2.core.util.Keyboard;

public class ItemQuantumGoggles extends ItemNanoGoggles {

    private static final Map<Integer, Integer> potionCost = new HashMap<>();

    public ItemQuantumGoggles(ArmorMaterial material, int renderIndex, int armorType) {
        super(material, renderIndex, armorType);
        this.setCreativeTab(EMT.TAB);
        this.setMaxDamage(27);
        this.setMaxStackSize(1);
        maxCharge = 10000000;
        tier = 4;
        visDiscount = 7;
        transferLimit = 12000;
        energyPerDamage = 20000;

        potionCost.put(Integer.valueOf(Potion.poison.id), Integer.valueOf(10000));
        potionCost.put(Integer.valueOf(Potion.wither.id), Integer.valueOf(15000));
        potionCost.put(Integer.valueOf(Potion.confusion.id), Integer.valueOf(5000));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.TEXTURE_PATH + ":armor/goggles_quantum");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return EMT.TEXTURE_PATH + ":textures/models/thaumicquantumhelmet.png";
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return 1D;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 4;
    }

    // MUAHAHAHA
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        try {
            Field f;
            f = Keyboard.class.getDeclaredField("playerKeys");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<EntityPlayer, Set<Enum<?>>> playerKeys = (Map<EntityPlayer, Set<Enum<?>>>) f.get(IC2.keyboard);

            Enum<?> hub = null;
            Set<Enum<?>> set = playerKeys.get(player);

            if (set != null) {
                for (Enum<?> e : set) {
                    if (e.ordinal() == 6) {
                        hub = e;
                    }
                }
                set.remove(hub);
            }

            IC2Items.getItem("quantumHelmet").getItem().onArmorTick(world, player, itemStack);

            if (hub != null) {
                set.add(hub);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
