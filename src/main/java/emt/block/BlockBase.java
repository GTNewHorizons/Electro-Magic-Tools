package emt.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import emt.EMT;

public abstract class BlockBase extends Block {

    public final String name;

    public BlockBase(String unlocName, Material material, String textureName, SoundType soundType, float hardness) {
        super(material);
        name = unlocName;
        setBlockName(EMT.MOD_ID + ".block." + unlocName);
        if (textureName != null) {
            setBlockTextureName(EMT.RESOURCE_PATH + ":" + textureName);
        }
        setCreativeTab(EMT.TAB);
        setStepSound(soundType);
        setHardness(hardness);
    }

    public BlockBase(String unlocName, Material material, SoundType soundType, float hardness) {
        this(unlocName, material, null, soundType, hardness);
    }
}
