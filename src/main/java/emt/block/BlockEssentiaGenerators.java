package emt.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gtnewhorizons.modularui.api.UIInfos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.tile.generator.TileEntityBaseGenerator;

public class BlockEssentiaGenerators extends BlockBaseContainer {

    private static int subtypes = 6;

    public BlockEssentiaGenerators(String name) {
        super(name, Material.iron, soundTypeMetal, subtypes, 4.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        super.registerBlockIcons(ir);
        this.blockIcon = ir.registerIcon(EMT.TEXTURE_PATH + ":machines/top");

        for (int meta = 0; meta < subtypes; meta++) {
            iconSets[meta].top = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/top");
            iconSets[meta].bottom = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/top");
            iconSets[meta].side = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/side");
        }

        iconSets[0].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/potentiafront");
        iconSets[1].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/ignisfront");
        iconSets[2].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/auramfront");
        iconSets[3].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/arborfront");
        iconSets[4].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/aerfront");
        iconSets[5].frontOff = ir.registerIcon(EMT.TEXTURE_PATH + ":essentiagenerator/aerfront");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
        list.add(new ItemStack(id, 1, 2));
        list.add(new ItemStack(id, 1, 3));
        list.add(new ItemStack(id, 1, 4));
        list.add(new ItemStack(id, 1, 5));
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileEntityBaseGenerator(meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        TileEntityBaseGenerator te = (TileEntityBaseGenerator) world.getTileEntity(x, y, z);
        if (te.isActive) {
            int color = te.aspect.getColor();
            float r = (float) (color >> 16 & 0xff) / 255F;
            float g = (float) (color >> 8 & 0xff) / 255F;
            float b = (float) (color & 0xff) / 255F;

            EntitySmokeFX fx = new EntitySmokeFX(world, x + 0.5f, y + 1.1, z + 0.5f, 0, 0.1, 0, 3);
            fx.setRBGColorF(r, g, b);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int s, float f1, float f2,
            float f3) {
        if (player.isSneaking()) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null) {
            UIInfos.TILE_MODULAR_UI.open(player, world, i, j, k);
        }
        return true;
    }
}
