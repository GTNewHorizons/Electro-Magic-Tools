package emt.gthandler.common.tileentities.machines.multi;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.apache.commons.lang3.tuple.Pair;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import emt.EMT;
import emt.gthandler.common.items.EMT_CasingBlock;
import emt.gthandler.common.loader.EMT_RecipeAdder;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;

public class DE_Fusion_Crafter extends GT_MetaTileEntity_EnhancedMultiBlockBase<DE_Fusion_Crafter> {

    private static final int CASING_INDEX = (1 << 7) + (15 + 48);
    private int mTierCasing = 0;
    private int mFusionTierCasing = 0;
    private int mCasing = 0;

    public DE_Fusion_Crafter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public DE_Fusion_Crafter(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new DE_Fusion_Crafter(mName);
    }

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final List<Pair<Block, Integer>> fusionCasingTiers = Arrays
            .asList(Pair.of(GregTech_API.sBlockCasings4, 6), Pair.of(GregTech_API.sBlockCasings4, 8));
    private static final List<Pair<Block, Integer>> coreTiers = Arrays.asList(
            Pair.of(EMT_CasingBlock.EMT_GT_BLOCKS[0], 8),
            Pair.of(EMT_CasingBlock.EMT_GT_BLOCKS[0], 9),
            Pair.of(EMT_CasingBlock.EMT_GT_BLOCKS[0], 10),
            Pair.of(EMT_CasingBlock.EMT_GT_BLOCKS[0], 11),
            Pair.of(EMT_CasingBlock.EMT_GT_BLOCKS[0], 12));
    private static final IStructureDefinition<DE_Fusion_Crafter> STRUCTURE_DEFINITION = StructureDefinition
            .<DE_Fusion_Crafter>builder()
            .addShape(
                    STRUCTURE_PIECE_MAIN,
                    transpose(
                            new String[][] { { "nnnnn", "nnnnn", "nnnnn", "nnnnn", "nnnnn" },
                                    { "     ", "  F  ", " FfF ", "  F  ", "     " },
                                    { "     ", "  F  ", " FfF ", "  F  ", "     " },
                                    { "RRRRR", "R F R", "RFfFR", "R F R", "RRRRR" },
                                    { "     ", "  F  ", " FfF ", "  F  ", "     " },
                                    { "     ", "  F  ", " FfF ", "  F  ", "     " },
                                    { "RRRRR", "R F R", "RFfFR", "R F R", "RRRRR" },
                                    { "     ", "  F  ", " FfF ", "  F  ", "     " },
                                    { "     ", "  F  ", " FfF ", "  F  ", "     " },
                                    { "NN~NN", "NNNNN", "NNNNN", "NNNNN", "NNNNN" } }))
            .addElement(
                    'N',
                    ofChain(
                            onElementPass(e -> e.mCasing++, ofBlock(EMT_CasingBlock.EMT_GT_BLOCKS[0], 7)),
                            ofHatchAdder(DE_Fusion_Crafter::addEnergyInputToMachineList, CASING_INDEX, 1),
                            ofHatchAdder(DE_Fusion_Crafter::addInputToMachineList, CASING_INDEX, 1),
                            ofHatchAdder(DE_Fusion_Crafter::addOutputToMachineList, CASING_INDEX, 1),
                            ofHatchAdder(DE_Fusion_Crafter::addMaintenanceToMachineList, CASING_INDEX, 1)))
            .addElement('n', onElementPass(e -> e.mCasing++, ofBlock(EMT_CasingBlock.EMT_GT_BLOCKS[0], 7)))
            .addElement('f', ofBlock(GregTech_API.sBlockCasings4, 7))
            .addElement('F', ofBlocksTiered((Block b, int m) -> {
                if (b != GregTech_API.sBlockCasings4 || (m != 6 && m != 8)) return -2;
                return m == 6 ? 1 : 2;
            }, fusionCasingTiers, -1, (e, i) -> e.mFusionTierCasing = i, e -> e.mFusionTierCasing))
            .addElement('R', ofBlocksTiered((Block b, int m) -> {
                if (b != EMT_CasingBlock.EMT_GT_BLOCKS[0] || m < 8 || m > 12) return -2;
                return m - 7;
            }, coreTiers, -1, (e, i) -> e.mTierCasing = i, e -> e.mTierCasing)).build();

