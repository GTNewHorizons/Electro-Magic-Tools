package emt.gthandler.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.gthandler.common.items.EMT_CasingBlock;
import gregtech.api.enums.Textures;
import gregtech.api.render.TextureFactory;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class EMT_GT_Block extends GT_Block_Casings_Abstract {

    @SideOnly(Side.CLIENT)
    private IIcon[] texture;
    private final String[] textureNames;

    public EMT_GT_Block(String[] texture) {
        super(EMT_CasingBlock.class, "EMT_GTBLOCK_CASEING", Material.anvil);
        this.setHardness(15.0F);
        this.setResistance(30.0F);
        this.textureNames = texture;
        this.setCreativeTab(EMT.TAB);

        // Taking one texture slot :P
        Textures.BlockIcons.setCasingTexture((byte) 1, (byte) (15 + 48), TextureFactory.of(this, 7));
    }

    @Override
    public int damageDropped(final int meta) {
        return meta;
    }

    @Override
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < textureNames.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return meta < texture.length ? texture[meta] : texture[1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        texture = new IIcon[textureNames.length];
        for (int i = 0; i < textureNames.length; i++) {
            texture[i] = par1IconRegister.registerIcon(textureNames[i]);
        }
    }

}
