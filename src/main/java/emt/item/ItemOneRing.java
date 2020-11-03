package emt.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.util.EMTTextHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IWarpingGear;

import java.util.List;
import java.util.Random;

public class ItemOneRing extends ItemBase implements IBauble, IWarpingGear, IRunicArmor {
    private static final String NBT_TAG_FORGE_DATA = "EMT";
    private static final String NBT_TAG_MIND_CORRUPTION = "MindCorruption";
    private static final String NBT_TAG_WARP = "warp";
    public IIcon[] icon = new IIcon[16];
    public Random random = new Random();

    public ItemOneRing() {
        super("bauble");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }

    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return getItemWarpLevel(itemstack, false);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        String name = "";
        switch (itemstack.getItemDamage()) {
            case 0: {
                name = "oneRing";
                break;
            }
            default:
                name = "nothing";
                break;
        }
        return "item." + EMT.MOD_ID + ".bauble." + name;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        String colourCode = ""+ EnumChatFormatting.DARK_RED;
        list.add(colourCode+"The Ring whispers to you, promising power. But at what cost? Long-term usage is NOT advised.");
        list.add(colourCode+"Can RUIN your game if you don't understand what this does. READ THE QUEST!!");
        super.addInformation(itemStack, player, list, par4);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ri) {
        this.icon[0] = ri.registerIcon(EMT.TEXTURE_PATH + ":onering");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon[meta];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        list.add(new ItemStack(this, 1, 0));
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        if (stack.getItemDamage() <= 0) {
            return BaubleType.RING;
        } else {
            return null;
        }
    }

    private NBTTagCompound getOrSetForgeTag(EntityPlayer pPlayer) {
        NBTTagCompound tPlayerNBT = pPlayer.getEntityData();
        NBTTagCompound forgeTag = new NBTTagCompound();

        // getCompoundTag already returns the contents of "ForgeData" TagCompound. Changed this to
        // save our value in EMT Sub-Tag
        if (tPlayerNBT.hasKey(NBT_TAG_FORGE_DATA))
            forgeTag = tPlayerNBT.getCompoundTag(NBT_TAG_FORGE_DATA);
        else
            tPlayerNBT.setTag(NBT_TAG_FORGE_DATA, forgeTag);

        return forgeTag;
    }

    private int getItemWarpLevel(ItemStack stack, boolean increment) {
        NBTTagCompound tWarpTag = new NBTTagCompound();
        int tWarpLevel = 0;
        if (stack.hasTagCompound()) {
            if (stack.getTagCompound().hasKey(NBT_TAG_WARP)) {
                tWarpLevel = stack.getTagCompound().getInteger(NBT_TAG_WARP);
                if (increment)
                    stack.getTagCompound().setInteger(NBT_TAG_WARP, ++tWarpLevel);
            }
        } else {
            tWarpTag.setInteger(NBT_TAG_WARP, 0);
            stack.setTagCompound(tWarpTag);
        }

        return tWarpLevel;
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (!player.isInvisible()) {
            player.setInvisible(true);
            ((EntityPlayer) player).capabilities.disableDamage = true;
        }

        // Stop doing this 20 times per second...
        if (random.nextInt(20) > 0)
            return;

		/* Why dump to a new tag when you can just grab the tag and work with it?
		NBTTagCompound tag = new NBTTagCompound();
		((EntityPlayer) player).writeToNBT(tag);
		NBTTagCompound forgeTag = tag.getCompoundTag("ForgeData");
*/

        // Retrieve Players NBT Data

        NBTTagCompound forgeTag = getOrSetForgeTag((EntityPlayer) player);

        int corruption = 0;

        // get corruption levels, init to 0 if not existing yet
        if (forgeTag.hasKey(NBT_TAG_MIND_CORRUPTION))
            corruption = forgeTag.getInteger(NBT_TAG_MIND_CORRUPTION);
        else
            forgeTag.setInteger(NBT_TAG_MIND_CORRUPTION, 0);

        if (!player.worldObj.isRemote) {
            //EMT.LOGGER.info( String.format( "Total Corruption: %d ItemWarpLevel: %d", corruption, getItemWarpLevel( stack, false ) ));

            if (corruption == 0) {
                ((EntityPlayer) player).addChatMessage(new ChatComponentText(EMTTextHelper.PURPLE + "You have worn the Ring. Your soul has now been forever " + EMTTextHelper.PURPLE + "tainted. " + EMTTextHelper.RED + EMTTextHelper.ITALIC + "Beware of wearing the ring. The tainting will only " + EMTTextHelper.RED + EMTTextHelper.ITALIC + "increase, and strange things will start happening."));
            }
            // First effect should become active after ~5 Minutes
            else if (corruption > 300 && corruption < 1400 && random.nextInt(100) == 0) {
                ((EntityPlayer) player).addChatMessage(new ChatComponentText(EMTTextHelper.PURPLE + "The Ring somehow feels heavy"));
                if (random.nextBoolean())
                    player.addPotionEffect(new PotionEffect(Potion.blindness.id, 500, 2, false));
                else
                    player.addPotionEffect(new PotionEffect(Potion.confusion.id, 500, 2, false));
            } else if (corruption >= 1400 && corruption < 3800 && random.nextInt(100) == 0) {
                ((EntityPlayer) player).capabilities.disableDamage = false;
                player.addPotionEffect(new PotionEffect(Potion.poison.id, 500, 2, false));
            } else if (corruption >= 3800 && corruption < 6500 && random.nextInt(100) == 0) {
                ((EntityPlayer) player).capabilities.disableDamage = false;
                player.motionY += 2d;
            } else if (corruption >= 6500 && random.nextInt(10000) == 0) {
                ((EntityPlayer) player).capabilities.disableDamage = false;
                player.addPotionEffect(new PotionEffect(Potion.wither.id, 100, 4, false));
            }

            if (corruption >= 300) { //worn more than 5 minutes
                if (random.nextInt(100) == 0) { //every 100 seconds
                    getItemWarpLevel(stack, true);
                    ((EntityPlayer) player).addChatMessage(new ChatComponentText(EMTTextHelper.PURPLE + "The Ring suddenly starts to glow purple"));
                }
		if (random.nextInt(300) == 0) { //randomly every 5 minutes
		    ThaumcraftApiHelper.addWarpToPlayer((EntityPlayer) player, 1, false); //adds 1 perm warp
		    ThaumcraftApiHelper.addStickyWarpToPlayer((EntityPlayer) player, random.nextInt(1)); //adds 0-1 sticky warp
		    ((EntityPlayer) player).addChatMessage(new ChatComponentText(EMTTextHelper.PURPLE + "Your body suddenly starts to glow purple"));
		}
		if (corruption >= 3600) { //if you wear it for more than an hour total
		    if (random.nextInt(300) == 0) {
		        ThaumcraftApiHelper.addWarpToPlayer((EntityPlayer) player, 5 + random.nextInt(5), false); //adds 5-10 perm warp
		        ThaumcraftApiHelper.addStickyWarpToPlayer((EntityPlayer) player, 5 + random.nextInt(5)); //adds 5-10 sticky warp
			ThaumcraftApiHelper.addWarpToPlayer((EntityPlayer) player, 10 + random.nextInt(40), true); //adds 10-50 temp warp, but this is ultra easy to remove
		        ((EntityPlayer) player).addChatMessage(new ChatComponentText(EMTTextHelper.PURPLE + "Your body resonates with the Ring and suddenly starts to glow purple ominously"));
		    }
		}
            }
        }
        forgeTag.setInteger(NBT_TAG_MIND_CORRUPTION, ++corruption);
        //tag.setTag( NBT_TAG_FORGE_DATA, forgeTag);
        //((EntityPlayer) player).readFromNBT(tag);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        // This purges all corruption once the ring is put on / a second ring is equipped?
/*
		NBTTagCompound tag = new NBTTagCompound();
		((EntityPlayer) player).writeToNBT(tag);
		tag.getCompoundTag( NBT_TAG_FORGE_DATA ).setInteger( NBT_TAG_MIND_CORRUPTION, 0);
		((EntityPlayer) player).readFromNBT(tag);
*/
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        player.setInvisible(false);
        if (!((EntityPlayer) player).capabilities.isCreativeMode)
            ((EntityPlayer) player).capabilities.disableDamage = false;

		/* Wtf? Remove ForgeData tag? There are more mods using this tag than just emt!
		((EntityPlayer) player).writeToNBT(tag);
		tag.removeTag( NBT_TAG_FORGE_DATA );
		((EntityPlayer) player).readFromNBT(tag);
		*/
        player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 1, false));
    }

    @Override
    public boolean canEquip(ItemStack stack, EntityLivingBase player) {
        return stack.getItemDamage() == 0 && player instanceof EntityPlayer;
    }

    @Override
    public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
        // So, how this was written, you basically could not unequip the ring for 600 ticks after you equipped it, but afterwards you could?
        // Let's make it a random chance to remove it instead...

        // NBTTagCompound tag = getOrSetForgeTag((EntityPlayer) player);
        // return (tag.getCompoundTag( NBT_TAG_FORGE_DATA ).getInteger( NBT_TAG_MIND_CORRUPTION ) > 600);
        boolean tRet = false;
        if (random.nextInt(5) == 0) {
            tRet = true;
            ((EntityPlayer) player).addChatMessage(new ChatComponentText(EMTTextHelper.GREEN + "You removed the Ring. Maybe you should think twice before using it next time?"));
        } else
            ((EntityPlayer) player).addChatMessage(new ChatComponentText(EMTTextHelper.PURPLE + "Nooo, don't remove me! I give you great power!"));

        return tRet;
    }

    @Override
    public int getRunicCharge(ItemStack itemStack) {
        return 0;
    }

}
