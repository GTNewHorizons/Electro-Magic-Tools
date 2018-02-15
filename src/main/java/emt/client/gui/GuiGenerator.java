package emt.client.gui;

import emt.EMT;
import emt.client.gui.container.ConainerGenerator;
import emt.tile.generator.TileEntityBaseGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiGenerator extends GuiContainer {
	
	private static ResourceLocation tex = new ResourceLocation(EMT.TEXTURE_PATH, "textures/guis/generator.png");
	
	TileEntityBaseGenerator tileentity;
	private boolean allowUserInput;
	private int xSize;
	private int ySize; 

	public GuiGenerator(InventoryPlayer invPlayer, TileEntityBaseGenerator entity) {
		super(new ConainerGenerator(invPlayer, entity));
		this.tileentity = entity;
		this.allowUserInput = false;
		this.xSize = 194;
		this.ySize = 168;
	}

	
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	  {
	    String formatPanelName = I18n.format(this.tileentity.getInventoryName(), new Object[0]);
	    int nmPos = (this.xSize - this.fontRendererObj.getStringWidth(formatPanelName)) / 2;
	    this.fontRendererObj.drawStringWithShadow(formatPanelName, nmPos, 7, 6618118);
	    
	    String storageString = I18n.format("emt.Storage", new Object[0]);
	    String fuel=I18n.format("emt.Fuel", new Object[0]);
	    if (this.tileentity.maxstorage<1000)
	    this.fontRendererObj.drawString(storageString + this.tileentity.storage + "/" + this.tileentity.maxstorage +"EU", 36, 22, 0);
	    else if (10000 <= this.tileentity.maxstorage && this.tileentity.maxstorage < 10000000)
	    	this.fontRendererObj.drawString(storageString + (this.tileentity.storage/1000) + "/" + (this.tileentity.maxstorage/1000) +"kEU", 36, 22, 0);
	    else if (10000000 <= this.tileentity.maxstorage && this.tileentity.maxstorage < 1000000000)
	    	this.fontRendererObj.drawString(storageString + (this.tileentity.storage/1000000) + "/" + (this.tileentity.maxstorage/1000000) +"MEU", 36, 22, 0);
	    else if (1000000000 <= this.tileentity.maxstorage)
	    	this.fontRendererObj.drawString(storageString + (this.tileentity.storage/1000000000) + "/" + (this.tileentity.maxstorage/1000000000) +"GEU", 36, 22, 0);
	    if(this.tileentity.isActive)
	    	this.fontRendererObj.drawString(I18n.format("emt.Generating",new Object[0])+Double.toString(this.tileentity.generating/20/20)+" EU/t", 36, 35, 0);
	    else
	    	this.fontRendererObj.drawString(I18n.format("emt.Generating",new Object[0])+"0 EU/t",36, 35, 0);
	    this.fontRendererObj.drawString(fuel+": "+Integer.toString(this.tileentity.fuel),36,48,0);
	    this.fontRendererObj.drawString(fuel+"/t: "+Integer.toString((this.tileentity.fuel*20*20)-this.tileentity.tick),36,63,0);
	  }

	@Override
	  protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	  {
	   // GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    
	    this.mc.renderEngine.bindTexture(tex);
	    
	    int h = (this.width - this.xSize) / 2;
	    int k = (this.height - this.ySize) / 2;
	    drawTexturedModalRect(h, k, 0, 0, this.xSize, this.ySize);
	    if (this.tileentity.storage > 0)
	    {
	      int l = this.tileentity.gaugeEnergyScaled(33);
	      drawTexturedModalRect(h + 18, k + 23, 195, 0, l, 14);
	    }
	    
	    if (this.tileentity.fuel > 0)
	    {
	      int l = this.tileentity.gaugeFuelScaled(33);
	      drawTexturedModalRect(h + 18, k + 42, 195, 0, l, 14);
	    }
	  }
}
