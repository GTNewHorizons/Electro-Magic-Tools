package emt.tile.generator;

import cpw.mods.fml.common.network.NetworkRegistry;
import emt.tile.DefinitelyNotAIC2Source;
import emt.tile.TileEntityEMT;
import emt.util.EMTConfigHandler;
import emt.util.EMTEssentiasOutputs;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.tileentity.IBasicEnergyContainer;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.net.GT_Packet_Block_Event;
import net.minecraft.block.Block;
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
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXEssentiaSource;

public class TileEntityBaseGenerator
        extends TileEntityEMT
        implements IInventory, IAspectContainer, IEssentiaTransport, IHasWorldObjectAndCoords, IEnergyConnected, IBasicEnergyContainer {
    public DefinitelyNotAIC2Source energySource = new DefinitelyNotAIC2Source(this, 100000L, 2);
    public Aspect aspect;
    public double generating;
    public int tick = 0;
    public boolean isActive = false;
    public int tier;
    public int storage;
    public short mpshownstroage;
    public int maxstorage;
    public byte maxfuel;
    public byte fuel;
    public byte refuel;
    public long timer = 0L;
    public boolean dead = true;
    public byte color;
    private boolean side;

    public TileEntityBaseGenerator(int aspect) {
        this();
        switch (aspect) {
            case 0:
                this.aspect = Aspect.ENERGY;
                break;
            case 1:
                this.aspect = Aspect.FIRE;
                break;
            case 2:
                this.aspect = Aspect.AURA;
                break;
            case 3:
                this.aspect = Aspect.TREE;
                break;
            case 4:
                this.aspect = Aspect.AIR;
                break;
            case 5:
                this.aspect = Aspect.GREED;
                break;
        }
        this.generating = EMTEssentiasOutputs.outputs.get(this.aspect.getTag());
    }

    public TileEntityBaseGenerator() {
        this.energySource.setCapacity(EMTConfigHandler.EssentiaGeneratorStorage);
        this.maxstorage = ((int) this.energySource.getCapacity());
        this.maxfuel = 64;
        this.fuel = 0;
        this.refuel = 1;
        this.color = -1;
    }

    public void updateEntity() {
        this.side = !this.worldObj.isRemote;
        this.dead = false;
        this.timer += 1L;
        if (this.timer <= Long.MAX_VALUE - 1)
            this.timer = 0L;
        storeFuel();
        fillfrompipe();
        createEnergy();
        inputintoGTnet();
    }


    public void storeFuel() {
        if (!side)
            return;

        if (this.fuel < this.maxfuel) {
            for (int x = this.xCoord - 4; x < this.xCoord + 4; x++) {
                for (int y = this.yCoord - 4; y < this.yCoord + 4; y++) {
                    for (int z = this.zCoord - 4; z < this.zCoord + 4; z++) {
                        TileEntity tile = this.worldObj.getTileEntity(x, y, z);
                        if (tile instanceof IAspectSource) {
                            IAspectSource as = (IAspectSource) tile;
                            if ((as.doesContainerContainAmount(this.aspect, this.refuel)) &&
                                    (as.takeFromContainer(this.aspect, this.refuel))) {
                                PacketHandler.INSTANCE.sendToAllAround(new PacketFXEssentiaSource(this.xCoord, this.yCoord, this.zCoord, (byte) (this.xCoord - x), (byte) (this.yCoord - y), (byte) (this.zCoord - z), this.aspect.getColor()), new NetworkRegistry.TargetPoint(getWorldObj().provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 32.0D));
                                addToContainer(this.aspect, this.refuel);
                            }
                        }
                    }
                }
            }
        }
    }

    public void fillfrompipe() {
        if (!side)
            return;

        if (this.fuel == this.maxfuel)
            return;

        TileEntity[] te = new TileEntity[ForgeDirection.VALID_DIRECTIONS.length];
        for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
            te[i] = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.VALID_DIRECTIONS[i]);
            if (te[i] != null) {
                IEssentiaTransport pipe = (IEssentiaTransport) te[i];
                if (!pipe.canOutputTo(ForgeDirection.VALID_DIRECTIONS[i])) {
                    return;
                }
                if ((pipe.getEssentiaType(ForgeDirection.VALID_DIRECTIONS[i]) != null) && (pipe.getEssentiaType(ForgeDirection.VALID_DIRECTIONS[i]).equals(this.aspect)) && (pipe.getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i]) < getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i]))) {
                    addToContainer(this.aspect, pipe.takeEssentia(this.aspect, 1, ForgeDirection.VALID_DIRECTIONS[i]));
                }
            }
        }
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("tile.EMT.essentia." + this.aspect.getTag() + ".name");
    }

    public void createEnergy() {
        if (!side) {
            if (this.isActive && this.tick < 400) {
                this.tick += 1;
            }
            return;
        }

        if (this.fuel > 0) {
            if (this.storage + this.generating / 20.0D / 20.0D < this.maxstorage) {
                this.isActive = true;
                this.energySource.addEnergy(this.generating / 20.0D / 20.0D);
                this.storage = ((int) this.energySource.getEnergyStored());
                this.tick += 1;
                if (this.tick == 400) {
                    this.fuel -= 1;
                    this.tick = 0;
                }
            } else {
                this.isActive = false;
            }
        } else {
            this.isActive = false;
        }
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.energySource.setEnergyStored(tag.getLong("estore"));
        if (tag.getByte("fuel") > this.fuel) {
            this.fuel = tag.getByte("fuel");
        }
        if (!tag.getString("aspect").isEmpty()) {
            this.aspect = Aspect.getAspect(tag.getString("aspect"));
            this.generating = EMTEssentiasOutputs.outputs.get(this.aspect.getTag());
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("fuel", this.fuel);
        tag.setLong("estore", this.energySource.getEnergyStored());
        if (aspect != null)
            tag.setString("aspect", this.aspect.getTag());
    }

    public int getSizeInventory() {
        return 5;
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
        return false;
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

    public int gaugeEnergyScaled(int i) {
        return this.mpshownstroage * 1000 * i / this.maxstorage;
    }

    public int gaugeFuelScaled(int i) {
        return this.fuel * i / this.maxfuel;
    }

    public boolean isItemValidForSlot(int i, ItemStack Stack) {
        return false;
    }

    public AspectList getAspects() {
        return new AspectList().add(this.aspect, this.fuel);
    }

    public void setAspects(AspectList aspects) {
    }

    public boolean doesContainerAccept(Aspect tag) {
        return tag.equals(this.aspect);
    }

    public int addToContainer(Aspect tag, int amount) {
        if ((!tag.equals(this.aspect)) || (amount < 0)) {
            return amount;
        }
        if (amount + this.fuel < this.maxfuel) {
            this.fuel += amount;
            return 0;
        }
        this.fuel += amount - (amount - (this.maxfuel - this.fuel));
        return amount - (this.maxfuel - this.fuel);
    }

    public boolean takeFromContainer(Aspect tag, int amount) {
        return false;
    }

    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    public boolean doesContainerContainAmount(Aspect tag, int amount) {
        return (tag.equals(this.aspect)) && (amount <= this.fuel);
    }

    public boolean doesContainerContain(AspectList ot) {
        return ot.aspects.containsKey(this.aspect) && ot.aspects.get(this.aspect) == this.fuel;
    }

    public int containerContains(Aspect tag) {
        return tag == this.aspect ? this.fuel : 0;
    }

    public boolean isConnectable(ForgeDirection face) {
        return true;
    }

    public boolean canInputFrom(ForgeDirection face) {
        return true;
    }

    public boolean canOutputTo(ForgeDirection face) {
        return false;
    }

    public void setSuction(Aspect aspect, int amount) {
    }

    public Aspect getSuctionType(ForgeDirection face) {
        return this.aspect;
    }

    public int getSuctionAmount(ForgeDirection face) {
        return this.fuel == this.maxfuel ? 0 : 128;
    }

    public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return 0;
    }

    public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
        if (amount < this.maxfuel - this.fuel) {
            return amount;
        }
        return this.maxfuel - addToContainer(aspect, amount);
    }

    public Aspect getEssentiaType(ForgeDirection face) {
        return this.aspect;
    }

    public int getEssentiaAmount(ForgeDirection face) {
        return this.fuel;
    }

    public int getMinimumSuction() {
        return Integer.MAX_VALUE;
    }

    public boolean renderExtendedTube() {
        return true;
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

    public long getOutputAmperage() {
        if (this.generating / 20.0D / 20.0D <= GT_Values.V[this.energySource.getSourceTier()]) {
            return 1L;
        }
        if (this.generating / 20.0D / 20.0D % GT_Values.V[this.energySource.getSourceTier()] == 0.0D) {
            return (long) (this.generating / 20.0D / 20.0D / GT_Values.V[this.energySource.getSourceTier()]);
        }
        return (long) (1L + (this.generating / 20.0D / 20.0D / GT_Values.V[this.energySource.getSourceTier()]));
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
            this.storage = ((int) this.energySource.getEnergyStored());
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
        return true;
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
        return !worldObj.isRemote;
    }

    public boolean isClientSide() {
        return worldObj.isRemote;
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
            if (!this.worldObj.getBlock(aX, ++aY, aZ).equals(Blocks.air)) {
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
