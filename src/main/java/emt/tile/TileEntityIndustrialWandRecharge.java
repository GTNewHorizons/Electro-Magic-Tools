package emt.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.forge.InvWrapper;
import com.gtnewhorizons.modularui.api.screen.ITileWithModularUI;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;

import cpw.mods.fml.common.FMLCommonHandler;
import emt.client.gui.EMT_UITextures;
import emt.init.EMTBlocks;
import emt.util.EMTConfigHandler;
import emt.util.EMTTextHelper;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.tile.IWrenchable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;

public class TileEntityIndustrialWandRecharge extends TileEntityEMT
        implements IInventory, IWrenchable, IEnergySink, ITileWithModularUI {

    private static final int capacity = 1000000;
    private static final int tier = 4;
    private double energyStored = 0;

    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        return true;
    }

    @Override
    public double getDemandedEnergy() {
        return Math.max(0.0D, (double) capacity - this.energyStored);
    }

    @Override
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if (this.getDemandedEnergy() > 0) {
            this.energyStored += amount;
            return 0.0D;
        }
        return amount;
    }

    @Override
    public int getSinkTier() {
        return tier;
    }

    private boolean canUseEnergy(double amount) {
        return this.energyStored >= amount;
    }

    private boolean useEnergy(double amount) {
        if (this.canUseEnergy(amount) && !FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            this.energyStored -= amount;
            return true;
        } else {
            return false;
        }
    }

    ItemStack ItemStacks[];

    public TileEntityIndustrialWandRecharge() {
        ItemStacks = new ItemStack[1];
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return ItemStacks[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (ItemStacks[i] != null) {

            if (ItemStacks[i].stackSize <= j) {
                ItemStack itemstack = ItemStacks[i];
                ItemStacks[i] = null;
                return itemstack;
            } else {
                ItemStack itemstack1 = ItemStacks[i].splitStack(j);

                if (ItemStacks[i].stackSize == 0) {
                    ItemStacks[i] = null;
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (ItemStacks[i] != null) {
            ItemStack itemstack = ItemStacks[i];
            ItemStacks[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void updateEntity() {
        if (this.worldObj.isRemote || getStackInSlot(0) == null) {
            return;
        }

        ItemStack wand = getStackInSlot(0);
        if (wand != null && wand.getItem() instanceof ItemWandCasting) {
            ItemWandCasting wandItem = (ItemWandCasting) wand.getItem();
            AspectList aspects = wandItem.getAspectsWithRoom((wand));
            if (aspects != null && aspects.size() != 0 && this.useEnergy(EMTConfigHandler.wandChargerConsumption)) {
                wandItem.addVis(wand, Aspect.ORDER, 1, true);
                wandItem.addVis(wand, Aspect.FIRE, 1, true);
                wandItem.addVis(wand, Aspect.ENTROPY, 1, true);
                wandItem.addVis(wand, Aspect.WATER, 1, true);
                wandItem.addVis(wand, Aspect.EARTH, 1, true);
                wandItem.addVis(wand, Aspect.AIR, 1, true);
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        ItemStacks[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.ItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.ItemStacks.length) {
                this.ItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        if (this.energyStored == 0) {
            this.energyStored = p_145839_1_.getDouble("Energy");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.ItemStacks.length; ++i) {
            if (this.ItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.ItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);
        p_145841_1_.setDouble("Energy", this.energyStored);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
            return false;
        }

        return player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public String getInventoryName() {
        return "Industrial Wand Charging Station";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

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
        return 0.8f;
    }

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        return new ItemStack(EMTBlocks.emtMachines, 1, 0);
    }

    @Override
    public ModularWindow createWindow(UIBuildContext buildContext) {
        ModularWindow.Builder builder = ModularWindow.builder(176, 166);
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);
        builder.bindPlayerInventory(buildContext.getPlayer());

        builder.widget(
                new SlotWidget(new InvWrapper(this), 0)
                        .setBackground(ModularUITextures.ITEM_SLOT, EMT_UITextures.OVERLAY_SLOT_WAND).setPos(79, 34))
                .widget(new TextWidget(EMTTextHelper.localize("gui.EMT.wandRecharge.title")).setPos(6, 6));

        return builder.build();
    }
}
