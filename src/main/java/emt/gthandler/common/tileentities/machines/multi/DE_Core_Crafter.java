package emt.gthandler.common.tileentities.machines.multi;

import java.util.ArrayList;
//import java.util.Arrays;//?

import emt.gthandler.common.items.EMT_CasingBlock;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
//import gregtech.api.items.GT_MetaGenerated_Tool;//?
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
//import gregtech.api.util.GT_Recipe;//?
import gregtech.api.util.GT_Utility;
//import gregtech.common.GT_Pollution;//?
//import gregtech.common.items.GT_MetaGenerated_Tool_01;//?
import net.minecraft.block.Block;
//import net.minecraft.entity.player.EntityPlayer;//?
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumChatFormatting;//?
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

//import com.dreammaster.gthandler.casings.GT_Container_CasingsNH;//?

//import static gregtech.api.enums.GT_Values.V;//?
//import static gregtech.api.enums.GT_Values.VN;//?


public class DE_Core_Crafter extends GT_MetaTileEntity_MultiBlockBase {
	private final int CASING_INDEX = 52;//?
	private int mTierCasing = 0;

	public DE_Core_Crafter(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public DE_Core_Crafter(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new DE_Core_Crafter(mName);
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                "Controller Block for the DE Core Crafter",
        		"Size(WxHxD): 5x10x5",
        		"Controller: Front bottom",        		
        		"Layer 1 and 10: Naquadah Alloy Core Casing (5x5)",
        		"Layer 4 and 7: Tiered Core Casing (5x5, hollow)",        		
        		"Bloody Ichorium for tier 1, Draconium for tier 2, etc",        		
        		"Layers 2 - 9: Center pillar of Fusion Coil Blocks,",
        		"with Fusion Machine Casing MK II in cardinal directions",        		
        		"Energy, Maintenance, Input and Output Buses go in layer 1",
        		"Upgraded overclocks reduce recipe time to 50%"//?
        };
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        ITexture[] sTexture;
        if (aSide == aFacing) {
            sTexture = new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC, Dyes.getModulation(-1, Dyes._NULL.mRGBa)), new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_TELEPORTER_ACTIVE : Textures.BlockIcons.OVERLAY_TELEPORTER)};
        } else {
            if (!aActive) {
                sTexture = new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC, Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
            } else {
                sTexture = new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_MAGIC_ACTIVE, Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
            }
        }
        return sTexture;
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "DistillationTower.png");//gui, change later?
    }

    @Override
	public boolean isCorrectMachinePart(ItemStack aStack) {
		return true;
	}

	@Override
    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

	@Override
	public boolean checkRecipe(ItemStack aStack) {
		ArrayList<ItemStack> tInputList = getStoredInputs();
		int tInputList_sS = tInputList.size();
		for (int i = 0; i < tInputList_sS - 1; i++) {
			for (int j = i + 1; j < tInputList_sS; j++) {
				if (GT_Utility.areStacksEqual((ItemStack) tInputList.get(i), (ItemStack) tInputList.get(j))) {
					if (((ItemStack) tInputList.get(i)).stackSize >= ((ItemStack) tInputList.get(j)).stackSize) {
						tInputList.remove(j--);
						tInputList_sS = tInputList.size();
					} else {
						tInputList.remove(i--);
						tInputList_sS = tInputList.size();
						break;
					}
				}
			}
		}
		tInputList.add(mInventory[1]);
		ItemStack[] tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);

		ArrayList<FluidStack> tFluidList = getStoredFluids();
		int tFluidList_sS = tFluidList.size();
		for (int i = 0; i < tFluidList_sS - 1; i++) {
			for (int j = i + 1; j < tFluidList_sS; j++) {
				if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
					if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
						tFluidList.remove(j--);
						tFluidList_sS = tFluidList.size();
					} else {
						tFluidList.remove(i--);
						tFluidList_sS = tFluidList.size();
						break;
					}
				}
			}
		}
		FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

		/*if (tInputList.size() > 0) {
            long tVoltage = getMaxInputVoltage();
            byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
            GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sFusionCraftingRecipes.findRecipe(getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[tTier], tFluids, tInputs);
            if ((tRecipe != null) && (this.mTierCasing >= tRecipe.mSpecialValue) && (tRecipe.isRecipeInputEqual(true, tFluids, tInputs))) {
                this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                this.mEfficiencyIncrease = 10000;

                this.mEUt = tRecipe.mEUt;
                if (tRecipe.mEUt <= 16) {
                    this.mEUt = (tRecipe.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
                    this.mMaxProgresstime = (tRecipe.mDuration / (1 << tTier - 1));
                } else {
                    this.mEUt = tRecipe.mEUt;
                    this.mMaxProgresstime = tRecipe.mDuration;
                    while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
                        this.mEUt *= 4;
                        this.mMaxProgresstime /= 2;
                    }
                }
                if (this.mEUt > 0) {
                    this.mEUt = (-this.mEUt);
                }

                this.mMaxProgresstime = mMaxProgresstime * 2 / (mTierCasing * 2);
                this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
                this.mOutputItems = new ItemStack[]{tRecipe.getOutput(0), tRecipe.getOutput(1)};
                this.mOutputFluids = new FluidStack[]{tRecipe.getFluidOutput(0)};
                updateSlots();
                return true;
			}
		}*/
		return false;
	}
	
	
	
	
	private boolean checkMachineFunction(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX*2;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ*2;
        int casingAmount = 0;
	
	
	
	
        this.mTierCasing = 1;
        
        
	

  
        //top layer
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                //if ((xDir + i != 0) || (zDir + j != 0)) {
                	if ((aBaseMetaTileEntity.getBlockOffset(xDir + i, 9, zDir + j) != EMT_CasingBlock.EMT_GT_BLOCKS[0]) || (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 9, zDir + j) != 7)) {
                        return false;
                    }
                	casingAmount++;
                //}
            }
        }
        
        
        //pillar
        for (int i = 1; i < 9; i++) {//check center
        	if(aBaseMetaTileEntity.getBlockOffset(xDir, i, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir, i, zDir)!= 7) {
                return false;
            }
        }
        for (int i = 1; i < 9; i++) {//check sides
        	if(aBaseMetaTileEntity.getBlockOffset(xDir + 1, i, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir + 1, i, zDir)!= 8) {
                return false;
            }
        }
        for (int i = 1; i < 9; i++) {
        	if(aBaseMetaTileEntity.getBlockOffset(xDir - 1, i, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir - 1, i, zDir)!= 8) {
                return false;
            }
        }
        for (int i = 1; i < 9; i++) {
        	if(aBaseMetaTileEntity.getBlockOffset(xDir, i, zDir + 1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir, i, zDir + 1)!= 8) {
                return false;
            }
        }
        for (int i = 1; i < 9; i++) {
        	if(aBaseMetaTileEntity.getBlockOffset(xDir, i, zDir - 1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir, i, zDir - 1)!= 8) {
                return false;
            }
        }
        
        
        

        //bottom layer
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if ((xDir + i != 0) || (zDir + j != 0)) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
                    if ((!addMaintenanceToMachineList(tTileEntity, 11)) && (!addInputToMachineList(tTileEntity, 11)) && (!addOutputToMachineList(tTileEntity, 11)) && (!addEnergyInputToMachineList(tTileEntity, 11))) {
                        if ((aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != EMT_CasingBlock.EMT_GT_BLOCKS[0]) || (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != 7)) {
                            return false;
                        }
                        casingAmount++;
                    }
                }
            }
        }
        return true;
        
        
	}
	
	

	
	
	/*
	//completely redo, because I can't trust it
	private boolean checkMachineFunction(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        int casingAmount = 0;

        this.mTierCasing = 0;
        byte tUsedMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + 2, 6, zDir);
        switch (tUsedMeta) {
            case 8:
                this.mTierCasing = 1;
                break;
            case 9:
                this.mTierCasing = 2;
                break;
            case 10:
                this.mTierCasing = 3;
                break;
            case 11:
                this.mTierCasing = 4;
                break;
            case 12:
                this.mTierCasing = 5;
                break;
            default:
                return false;
        }
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if ((xDir + i != 0) || (zDir + j != 0)) {
                	if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 9, zDir + j) != EMT_CasingBlock.EMT_GT_BLOCKS[0]) {
                        return false;
                    }
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 9, zDir + j) != 7) {
                        return false;
                    }
                    if (Math.abs(i)==2 || Math.abs(j)==2) {
                    	if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 6, zDir + j) != EMT_CasingBlock.EMT_GT_BLOCKS[0]) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 6, zDir + j) != tUsedMeta) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 3, zDir + j) != EMT_CasingBlock.EMT_GT_BLOCKS[0]) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 3, zDir + j) != tUsedMeta) {
                            return false;
                        }
                    }
                }
            }
        }
        

        
        //8
        if(aBaseMetaTileEntity.getBlockOffset(xDir+1, 8, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir-1, 8, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 8, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 7) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 8, zDir+1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 8, zDir-1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        //7
        if(aBaseMetaTileEntity.getBlockOffset(xDir+1, 7, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir-1, 7, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 7, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 7) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 7, zDir+1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 7, zDir-1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        //6
        if(aBaseMetaTileEntity.getBlockOffset(xDir+1, 6, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir-1, 6, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 6, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 7) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 6, zDir+1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 6, zDir-1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        //5
        if(aBaseMetaTileEntity.getBlockOffset(xDir+1, 5, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir-1, 5, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 5, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 7) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 5, zDir+1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 5, zDir-1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        //4
        if(aBaseMetaTileEntity.getBlockOffset(xDir+1, 4, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir-1, 4, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 4, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 7) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 4, zDir+1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 4, zDir-1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        //3
        if(aBaseMetaTileEntity.getBlockOffset(xDir+1, 3, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir-1, 3, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 3, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 7) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 3, zDir+1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 3, zDir-1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        //2
        if(aBaseMetaTileEntity.getBlockOffset(xDir+1, 2, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir-1, 2, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 2, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 7) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 2, zDir+1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 2, zDir-1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        //1
        if(aBaseMetaTileEntity.getBlockOffset(xDir+1, 1, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir-1, 1, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 1, zDir)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 7) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 1, zDir+1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
        if(aBaseMetaTileEntity.getBlockOffset(xDir, 1, zDir-1)!= GregTech_API.sBlockCasings4 || aBaseMetaTileEntity.getMetaIDOffset(xDir+1, 8, zDir)!= 8) {
            return false;
        }
       
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
            	if ((xDir + i != 0) || (zDir + j != 0)) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
                    Block block = aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j);
                    if ((!addMaintenanceToMachineList(tTileEntity, CASING_INDEX)) && (!addInputToMachineList(tTileEntity, CASING_INDEX)) && (!addOutputToMachineList(tTileEntity, CASING_INDEX)) && (!addEnergyInputToMachineList(tTileEntity, CASING_INDEX))) {
                    	if (block == EMT_CasingBlock.EMT_GT_BLOCKS[0] && aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) == 7) {
							casingAmount++;
						} else {
							return false;
						}
                    }
                }
            }
        }
        return true;
    }*/
	
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack){
        boolean result= this.checkMachineFunction(aBaseMetaTileEntity,aStack);
        if (!result) this.mTierCasing=0;
        return result;
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
    
}
