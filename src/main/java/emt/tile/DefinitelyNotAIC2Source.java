package emt.tile;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.FMLCommonHandler;

public class DefinitelyNotAIC2Source {

    public final TileEntity parent;
    protected long capacity;
    protected byte tier;
    protected long power;
    protected long energyStored;

    public DefinitelyNotAIC2Source(TileEntity parent1, long capacity1, int tier1) {
        long power = 8L << (2 * tier1);
        this.parent = parent1;
        this.capacity = capacity1 < power ? power : capacity1;
        this.tier = (byte) tier1;
        this.power = power;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public void setCapacity(double capacity1) {
        if (capacity1 < this.power) {
            capacity1 = this.power;
        }

        this.capacity = (long) capacity1;
    }

    public void setCapacity(long capacity1) {
        if (capacity1 < this.power) {
            capacity1 = this.power;
        }

        this.capacity = capacity1;
    }

    public long getEnergyStored() {
        return this.energyStored;
    }

    public void setEnergyStored(long amount) {
        this.energyStored = amount;
    }

    public double addEnergy(double amount) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            return 0.0D;
        } else {
            if (amount > this.capacity - this.energyStored) {
                amount = this.capacity - this.energyStored;
            }

            this.energyStored += amount;
            return amount;
        }
    }

    public void drawEnergy(double amount) {
        this.energyStored -= amount;
    }

    public byte getSourceTier() {
        return this.tier;
    }
}
