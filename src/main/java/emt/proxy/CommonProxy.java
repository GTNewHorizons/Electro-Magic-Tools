package emt.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import emt.client.gui.GuiEtherealMacerator;
import emt.client.gui.GuiGenerator;
import emt.client.gui.GuiIndustrialWandRecharger;
import emt.client.gui.container.ConainerGenerator;
import emt.client.gui.container.ContainerEtheralMacerator;
import emt.client.gui.container.ContainerIndustrialWandRecharge;
import emt.tile.TileEntityEtherealMacerator;
import emt.tile.TileEntityIndustrialWandRecharge;
import emt.tile.generator.TileEntityBaseGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

	public void load() {
		registerRenders();
	}

	public void registerRenders() {
		/* Empty in base proxy */
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		switch (ID) {
			case 0:
				if (entity != null && entity instanceof TileEntityIndustrialWandRecharge) {
					return new ContainerIndustrialWandRecharge(player.inventory, (TileEntityIndustrialWandRecharge) entity);
				}
			case 1:
				if (entity != null && entity instanceof TileEntityEtherealMacerator) {
					return new ContainerEtheralMacerator(player.inventory, (TileEntityEtherealMacerator) entity);
				}
				else if (entity != null && entity instanceof TileEntityBaseGenerator) {
					return new ConainerGenerator(player.inventory, (TileEntityBaseGenerator) entity);
				}
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		switch (ID) {
			case 0:
				if (entity != null && entity instanceof TileEntityIndustrialWandRecharge) {
					return new GuiIndustrialWandRecharger(player.inventory, (TileEntityIndustrialWandRecharge) entity);
				}
			case 1:
				if (entity != null && entity instanceof TileEntityEtherealMacerator) {
					return new GuiEtherealMacerator(player.inventory, (TileEntityEtherealMacerator) entity);
				}
				else if (entity != null && entity instanceof TileEntityBaseGenerator) {
					return new GuiGenerator(player.inventory, (TileEntityBaseGenerator) entity);
				}
			default:
				return null;
		}
	}
}
