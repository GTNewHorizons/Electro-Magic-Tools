package emt.block;

import java.util.List;
import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.gtnewhorizons.modularui.api.UIInfos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.tile.solar.Solars;

public class BlockSolars extends BlockBaseContainer {

    public BlockSolars(String name, int countOfMetas, int instance) {
        super(name, Material.iron, soundTypeMetal, countOfMetas, instance, 4.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        IIcon solarTop = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/solartop");
        IIcon doubleSolarTop = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/doublesolartop");
        IIcon tripleSolarTop = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/triplesolartop");

        IIcon bottom = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/bottom");

        IIcon side = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/side");
        IIcon waterSide = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/water/waterside");
        IIcon darkSide = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/dark/darkside");
        IIcon orderSide = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/order/orderside");
        IIcon fireSide = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/fire/fireside");
        IIcon airSide = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/air/airside");
        IIcon earthSide = ir.registerIcon(EMT.TEXTURE_PATH + ":solars/earth/earthside");

        IIcon[] sides = { side, orderSide, darkSide, airSide, earthSide, waterSide, fireSide };

        if (instance != 1 && instance != 0) for (int i = 0; i < countOfMetas; i++) {
            iconSets[i].bottom = bottom;
            iconSets[i].side = sides[(Objects.requireNonNull(Solars.getTileEntitySolarBase(instance, i)).aspect)];
            iconSets[i].top = tripleSolarTop;
        }
        else if (instance == 0) {
            for (int i = 0; i < countOfMetas; i++) {
                iconSets[i].bottom = bottom;
            }
            iconSets[0].top = solarTop;
            iconSets[1].top = doubleSolarTop;
            iconSets[2].top = tripleSolarTop;
            iconSets[3].top = solarTop;
            iconSets[4].top = doubleSolarTop;
            iconSets[5].top = tripleSolarTop;
            iconSets[6].top = solarTop;
            iconSets[7].top = doubleSolarTop;
            iconSets[8].top = tripleSolarTop;
            iconSets[9].top = solarTop;
            iconSets[10].top = doubleSolarTop;
            iconSets[11].top = tripleSolarTop;
            iconSets[12].top = solarTop;
            iconSets[13].top = doubleSolarTop;
            iconSets[14].top = tripleSolarTop;
            iconSets[15].top = solarTop;
            iconSets[0].side = side;
            iconSets[1].side = side;
            iconSets[2].side = side;
            iconSets[3].side = waterSide;
            iconSets[4].side = waterSide;
            iconSets[5].side = waterSide;
            iconSets[6].side = darkSide;
            iconSets[7].side = darkSide;
            iconSets[8].side = darkSide;
            iconSets[9].side = orderSide;
            iconSets[10].side = orderSide;
            iconSets[11].side = orderSide;
            iconSets[12].side = fireSide;
            iconSets[13].side = fireSide;
            iconSets[14].side = fireSide;
            iconSets[15].side = airSide;
        } else {
            iconSets[0].top = doubleSolarTop;
            iconSets[1].top = tripleSolarTop;
            iconSets[2].top = solarTop;
            iconSets[3].top = doubleSolarTop;
            iconSets[4].top = tripleSolarTop;
            for (int i = 0; i < countOfMetas; i++) {
                iconSets[i].bottom = bottom;
            }
            iconSets[0].side = airSide;
            iconSets[1].side = airSide;
            iconSets[2].side = earthSide;
            iconSets[3].side = earthSide;
            iconSets[4].side = earthSide;
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item id, CreativeTabs tab, List<ItemStack> list) {
        for (int meta = 0; meta < countOfMetas; meta++) {
            list.add(new ItemStack(id, 1, meta));
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {

        if (meta >= countOfMetas) return null;

        if (side < 1) {
            return iconSets[meta].bottom;
        }
        if (side == 1) {
            return iconSets[meta].top;
        }

        return iconSets[meta].side;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return Solars.getTileEntitySolarBase(instance, meta);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int s, float f1, float f2,
            float f3) {
        if (player.isSneaking()) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null) {
            UIInfos.TILE_MODULAR_UI.open(player, world, i, j, k);
        }
        return true;
    }
}
