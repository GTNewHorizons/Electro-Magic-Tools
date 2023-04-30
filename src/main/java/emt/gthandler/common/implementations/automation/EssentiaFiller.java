package emt.gthandler.common.implementations.automation;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import emt.gthandler.common.implementations.EssentiaHatch;
import emt.tile.TileEntityEMT;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;

public class EssentiaFiller extends TileEntityEMT implements IAspectContainer, IEssentiaTransport {

    private AspectList current = new AspectList();
    private byte tick = 0;

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagList tlist = new NBTTagList();
        Aspect[] aspectA = current.getAspects();
        for (int i = 0; i < aspectA.length; ++i) {
            Aspect aspect = aspectA[i];
            if (aspect != null) {
                NBTTagCompound f = new NBTTagCompound();
                f.setString("key", aspect.getTag());
                f.setInteger("amount", current.getAmount(aspect));
                tlist.appendTag(f);
            }
        }
        tagCompound.setTag("Aspects", tlist);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        current = new AspectList();
        NBTTagList tlist = tagCompound.getTagList("Aspects", 10);
        for (int j = 0; j < tlist.tagCount(); ++j) {
            NBTTagCompound rs = tlist.getCompoundTagAt(j);
            if (rs.hasKey("key")) {
                current.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
            }
        }
    }

    @Override
    public void updateEntity() {
        ++tick;
        fillfrompipe();
        if (tick % 20 == 0) {
            TileEntity T = this.worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
            if (checkTE(T)) {
                tick = 0;
                return;
            }
            if (T == null) T = this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
            if (checkTE(T)) {
                tick = 0;
                return;
            }
            if (T == null) T = this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
            if (checkTE(T)) {
                tick = 0;
                return;
            }
            if (T == null) T = this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            if (checkTE(T)) {
                tick = 0;
                return;
            }
            if (T == null) T = this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
            if (checkTE(T)) {
                tick = 0;
                return;
            }
            if (T == null) T = this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
            if (checkTE(T)) {
                tick = 0;
                return;
            }
        }
    }

    private boolean checkTE(final TileEntity T) {
        if (T == null) return false;
        if (this.current.visSize() > 0) if (T instanceof IGregTechTileEntity) {
            IMetaTileEntity M = ((IGregTechTileEntity) T).getMetaTileEntity();
            if (M instanceof EssentiaHatch) {
                if (((EssentiaHatch) M).addAspectList(current)) this.current = new AspectList();
                return true;
            }
        }

        return false;
    }

    public void fillfrompipe() {
        TileEntity[] te = new TileEntity[ForgeDirection.VALID_DIRECTIONS.length];
        for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
            te[i] = ThaumcraftApiHelper.getConnectableTile(
                    this.worldObj,
                    this.xCoord,
                    this.yCoord,
                    this.zCoord,
                    ForgeDirection.VALID_DIRECTIONS[i]);
            if (te[i] != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) te[i];
                if (!pipe.canOutputTo(ForgeDirection.VALID_DIRECTIONS[i])) {
                    return;
                }
                if ((pipe.getEssentiaType(ForgeDirection.VALID_DIRECTIONS[i]) != null)
                        && (pipe.getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i])
                                < getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i]))) {
                    addToContainer(
                            pipe.getEssentiaType(ForgeDirection.VALID_DIRECTIONS[i]),
                            pipe.takeEssentia(
                                    pipe.getEssentiaType(ForgeDirection.VALID_DIRECTIONS[i]),
                                    1,
                                    ForgeDirection.VALID_DIRECTIONS[i]));
                }
            }
        }
    }

    @Override
    public AspectList getAspects() {
        return current;
    }

    @Override
    public void setAspects(AspectList aspectList) {
        this.current.add(aspectList);
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return true;
    }

    @Override
    public int addToContainer(Aspect aspect, int i) {
        current.add(aspect, i);
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return current.aspects.containsKey(aspect) && i <= current.getAmount(aspect);
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        ArrayList<Boolean> ret = new ArrayList<Boolean>();
        for (Aspect a : aspectList.aspects.keySet()) if (current.aspects.containsKey(a)) {
            ret.add(true);
        } else ret.add(false);
        return !ret.contains(false);
    }

    @Override
    public int containerContains(Aspect aspect) {
        return current.aspects.containsKey(aspect) ? current.getAmount(aspect) : 0;
    }

    @Override
    public boolean isConnectable(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public boolean canInputFrom(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public boolean canOutputTo(ForgeDirection forgeDirection) {
        return false;
    }

    @Override
    public void setSuction(Aspect aspect, int i) {}

    @Override
    public Aspect getSuctionType(ForgeDirection forgeDirection) {
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        return 256;
    }

    @Override
    public int takeEssentia(Aspect aspect, int i, ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int i, ForgeDirection forgeDirection) {
        current.add(aspect, i);
        return 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        return current.getAspects()[0];
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        int ret = 0;
        for (final Aspect A : current.aspects.keySet()) {
            ret += current.getAmount(A);
        }
        return ret;
    }

    @Override
    public int getMinimumSuction() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }
}
