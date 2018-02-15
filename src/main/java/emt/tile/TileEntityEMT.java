package emt.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityEMT extends TileEntity {

	public int facing;
	public boolean isOn;

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		facing = tagCompound.getInteger("facing");
		isOn = tagCompound.getBoolean("isOn");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("facing", facing);
		tagCompound.setBoolean("isOn", isOn);
		super.writeToNBT(tagCompound);
	}

	@Override
	public final Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
		return packet;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.func_148857_g();
		readFromNBT(nbt);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (worldObj.isRemote) {
			return;
		}
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
