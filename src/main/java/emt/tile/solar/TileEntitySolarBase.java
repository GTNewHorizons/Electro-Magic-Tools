package emt.tile.solar;

import cpw.mods.fml.common.FMLCommonHandler;
import emt.init.EMTBlocks;
import emt.tile.DefinitelyNotAIC2Source;
import emt.tile.TileEntityEMT;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.tileentity.IBasicEnergyContainer;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.net.GT_Packet_Block_Event;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;

public class TileEntitySolarBase extends TileEntityEMT implements IInventory, IWrenchable, IHasWorldObjectAndCoords, IEnergyConnected, IBasicEnergyContainer {

    private static final byte NOTHING = 0;
    private static final byte ORDO = 1;
    private static final byte PERDITIO = 2;
    private static final byte AER = 3;
    private static final byte TERRA = 4;
    private static final byte AQUA = 5;
    private static final byte IGNIS = 6;

    public String guiname;
    public boolean initialized;
    public boolean theSunIsVisible;
    public int tick;
    public double output = 0;
    public long timer = 0L;
    public boolean dead = true;
    public double generating;
    public long mp_storage = 0;
    public byte color = (-1);
    public long storage;
    public long maxstorage = 0;
    public boolean isActive = false;
    public DefinitelyNotAIC2Source energySource;
    public byte aspect = 0;
    protected boolean side;
    private int instance, meta;

    public TileEntitySolarBase(double output, Aspect aspect, String guiname, int instance, int meta) {
        this.tick = 10;
        this.storage = 0;
        long capacity = (long) (output * 1000);
        this.output = output;
        this.energySource = new DefinitelyNotAIC2Source(this, capacity, (int) getTierFromCapacity(capacity));
        this.maxstorage = this.getEUCapacity();
        this.aspect = getByteFromAspect(aspect);
        this.guiname = guiname;
        this.instance = instance;
        this.meta = meta;
    }


    /**
     * DO NOT CALL THIS ITS USED INTERNALLY ONLY
     */
    public TileEntitySolarBase() {
        this.tick = 10;
    }

    private static byte getByteFromAspect(Aspect aspect) {
        if (aspect == null)
            return NOTHING;
        if (aspect.equals(Aspect.ORDER))
            return ORDO;
        else if (aspect.equals(Aspect.ENTROPY))
            return PERDITIO;
        else if (aspect.equals(Aspect.AIR))
            return AER;
        else if (aspect.equals(Aspect.EARTH))
            return TERRA;
        else if (aspect.equals(Aspect.WATER))
            return AQUA;
        else if (aspect.equals(Aspect.FIRE))
            return IGNIS;
        return 0;
    }

    private int getTierFromCapacity(long capacity) {
        for (int i = 0; i < GT_Values.V.length; i++) {
            if (capacity / 1000 < GT_Values.V[i])
                return i;
        }
        return GT_Values.V.length - 1;
    }

    @Override
    public void updateEntity() {
        this.side = !this.worldObj.isRemote ? FMLCommonHandler.instance().getEffectiveSide().isServer() : FMLCommonHandler.instance().getSide().isServer();
        this.dead = false;
        this.timer = +1L;
        inputintoGTnet();
        checkConditions();
        this.storage = this.energySource.getEnergyStored();
    }

    private float getMaxMulti() {
        switch (aspect) {
            case ORDO:
            case PERDITIO: {
                return 2F;
            }
            case AER:
            case TERRA: {
                return 3F;
            }
            case IGNIS:
            case AQUA: {
                return 6F;
            }
            default:
                return 1f;
        }
    }

