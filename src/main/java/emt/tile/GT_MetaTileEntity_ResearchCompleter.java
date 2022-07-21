package emt.tile;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.network.PacketNodeInfo;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.research.ResearchNoteData;
import thaumcraft.common.tiles.TileNode;

import java.util.ArrayList;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdder;

public class GT_MetaTileEntity_ResearchCompleter extends GT_MetaTileEntity_EnhancedMultiBlockBase<GT_MetaTileEntity_ResearchCompleter> {
    private static final int CASING_INDEX = 184;
    private static final int MAX_LENGTH = 13;
    private static final int RECIPE_LENGTH = 1200;
    private static final int RECIPE_EUT = 120;
    private static final float NODE_COST_MULTIPLIER = 1.0f;

    private int recipeAspectCost;
    private int aspectsAbsorbed;

    protected int mLength;
    protected int mCasing;
    protected boolean endFound;

    //For displaying beam
    private int lastNodeDistance;
    private int lastNodeColor;
    private int syncTimer;

    private static final String STRUCTURE_PIECE_FIRST = "first";
    private static final String STRUCTURE_PIECE_LATER = "later";
    private static final String STRUCTURE_PIECE_LAST = "last";
    private static final String STRUCTURE_PIECE_LATER_HINT = "laterHint";
    private static final IStructureDefinition<GT_MetaTileEntity_ResearchCompleter> STRUCTURE_DEFINITION = StructureDefinition.<GT_MetaTileEntity_ResearchCompleter>builder()
            .addShape(STRUCTURE_PIECE_FIRST, transpose(new String[][]{
                    {"ccc"},
                    {"g~g"},
                    {"ccc"},
            }))
            .addShape(STRUCTURE_PIECE_LATER, transpose(new String[][]{
                    {"c c"},
                    {"gxg"},
                    {"c c"},
            }))
            .addShape(STRUCTURE_PIECE_LAST, transpose(new String[][]{
                    {"c"},
                    {"g"},
                    {"c"},
            }))
            .addShape(STRUCTURE_PIECE_LATER_HINT, transpose(new String[][]{
                    {"c c"},
                    {"g g"},
                    {"c c"},
            }))
            .addElement('c', ofChain( //Magical machine casing or hatch
                    ofHatchAdder(GT_MetaTileEntity_ResearchCompleter::addEnergyInputToMachineList, CASING_INDEX, 1),
                    ofHatchAdder(GT_MetaTileEntity_ResearchCompleter::addInputToMachineList, CASING_INDEX, 1),
                    ofHatchAdder(GT_MetaTileEntity_ResearchCompleter::addOutputToMachineList, CASING_INDEX, 1),
                    ofHatchAdder(GT_MetaTileEntity_ResearchCompleter::addMaintenanceToMachineList, CASING_INDEX, 1),
                    onElementPass(GT_MetaTileEntity_ResearchCompleter::onCasingFound, ofBlock(GregTech_API.sBlockCasings8, 8))
            ))
            .addElement('x', ofChain( //Check for the end but otherwise treat as a skipped spot
                    onElementPass(GT_MetaTileEntity_ResearchCompleter::onEndFound, ofBlock(ConfigBlocks.blockCosmeticOpaque, 2)),
                    isAir(), //Forgive me
                    notAir()
            ))
            .addElement('g', ofBlock(ConfigBlocks.blockCosmeticOpaque, 2)) //Warded glass
            .build();


