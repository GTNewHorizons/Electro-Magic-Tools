package emt.gthandler.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.gthandler.common.blocks.EMT_GT_Block;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class EMT_CasingBlock extends ItemBlock {

    public static final Block[] EMT_GT_BLOCKS = {new EMT_GT_Block("EMT_GTBLOCK_CASEING", new String[]
            {
                    EMT.TEXTURE_PATH + ":machines/side",
                    EMT.TEXTURE_PATH + ":machines/GT/inner",
                    EMT.TEXTURE_PATH + ":machines/GT/siderawI",
                    EMT.TEXTURE_PATH + ":machines/GT/rawcoreI",
                    EMT.TEXTURE_PATH + ":machines/GT/siderawII",
                    EMT.TEXTURE_PATH + ":machines/GT/rawcoreII",
                    EMT.TEXTURE_PATH + ":machines/GT/rawcoreII", //unused?
                    EMT.TEXTURE_PATH + ":machines/GT/ROBUST_NAQUADAH_ALLOY",
                    EMT.TEXTURE_PATH + ":machines/GT/BLOODY_ICHORIUM",
                    EMT.TEXTURE_PATH + ":machines/GT/DRACONIUM",
                    EMT.TEXTURE_PATH + ":machines/GT/WYVERN",
                    EMT.TEXTURE_PATH + ":machines/GT/AWAKENED_DRACONIUM",
                    EMT.TEXTURE_PATH + ":machines/GT/CHAOTIC",                    
            }
    )};
    protected final String mNoMobsToolTip = GT_LanguageManager.addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
    protected final String mNoTileEntityToolTip = GT_LanguageManager.addStringLocalization("gt.notileentityinthisblock", "This is NOT a TileEntity!");
    
    protected final String deccCasing01Tooltip = GT_LanguageManager.addStringLocalization("gt.casing01tooltip", "DE Core Crafter Casing, Tier 1");
    protected final String deccCasing02Tooltip = GT_LanguageManager.addStringLocalization("gt.casing02tooltip", "DE Core Crafter Casing, Tier 2");
    protected final String deccCasing03Tooltip = GT_LanguageManager.addStringLocalization("gt.casing03tooltip", "DE Core Crafter Casing, Tier 3");
    protected final String deccCasing04Tooltip = GT_LanguageManager.addStringLocalization("gt.casing04tooltip", "DE Core Crafter Casing, Tier 4");
    protected final String deccCasing05Tooltip = GT_LanguageManager.addStringLocalization("gt.casing05tooltip", "DE Core Crafter Casing, Tier 5");

    public EMT_CasingBlock(final Block par1) {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(EMT.TAB);
    }

    @Override
    public int getMetadata(final int aMeta) {
        return aMeta;
    }

    @Override
    public String getUnlocalizedName(final ItemStack aStack) {
        return this.field_150939_a.getUnlocalizedName() + "." + this.getDamage(aStack);
    }

    @Override
    @SideOnly(Side.CLIENT) //so it only gets sent clientside?
    public void addInformation(final ItemStack aStack, final EntityPlayer aPlayer, final List aList, final boolean aF3_H) {
        aList.add(this.mNoMobsToolTip);
        aList.add(this.mNoTileEntityToolTip);
        
        switch (getDamage(aStack)) {
        case 8:
            aList.add(this.deccCasing01Tooltip);
            break;
        case 9:
            aList.add(this.deccCasing02Tooltip);
            break;
        case 10:
            aList.add(this.deccCasing03Tooltip);
            break;
        case 11:
            aList.add(this.deccCasing04Tooltip);
            break;
        case 12:
            aList.add(this.deccCasing05Tooltip);
            break;
        default:
        	break;
        }    
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.field_150939_a.getIcon(0, stack.getItemDamage());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return getIcon(stack, renderPass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
        return this.field_150939_a.getIcon(0, p_77618_2_);
    }
}