    public float calc_multi() {
        switch (aspect) {
            case ORDO: {
                if (this.worldObj.isDaytime())
                    return 2F;
                else return 0.75F;
            }
            case PERDITIO: {
                if (this.worldObj.isDaytime())
                    return 0.75F;
                else return 2F;
            }
            case AER: {
                if (this.yCoord < 5)
                    return 1F;
                else if (this.yCoord > 220)
                    return 3F;
                else
                    return (float) ((41D / 43D) + ((2D / 215D) * (double) this.yCoord));
            }
            case TERRA: {
                if (this.yCoord < 5)
                    return 3F;
                else if (this.yCoord > 220)
                    return 1F;
                else
                    return (float) ((-2D) / 215D * (double) this.yCoord + 131D / 43D);
            }
            case AQUA: {
                if (worldObj.isThundering())
                    return 6F;
                else if (worldObj.isRaining())
                    return 3F;
                else if (!worldObj.isThundering() && !worldObj.isRaining() && worldObj.getBlock(xCoord, yCoord + 1, zCoord).equals(Blocks.water))
                    return 2F;
                else
                    return 1F;
            }
            case IGNIS: {
                float mult = 1F;

                if (VisNetHandler.drainVis(worldObj, xCoord, yCoord, zCoord, Aspect.FIRE, 10) >= 10)
                    mult *= 3F;
                if (this.worldObj.provider.dimensionId == (-1))
                    mult *= 2F;

                return mult;
            }
            default:
                return 1f;
        }
    }

    public double getSourceStored() {
        return this.energySource.getEnergyStored();
    }

    public void checkConditions() {
        switch (aspect) {
            case IGNIS: {
                initialized = true;
                boolean ignis = ((this.worldObj.provider.dimensionId == (-1)) || (theSunIsVisible));
                if (ignis) {
                    this.isActive = true;
                    if (side) {
                        this.generating = output * calc_multi();
                        this.energySource.addEnergy(this.output * calc_multi());
                    }
                } else {
                    isActive = false;
                    this.generating = 0;
                }
                if (--tick == 0) {
                    updateSunState();
                    tick = 64;
                }
                break;
            }
            default: {
                initialized = true;
                if (--tick == 0) {
                    updateSunState();
                    tick = 64;
                }
                createEnergy();
            }
        }
    }

