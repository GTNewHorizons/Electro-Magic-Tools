package emt.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.forge.InvWrapper;
import com.gtnewhorizons.modularui.api.screen.ITileWithModularUI;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;

import emt.init.EMTBlocks;
import emt.util.EMTConfigHandler;
import emt.util.EMTTextHelper;
import ic2.api.tile.IWrenchable;
import thaumcraft.common.config.ConfigItems;

public class TileEntityEtherealMacerator extends TileEntityEMT
        implements ISidedInventory, IWrenchable, ITileWithModularUI {

    private static final UITexture PROGRESSBAR_MACERATE = UITexture.fullImage("gregtech", "gui/progressbar/macerate");
    private static final int[] slots_top = new int[] { 0 };
    private static final int[] slots_bottom = new int[] { 2, 1 };
    private static final int[] slots_sides = new int[] { 1 };
    public int maceratingSpeed = EMTConfigHandler.etherealProcessorBaseSpeed;
    public int cookTime;
    private ItemStack[] slots = new ItemStack[3];

    @Override
    public int getSizeInventory() {
        return this.slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.slots[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.slots[par1] != null) {
            ItemStack itemstack;

            if (this.slots[par1].stackSize <= par2) {
                itemstack = this.slots[par1];
                this.slots[par1] = null;
                return itemstack;
            } else {
                itemstack = this.slots[par1].splitStack(par2);

                if (this.slots[par1].stackSize == 0) {
                    this.slots[par1] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.slots[par1] != null) {
            ItemStack itemstack = this.slots[par1];
            this.slots[par1] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.slots[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
        this.slots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.slots.length) {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        this.cookTime = tagCompound.getShort("CookTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setShort("CookTime", (short) this.cookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.slots.length; ++i) {
            if (this.slots[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.slots[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tagCompound.setTag("Items", nbttaglist);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void updateEntity() {
        if (this.canSmelt() && !isOverLimit(1) && !isOverLimit(2) && isAllowed(this.slots[0])) {
            ++this.cookTime;
            this.isOn = true;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

            if (this.cookTime == this.maceratingSpeed) {
                this.cookTime = 0;
                this.smeltItem();
            }
        } else {
            this.cookTime = 0;
            this.isOn = false;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    private boolean canSmelt() {
        if (this.slots[0] == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
            if (itemstack == null) return false;
            if (this.slots[2] == null) return true;
            if (!this.slots[2].isItemEqual(itemstack)) return false;
            int result = slots[2].stackSize + (itemstack.stackSize * 2);
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);

            if (this.slots[2] == null) {
                this.slots[2] = itemstack.copy();
                this.slots[2].stackSize *= 2;
            } else if (this.slots[2].isItemEqual(itemstack)) {
                slots[2].stackSize += (itemstack.stackSize * 2);
            }

            --this.slots[0].stackSize;

            if (this.slots[0].stackSize <= 0) {
                this.slots[0] = null;
            }
            this.addBonus();
        }
    }

    public void addBonus() {
        if (this.canSmelt()) {
            if (this.worldObj.rand.nextInt(EMTConfigHandler.etherealProcessorBonus) == 0) {
                if (this.slots[1] == null) {
                    this.setInventorySlotContents(1, new ItemStack(ConfigItems.itemNugget, 1, 6));
                } else if (this.slots[1].isItemEqual(new ItemStack(ConfigItems.itemNugget, 1, 6))
                        && this.isOverLimit(1) == false
                        && this.isOverLimit(2) == false) {
                            slots[1] = new ItemStack(getStackInSlot(1).getItem(), slots[1].stackSize + 1, 2);
                        }
            }
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
                : par1EntityPlayer.getDistanceSq(
                        (double) this.xCoord + 0.5D,
                        (double) this.yCoord + 0.5D,
                        (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot != 0) return false;
        if (FurnaceRecipes.smelting().getSmeltingResult(stack) != null) return true;
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int par1) {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
        return true;
    }

    public boolean isOverLimit(int i) {
        if (slots[i] != null) {
            if (slots[i].stackSize >= 63) return true;
            else if (slots[i].stackSize < 63) return false;
            else return false;
        } else {
            return false;
        }
    }

    public boolean isAllowed(ItemStack stack) {
        if (stack == null) return false;
        int[] oreDictIDs = OreDictionary.getOreIDs(stack);
        for (int oreDictID : oreDictIDs) {
            String tName = OreDictionary.getOreName(oreDictID);
            for (String allowed : EMTConfigHandler.etherealMaceratorWhiteList) {
                if (tName.indexOf(allowed) == 0) return true;
            }
        }
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
        return 1;
    }

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        return new ItemStack(EMTBlocks.emtMachines, 1, 1);
    }

    @Override
    public String getInventoryName() {
        return "Ethereal Macerator";
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
    public ModularWindow createWindow(UIBuildContext buildContext) {
        ModularWindow.Builder builder = ModularWindow.builder(176, 166);
        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);
        builder.bindPlayerInventory(buildContext.getPlayer());
        InvWrapper invWrapper = new InvWrapper(this);

        builder.widget(
                new SlotWidget(invWrapper, 0)
                        .setFilter(
                                stack -> isAllowed(stack) && FurnaceRecipes.smelting().getSmeltingResult(stack) != null)
                        .setPos(55, 25))
                .widget(
                        new SlotWidget(invWrapper, 2).setAccess(true, false)
                                .setBackground(ModularUITextures.ITEM_SLOT.withFixedSize(26, 26, -4, -4))
                                .setPos(115, 25))
                .widget(new SlotWidget(invWrapper, 1).setAccess(true, false).setPos(115, 52))
                .widget(
                        new ProgressBar().setTexture(PROGRESSBAR_MACERATE, 20)
                                .setProgress(() -> (float) cookTime / maceratingSpeed).setPos(81, 36).setSize(20, 18))
                .widget(new TextWidget(EMTTextHelper.localize("gui.EMT.etherealMacerator.title")).setPos(6, 6));

        return builder.build();
    }
}
