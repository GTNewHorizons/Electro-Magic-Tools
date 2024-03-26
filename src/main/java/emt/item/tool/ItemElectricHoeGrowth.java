package emt.item.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.blocks.BlockCustomPlant;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.utils.Utils;

public class ItemElectricHoeGrowth extends ItemHoe implements IElectricItem {

    public IIcon icon;
    public int maxCharge = 200000;
    public int growthCost = 500;
    public int tillCost = 100;

    public ItemElectricHoeGrowth() {
        super(ToolMaterial.EMERALD);
        this.setCreativeTab(EMT.TAB);
        this.setMaxStackSize(1);
        this.growthCost = 10000;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icon = iconRegister.registerIcon(EMT.TEXTURE_PATH + ":tools/hoe_growth");
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
        ItemStack itemStack = new ItemStack(this, 1);
        if (getChargedItem(itemStack) == this) {
            ItemStack charged = new ItemStack(this, 1);
            ElectricItem.manager.charge(charged, 2147483647, 2147483647, true, false);
            itemList.add(charged);
        }
        if (getEmptyItem(itemStack) == this) {
            itemList.add(new ItemStack(this, 1, getMaxDamage()));
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return this.icon;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7,
            float par8, float par9, float par10) {
        if (stack.hasTagCompound()) {
            stack.stackTagCompound.setBoolean("Unbreakable", true);
        }
        boolean did = false;
        for (int x1 = -1; x1 <= 1; x1++) {
            for (int z1 = -1; z1 <= 1; z1++) {
                if (ElectricItem.manager.canUse(stack, 25)
                        && super.onItemUse(stack, player, world, x + x1, y, z + z1, par7, par8, par9, par10)) {
                    ElectricItem.manager.use(stack, 25, player);
                    Thaumcraft.proxy.blockSparkle(world, x + x1, y, z + z1, 8401408, 2);
                    if (!did) {
                        did = true;
                    }
                }
            }
        }
        if (!did && ElectricItem.manager.canUse(stack, 250)) {
            did = Utils.useBonemealAtLoc(world, player, x, y, z);
            if (!did) {
                Block block = world.getBlock(x, y, z);
                int meta = world.getBlockMetadata(x, y, z);
                if ((block == ConfigBlocks.blockCustomPlant) && (meta == 0)) {
                    ((BlockCustomPlant) block).growGreatTree(world, x, y, z, world.rand);
                    Thaumcraft.proxy.blockSparkle(world, x, y, z, 0, 2);
                    did = true;
                } else if ((block == ConfigBlocks.blockCustomPlant) && (meta == 1)) {
                    ((BlockCustomPlant) block).growSilverTree(world, x, y, z, world.rand);
                    Thaumcraft.proxy.blockSparkle(world, x, y, z, 0, 2);
                    did = true;
                }
            } else {
                ElectricItem.manager.use(stack, 250, player);
                Thaumcraft.proxy.blockSparkle(world, x, y, z, 0, 3);
            }
            if (did) {
                world.playSoundEffect(
                        x + 0.5D,
                        y + 0.5D,
                        z + 0.5D,
                        "thaumcraft:wand",
                        0.75F,
                        0.9F + world.rand.nextFloat() * 0.2F);
            }
        }
        if (stack.hasTagCompound()) {
            stack.stackTagCompound.setBoolean("Unbreakable", false);
        }
        return did;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List<String> list, boolean par4) {
        list.add(StatCollector.translateToLocal("ic2.item.tooltip.PowerTier") + " " + getTier(new ItemStack(this)));
    }

    /* IC2 API METHODS */

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return maxCharge;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 2;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 1000;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }
}
