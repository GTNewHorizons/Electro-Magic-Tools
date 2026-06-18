package emt.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMachines extends ItemBlock {

    public ItemBlockMachines(Block id) {
        super(id);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        String name = "";
        switch (itemstack.getItemDamage()) {
            case 0: {
                name = "wandrecharge";
                break;
            }
            case 1: {
                name = "etheralprocessor";
                break;
            }
            case 2: {
                name = "essentiafiller";
                break;
            }
            case 3: {
                name = "raindispeller";
                break;
            }
            default:
                name = "nothing";
        }
        return getUnlocalizedName() + "." + name;
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_,
            boolean p_77624_4_) {
        switch (p_77624_1_.getItemDamage()) {
            case 2: {
                p_77624_3_.add("Connects between Essentia Hatches and the Pipe Network");
                p_77624_3_.add("Looses its Essentia on Restart");
                p_77624_3_.add("Added by bartimaeusnek");
            }
        }
    }
}
