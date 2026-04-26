package emt.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.tile.TileEntityEMT;

public abstract class BlockBaseContainer extends BlockContainer {

    public int instance;
    public int countOfMetas;

    @SideOnly(Side.CLIENT)
    IconSet[] iconSets;

    public BlockBaseContainer(String unlocName, Material material, SoundType soundType, float hardness) {
        this(unlocName, material, soundType, 1, hardness);
    }

    public BlockBaseContainer(String unlocName, Material material, SoundType soundType, int countOfMetas,
            float hardness) {
        this(unlocName, material, soundType, countOfMetas, 0, hardness);
    }

    public BlockBaseContainer(String unlocName, Material material, SoundType soundType, int countOfMetas,
            int curInstance, float hardness) {
        super(material);
        this.setBlockName(EMT.MOD_ID + "." + unlocName);
        this.setCreativeTab(EMT.TAB);
        this.setStepSound(soundType);
        this.setHardness(hardness);
        setHarvestLevel("wrench", 1);

        this.countOfMetas = countOfMetas;
        this.instance = curInstance;

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            iconSets = new IconSet[countOfMetas];

            for (int meta = 0; meta < countOfMetas; meta++) {
                iconSets[meta] = new IconSet();
            }
        }
    }

    public IconSet[] getIconSets() {
        return iconSets;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        TileEntityEMT tile = (TileEntityEMT) access.getTileEntity(x, y, z);
        int meta = access.getBlockMetadata(x, y, z);

        if (meta >= countOfMetas) return null;

        IconSet set = iconSets[meta];

        if (side == 0) {
            return set.bottom;
        } else if (side == 1) {
            return set.top;
        } else if (tile == null || side != tile.facing) {
            return set.side;
        } else if (tile.isOn) {
            if (set.frontOn != null) {
                return set.frontOn;
            }

            if (set.frontOff != null) {
                return set.frontOff;
            }

            return set.side;
        } else {
            if (set.frontOff != null) {
                return set.frontOff;
            }

            return set.side;
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {

        if (meta >= countOfMetas) return null;

        IconSet set = iconSets[meta];

        if (side < 1) {
            return set.bottom;
        }
        if (side == 1) {
            return set.top;
        }
        if (side == 3) {
            return set.frontOff;
        }

        return set.side;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        TileEntityEMT tile = (TileEntityEMT) world.getTileEntity(x, y, z);

        if (tile == null) return;

        switch (facing) {
            case 0:
                tile.facing = 2;
                break;

            case 1:
                tile.facing = 5;
                break;

            case 2:
                tile.facing = 3;
                break;

            case 3:
                tile.facing = 4;
                break;
        }
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return true;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    public static class IconSet {

        public IIcon top;
        public IIcon bottom;
        public IIcon side;
        public IIcon frontOff;
        public IIcon frontOn;
    }
}
