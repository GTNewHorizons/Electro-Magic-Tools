package emt.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.tile.TileElectricCloud;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXSparkle;

public class BlockElectricCloud extends BlockBase {

    public IIcon icon;

    public BlockElectricCloud(String unlocName) {
        super(unlocName, Material.cloth, soundTypeCloth, 0.1f);
        setBlockBounds(0.3f, 0.3f, 0.3f, 0.7f, 0.7f, 0.7f);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rnd) {
        FXSparkle ef = new FXSparkle(
                world,
                x + 0.5F,
                y + 0.5F,
                z + 0.5F,
                x + 0.5F + (rnd.nextFloat() - rnd.nextFloat()) / 3.0F,
                y + 0.5F + (rnd.nextFloat() - rnd.nextFloat()) / 3.0F,
                z + 0.5F + (rnd.nextFloat() - rnd.nextFloat()) / 3.0F,
                1.0F,
                6,
                3);
        ef.setGravity(0.05F);
        ParticleEngine.instance.addEffect(world, ef);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileElectricCloud();
    }

    @Override
    public String getUnlocalizedName() {
        return "tile.EMT." + super.getUnlocalizedName().substring(15);
    }

    @Override
    public void registerBlockIcons(IIconRegister ir) {
        icon = ir.registerIcon(EMT.TEXTURE_PATH + ":empty");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icon;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public int getLightValue() {
        return 15;
    }

    @Override
    public boolean isAir(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
