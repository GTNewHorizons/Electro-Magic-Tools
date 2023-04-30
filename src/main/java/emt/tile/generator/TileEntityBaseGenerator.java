package emt.tile.generator;

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

import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.screen.ITileWithModularUI;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;
import com.gtnewhorizons.modularui.common.widget.TextWidget;

import cpw.mods.fml.common.network.NetworkRegistry;
import emt.client.gui.EMT_UITextures;
import emt.tile.DefinitelyNotAIC2Source;
import emt.tile.TileEntityEMT;
import emt.util.EMTConfigHandler;
import emt.util.EMTEssentiasOutputs;
import emt.util.EMTTextHelper;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.tileentity.IBasicEnergyContainer;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import gregtech.api.net.GT_Packet_Block_Event;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXEssentiaSource;

public class TileEntityBaseGenerator extends TileEntityEMT implements IInventory, IAspectContainer, IEssentiaTransport,
        IHasWorldObjectAndCoords, IEnergyConnected, IBasicEnergyContainer, ITileWithModularUI {

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

    @Override
    public void updateEntity() {
        this.side = !this.worldObj.isRemote;
        this.dead = false;
        this.timer += 1L;
        if (this.timer <= Long.MAX_VALUE - 1) this.timer = 0L;
        storeFuel();
        fillfrompipe();
        createEnergy();
        inputintoGTnet();
    }

    public void storeFuel() {
        if (!side) return;

        if (this.fuel < this.maxfuel) {
            for (int x = this.xCoord - 4; x < this.xCoord + 4; x++) {
                for (int y = this.yCoord - 4; y < this.yCoord + 4; y++) {
                    for (int z = this.zCoord - 4; z < this.zCoord + 4; z++) {
                        TileEntity tile = this.worldObj.getTileEntity(x, y, z);
                        if (tile instanceof IAspectSource) {
                            IAspectSource as = (IAspectSource) tile;
                            if ((as.doesContainerContainAmount(this.aspect, this.refuel))
                                    && (as.takeFromContainer(this.aspect, this.refuel))) {
                                PacketHandler.INSTANCE.sendToAllAround(
                                        new PacketFXEssentiaSource(
                                                this.xCoord,
                                                this.yCoord,
                                                this.zCoord,
                                                (byte) (this.xCoord - x),
                                                (byte) (this.yCoord - y),
                                                (byte) (this.zCoord - z),
                                                this.aspect.getColor()),
                                        new NetworkRegistry.TargetPoint(
                                                getWorldObj().provider.dimensionId,
                                                this.xCoord,
                                                this.yCoord,
                                                this.zCoord,
                                                32.0D));
                                addToContainer(this.aspect, this.refuel);
                            }
                        }
                    }
                }
            }
        }
    }

    public void fillfrompipe() {
        if (!side) return;

        if (this.fuel == this.maxfuel) return;

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
                        && (pipe.getEssentiaType(ForgeDirection.VALID_DIRECTIONS[i]).equals(this.aspect))
                        && (pipe.getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i])
                                < getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i]))) {
                    addToContainer(this.aspect, pipe.takeEssentia(this.aspect, 1, ForgeDirection.VALID_DIRECTIONS[i]));
                }
            }
        }
    }

    @Override
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

    @Override
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

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("fuel", this.fuel);
        tag.setLong("estore", this.energySource.getEnergyStored());
        if (aspect != null) tag.setString("aspect", this.aspect.getTag());
    }

    @Override
    public int getSizeInventory() {
        return 5;
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
        return false;
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

    public int gaugeEnergyScaled(int i) {
        return this.mpshownstroage * 1000 * i / this.maxstorage;
    }

    public int gaugeFuelScaled(int i) {
        return this.fuel * i / this.maxfuel;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack Stack) {
        return false;
    }

    @Override
    public AspectList getAspects() {
        return new AspectList().add(this.aspect, this.fuel);
    }

    @Override
    public void setAspects(AspectList aspects) {}

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return tag.equals(this.aspect);
    }

    @Override
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

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amount) {
        return (tag.equals(this.aspect)) && (amount <= this.fuel);
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        return ot.aspects.containsKey(this.aspect) && ot.aspects.get(this.aspect) == this.fuel;
    }

    @Override
    public int containerContains(Aspect tag) {
        return tag == this.aspect ? this.fuel : 0;
    }

    @Override
    public boolean isConnectable(ForgeDirection face) {
        return true;
    }

    @Override
    public boolean canInputFrom(ForgeDirection face) {
        return true;
    }

    @Override
    public boolean canOutputTo(ForgeDirection face) {
        return false;
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {}

    @Override
    public Aspect getSuctionType(ForgeDirection face) {
        return this.aspect;
    }

    @Override
    public int getSuctionAmount(ForgeDirection face) {
        return this.fuel == this.maxfuel ? 0 : 128;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
        if (amount < this.maxfuel - this.fuel) {
            return amount;
        }
        return this.maxfuel - addToContainer(aspect, amount);
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection face) {
        return this.aspect;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection face) {
        return this.fuel;
    }

    @Override
    public int getMinimumSuction() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
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

    @Override
    public long getOutputAmperage() {
        if (this.generating / 20.0D / 20.0D <= GT_Values.V[this.energySource.getSourceTier()]) {
            return 1L;
        }
        if (this.generating / 20.0D / 20.0D % GT_Values.V[this.energySource.getSourceTier()] == 0.0D) {
            return (long) (this.generating / 20.0D / 20.0D / GT_Values.V[this.energySource.getSourceTier()]);
        }
        return (long) (1L + (this.generating / 20.0D / 20.0D / GT_Values.V[this.energySource.getSourceTier()]));
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
            this.storage = ((int) this.energySource.getEnergyStored());
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
        return true;
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
        return !worldObj.isRemote;
    }

    @Override
    public boolean isClientSide() {
        return worldObj.isRemote;
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
            if (!this.worldObj.getBlock(aX, ++aY, aZ).equals(Blocks.air)) {
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
    public boolean outputsEnergyTo(ForgeDirection side, boolean b1) {
        return true;
    }

    @Override
    public boolean inputEnergyFrom(ForgeDirection side, boolean b1) {
        return false;
    }

    @Override
    public ModularWindow createWindow(UIBuildContext buildContext) {
        ModularWindow.Builder builder = ModularWindow.builder(176, 107);
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);

        builder.widget(new TextWidget(new Text(getInventoryName()).color(0x64fc06).shadow()).setPos(9, 9)).widget(
                new ProgressBar()
                        .setTexture(EMT_UITextures.PICTURE_GAUGE_EMPTY_25, EMT_UITextures.PICTURE_GAUGE_GENERATOR, 25)
                        .setProgress(() -> (float) storage / this.maxstorage).setSynced(false, false).setPos(9, 24)
                        .setSize(25, 11))
                .widget(
                        new ProgressBar()
                                .setTexture(
                                        EMT_UITextures.PICTURE_GAUGE_EMPTY_25,
                                        EMT_UITextures.PICTURE_GAUGE_GENERATOR,
                                        25)
                                .setProgress(() -> (float) fuel / this.maxfuel).setSynced(false, false).setPos(9, 43)
                                .setSize(25, 11))
                .widget(
                        TextWidget
                                .dynamicString(
                                        () -> StatCollector.translateToLocal("emt.Storage")
                                                + EMTTextHelper.formatNumber(storage)
                                                + "/"
                                                + EMTTextHelper.formatNumber(maxstorage)
                                                + "EU")
                                .setSynced(false).setDefaultColor(0).setPos(36, 22))
                .widget(new FakeSyncWidget.IntegerSyncer(() -> storage, val -> storage = val))
                .widget(
                        TextWidget.dynamicString(
                                () -> StatCollector.translateToLocal("emt.Generating")
                                        + (isActive ? generating / 20 / 20 : 0)
                                        + " EU/t")
                                .setSynced(false).setDefaultColor(0).setPos(36, 35))
                .widget(new FakeSyncWidget.DoubleSyncer(() -> generating, val -> generating = val))
                .widget(new FakeSyncWidget.BooleanSyncer(() -> isActive, val -> isActive = val))
                .widget(
                        TextWidget.dynamicString(() -> StatCollector.translateToLocal("emt.Fuel") + ": " + fuel)
                                .setSynced(false).setDefaultColor(0).setPos(36, 48))
                .widget(new FakeSyncWidget.ByteSyncer(() -> fuel, val -> fuel = val))
                .widget(
                        TextWidget
                                .dynamicString(
                                        () -> StatCollector.translateToLocal("emt.remaining_second")
                                                + ((fuel * 20 * 20) - tick) / 20)
                                .setSynced(false).setDefaultColor(0).setPos(36, 63))
                .widget(new FakeSyncWidget.IntegerSyncer(() -> tick, val -> tick = val));

        return builder.build();
    }
}
