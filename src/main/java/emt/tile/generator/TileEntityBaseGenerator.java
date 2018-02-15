package emt.tile.generator;

import cpw.mods.fml.common.FMLCommonHandler;
import emt.tile.TileEntityEMT;
import emt.util.EMTConfigHandler;
import ic2.api.energy.prefab.BasicSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXEssentiaSource;

public class TileEntityBaseGenerator extends TileEntityEMT implements IInventory, IAspectContainer, IEssentiaTransport {

	public BasicSource energySource = new BasicSource(this, 100000, 2);
	public Aspect aspect;
	public double generating;
	public int tick = 0;
	public boolean isActive = false;
	public int tier;
	public int storage;
	public int maxstorage;
	public int maxfuel;
	public int fuel;
	public int refuel;

	public TileEntityBaseGenerator() {
		energySource.setCapacity(EMTConfigHandler.EssentiaGeneratorStorage);
		maxstorage = (int) energySource.getCapacity();
		maxfuel = 64;
		fuel = 0;
		storage = 0;
		refuel = 1;
	}

	@Override
	public void updateEntity() {
			storeFuel();
			fillfrompipe();
			createEnergy();
			energySource.updateEntity();
	}
	
	public void storeFuel() {
		if (fuel<maxfuel) {
		for (int x = this.xCoord - 4; x < this.xCoord + 4; x++) {
			for (int y = this.yCoord - 4; y < this.yCoord + 4; y++) {
				for (int z = this.zCoord - 4; z < this.zCoord + 4; z++) {
					TileEntity tile = this.worldObj.getTileEntity(x, y, z);
					if (tile != null && tile instanceof IAspectSource) {
						IAspectSource as = (IAspectSource) tile;
						if (as.doesContainerContainAmount(aspect, refuel)) {
							if (as.takeFromContainer(aspect, refuel)) {
								PacketHandler.INSTANCE.sendToAllAround(new PacketFXEssentiaSource(xCoord, yCoord, zCoord, (byte) (xCoord - x), (byte) (yCoord - y), (byte) (zCoord - z), aspect.getColor()), new cpw.mods.fml.common.network.NetworkRegistry.TargetPoint(this.getWorldObj().provider.dimensionId, xCoord, yCoord, zCoord, 32.0D));
								this.addToContainer(aspect, refuel);
							}
						}
					}
				}
			}
		}
	}
	}

	public void fillfrompipe() {
		
		TileEntity[] te = new TileEntity[ForgeDirection.VALID_DIRECTIONS.length];
		for (int i =0;i<ForgeDirection.VALID_DIRECTIONS.length;i++) {
				te[i]=ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.VALID_DIRECTIONS[i]);
				if (te[i] != null)
			    {
			      IEssentiaTransport pipe = (IEssentiaTransport)te[i];
			      if (!pipe.canOutputTo(ForgeDirection.VALID_DIRECTIONS[i])) {
			        return;
			      }
			      else
			    	  if (pipe.getEssentiaType(ForgeDirection.VALID_DIRECTIONS[i])!=null && pipe.getEssentiaType(ForgeDirection.VALID_DIRECTIONS[i]).equals(aspect) && (pipe.getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i]) < getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i]))) {
			    	        addToContainer(aspect, pipe.takeEssentia(aspect, 1, ForgeDirection.VALID_DIRECTIONS[i]));
			    }
		}	
	}
	}
	
	public void createEnergy() {
		if (fuel>0) {
		if (storage+(generating/20/20) < maxstorage) {
		isActive = true;
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
		energySource.addEnergy(generating/20/20);
		storage = (int) energySource.getEnergyStored();
		}
		tick = tick+1;
		if (tick == 20*20) {
		fuel=fuel-1;
		tick = 0;
		}
		}
		else if (storage == maxstorage)
			isActive = false;
		else if ((storage+(generating/20/20) > maxstorage) && storage != maxstorage) {
			isActive = true;
			if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			energySource.setEnergyStored((double)maxstorage);
			storage = maxstorage;
			}
			tick = tick+1;
			if (tick == 20*20) {
				fuel=fuel-1;
				tick = 0;
				}
			isActive = false;
		}
		
		}
		else {
			isActive = false;
		}
		
	}

	@Override
	public void onChunkUnload() {
		energySource.onChunkUnload();
	}

	@Override
	public void invalidate() {
		energySource.invalidate();
		super.invalidate();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energySource.readFromNBT(tag);
		if (tag.getInteger("storage")>storage)
			storage = tag.getInteger("storage");
		if (tag.getInteger("fuel")>fuel)
			fuel = tag.getInteger("fuel");
		
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("storage", this.storage);
		tag.setInteger("fuel", this.fuel);
		super.writeToNBT(tag);
		energySource.writeToNBT(tag);
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
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		
	}

	@Override
	public String getInventoryName() {
		return "EMT";
	}

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
	public void openInventory() {	
	}

	@Override
	public void closeInventory() {
	}
	
	public int gaugeEnergyScaled(int i)
	{
	  return this.storage * i / this.maxstorage;
	}
	  
	  public int gaugeFuelScaled(int i)
	  {
	    return fuel*i/maxfuel;
	  }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack Stack) {
		/*boolean ret= false;
		if (i<=4)
			ret = true;
		else if(IEssentiaContainerItem.class.isInstance(Stack))
			ret = true;
		return ret;*/
		return false;
	}
	
	@Override
	public AspectList getAspects() {
		return new AspectList().add(aspect, fuel);
	}

	@Override
	public void setAspects(AspectList aspects) {
		
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		if(!tag.equals(aspect))
		return false;
		else return true;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		if(!tag.equals(aspect)||amount<0)
			return amount;
		else if (amount+this.fuel<this.maxfuel) {
			this.fuel=this.fuel+amount;
			return 0;
		}
		else {
			this.fuel=this.fuel+(amount-(amount-(this.maxfuel-this.fuel)));
			return amount-(this.maxfuel-this.fuel);
		}
			
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
		return (tag.equals(aspect)&&amount <= fuel);
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return ot.aspects.containsKey(aspect);
	}

	@Override
	public int containerContains(Aspect tag) {
		return tag == aspect ? fuel : 0;
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
	public void setSuction(Aspect aspect, int amount) {
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return aspect;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 128;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		if(amount<(maxfuel-fuel))
			return amount;
		else
			return maxfuel - addToContainer(aspect,amount);	
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return aspect;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return fuel;
	}

	@Override
	public int getMinimumSuction() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean renderExtendedTube() {
		return true;
	}


}
