package emt.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEMT extends TileEntity {

    public int facing;
    public boolean isOn;

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        this.facing = tagCompound.getInteger("facing");
        this.isOn = tagCompound.getBoolean("isOn");
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("facing", this.facing);
        tagCompound.setBoolean("isOn", this.isOn);
        super.writeToNBT(tagCompound);
    }

    public final Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
        return packet;
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();
        readFromNBT(nbt);
    }

    public void markDirty() {
        super.markDirty();
        if (this.worldObj.isRemote) {
            return;
        }
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
}
