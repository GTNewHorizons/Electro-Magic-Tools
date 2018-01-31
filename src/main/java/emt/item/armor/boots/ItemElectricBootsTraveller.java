package emt.item.armor.boots;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.util.EMTConfigHandler;
import emt.util.EMTTextHelper;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IMetalArmor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;

import java.util.List;

public class ItemElectricBootsTraveller extends ItemArmor implements IElectricItem, IVisDiscountGear, IMetalArmor, ISpecialArmor {

	public int maxCharge = 100000;
	public int energyPerDamage = 1000;
	public int visDiscount = 2;
	public float speedBonus = 0.055F;
	public float jumpBonus = 0.3F;
	public double transferLimit = 100;

	public ItemElectricBootsTraveller(ArmorMaterial material, int par3, int par4) {
		super(material, par3, par4);
		this.setMaxDamage(27);
		this.setMaxStackSize(1);
		this.setCreativeTab(EMT.TAB);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
		ItemStack itemStack = new ItemStack(this, 1);
		if (getChargedItem(itemStack) == this) {
			ItemStack charged = new ItemStack(this, 1);
			ElectricItem.manager.charge(charged, 2147483647, 2147483647, true, false);
			itemList.add(charged);
		}
		if (getEmptyItem(itemStack) == this) {
			itemList.add(new ItemStack(this, 1, getMaxDamage()));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(EMT.TEXTURE_PATH + ":armor/boots_electric");
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source.isUnblockable()) {
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(0, 0.0D, 0);
		}
		else {
			double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
			int energyPerDamage = getEnergyPerDamage();
			double damageLimit = energyPerDamage <= 0 ? 0 : (25 * ElectricItem.manager.getCharge(armor)) / energyPerDamage;
			return new net.minecraftforge.common.ISpecialArmor.ArmorProperties(0, absorptionRatio, (int) damageLimit);
		}
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (ElectricItem.manager.getCharge(armor) >= getEnergyPerDamage()) {
			return (int) Math.round(20D * getBaseAbsorptionRatio() * getDamageAbsorptionRatio());
		}
		else {
			return 0;
		}
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		ElectricItem.manager.discharge(stack, damage * getEnergyPerDamage(), 0x7fffffff, true, false, false);
	}

	public double getDamageAbsorptionRatio() {
		return 0.5D;
	}

	private double getBaseAbsorptionRatio() {
		return 0.15D;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		list.add(EMTTextHelper.localize("tooltip.EMT.visDiscount") + ": " + String.valueOf(visDiscount) + "%");
	}

	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
		return visDiscount;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if ((!player.capabilities.isFlying) && (player.moveForward > 0.0F)) {
			if ((player.worldObj.isRemote) && (!player.isSneaking())) {
				if (!Thaumcraft.instance.entityEventHandler.prevStep.containsKey(Integer.valueOf(player.getEntityId()))) {
					Thaumcraft.instance.entityEventHandler.prevStep.put(Integer.valueOf(player.getEntityId()), Float.valueOf(player.stepHeight));
				}
				player.stepHeight = 1.0F;
			}
			
			if (((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F)) {
				player.moveFlying(0F, 1F, speedBonus);
				player.jumpMovementFactor = jumpBonus;
			}
		}
	}

	@SubscribeEvent
	public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			boolean hasArmor = player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == this;

			if (hasArmor)
				player.motionY += jumpBonus;
		}
	}

	@SubscribeEvent
	public void onLivingFall(LivingFallEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) event.entity;
			if ((entity.inventory.armorInventory[0] != null) && (entity.inventory.armorInventory[0].getItem() instanceof ItemElectricBootsTraveller)) {
				//Minimum distance for fall damage is 3.  Don't drain energy if not taking actual damage.
				if (event.distance >= 3) {
					ItemStack stack = entity.inventory.armorInventory[0];
					if (ElectricItem.manager.use(stack, energyPerDamage, entity))
						event.setCanceled(true);
				}
			}
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return EMT.TEXTURE_PATH + ":textures/models/electricboots.png";
	}

	@Override
	public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
		return true;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		if (EMTConfigHandler.enchanting == false) {
			return 0;
		}
		else {
			return 4;
		}
	}

	@Override
	public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
		if (EMTConfigHandler.enchanting == false) {
			return false;
		}
		else {
			return true;
		}
	}

	public int getEnergyPerDamage() {
		return energyPerDamage;
	}

	/* IC2 API METHODS */

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return false;
	}

	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return maxCharge;
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return 2;
	}

	@Override
	public double getTransferLimit(ItemStack itemStack) {
		return transferLimit;
	}

	@Override
	public Item getChargedItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack) {
		return this;
	}
}
