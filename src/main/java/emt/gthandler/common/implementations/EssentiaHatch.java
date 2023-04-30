package emt.gthandler.common.implementations;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class EssentiaHatch extends GT_MetaTileEntity_Hatch_Input {

    private static int ApTe = (int) Math.ceil(Aspect.aspects.size() / GT_Values.V.length);
    private AspectList current = new AspectList();

    public EssentiaHatch(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier);
    }

    public EssentiaHatch(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    public EssentiaHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    public boolean addAspectList(final AspectList A) {

        if ((this.mTier + 1) * ApTe > A.size() + current.size()) {
            if (current.visSize() + A.visSize() < (this.mTier + 1) * ApTe * 64) current.add(A);
            return true;
        } else return false;
    }

    public void setCurrent(AspectList List) {
        this.current = List;
    }

    public AspectList getAspects() {
        return current;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new EssentiaHatch(
                "Essentia Hatch " + GT_Values.VN[this.mTier],
                this.mTier,
                this.getDescription(),
                this.mTextures);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        return false;
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("This Essentia-Hatch contains:");
        for (final Aspect A : this.current.aspects.keySet())
            list.add(A.getName() + ": " + Integer.toString(current.getAmount(A)));

        String[] ret = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        }

        return ret;

        /*
         * String[] old = super.getInfoData(); if (old.length!=0) { String[] newDescription = new String[old.length +
         * 1]; for (int i = 0; i < old.length; i++) { newDescription[i] = old[i]; } newDescription[old.length] =
         * "This Contains: " + current.size() + " different Aspects"; return newDescription; }else return new
         * String[]{"This Contains: " + current.size() + " different Aspects"};
         */
    }

    @Override
    public String[] getDescription() { // this is the fucking tooltip -_-'
        return new String[] { "Essentia Hatch for usage in Multiblocks", "Can only be filled with the Essentia Filler",
                "Can hold " + Integer.toString((ApTe * (mTier + 1))) + " Different Essentia Types",
                "Can hold " + Integer.toString((64 * ApTe * (mTier + 1))) + " Essentia in total" };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public void saveNBTData(NBTTagCompound var1) {

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
        var1.setTag("Aspects", tlist);
        super.saveNBTData(var1);
    }

    @Override
    public void loadNBTData(NBTTagCompound var1) {
        current = new AspectList();
        NBTTagList tlist = var1.getTagList("Aspects", 10);

        for (int j = 0; j < tlist.tagCount(); ++j) {
            NBTTagCompound rs = tlist.getCompoundTagAt(j);
            if (rs.hasKey("key")) {
                current.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
            }
        }

        super.loadNBTData(var1);
    }

    public int fuelleft() {
        int ret = 0;
        for (final Aspect A : current.aspects.keySet()) {
            ret += current.getAmount(A);
        }
        return ret;
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return false;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection aSide,
            ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection aSide,
            ItemStack aStack) {
        return false;
    }

    @Override
    public int getCapacity() {
        return 0;
    }
}
