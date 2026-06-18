package emt.tile;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXWisp;
import thaumcraft.common.Thaumcraft;

public class TileElectricCloud extends TileEntity {

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if (worldObj.isRemote && FMLCommonHandler.instance().getSide().isClient()
                && FMLClientHandler.instance().getClient().renderViewEntity != null)
            fx();
    }

    @SideOnly(Side.CLIENT)
    public void fx() {
        if (worldObj.rand.nextInt(4) == 0) {
            Thaumcraft.proxy.wispFX3(
                    worldObj,
                    xCoord + 0.5F,
                    yCoord + 0.5F,
                    zCoord + 0.5F,
                    xCoord + 0.4F + worldObj.rand.nextFloat() * 0.2F,
                    yCoord + 0.5F,
                    zCoord + 0.4F + worldObj.rand.nextFloat() * 0.2F,
                    0.25F,
                    1,
                    true,
                    -0.02F);
        }

        if (worldObj.rand.nextInt(12) == 0) {
            Thaumcraft.proxy.nodeBolt(
                    worldObj,
                    xCoord + 0.5f,
                    yCoord + 0.5f,
                    zCoord + 0.5f,
                    xCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f,
                    yCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f,
                    zCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f);
            if (worldObj.rand.nextInt(2) == 0) {
                Thaumcraft.proxy.nodeBolt(
                        worldObj,
                        xCoord + 0.5f,
                        yCoord + 0.5f,
                        zCoord + 0.5f,
                        xCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f,
                        yCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f,
                        zCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f);
            }
            if (worldObj.rand.nextInt(4) == 0) {
                Thaumcraft.proxy.nodeBolt(
                        worldObj,
                        xCoord + 0.5f,
                        yCoord + 0.5f,
                        zCoord + 0.5f,
                        xCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f,
                        yCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f,
                        zCoord + (worldObj.rand.nextInt(40) / 20f) - 0.5f);
            }
        }

        if (worldObj.rand.nextInt(3) == 0) {
            FXWisp wisp = new FXWisp(
                    worldObj,
                    xCoord + 0.5 + ((worldObj.rand.nextInt(40) / 160f) - 0.125f),
                    yCoord + 0.5,
                    zCoord + 0.5 + ((worldObj.rand.nextInt(40) / 160f) - 0.125f),
                    0.4f + worldObj.rand.nextInt(40) / 100f,
                    1);
            wisp.setGravity(-0.02F);
            wisp.setRBGColorF(worldObj.rand.nextInt(20) / 100f, worldObj.rand.nextInt(20) / 100f, 1);
            wisp.setAlphaF(1);
            ParticleEngine.instance.addEffect(worldObj, wisp);
        }
    }
}