    public GT_MetaTileEntity_ResearchCompleter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_ResearchCompleter(String aName) {
        super(aName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("recipeAspectCost", recipeAspectCost);
        aNBT.setInteger("aspectsAbsorbed", aspectsAbsorbed);
        aNBT.setInteger("mLength", mLength);

        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        recipeAspectCost = aNBT.getInteger("recipeAspectCost");
        aspectsAbsorbed = aNBT.getInteger("aspectsAbsorbed");
        mLength = aNBT.getInteger("mLength");

        super.loadNBTData(aNBT);
    }

    //For client beam animation
    public void setNodeValues(int nodeDistance, int nodeColor) {
        this.lastNodeDistance = nodeDistance;
        this.lastNodeColor = nodeColor;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isClientSide()) {
            if (aBaseMetaTileEntity.isActive()) {
                int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
                int yDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetY;
                int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
                double xCoord = aBaseMetaTileEntity.getXCoord() + 0.5;
                double yCoord = aBaseMetaTileEntity.getYCoord() + 0.5;
                double zCoord = aBaseMetaTileEntity.getZCoord() + 0.5;
                Thaumcraft.proxy.beam(aBaseMetaTileEntity.getWorld(), xCoord + 0.5 * xDir, yCoord + 0.5 * yDir, zCoord + 0.5 * zDir, xCoord + xDir * lastNodeDistance, yCoord + yDir * lastNodeDistance, zCoord + zDir * lastNodeDistance, 3, lastNodeColor, true, 2, 1);
            } else {
                lastNodeDistance = 0;
                lastNodeColor = 0;
            }
        }

        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        float progressAmount = ((float) this.mProgresstime) / this.mMaxProgresstime;
        int requiredVis = (int)Math.ceil(progressAmount * recipeAspectCost - aspectsAbsorbed);
        syncTimer--;

        IGregTechTileEntity aBaseMetaTileEntity = this.getBaseMetaTileEntity();
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int yDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetY;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        int i = 1;

        //Loop through node spaces and drain them from front to back
        while (i < this.mLength - 1 && requiredVis > 0) {
            int nodeX = aBaseMetaTileEntity.getXCoord() + xDir * i;
            int nodeY = aBaseMetaTileEntity.getYCoord() + yDir * i;
            int nodeZ = aBaseMetaTileEntity.getZCoord() + zDir * i;
            TileEntity tileEntity = aBaseMetaTileEntity.getWorld().getTileEntity(nodeX, nodeY, nodeZ);

            if (tileEntity instanceof TileNode) {
                TileNode aNode = (TileNode)tileEntity;
                AspectList aspectsBase = aNode.getAspectsBase();

                for (Aspect aspect : aspectsBase.getAspects()) {
                    int aspectAmount = aspectsBase.getAmount(aspect);
                    int drainAmount = Math.min(requiredVis, aspectAmount);
                    aNode.setNodeVisBase(aspect, (short) (aspectAmount - drainAmount));
                    aNode.takeFromContainer(aspect, drainAmount);
                    requiredVis -= drainAmount;
                    aspectsAbsorbed += drainAmount;

                    if (requiredVis <= 0) {
                        if (i != lastNodeDistance || aspect.getColor() != lastNodeColor)
                            sendClientAnimationUpdate(aBaseMetaTileEntity, i, aspect.getColor());

                        break;
                    }
                }

                if (aspectsBase.visSize() <= 0)
                    aBaseMetaTileEntity.getWorld().setBlockToAir(nodeX, nodeY, nodeZ);
                else {
                    aNode.markDirty();
                    aBaseMetaTileEntity.getWorld().markBlockForUpdate(nodeX, nodeY, nodeZ);
                }
            }
            i++;
        }

        if (syncTimer <= 0)
            sendClientAnimationUpdate(aBaseMetaTileEntity, lastNodeDistance, lastNodeColor);

        if (requiredVis > 0)
            this.criticalStopMachine();

        return super.onRunningTick(aStack);
    }

