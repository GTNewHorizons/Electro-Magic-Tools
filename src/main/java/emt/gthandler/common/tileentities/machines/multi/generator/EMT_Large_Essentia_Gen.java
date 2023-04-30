package emt.gthandler.common.tileentities.machines.multi.generator;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import emt.gthandler.common.implementations.EssentiaHatch;
import emt.gthandler.common.items.EMT_CasingBlock;
import emt.util.EMTEssentiasOutputs;
import emt.util.EMTTextHelper;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.XSTR;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.common.config.ConfigBlocks;

public class EMT_Large_Essentia_Gen extends GT_MetaTileEntity_MultiBlockBase {

    private static final byte CASING_INDEX = 2;
    public ArrayList<EssentiaHatch> mEssentiaHatches = new ArrayList();
    private byte tick = 0;
    private boolean has_lube = false;

    public EMT_Large_Essentia_Gen(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public EMT_Large_Essentia_Gen(String aName) {
        super(aName);
    }

    @Override
    public String[] getDescription() {
        return new String[] { "Controller Block for the Large Essentia Generator.",
                "Produces energy from magical essentia decay.", "Size(WxHxD): 7x4x7, Controller (Bottom, Center)",
                "Top-Middle 5x1x5 layer are Muffler Hatches", "Middle 5x2x5 layer are Essentia Diffusion Cells",
                "Everything from here on goes either in the bottom or on the sides", "1x Fluid Input Hatch",
                "1x Essentia Input Hatch", "1x Dynamo Hatches", "1x Maintenance Hatch",
                "The outer hull is made out of Magic Containing Casings",
                "Produces 20x more than the singleblock generator per essentia",
                "Causes magical and serious conventional pollution",
                "Needs 1L Lubricant per second or will cause way more conventional pollution",
                "Needs 10 " + EMTTextHelper.LIGHT_BLUE
                        + "Aqua"
                        + EMTTextHelper.LIGHT_GRAY
                        + " cVis per second or will cause way more magical pollution",
                "Added by EMT", "Made by bartimaeusnek" };
    }

    public int getCurrentEfficiency(ItemStack itemStack) {
        return this.mEfficiency
                - (this.getIdealStatus() - this.getRepairStatus()) * this.getMaxEfficiency(itemStack) / 10;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
            int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX),
                    new GT_RenderedTexture(
                            aActive ? Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE
                                    : Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER) };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(CASING_INDEX) };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity arg0) {
        return new EMT_Large_Essentia_Gen(this.mName);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        // TODO Auto-generated method stub
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getInventoryName() {
        // TODO Auto-generated method stub
        return this.mName;
    }

    @Override
    public boolean hasCustomInventoryName() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void markDirty() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void openInventory() {
        // TODO Auto-generated method stub

    }

    @Override
    public void closeInventory() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack arg1) {

        final int xDir = aBaseMetaTileEntity.getBackFacing().offsetX * 3;
        final int zDir = aBaseMetaTileEntity.getBackFacing().offsetZ * 3;

        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                for (int h = 0; h <= 3; h++) {
                    final IGregTechTileEntity tTileEntity = aBaseMetaTileEntity
                            .getIGregTechTileEntityOffset(xDir + x, h, zDir + z);

                    if (!(Math.abs(x) == 3 || Math.abs(z) == 3)) {
                        // Muffler on top.
                        if ((h == 3)) {
                            if (!this.addMufflerToMachineList(tTileEntity, CASING_INDEX)) {
                                return false;
                            }
                        } else if (h != 0) {
                            // inside Special Blocks
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + x, h, zDir + z)
                                    != EMT_CasingBlock.EMT_GT_BLOCKS[0])
                                return false;
                            else if ((aBaseMetaTileEntity.getMetaIDOffset(xDir + x, h, zDir + z) != 1)) return false;
                        } else if (!(this.addEssetiaHatchToList(tTileEntity, CASING_INDEX)
                                || this.addDynamoToMachineList(tTileEntity, CASING_INDEX)
                                || this.addMaintenanceToMachineList(tTileEntity, CASING_INDEX)
                                || this.addInputToMachineList(tTileEntity, CASING_INDEX)))
                            // ground still casings
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + x, h, zDir + z)
                                    != EMT_CasingBlock.EMT_GT_BLOCKS[0])
                                return false;
                            else if ((aBaseMetaTileEntity.getMetaIDOffset(xDir + x, h, zDir + z) != 0)) return false;

                    } else if (h != 0) {
                        // walls
                        if (!(this.addEssetiaHatchToList(tTileEntity, CASING_INDEX)
                                || this.addDynamoToMachineList(tTileEntity, CASING_INDEX)
                                || this.addMaintenanceToMachineList(tTileEntity, CASING_INDEX)
                                || this.addInputToMachineList(tTileEntity, CASING_INDEX)))
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + x, h, zDir + z)
                                    != EMT_CasingBlock.EMT_GT_BLOCKS[0])
                                return false;
                        else if ((aBaseMetaTileEntity.getMetaIDOffset(xDir + x, h, zDir + z) != 0)) return false;
                    } else if (h == 0) {
                        // top and bottom corners
                        if (!(this.addEssetiaHatchToList(tTileEntity, CASING_INDEX)
                                || this.addDynamoToMachineList(tTileEntity, CASING_INDEX)
                                || this.addMaintenanceToMachineList(tTileEntity, CASING_INDEX)
                                || this.addInputToMachineList(tTileEntity, CASING_INDEX)))
                            if (((xDir + x) != 0) || ((zDir + z) != 0)) // controller
                                if (aBaseMetaTileEntity.getBlockOffset(xDir + x, h, zDir + z)
                                        != EMT_CasingBlock.EMT_GT_BLOCKS[0])
                                    return false;
                        else if ((aBaseMetaTileEntity.getMetaIDOffset(xDir + x, h, zDir + z) != 0)) return false;
                    }
                }
            }
        }

        if (this.mDynamoHatches.size() != 1) return false;

        if (this.mInputHatches.size() != 1) return false;

        if (this.mMaintenanceHatches.size() != 1) return false;

        /*
         * //repair shit debug shit this.mWrench = true; this.mScrewdriver = true; this.mSoftHammer = true;
         * this.mHardHammer = true; this.mSolderingTool = true; this.mCrowbar = true;
         */
        return true;
    }

    public boolean addInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity instanceof EssentiaHatch) return addEssetiaHatchToList(aTileEntity, aBaseCasingIndex);
        return super.addInputToMachineList(aTileEntity, aBaseCasingIndex);
    }

    private boolean addEssetiaHatchToList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof EssentiaHatch) {
                ((EssentiaHatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return this.mEssentiaHatches.add((EssentiaHatch) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    private void make_energy() {
        AspectList fuel = this.mEssentiaHatches.get(0).getAspects();
        double cached = 0;

        ++tick;

        // make energy
        for (final Aspect A : fuel.aspects.keySet()) {
            if (fuel.getAmount(A) > 0) {
                double burnvalue = EMTEssentiasOutputs.outputs.get(A.getTag()).doubleValue();
                cached += (burnvalue / 20D);
                cached = Math.ceil(cached);

                if (tick % 20 == 0) { // every second consume 1 essentia
                    // consume essentia and remove it from the Aspect List if its 0
                    this.mEssentiaHatches.get(0).getAspects().reduce(A, 1);
                    if (this.mEssentiaHatches.get(0).getAspects().getAmount(A) == 0)
                        this.mEssentiaHatches.get(0).getAspects().remove(A);
                }
            }
        }

        if (tick % 20 == 0) {
            // consume lubricant
            if (has_lube) this.mInputHatches.get(0).getFluid().amount -= 1;

            tick = 0; // reset ticktimer
        }

        // transfer energy into mEU
        this.mEUt = (int) Math.ceil(cached);
    }

    @Override
    public boolean checkRecipe(ItemStack arg0) {
        /*
         * if (!this.getBaseMetaTileEntity().isActive()) if (this.mEfficiency > 100) this.mEfficiency -= 8; else
         * this.mEfficiency = 100;
         */
        if (this.mEssentiaHatches.get(0).fuelleft() > 0) {
            this.mMaxProgresstime = 1;
            this.mProgresstime = 0;
        }

        return this.mEssentiaHatches.get(0).fuelleft() > 0;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {

        if (tick % 20 == 0) { // every second
            // highers eff to max by 0.16% every running tick = 3,2% every second
            if (this.mEfficiency < this.getMaxEfficiency(null) - 320) this.mEfficiency += 320;
            else this.mEfficiency = getMaxEfficiency(null);

            // check for Lubricant
            this.has_lube = this.mInputHatches.get(0).getFluid() != null
                    && this.mInputHatches.get(0).getFluid().isFluidEqual(Materials.Lubricant.getFluid(1L))
                    && this.mInputHatches.get(0).getFluidAmount() > 0;
        }

        if (tick % 2 == 0) { // every 2nd tick

            final XSTR R = new XSTR();
            final World WORLD = this.getBaseMetaTileEntity().getWorld();
            int x = this.getBaseMetaTileEntity().getXCoord();
            int y = this.getBaseMetaTileEntity().getYCoord();
            int z = this.getBaseMetaTileEntity().getZCoord();

            // if out of aqua, make much flux but to not stop.
            boolean outOfcVis = VisNetHandler.drainVis(WORLD, x, y, z, Aspect.WATER, 10) <= 9;

            // Random Change of a bit of Flux Gas.
            final int RANDOMHATCH = R.nextInt(this.mMufflerHatches.size());

            // gets a chanche to release flux gas based on missing efficiency, min 1%
            if ((R.nextInt(getMaxEfficiency(null)) > (this.mEfficiency > 100 ? this.mEfficiency : 100)) || outOfcVis) {
                x = this.mMufflerHatches.get(RANDOMHATCH).getBaseMetaTileEntity().getXCoord();
                y = this.mMufflerHatches.get(RANDOMHATCH).getBaseMetaTileEntity().getYCoord() + 1;
                z = this.mMufflerHatches.get(RANDOMHATCH).getBaseMetaTileEntity().getZCoord();
                WORLD.setBlock(x, y, z, ConfigBlocks.blockFluxGas, R.nextInt(8), 3);
            }
        }

        make_energy();
        ++this.mProgresstime;
        return super.onRunningTick(aStack);
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack arg0) {
        return false;
    }

    @Override
    public int getDamageToComponent(ItemStack arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxEfficiency(ItemStack arg0) {
        // TODO Auto-generated method stub
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack arg0) {
        return has_lube ? 50 : 1000;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack arg0) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public String[] getInfoData() {
        String[] old = super.getInfoData();
        old[2] = "Produces " + EMTTextHelper.RED + mEUt + EMTTextHelper.WHITE + "EU/t";
        return old;
    }

    /*
     * @Override public void explodeMultiblock() { final World WORLD=this.getBaseMetaTileEntity().getWorld(); final int
     * xDir = ForgeDirection.getOrientation(this.getBaseMetaTileEntity().getBackFacing()).offsetX * 3; final int zDir =
     * ForgeDirection.getOrientation(this.getBaseMetaTileEntity().getBackFacing()).offsetZ * 3; for (int x = -3; x <= 3;
     * x++) for (int z = -3; z <= 3; z++) for (int h = 0; h <= 3; h++) WORLD.setBlock(xDir+x,
     * this.getBaseMetaTileEntity().getYCoord()+h, zDir+z, ConfigBlocks.blockFluxGas, 8, 3); super.explodeMultiblock();
     * }
     */
}
