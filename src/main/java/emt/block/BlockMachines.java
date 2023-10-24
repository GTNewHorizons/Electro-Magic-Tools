package emt.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gtnewhorizons.modularui.api.UIInfos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.tile.TileEntityEtherealMacerator;
import emt.tile.TileEntityIndustrialWandRecharge;
import ic2.api.item.IC2Items;

public class BlockMachines extends BlockBaseContainer {

    public BlockMachines(String name) {
        super(name, Material.iron, soundTypeMetal, 3, 4.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        super.registerBlockIcons(ir);

        this.blockIcon = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/top");

        iconSets[0].top = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/top");
        iconSets[1].top = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmaceratortop");
        iconSets[2].top = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmaceratortop");

        iconSets[0].bottom = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/side");
        iconSets[1].bottom = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmacerator");
        iconSets[2].bottom = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmaceratortop");

        iconSets[0].side = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/side");
        iconSets[1].side = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmacerator");
        iconSets[2].side = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmaceratortop");

        iconSets[0].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/wandcharger");
        iconSets[1].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmaceratorfront");
        iconSets[2].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmaceratortop");

        iconSets[0].frontOn = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/wandcharger");
        iconSets[1].frontOn = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmaceratorfrontactive");
        iconSets[2].frontOn = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/etherealmaceratortop");
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List list) {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
        list.add(new ItemStack(id, 1, 2));
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        if (meta == 0) {
            return new TileEntityIndustrialWandRecharge();
        } else if (meta == 1) {
            return new TileEntityEtherealMacerator();
        }
        return super.createTileEntity(world, meta);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileEntityIndustrialWandRecharge || te instanceof TileEntityEtherealMacerator) {
                UIInfos.TILE_MODULAR_UI.open(player, world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return IC2Items.getItem("machine").getItem();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        dropItems(world, x, y, z);
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    private void dropItems(World world, int x, int y, int z) {
        Random rand = world.rand;

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) {
            return;
        }
        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);

            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(
                        world,
                        x + rx,
                        y + ry,
                        z + rz,
                        new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
    }
}
