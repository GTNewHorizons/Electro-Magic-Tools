package emt.item.armor.wings;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.client.model.ModelWings;
import emt.util.EMTConfigHandler;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.util.StackUtil;
import thaumcraft.api.IRunicArmor;

public class ItemFeatherWing extends ItemArmor implements IRunicArmor {

    public int visDiscount = 0;

    public ItemFeatherWing(ArmorMaterial material, int par3, int par4) {
        super(material, par3, par4);
        this.setMaxStackSize(1);
        this.setMaxDamage(120);
        this.setCreativeTab(EMT.TAB);
        this.isDamageable();
    }

    public float getFallDamageMult() {
        return 0.75F;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EMT.TEXTURE_PATH + ":armor/wing_feather");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return EMT.TEXTURE_PATH + ":textures/models/featherwing.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, int armorSlot) {
        try {
            if (entity instanceof EntityPlayer) {
                ModelWings mw = new ModelWings();
                mw.isJumping = stack.stackTagCompound.getBoolean("isJumping");
                return mw;
            }
        } catch (NullPointerException e) {
            return new ModelWings();
        }
        return new ModelWings();
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        useWings(player, stack, world, 0.11f, 0.9f, 0.9f, 0);
    }

    public void useWings(EntityPlayer player, ItemStack stack, World world, float motionY, float motionXZ, float f1,
            int amount) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);

        boolean isJmuping = nbtData.getBoolean("isJumping");
        boolean isHolding = nbtData.getBoolean("isHolding");

        nbtData.setBoolean("isJumping", IC2.keyboard.isJumpKeyDown(player));

        if (isJmuping) {
            byte f = nbtData.getByte("f");
            nbtData.setBoolean("isHolding", true);
            if (IC2.keyboard.isSneakKeyDown(player)) nbtData.setByte("f", (byte) (0));
            else nbtData.setByte("f", (byte) (f + 1));
            if (f > 7) nbtData.setByte("f", (byte) 7);
        } else if (isHolding) {
            byte f = nbtData.getByte("f");
            nbtData.setBoolean("isHolding", false);
            if (!IC2.keyboard.isSneakKeyDown(player)) {
                player.motionY = motionY * f;
                ElectricItem.manager.use(stack, ((motionY * f) * 10) * amount, player);
                if (player.motionX < 0.5 && player.motionZ < 0.5 && player.motionX > -0.5 && player.motionZ > -0.5) {
                    player.motionX /= motionXZ;
                    player.motionZ /= motionXZ;
                }
                world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.ghast.fireball", 1, 1);
                for (int i = 0; i < 4; i++) {
                    world.spawnParticle(
                            "cloud",
                            player.posX - 1 + (world.rand.nextInt(100) / 50d),
                            player.posY - 1,
                            player.posZ - 1 + (world.rand.nextInt(100) / 50d),
                            0,
                            -0.5,
                            0);
                }
            }
            nbtData.setByte("f", (byte) 0);
        }

        if (isJmuping && !player.onGround && player.motionY < 0) {
            player.motionY *= f1;
        }

        if (player.isInWater() && !player.capabilities.isCreativeMode) {
            player.motionY += -0.03;
        }

        if (EMTConfigHandler.impactOfRain) {
            if ((world.getBiomeGenForCoords((int) player.posX, (int) player.posZ)).canSpawnLightningBolt()
                    && world.canBlockSeeTheSky((int) player.posX, (int) player.posY, (int) player.posZ)
                    && world.isRaining()
                    && !player.capabilities.isCreativeMode) {
                player.motionY += -0.03;
            }
        }

        if (player.isSneaking() && !player.onGround && player.motionY < 0) {
            player.motionY *= 0.6;
        }
    }

    @Override
    public int getRunicCharge(ItemStack itemStack) {
        return 0;
    }
}
