package emt.gthandler.common.loader;

import static emt.command.CommandOutputs.mkbook;

import cpw.mods.fml.common.Loader;
import emt.gthandler.common.implementations.EssentiaHatch;
import emt.gthandler.common.items.EMT_CasingBlock;
import emt.gthandler.common.tileentities.machines.multi.DE_Fusion_Crafter;
import emt.gthandler.common.tileentities.machines.multi.generator.EMT_Large_Essentia_Gen;
import emt.tile.GT_MetaTileEntity_ResearchCompleter;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class EMT_GT_Loader implements Runnable {

    public static final int TIERS = GT_Values.VN.length - 1;
    public static int aIDoffset = 13000 - TIERS - 1;
    public static ItemStack[] EHatch = new ItemStack[TIERS];
    public static ItemStack LEG;
    public static ItemStack DECC;
    public static ItemStack ResearchMultiblock;

    @Override
    public void run() {

        for (int i = 0; i < TIERS; i++) {
            EHatch[i] = new EssentiaHatch(
                            aIDoffset + i, "Essentia Hatch " + GT_Values.VN[i], "Essentia Hatch " + GT_Values.VN[i], i)
                    .getStackForm(1L);
        }

        LEG = new EMT_Large_Essentia_Gen(aIDoffset + TIERS + 1, "Large Essentia Generator", "Large Essentia Generator")
                .getStackForm(1L);

        // Dragonblood recipe for magics haters
        if (Loader.isModLoaded("miscutils")) {
            GT_Values.RA.addMixerRecipe(
                    new ItemStack[] {
                        new ItemStack(Blocks.dragon_egg, 1),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DraconiumAwakened, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DraconiumAwakened, 64L)
                    },
                    new FluidStack[] {Materials.Radon.getPlasma(144)},
                    new ItemStack[] {GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 8L)},
                    new FluidStack[] {new FluidStack(FluidRegistry.getFluid("molten.dragonblood"), 288)},
                    14000,
                    1966080);
        }
        // Casing blocks
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Iridium, 1L),
                    GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Osmiridium, 4L)
                },
                Materials.SolderingAlloy.getMolten(576L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 2),
                100,
                (int) (GT_Values.V[5] - (GT_Values.V[5] / 10)));
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Indium, 1L),
                    GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Tungsten, 4L)
                },
                Materials.SolderingAlloy.getMolten(576L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 3),
                100,
                (int) (GT_Values.V[5] - (GT_Values.V[5] / 10)));
        if (Loader.isModLoaded("ThaumicTinkerer"))
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] {
                        new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 2),
                        GT_ModHandler.getModItem("ThaumicTinkerer", "kamiResource", 2L, 2)
                    },
                    Materials.Concrete.getMolten(2304L),
                    new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 4),
                    100,
                    (int) (GT_Values.V[5] - (GT_Values.V[5] / 10)));
        else
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] {
                        new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 2), Materials.ElectrumFlux.getPlates(8)
                    },
                    Materials.Concrete.getMolten(2304L),
                    new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 4),
                    100,
                    (int) (GT_Values.V[5] - (GT_Values.V[5] / 10)));
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 3),
                    GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 8L)
                },
                Materials.Osmiridium.getMolten(576L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 5),
                100,
                (int) (GT_Values.V[5] - (GT_Values.V[5] / 10)));
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 6L),
                    GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.NaquadahAlloy, 6L)
                },
                Materials.Void.getMolten(1152L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 7),
                24000,
                491520);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    GT_ModHandler.getModItem("dreamcraft", "tile.BloodyIchorium", 1, 0),
                    GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Osmiridium, 6L)
                },
                Materials.Void.getMolten(1152L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 8),
                24000,
                491520);
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 8),
                    GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Draconium, 6L),
                    GT_ModHandler.getModItem("DraconicEvolution", "draconicCore", 1, 0)
                },
                Materials.Void.getMolten(2304L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 9),
                24000,
                491520); // uv
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 9),
                    GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.CosmicNeutronium, 6L),
                    GT_ModHandler.getModItem("DraconicEvolution", "wyvernCore", 2, 0)
                },
                Materials.Void.getMolten(4608L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 10),
                12000,
                1996080); // uhv, you need uhv to get the thing anyway?
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 10),
                    GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.DraconiumAwakened, 6L),
                    GT_ModHandler.getModItem("DraconicEvolution", "awakenedCore", 3, 0)
                },
                Materials.Void.getMolten(9216L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 11),
                12000,
                7864320); // uev
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 11),
                    GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Infinity, 6L),
                    GT_ModHandler.getModItem("DraconicEvolution", "chaoticCore", 4, 0)
                },
                Materials.Void.getMolten(18432L),
                new ItemStack(EMT_CasingBlock.EMT_GT_BLOCKS[0], 1, 12),
                12000,
                31457280); // uiv, use the multi

        EMT_RecipeAdder.Run();

        DECC = new DE_Fusion_Crafter(5001, "de_fusion_crafter", "Draconic Evolution Fusion Crafter").getStackForm(1L);

        /* New DECF controller recipe moved to TT. only create the old one here if tectech or blood magic is not around.*/

        if (!(Loader.isModLoaded("tectech") && Loader.isModLoaded("AWWayofTime"))) {
            GT_Values.RA.addAssemblylineRecipe(
                    GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 10780),
                    144000,
                    new Object[] {
                        GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1, 10780),
                        ItemList.Robot_Arm_LuV.get(2L),
                        ItemList.Electric_Motor_LuV.get(2L),
                        ItemList.Field_Generator_LuV.get(1L),
                        new Object[] {OrePrefixes.circuit.get(Materials.Master), 2},
                        ItemList.Casing_Coil_Naquadah.get(8L),
                    },
                    new FluidStack[] {
                        Materials.SolderingAlloy.getMolten(2304L), Materials.Thaumium.getMolten(1440L),
                    },
                    DECC,
                    1500,
                    16384);
        }

        for (int i = 0; i < ItemList.HATCHES_INPUT.length; i++) {
            GT_Values.RA.addAssemblerRecipe(
                    new ItemStack[] {ItemList.HATCHES_INPUT[i].get(1L), new ItemStack(ConfigBlocks.blockTube, 1, 4)},
                    Materials.Thaumium.getMolten(288L),
                    EHatch[i],
                    100,
                    (int) (GT_Values.V[i] - (GT_Values.V[i] / 10)));
        }

        ResearchMultiblock = new GT_MetaTileEntity_ResearchCompleter(
                        aIDoffset + TIERS + 2, "Research Completer", "Research Completer")
                .getStackForm(1L);
    }

    public void runlate() {
        GT_Values.RA.addAssemblerRecipe(
                new ItemStack[] {
                    new ItemStack(Items.book),
                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),
                    new ItemStack(ConfigItems.itemShard, 1, OreDictionary.WILDCARD_VALUE)
                },
                GT_Values.NF,
                mkbook(),
                128,
                64);
    }
}