    public void createEnergy() {
        switch (aspect) {
            case ORDO:
            case PERDITIO: {
                if ((this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord)) && (!this.worldObj.isRaining()) && (!this.worldObj.isThundering())) {
                    isActive = true;
                    if (side) {
                        this.energySource.addEnergy(this.output * calc_multi());
                        this.generating = output * calc_multi();
                    }
                } else {
                    isActive = false;
                    this.generating = 0;
                }
                break;
            }
            case AQUA: {
                if (theSunIsVisible || this.calc_multi() > 1F && this.worldObj.isDaytime() || this.calc_multi() == 6F) {
                    isActive = true;
                    if (side) {
                        this.generating = output * calc_multi();
                        energySource.addEnergy(output * calc_multi());
                    }
                } else {
                    isActive = false;
                    this.generating = 0D;
                }
                break;
            }
            default: {
                if (theSunIsVisible) {
                    isActive = true;
                    if (side) {
                        this.energySource.addEnergy(this.output * calc_multi());
                        this.generating = output * calc_multi();
                    }
                } else {
                    isActive = false;
                    this.generating = 0;
                }
            }
        }
    }

    public void updateSunState() {
        boolean isRaining = this.aspect != AQUA && (worldObj.isRaining() || worldObj.isThundering());
        theSunIsVisible = worldObj.isDaytime() && !isRaining && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setLong("storage", this.energySource.getEnergyStored());

        NBTTagCompound subtag = new NBTTagCompound();
        subtag.setInteger("instance", this.instance);
        subtag.setInteger("meta", this.meta);
        subtag.setString("guiname", this.guiname);
        subtag.setByte("aspect", this.aspect);
        subtag.setDouble("Output", this.output);
        nbttagcompound.setTag("solarstats", subtag);

    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        NBTTagCompound subtag = nbttagcompound.getCompoundTag("solarstats");
        this.instance = subtag.getInteger("instance");
        this.meta = subtag.getInteger("meta");
        this.guiname = subtag.getString("guiname");
        this.aspect = subtag.getByte("aspect");
        this.output = subtag.getDouble("Output");
        long capacity = (long) (output * 1000);
        this.energySource = new DefinitelyNotAIC2Source(this, capacity, (int) getTierFromCapacity(capacity));
        this.maxstorage = this.getEUCapacity();
        this.energySource.setEnergyStored(nbttagcompound.getLong("storage"));
        this.storage = this.energySource.getEnergyStored();
        this.mp_storage = this.storage / 1000;
    }

    public int getSizeInventory() {
        return 0;
    }

    public ItemStack getStackInSlot(int i) {
        return null;
    }

    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
    }

    public boolean hasCustomInventoryName() {
        return true;
    }

    public String getInventoryName() {
        return guiname;
    }

    public int getInventoryStackLimit() {
        return 0;
    }

    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }

    public int gaugeEnergyScaled(long i) {
        return (int) ((this.mp_storage * 1000 * i) / this.maxstorage);
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }

    @Override
    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return false;
    }

    @Override
    public short getFacing() {
        return 0;
    }

    @Override
    public void setFacing(short facing) {
    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public float getWrenchDropRate() {
        return 1F;
    }

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        return new ItemStack(EMTBlocks.solars[instance], 1, meta);
    }

    public void inputintoGTnet() {
        if (!side)
            return;

        if (isUniversalEnergyStored(getOutputVoltage() * getOutputAmperage())) {
            long tEU = IEnergyConnected.Util.emitEnergyToNetwork(getOutputVoltage(), getOutputAmperage(), this);
            drainEnergyUnits((byte) 0, getOutputVoltage(), tEU);
        }
    }

    public byte getColorization() {
        return this.color;
    }

    public byte setColorization(byte aColor) {
        this.color = aColor;
        return this.color;
    }

    public boolean isUniversalEnergyStored(long aEnergyAmount) {
        return aEnergyAmount < this.energySource.getEnergyStored();
    }

    public long getUniversalEnergyStored() {
        return (long) this.energySource.getEnergyStored();
    }

    public long getUniversalEnergyCapacity() {
        return (long) this.energySource.getCapacity();
    }

    public long[] calculateMaxVoltAmp() {
        long[] voltAmp = new long[2];
        voltAmp[0] = getOutputVoltage();
        long generating = (long) Math.floor(output * getMaxMulti());
        if (generating <= GT_Values.V[energySource.getSourceTier()]) {
            voltAmp[1] = 1L;
        } else if (generating % GT_Values.V[energySource.getSourceTier()] == 0.0D) {
            voltAmp[1] = (generating / GT_Values.V[energySource.getSourceTier()]);
        } else
            voltAmp[1] = (1L + (generating / GT_Values.V[energySource.getSourceTier()]));
        return voltAmp;
    }

    public long getOutputAmperage() {
        return calculateMaxVoltAmp()[1];
//        long ret;
//        if (this.energySource.getSourceTier() <= 4) {
//            if (this.generating <= GT_Values.V[this.energySource.getSourceTier() - 2]) {
//                ret = 1L;
//            }
//            if (this.generating % GT_Values.V[this.energySource.getSourceTier() - 2] == 0.0D) {
//                ret = (long) (this.generating / GT_Values.V[this.energySource.getSourceTier() - 2]);
//            } else
//                ret = (long) (1L + (this.generating / GT_Values.V[this.energySource.getSourceTier() - 2]));
//        } else {
//            if (this.generating <= GT_Values.V[this.energySource.getSourceTier() - 1]) {
//                ret = 1L;
//            }
//            if (this.generating % GT_Values.V[this.energySource.getSourceTier()] == 0.0D) {
//                ret = (long) (this.generating / GT_Values.V[this.energySource.getSourceTier() - 1]);
//            } else
//                ret = (long) (1L + (this.generating / GT_Values.V[this.energySource.getSourceTier() - 1]));
//        }
//        return ret;
    }

    public long getOutputVoltage() {
        return GT_Values.V[this.energySource.getSourceTier()];
    }

    public long getInputAmperage() {
        return 0L;
    }

    public long getInputVoltage() {
        return 0L;
    }

    public boolean decreaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooLessEnergy) {
        if (this.energySource.getEnergyStored() > aEnergy) {
            this.energySource.drawEnergy(aEnergy);
            this.storage = this.energySource.getEnergyStored();
            return true;
        }
        return false;
    }

    public boolean increaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        return false;
    }

    public boolean drainEnergyUnits(byte aSide, long aVoltage, long aAmperage) {
        return decreaseStoredEnergyUnits(aVoltage * aAmperage, this.energySource.getEnergyStored() > aVoltage * aAmperage);
    }

    public long getAverageElectricInput() {
        return 0L;
    }

    public long getAverageElectricOutput() {
        return 0L;
    }

    public long getStoredEU() {
        return (long) this.energySource.getEnergyStored();
    }

    public long getEUCapacity() {
        return (long) this.energySource.getCapacity();
    }

    public long getStoredSteam() {
        return 0L;
    }

    public long getSteamCapacity() {
        return 0L;
    }

    public boolean increaseStoredSteam(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        return false;
    }

    public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperage) {
        return 0L;
    }

    public boolean inputEnergyFrom(byte aSide) {
        return false;
    }

    public boolean outputsEnergyTo(byte aSide) {
        return true;
    }

    public boolean energyStateReady() {
        return false;
    }

    public World getWorld() {
        return this.worldObj;
    }

    public int getXCoord() {
        return this.xCoord;
    }

    public short getYCoord() {
        return (short) this.yCoord;
    }

    public int getZCoord() {
        return this.zCoord;
    }

    public boolean isServerSide() {
        return side;
    }

    public boolean isClientSide() {
        return side;
    }

    public int getRandomNumber(int aRange) {
        return this.worldObj.rand.nextInt(aRange);
    }

    public TileEntity getTileEntity(int aX, int aY, int aZ) {
        return this.worldObj.getTileEntity(aX, aY, aZ);
    }

    public TileEntity getTileEntityOffset(int aX, int aY, int aZ) {
        return this.worldObj.getTileEntity(aX + this.xCoord, aY + this.yCoord, aZ + this.zCoord);
    }

    public TileEntity getTileEntityAtSide(byte aSide) {
        if ((aSide < 0) || (aSide >= 6)) {
            return null;
        }
        int tX = getOffsetX(aSide, 1);
        int tY = getOffsetY(aSide, 1);
        int tZ = getOffsetZ(aSide, 1);
        return this.worldObj.getTileEntity(tX, tY, tZ);
    }

    public TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        if (aDistance == 1) {
            return getTileEntityAtSide(aSide);
        }
        return getTileEntity(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    public IInventory getIInventory(int aX, int aY, int aZ) {
        return null;
    }

    public IInventory getIInventoryOffset(int aX, int aY, int aZ) {
        return null;
    }

    public IInventory getIInventoryAtSide(byte aSide) {
        return null;
    }

    public IInventory getIInventoryAtSideAndDistance(byte aSide, int aDistance) {
        return null;
    }

    public IFluidHandler getITankContainer(int aX, int aY, int aZ) {
        return null;
    }

    public IFluidHandler getITankContainerOffset(int aX, int aY, int aZ) {
        return null;
    }

    public IFluidHandler getITankContainerAtSide(byte aSide) {
        return null;
    }

    public IFluidHandler getITankContainerAtSideAndDistance(byte aSide, int aDistance) {
        return null;
    }

    public IGregTechTileEntity getIGregTechTileEntity(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            return (IGregTechTileEntity) tTileEntity;
        }
        return null;
    }

    public IGregTechTileEntity getIGregTechTileEntityOffset(int aX, int aY, int aZ) {
        return getIGregTechTileEntity(aX + this.xCoord, aY + this.yCoord, aZ + this.zCoord);
    }

    public IGregTechTileEntity getIGregTechTileEntityAtSide(byte aSide) {
        TileEntity tTileEntity = getTileEntityAtSide(aSide);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            return (IGregTechTileEntity) tTileEntity;
        }
        return null;
    }

    public IGregTechTileEntity getIGregTechTileEntityAtSideAndDistance(byte aSide, int aDistance) {
        TileEntity tTileEntity = getTileEntityAtSideAndDistance(aSide, aDistance);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            return (IGregTechTileEntity) tTileEntity;
        }
        return null;
    }

    public Block getBlock(int aX, int aY, int aZ) {
        return this.worldObj.getBlock(aX, aY, aZ);
    }

    public Block getBlockOffset(int aX, int aY, int aZ) {
        return getBlock(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
    }

    public Block getBlockAtSide(byte aSide) {
        if ((aSide < 0) || (aSide >= 6)) {
            return null;
        }
        int tX = getOffsetX(aSide, 1);
        int tY = getOffsetY(aSide, 1);
        int tZ = getOffsetZ(aSide, 1);
        return this.worldObj.getBlock(tX, tY, tZ);
    }

    public Block getBlockAtSideAndDistance(byte aSide, int aDistance) {
        if (aDistance == 1) {
            return getBlockAtSide(aSide);
        }
        return getBlock(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    public byte getMetaID(int aX, int aY, int aZ) {
        return (byte) this.worldObj.getBlockMetadata(aX, aY, aZ);
    }

    public byte getMetaIDOffset(int aX, int aY, int aZ) {
        return getMetaID(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
    }

    public byte getMetaIDAtSide(byte aSide) {
        int tX = getOffsetX(aSide, 1);
        int tY = getOffsetY(aSide, 1);
        int tZ = getOffsetZ(aSide, 1);
        return (byte) this.worldObj.getBlockMetadata(tX, tY, tZ);
    }

    public byte getMetaIDAtSideAndDistance(byte aSide, int aDistance) {
        if (aDistance == 1) {
            return getMetaIDAtSide(aSide);
        }
        return getMetaID(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    public byte getLightLevel(int aX, int aY, int aZ) {
        return 0;
    }

    public byte getLightLevelOffset(int aX, int aY, int aZ) {
        return 0;
    }

    public byte getLightLevelAtSide(byte aSide) {
        return 0;
    }

    public byte getLightLevelAtSideAndDistance(byte aSide, int aDistance) {
        return 0;
    }

    public boolean getOpacity(int aX, int aY, int aZ) {
        return false;
    }

    public boolean getOpacityOffset(int aX, int aY, int aZ) {
        return false;
    }

    public boolean getOpacityAtSide(byte aSide) {
        return false;
    }

    public boolean getOpacityAtSideAndDistance(byte aSide, int aDistance) {
        return false;
    }

    public boolean getSky(int aX, int aY, int aZ) {
        boolean sky = true;
        do {
            if (!this.worldObj.getBlock(aX, ++aY, aZ).getMaterial().equals(Material.air)) {
                sky = false;
            }
        } while ((sky) && (aY < 256));
        return sky;
    }

    public boolean getSkyOffset(int aX, int aY, int aZ) {
        return getSky(aX + this.xCoord, aY + this.yCoord, aZ + this.zCoord);
    }

    public boolean getSkyAtSide(byte aSide) {
        return false;
    }

    public boolean getSkyAtSideAndDistance(byte aSide, int aDistance) {
        return false;
    }

    public boolean getAir(int aX, int aY, int aZ) {
        return this.worldObj.getBlock(aX, aY, aZ).equals(Blocks.air);
    }

    public boolean getAirOffset(int aX, int aY, int aZ) {
        return getAir(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
    }

    public boolean getAirAtSide(byte aSide) {
        return getAirAtSideAndDistance(aSide, 1);
    }

    public boolean getAirAtSideAndDistance(byte aSide, int aDistance) {
        return getAir(getOffsetX(aSide, aDistance), getOffsetY(aSide, aDistance), getOffsetZ(aSide, aDistance));
    }

    public BiomeGenBase getBiome() {
        return this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord);
    }

    public BiomeGenBase getBiome(int aX, int aZ) {
        return this.worldObj.getBiomeGenForCoords(aX, aZ);
    }

    public int getOffsetX(byte aSide, int aMultiplier) {
        return this.xCoord + ForgeDirection.getOrientation(aSide).offsetX * aMultiplier;
    }

    public short getOffsetY(byte aSide, int aMultiplier) {
        return (short) (this.yCoord + ForgeDirection.getOrientation(aSide).offsetY * aMultiplier);
    }

    public int getOffsetZ(byte aSide, int aMultiplier) {
        return this.zCoord + ForgeDirection.getOrientation(aSide).offsetZ * aMultiplier;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void sendBlockEvent(byte aID, byte aValue) {
        GT_Values.NW.sendPacketToAllPlayersInRange(this.worldObj, new GT_Packet_Block_Event(this.xCoord, (short) this.yCoord, this.zCoord, aID, aValue), this.xCoord, this.zCoord);
    }

    public long getTimer() {
        return this.timer;
    }

    public void setLightValue(byte aLightValue) {
    }

    public boolean isInvalidTileEntity() {
        return this.tileEntityInvalid;
    }

    public boolean openGUI(EntityPlayer aPlayer, int aID) {
        return false;
    }

    public boolean openGUI(EntityPlayer aPlayer) {
        return false;
    }

    public boolean outputsEnergyTo(byte b, boolean b1) {
        return true;
    }

    public boolean inputEnergyFrom(byte b, boolean b1) {
        return false;
    }
}
