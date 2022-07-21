package emt.client.gui.container;

import emt.tile.generator.TileEntityBaseGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;

import java.util.Locale;

public class ConainerGenerator
        extends Container {
    private TileEntityBaseGenerator tileentity;

    public ConainerGenerator(InventoryPlayer inventoryplayer, TileEntityBaseGenerator tileentity) {
        this.tileentity = tileentity;
    }

    public void detectAndSendChanges() {
        for (int i = 0; i < this.crafters.size(); i++) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            icrafting.sendProgressBarUpdate(this, 0, (short) (this.tileentity.generating / 400));
            icrafting.sendProgressBarUpdate(this, 1, (byte) this.tileentity.fuel);
            icrafting.sendProgressBarUpdate(this, 2, (short) (this.tileentity.storage / 1000));
            icrafting.sendProgressBarUpdate(this, 3, (byte) encryptAspect(this.tileentity.aspect));
            icrafting.sendProgressBarUpdate(this, 4, (byte) (this.tileentity.isActive ? 1 : 0));
        }
    }

    private byte encryptAspect(Aspect aspect) {
        switch (aspect.getTag().toUpperCase(Locale.US)) {
            case "POTENTIA":
                return 0;
            case "IGNIS":
                return 1;
            case "AURAM":
                return 2;
            case "ARBOR":
                return 3;
            case "AER":
                return 4;
            case "LUCRUM":
                return 5;
        }
        return 0;
    }

    public void updateProgressBar(int i, int j) {
        if (i == 0) {
            this.tileentity.generating = (((int) j) * 400);
        } else if (i == 1) {
            if (j == 0 || this.tileentity.fuel != j)
                this.tileentity.tick = 0;
            this.tileentity.fuel = ((byte) j);
        } else if (i == 2) {
            this.tileentity.mpshownstroage = (short) j;
        } else if (i == 3 && this.tileentity.aspect == null)
            switch (j) {
                case 0:
                    this.tileentity.aspect = Aspect.ENERGY;
                    break;
                case 1:
                    this.tileentity.aspect = Aspect.FIRE;
                    break;
                case 2:
                    this.tileentity.aspect = Aspect.AURA;
                    break;
                case 3:
                    this.tileentity.aspect = Aspect.TREE;
                    break;
                case 4:
                    this.tileentity.aspect = Aspect.AIR;
                    break;
                case 5:
                    this.tileentity.aspect = Aspect.GREED;
                    break;
            }
        else if (i == 4) {
            this.tileentity.isActive = ((byte) j) == ((byte) 1);
        }
    }


    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return null;
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        this.detectAndSendChanges();
        return this.tileentity.isUseableByPlayer(entityplayer);
    }
}