    @Override
    public IStructureDefinition<DE_Fusion_Crafter> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mCasing = 0;
        mTierCasing = -1;
        mFusionTierCasing = -1;
        if (!checkPiece(STRUCTURE_PIECE_MAIN, 2, 9, 0)) return false;
        if (mCasing < 19) return false;
        if (mTierCasing == -2 || mFusionTierCasing == -2) return false;
        if (mTierCasing > 3 && mFusionTierCasing < 2) return false;
        return mMaintenanceHatches.size() == 1;
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Fusion Crafter").addInfo("Controller Block for the Draconic Evolution Fusion Crafter")
                .addInfo("Machine can be overclocked by using casings above the recipe tier:")
                .addInfo("Recipe time is divided by number of tiers above the recipe")
                .addInfo("Normal EU OC still applies !")
                .addInfo(
                        "To see the structure, use a " + EnumChatFormatting.BLUE
                                + "Tec"
                                + EnumChatFormatting.DARK_BLUE
                                + "Tech"
                                + EnumChatFormatting.GRAY
                                + " structure hologram on the Controller!")
                .addSeparator().beginStructureBlock(5, 10, 5, false).addController("Front bottom center")
                .addCasingInfo("Naquadah Alloy Fusion Casing", 19)
                .addOtherStructurePart("Fusion Coil Block", "Center pillar")
                .addOtherStructurePart("Fusion Machine Casing", "Touching Fusion Coil Block at every side")
                .addOtherStructurePart("Tiered Fusion Casing", "Rings (5x5 hollow) at layer 4 and 7")
                .addStructureInfo("Bloody Ichorium for tier 1, Draconium for tier 2, etc")
                .addStructureInfo("To use tier 3 + you have to use fusion casing MK II")
                .addInputBus("Any bottom casing", 1).addInputHatch("Any bottom casing", 1)
                .addOutputBus("Any bottom casing", 1).addOutputHatch("Any bottom casing", 1)
                .addEnergyHatch("Any bottom casing", 1).addMaintenanceHatch("Any bottom casing", 1)
                .toolTipFinisher(EMT.NAME);
        return tt;
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
            int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            if (aActive) return new ITexture[] { TextureFactory.of(MACHINE_CASING_MAGIC),
                    TextureFactory.builder().addIcon(OVERLAY_TELEPORTER_ACTIVE).extFacing().build(),
                    TextureFactory.builder().addIcon(OVERLAY_TELEPORTER_ACTIVE_GLOW).extFacing().glow().build() };
            return new ITexture[] { TextureFactory.of(MACHINE_CASING_MAGIC),
                    TextureFactory.builder().addIcon(OVERLAY_TELEPORTER).extFacing().build(),
                    TextureFactory.builder().addIcon(OVERLAY_TELEPORTER_GLOW).extFacing().glow().build() };
        }
        if (aActive) return new ITexture[] { TextureFactory.of(MACHINE_CASING_MAGIC),
                TextureFactory.builder().addIcon(MACHINE_CASING_MAGIC_ACTIVE).extFacing().build(),
                TextureFactory.builder().addIcon(MACHINE_CASING_MAGIC_ACTIVE_GLOW).extFacing().glow().build() };
        return new ITexture[] { TextureFactory.of(MACHINE_CASING_MAGIC),
                TextureFactory.builder().addIcon(MACHINE_CASING_MAGIC).extFacing().build(),
                TextureFactory.builder().addIcon(MACHINE_CASING_MAGIC_GLOW).extFacing().glow().build() };
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return EMT_RecipeAdder.sFusionCraftingRecipes;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        ItemStack[] tInputs = getCompactedInputs();
        FluidStack[] tFluids = getCompactedFluids();

        if (tInputs.length > 0) {
            long tVoltage = getMaxInputVoltage();
            byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
            GT_Recipe tRecipe = EMT_RecipeAdder.sFusionCraftingRecipes
                    .findRecipe(getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);
            if ((tRecipe != null) && (this.mTierCasing >= tRecipe.mSpecialValue)
                    && (tRecipe.isRecipeInputEqual(true, tFluids, tInputs))) {
                calculateOverclockedNessMulti(tRecipe.mEUt, tRecipe.mDuration, 2, tVoltage);
                if (this.mEUt > 0) this.mEUt = (-this.mEUt);
                this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                this.mEfficiencyIncrease = 10000;
                this.mMaxProgresstime /= ((mTierCasing - tRecipe.mSpecialValue) + 1);
                this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
                this.mOutputItems = new ItemStack[] { tRecipe.getOutput(0), tRecipe.getOutput(1) };
                this.mOutputFluids = new FluidStack[] { tRecipe.getFluidOutput(0) };
                updateSlots();
                return true;
            }
        }
        return false;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(STRUCTURE_PIECE_MAIN, itemStack, b, 2, 9, 0);
    }
}