    private void sendClientAnimationUpdate(IGregTechTileEntity aBaseMetaTileEntity, int nodeDistance, int nodeColor) {
        int xCoord = aBaseMetaTileEntity.getXCoord();
        int yCoord = aBaseMetaTileEntity.getYCoord();
        int zCoord = aBaseMetaTileEntity.getZCoord();
        int dim = aBaseMetaTileEntity.getWorld().provider.dimensionId;

        PacketNodeInfo packet = new PacketNodeInfo(xCoord, yCoord, zCoord, dim, nodeDistance, nodeColor);
        EMT.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dim, xCoord, yCoord, zCoord, 128));

        lastNodeDistance = nodeDistance;
        lastNodeColor = nodeColor;
        syncTimer = 100;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        ArrayList<ItemStack> tInputList = this.getStoredInputs();

        for (ItemStack stack : tInputList) {
            if (GT_Utility.isStackValid(stack) && stack.stackSize > 0) {
                if (stack.getItem() == ConfigItems.itemResearchNotes && !stack.stackTagCompound.getBoolean("complete")) {
                    ResearchNoteData noteData = ResearchManager.getData(stack);
                    if (noteData == null)
                        continue;
                    ResearchItem researchItem = ResearchCategories.getResearch(noteData.key);
                    if (researchItem == null)
                        continue;

                    this.mEfficiency = 10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000;
                    this.mEfficiencyIncrease = 10000;
                    this.calculateOverclockedNessMulti(RECIPE_EUT, RECIPE_LENGTH, 1, this.getMaxInputVoltage());
                    if (this.mMaxProgresstime == 2147483646 && this.mEUt == 2147483646) {
                        return false;
                    }
                    if (this.mEUt > 0) {
                        this.mEUt = -this.mEUt;
                    }

                    //Create a completed version of the note to output
                    this.mOutputItems = new ItemStack[]{GT_Utility.copyAmount(1L, stack)};
                    this.mOutputItems[0].stackTagCompound.setBoolean("complete", true);
                    this.mOutputItems[0].setItemDamage(64);
                    stack.stackSize -= 1;
                    this.aspectsAbsorbed = 0;
                    this.recipeAspectCost = (int)Math.ceil(researchItem.tags.visSize() * NODE_COST_MULTIPLIER);

                    this.lastNodeDistance = 0;
                    this.lastNodeColor = 0;

                    this.sendLoopStart((byte) 20);
                    this.updateSlots();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        mLength = 1;
        mCasing = 0;
        endFound = false;

        //check front
        if (!checkPiece(STRUCTURE_PIECE_FIRST, 1, 1, 0))
            return false;

        //check middle pieces
        while (!endFound && mLength++ < MAX_LENGTH) {
            if (!checkPiece(STRUCTURE_PIECE_LATER, 1, 1, -(mLength - 1)))
                return false;
        }

        return endFound && mLength >= 3 && checkPiece(STRUCTURE_PIECE_LAST, 0, 1, -(mLength - 1)) && mCasing >= mLength * 3;
    }

    @Override
    public int getMaxEfficiency(ItemStack itemStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack itemStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack itemStack) {
        return false;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new GT_MetaTileEntity_ResearchCompleter(this.mName);
    }

    protected void onCasingFound() {
        mCasing++;
    }

    protected void onEndFound() {
        endFound = true;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            if (aActive) return new ITexture[]{
                    Textures.BlockIcons.getCasingTextureForId(CASING_INDEX),
                    TextureFactory.builder().addIcon(OVERLAY_FRONT_RESEARCH_COMPLETER_ACTIVE).extFacing().build(),
                    TextureFactory.builder().addIcon(OVERLAY_FRONT_RESEARCH_COMPLETER_ACTIVE_GLOW).extFacing().glow().build()};
            return new ITexture[]{
                    Textures.BlockIcons.getCasingTextureForId(CASING_INDEX),
                    TextureFactory.builder().addIcon(OVERLAY_FRONT_RESEARCH_COMPLETER).extFacing().build(),
                    TextureFactory.builder().addIcon(OVERLAY_FRONT_RESEARCH_COMPLETER_GLOW).extFacing().glow().build()};
        }
        return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(CASING_INDEX)};
    }

    @Override
    public IStructureDefinition<GT_MetaTileEntity_ResearchCompleter> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Research Completer")
                .addInfo("Controller block for the Research Completer")
                .addInfo("Completes Thaumcraft research notes using EU and Thaumcraft nodes")
                .addInfo("Place nodes in the center row")
                .addSeparator()
                .beginVariableStructureBlock(3, 3, 3, 3, 3, MAX_LENGTH, true)
                .addController("Front center")
                .addOtherStructurePart("Magical machine casing", "Top and bottom layers outside. 3 x L minimum")
                .addOtherStructurePart("Warded glass", "Middle layer outside")
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing")
                .addOutputBus("Any casing")
                .toolTipFinisher("Electro-Magic Tools");
        return tt;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_FIRST, stackSize, hintsOnly, 1, 1, 0);
        int tTotalLength = Math.min(MAX_LENGTH, stackSize.stackSize + 2);
        for (int i = 1; i < tTotalLength; i++) {
            buildPiece(STRUCTURE_PIECE_LATER_HINT, stackSize, hintsOnly, 1, 1, -i);
        }
        buildPiece(STRUCTURE_PIECE_LAST, stackSize, hintsOnly, 0, 1, -(tTotalLength - 1));
    }
}
