package emt.tile.solar;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.NumberFormatMUI;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.screen.ITileWithModularUI;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;
import com.gtnewhorizons.modularui.common.widget.TextWidget;

import cpw.mods.fml.common.FMLCommonHandler;
import emt.client.gui.EMT_UITextures;
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
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;

public class TileEntitySolarBase extends TileEntityEMT implements IInventory, IWrenchable, IHasWorldObjectAndCoords,
        IEnergyConnected, IBasicEnergyContainer, ITileWithModularUI {

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

    protected static NumberFormatMUI numberFormat = new NumberFormatMUI();

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
        if (aspect == null) return NOTHING;
        if (aspect.equals(Aspect.ORDER)) return ORDO;
        else if (aspect.equals(Aspect.ENTROPY)) return PERDITIO;
        else if (aspect.equals(Aspect.AIR)) return AER;
        else if (aspect.equals(Aspect.EARTH)) return TERRA;
        else if (aspect.equals(Aspect.WATER)) return AQUA;
        else if (aspect.equals(Aspect.FIRE)) return IGNIS;
        return 0;
    }

    private int getTierFromCapacity(long capacity) {
        for (int i = 0; i < GT_Values.V.length; i++) {
            if (capacity / 1000 < GT_Values.V[i]) return i;
        }
        return GT_Values.V.length - 1;
    }

    @Override
    public void updateEntity() {
        this.side = !this.worldObj.isRemote ? FMLCommonHandler.instance().getEffectiveSide().isServer()
                : FMLCommonHandler.instance().getSide().isServer();
        if (!side) return;
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
                if (this.worldObj.isDaytime()) return 2F;
                else return 0.75F;
            }
            case PERDITIO: {
                if (this.worldObj.isDaytime()) return 0.75F;
                else return 2F;
            }
            case AER: {
                if (this.yCoord < 5) return 1F;
                else if (this.yCoord > 220) return 3F;
                else return (float) ((41D / 43D) + ((2D / 215D) * (double) this.yCoord));
            }
            case TERRA: {
                if (this.yCoord < 5) return 3F;
                else if (this.yCoord > 220) return 1F;
                else return (float) ((-2D) / 215D * (double) this.yCoord + 131D / 43D);
            }
            case AQUA: {
                if (worldObj.isThundering()) return 6F;
                else if (worldObj.isRaining()) return 3F;
                else if (!worldObj.isThundering() && !worldObj.isRaining()
                        && worldObj.getBlock(xCoord, yCoord + 1, zCoord).equals(Blocks.water))
                    return 2F;
                else return 1F;
            }
            case IGNIS: {
                float mult = 1F;

                if (VisNetHandler.drainVis(worldObj, xCoord, yCoord, zCoord, Aspect.FIRE, 10) >= 10) mult *= 3F;
                if (this.worldObj.provider.dimensionId == (-1)) mult *= 2F;

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
                if ((this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord))
                        && (!this.worldObj.isRaining())
                        && (!this.worldObj.isThundering())) {
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

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {}

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public String getInventoryName() {
        return guiname;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

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
    public void setFacing(short facing) {}

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
        if (!side) return;

        if (isUniversalEnergyStored(getOutputVoltage() * getOutputAmperage())) {
            long tEU = IEnergyConnected.Util.emitEnergyToNetwork(getOutputVoltage(), getOutputAmperage(), this);
            drainEnergyUnits(ForgeDirection.DOWN, getOutputVoltage(), tEU);
        }
    }

    @Override
    public byte getColorization() {
        return this.color;
    }

    @Override
    public byte setColorization(byte aColor) {
        this.color = aColor;
        return this.color;
    }

    @Override
    public boolean isUniversalEnergyStored(long aEnergyAmount) {
        return aEnergyAmount < this.energySource.getEnergyStored();
    }

    @Override
    public long getUniversalEnergyStored() {
        return (long) this.energySource.getEnergyStored();
    }

    @Override
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
        } else voltAmp[1] = (1L + (generating / GT_Values.V[energySource.getSourceTier()]));
        return voltAmp;
    }

    @Override
    public long getOutputAmperage() {
        return calculateMaxVoltAmp()[1];
        // long ret;
        // if (this.energySource.getSourceTier() <= 4) {
        // if (this.generating <= GT_Values.V[this.energySource.getSourceTier() - 2]) {
        // ret = 1L;
        // }
        // if (this.generating % GT_Values.V[this.energySource.getSourceTier() - 2] == 0.0D) {
        // ret = (long) (this.generating / GT_Values.V[this.energySource.getSourceTier() - 2]);
        // } else
        // ret = (long) (1L + (this.generating / GT_Values.V[this.energySource.getSourceTier() - 2]));
        // } else {
        // if (this.generating <= GT_Values.V[this.energySource.getSourceTier() - 1]) {
        // ret = 1L;
        // }
        // if (this.generating % GT_Values.V[this.energySource.getSourceTier()] == 0.0D) {
        // ret = (long) (this.generating / GT_Values.V[this.energySource.getSourceTier() - 1]);
        // } else
        // ret = (long) (1L + (this.generating / GT_Values.V[this.energySource.getSourceTier() - 1]));
        // }
        // return ret;
    }

    @Override
    public long getOutputVoltage() {
        return GT_Values.V[this.energySource.getSourceTier()];
    }

    @Override
    public long getInputAmperage() {
        return 0L;
    }

    @Override
    public long getInputVoltage() {
        return 0L;
    }

    @Override
    public boolean decreaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooLessEnergy) {
        if (this.energySource.getEnergyStored() > aEnergy) {
            this.energySource.drawEnergy(aEnergy);
            this.storage = this.energySource.getEnergyStored();
            return true;
        }
        return false;
    }

    @Override
    public boolean increaseStoredEnergyUnits(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        return false;
    }

    @Override
    public boolean drainEnergyUnits(ForgeDirection side, long aVoltage, long aAmperage) {
        return decreaseStoredEnergyUnits(
                aVoltage * aAmperage,
                this.energySource.getEnergyStored() > aVoltage * aAmperage);
    }

    @Override
    public long getAverageElectricInput() {
        return 0L;
    }

    @Override
    public long getAverageElectricOutput() {
        return 0L;
    }

    @Override
    public long getStoredEU() {
        return (long) this.energySource.getEnergyStored();
    }

    @Override
    public long getEUCapacity() {
        return (long) this.energySource.getCapacity();
    }

    @Override
    public long getStoredSteam() {
        return 0L;
    }

    @Override
    public long getSteamCapacity() {
        return 0L;
    }

    @Override
    public boolean increaseStoredSteam(long aEnergy, boolean aIgnoreTooMuchEnergy) {
        return false;
    }

    @Override
    public long injectEnergyUnits(ForgeDirection side, long aVoltage, long aAmperage) {
        return 0L;
    }

    @Override
    public boolean inputEnergyFrom(ForgeDirection side) {
        return false;
    }

    @Override
    public boolean outputsEnergyTo(ForgeDirection side) {
        return true;
    }

    public boolean energyStateReady() {
        return false;
    }

    @Override
    public World getWorld() {
        return this.worldObj;
    }

    @Override
    public int getXCoord() {
        return this.xCoord;
    }

    @Override
    public short getYCoord() {
        return (short) this.yCoord;
    }

    @Override
    public int getZCoord() {
        return this.zCoord;
    }

    @Override
    public boolean isServerSide() {
        return side;
    }

    @Override
    public boolean isClientSide() {
        return side;
    }

    @Override
    public int getRandomNumber(int aRange) {
        return this.worldObj.rand.nextInt(aRange);
    }

    @Override
    public TileEntity getTileEntity(int aX, int aY, int aZ) {
        return this.worldObj.getTileEntity(aX, aY, aZ);
    }

    @Override
    public TileEntity getTileEntityOffset(int aX, int aY, int aZ) {
        return this.worldObj.getTileEntity(aX + this.xCoord, aY + this.yCoord, aZ + this.zCoord);
    }

    @Override
    public TileEntity getTileEntityAtSide(ForgeDirection side) {
        if (side == ForgeDirection.UNKNOWN) {
            return null;
        }
        int tX = getOffsetX(side, 1);
        int tY = getOffsetY(side, 1);
        int tZ = getOffsetZ(side, 1);
        return this.worldObj.getTileEntity(tX, tY, tZ);
    }

    @Override
    public TileEntity getTileEntityAtSideAndDistance(ForgeDirection side, int aDistance) {
        if (aDistance == 1) {
            return getTileEntityAtSide(side);
        }
        return getTileEntity(getOffsetX(side, aDistance), getOffsetY(side, aDistance), getOffsetZ(side, aDistance));
    }

    @Override
    public IInventory getIInventory(int aX, int aY, int aZ) {
        return null;
    }

    @Override
    public IInventory getIInventoryOffset(int aX, int aY, int aZ) {
        return null;
    }

    @Override
    public IInventory getIInventoryAtSide(ForgeDirection side) {
        return null;
    }

    @Override
    public IInventory getIInventoryAtSideAndDistance(ForgeDirection side, int aDistance) {
        return null;
    }

    @Override
    public IFluidHandler getITankContainer(int aX, int aY, int aZ) {
        return null;
    }

    @Override
    public IFluidHandler getITankContainerOffset(int aX, int aY, int aZ) {
        return null;
    }

    @Override
    public IFluidHandler getITankContainerAtSide(ForgeDirection side) {
        return null;
    }

    @Override
    public IFluidHandler getITankContainerAtSideAndDistance(ForgeDirection side, int aDistance) {
        return null;
    }

    @Override
    public IGregTechTileEntity getIGregTechTileEntity(int aX, int aY, int aZ) {
        TileEntity tTileEntity = getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            return (IGregTechTileEntity) tTileEntity;
        }
        return null;
    }

    @Override
    public IGregTechTileEntity getIGregTechTileEntityOffset(int aX, int aY, int aZ) {
        return getIGregTechTileEntity(aX + this.xCoord, aY + this.yCoord, aZ + this.zCoord);
    }

    @Override
    public IGregTechTileEntity getIGregTechTileEntityAtSide(ForgeDirection side) {
        TileEntity tTileEntity = getTileEntityAtSide(side);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            return (IGregTechTileEntity) tTileEntity;
        }
        return null;
    }

    @Override
    public IGregTechTileEntity getIGregTechTileEntityAtSideAndDistance(ForgeDirection side, int aDistance) {
        TileEntity tTileEntity = getTileEntityAtSideAndDistance(side, aDistance);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            return (IGregTechTileEntity) tTileEntity;
        }
        return null;
    }

    @Override
    public Block getBlock(int aX, int aY, int aZ) {
        return this.worldObj.getBlock(aX, aY, aZ);
    }

    @Override
    public Block getBlockOffset(int aX, int aY, int aZ) {
        return getBlock(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
    }

    @Override
    public Block getBlockAtSide(ForgeDirection side) {
        if (side == ForgeDirection.UNKNOWN) {
            return null;
        }
        int tX = getOffsetX(side, 1);
        int tY = getOffsetY(side, 1);
        int tZ = getOffsetZ(side, 1);
        return this.worldObj.getBlock(tX, tY, tZ);
    }

    @Override
    public Block getBlockAtSideAndDistance(ForgeDirection side, int aDistance) {
        if (aDistance == 1) {
            return getBlockAtSide(side);
        }
        return getBlock(getOffsetX(side, aDistance), getOffsetY(side, aDistance), getOffsetZ(side, aDistance));
    }

    @Override
    public byte getMetaID(int aX, int aY, int aZ) {
        return (byte) this.worldObj.getBlockMetadata(aX, aY, aZ);
    }

    @Override
    public byte getMetaIDOffset(int aX, int aY, int aZ) {
        return getMetaID(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
    }

    @Override
    public byte getMetaIDAtSide(ForgeDirection side) {
        int tX = getOffsetX(side, 1);
        int tY = getOffsetY(side, 1);
        int tZ = getOffsetZ(side, 1);
        return (byte) this.worldObj.getBlockMetadata(tX, tY, tZ);
    }

    @Override
    public byte getMetaIDAtSideAndDistance(ForgeDirection side, int aDistance) {
        if (aDistance == 1) {
            return getMetaIDAtSide(side);
        }
        return getMetaID(getOffsetX(side, aDistance), getOffsetY(side, aDistance), getOffsetZ(side, aDistance));
    }

    @Override
    public byte getLightLevel(int aX, int aY, int aZ) {
        return 0;
    }

    @Override
    public byte getLightLevelOffset(int aX, int aY, int aZ) {
        return 0;
    }

    @Override
    public byte getLightLevelAtSide(ForgeDirection side) {
        return 0;
    }

    @Override
    public byte getLightLevelAtSideAndDistance(ForgeDirection side, int aDistance) {
        return 0;
    }

    @Override
    public boolean getOpacity(int aX, int aY, int aZ) {
        return false;
    }

    @Override
    public boolean getOpacityOffset(int aX, int aY, int aZ) {
        return false;
    }

    @Override
    public boolean getOpacityAtSide(ForgeDirection side) {
        return false;
    }

    @Override
    public boolean getOpacityAtSideAndDistance(ForgeDirection side, int aDistance) {
        return false;
    }

    @Override
    public boolean getSky(int aX, int aY, int aZ) {
        boolean sky = true;
        do {
            if (!this.worldObj.getBlock(aX, ++aY, aZ).getMaterial().equals(Material.air)) {
                sky = false;
            }
        } while ((sky) && (aY < 256));
        return sky;
    }

    @Override
    public boolean getSkyOffset(int aX, int aY, int aZ) {
        return getSky(aX + this.xCoord, aY + this.yCoord, aZ + this.zCoord);
    }

    @Override
    public boolean getSkyAtSide(ForgeDirection side) {
        return false;
    }

    @Override
    public boolean getSkyAtSideAndDistance(ForgeDirection side, int aDistance) {
        return false;
    }

    @Override
    public boolean getAir(int aX, int aY, int aZ) {
        return this.worldObj.getBlock(aX, aY, aZ).equals(Blocks.air);
    }

    @Override
    public boolean getAirOffset(int aX, int aY, int aZ) {
        return getAir(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
    }

    @Override
    public boolean getAirAtSide(ForgeDirection side) {
        return getAirAtSideAndDistance(side, 1);
    }

    @Override
    public boolean getAirAtSideAndDistance(ForgeDirection side, int aDistance) {
        return getAir(getOffsetX(side, aDistance), getOffsetY(side, aDistance), getOffsetZ(side, aDistance));
    }

    @Override
    public BiomeGenBase getBiome() {
        return this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord);
    }

    @Override
    public BiomeGenBase getBiome(int aX, int aZ) {
        return this.worldObj.getBiomeGenForCoords(aX, aZ);
    }

    @Override
    public int getOffsetX(ForgeDirection side, int aMultiplier) {
        return this.xCoord + side.offsetX * aMultiplier;
    }

    @Override
    public short getOffsetY(ForgeDirection side, int aMultiplier) {
        return (short) (this.yCoord + side.offsetY * aMultiplier);
    }

    @Override
    public int getOffsetZ(ForgeDirection side, int aMultiplier) {
        return this.zCoord + side.offsetZ * aMultiplier;
    }

    @Override
    public boolean isDead() {
        return this.dead;
    }

    @Override
    public void sendBlockEvent(byte aID, byte aValue) {
        GT_Values.NW.sendPacketToAllPlayersInRange(
                this.worldObj,
                new GT_Packet_Block_Event(this.xCoord, (short) this.yCoord, this.zCoord, aID, aValue),
                this.xCoord,
                this.zCoord);
    }

    @Override
    public long getTimer() {
        return this.timer;
    }

    @Override
    public void setLightValue(byte aLightValue) {}

    @Override
    public boolean isInvalidTileEntity() {
        return this.tileEntityInvalid;
    }

    @Override
    public boolean outputsEnergyTo(ForgeDirection b, boolean b1) {
        return true;
    }

    @Override
    public boolean inputEnergyFrom(ForgeDirection b, boolean b1) {
        return false;
    }

    @Override
    public ModularWindow createWindow(UIBuildContext buildContext) {
        ModularWindow.Builder builder = ModularWindow.builder(176, 107);
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);

        builder.widget(new TextWidget(new Text(getInventoryName()).color(0x64fc06).shadow()).setPos(9, 9)).widget(
                new ProgressBar()
                        .setTexture(EMT_UITextures.PICTURE_GAUGE_EMPTY_25, EMT_UITextures.PICTURE_GAUGE_SOLAR, 25)
                        .setProgress(() -> (float) storage / this.maxstorage).setSynced(false, false).setPos(9, 24)
                        .setSize(25, 11))
                .widget(
                        new ProgressBar()
                                .setTexture(
                                        EMT_UITextures.PICTURE_GAUGE_EMPTY_11,
                                        EMT_UITextures.PICTURE_SOLAR_INDICATOR,
                                        11)
                                .setProgress(() -> generating > 9 ? 1f : 0f).setSynced(false, false).setPos(9, 43)
                                .setSize(11, 11))
                .widget(
                        new TextWidget().setStringSupplier(
                                () -> StatCollector.translateToLocal("emt.Storage") + " "
                                        + numberFormat.formatWithSuffix(storage)
                                        + "/"
                                        + numberFormat.formatWithSuffix(maxstorage)
                                        + " EU")
                                .setDefaultColor(0).setPos(36, 22))
                .widget(new FakeSyncWidget.LongSyncer(() -> storage, val -> storage = val))
                .widget(
                        new TextWidget().setStringSupplier(
                                () -> StatCollector.translateToLocal("emt.Generating") + " "
                                        + numberFormat.formatWithSuffix((long) generating)
                                        + " EU/t")
                                .setDefaultColor(0).setPos(36, 35))
                .widget(new FakeSyncWidget.DoubleSyncer(() -> generating, val -> generating = val));

        return builder.build();
    }
}
