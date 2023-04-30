package emt.tile.solar;

import static thaumcraft.api.aspects.Aspect.*;

import java.util.LinkedHashMap;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import emt.init.EMTBlocks;
import emt.util.EMTConfigHandler;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import thaumcraft.api.aspects.Aspect;

// Solar Factory
public enum Solars {

    // compability to older versions
    Compressed(EMTConfigHandler.compressedSolarOutput, null, 0, 0, "solar.compressed"),
    Double_Compressed(EMTConfigHandler.doubleCompressedSolarOutput, null, 0, 1, "solar.doublecompressed"),
    Triple_Compressed(EMTConfigHandler.tripleCompressedSolarOutput, null, 0, 2, "solar.triplecompressed"),
    Compressed_Aqua(EMTConfigHandler.compressedSolarOutput, WATER, 0, 3, "solar.water"),
    Double_Compressed_Aqua(EMTConfigHandler.doubleCompressedSolarOutput, WATER, 0, 4, "solar.doublewater"),
    Triple_Compressed_Aqua(EMTConfigHandler.tripleCompressedSolarOutput, WATER, 0, 5, "solar.triplewater"),
    Compressed_Perditio(EMTConfigHandler.compressedSolarOutput, ENTROPY, 0, 6, "solar.dark"),
    Double_Compressed_Perditio(EMTConfigHandler.doubleCompressedSolarOutput, ENTROPY, 0, 7, "solar.doubledark"),
    Triple_Compressed_Perditio(EMTConfigHandler.tripleCompressedSolarOutput, ENTROPY, 0, 8, "solar.tripledark"),
    Compressed_Ordo(EMTConfigHandler.compressedSolarOutput, ORDER, 0, 9, "solar.order"),
    Double_Compressed_Ordo(EMTConfigHandler.doubleCompressedSolarOutput, ORDER, 0, 10, "solar.doubleorder"),
    Triple_Compressed_Ordo(EMTConfigHandler.tripleCompressedSolarOutput, ORDER, 0, 11, "solar.tripleorder"),
    Compressed_Ignis(EMTConfigHandler.compressedSolarOutput, FIRE, 0, 12, "solar.fire"),
    Double_Compressed_Ignis(EMTConfigHandler.doubleCompressedSolarOutput, FIRE, 0, 13, "solar.doublefire"),
    Triple_Compressed_Ignis(EMTConfigHandler.tripleCompressedSolarOutput, FIRE, 0, 14, "solar.triplefire"),
    Compressed_Aer(EMTConfigHandler.compressedSolarOutput, AIR, 0, 15, "solar.air"),
    Double_Compressed_Aer(EMTConfigHandler.doubleCompressedSolarOutput, AIR, 1, 0, "solar.doubleair"),
    Triple_Compressed_Aer(EMTConfigHandler.tripleCompressedSolarOutput, AIR, 1, 1, "solar.tripleair"),
    Compressed_Terra(EMTConfigHandler.compressedSolarOutput, EARTH, 1, 2, "solar.earth"),
    Double_Compressed_Terra(EMTConfigHandler.doubleCompressedSolarOutput, EARTH, 1, 3, "solar.doubleearth"),
    Triple_Compressed_Terra(EMTConfigHandler.tripleCompressedSolarOutput, EARTH, 1, 4, "solar.tripleearth"),

    // New Solars
    Quadruple_Compressed(EMTConfigHandler.quadrupleCompressedSolarOutput, null, 2, 0, "Quadruple_Compressed_Solar"),
    Quadruple_Compressed_Ordo(EMTConfigHandler.quadrupleCompressedSolarOutput, ORDER, 2, 1,
            "Quadruple_Compressed_Ordo_Solar"),
    Quadruple_Compressed_Perditio(EMTConfigHandler.quadrupleCompressedSolarOutput, ENTROPY, 2, 2,
            "Quadruple_Compressed_Perditio_Solar"),
    Quadruple_Compressed_Aer(EMTConfigHandler.quadrupleCompressedSolarOutput, AIR, 2, 3,
            "Quadruple_Compressed_Aer_Solar"),
    Quadruple_Compressed_Terra(EMTConfigHandler.quadrupleCompressedSolarOutput, EARTH, 2, 4,
            "Quadruple_Compressed_Terra_Solar"),
    Quadruple_Compressed_Aqua(EMTConfigHandler.quadrupleCompressedSolarOutput, WATER, 2, 5,
            "Quadruple_Compressed_Aqua_Solar"),
    Quadruple_Compressed_Ignis(EMTConfigHandler.quadrupleCompressedSolarOutput, FIRE, 2, 6,
            "Quadruple_Compressed_Ignis_Solar"),
    Quintuple_Compressed(EMTConfigHandler.quintupleCompressedSolarOutput, null, 2, 7, "Quintuple_Compressed_Solar"),
    Quintuple_Compressed_Ordo(EMTConfigHandler.quintupleCompressedSolarOutput, ORDER, 2, 8,
            "Quintuple_Compressed_Ordo_Solar"),
    Quintuple_Compressed_Perditio(EMTConfigHandler.quintupleCompressedSolarOutput, ENTROPY, 2, 9,
            "Quintuple_Compressed_Perditio_Solar"),
    Quintuple_Compressed_Aer(EMTConfigHandler.quintupleCompressedSolarOutput, AIR, 2, 10,
            "Quintuple_Compressed_Aer_Solar"),
    Quintuple_Compressed_Terra(EMTConfigHandler.quintupleCompressedSolarOutput, EARTH, 2, 11,
            "Quintuple_Compressed_Terra_Solar"),
    Quintuple_Compressed_Aqua(EMTConfigHandler.quintupleCompressedSolarOutput, WATER, 2, 12,
            "Quintuple_Compressed_Aqua_Solar"),
    Quintuple_Compressed_Ignis(EMTConfigHandler.quintupleCompressedSolarOutput, FIRE, 2, 13,
            "Quintuple_Compressed_Ignis_Solar"),
    Sextuple_Compressed(EMTConfigHandler.sextupleCompressedSolarOutput, null, 2, 14, "Sextuple_Compressed_Solar"),
    Sextuple_Compressed_Ordo(EMTConfigHandler.sextupleCompressedSolarOutput, ORDER, 2, 15,
            "Sextuple_Compressed_Ordo_Solar"),
    Sextuple_Compressed_Perditio(EMTConfigHandler.sextupleCompressedSolarOutput, ENTROPY, 3, 0,
            "Sextuple_Compressed_Perditio_Solar"),
    Sextuple_Compressed_Aer(EMTConfigHandler.sextupleCompressedSolarOutput, AIR, 3, 1, "Sextuple_Compressed_Aer_Solar"),
    Sextuple_Compressed_Terra(EMTConfigHandler.sextupleCompressedSolarOutput, EARTH, 3, 2,
            "Sextuple_Compressed_Terra_Solar"),
    Sextuple_Compressed_Aqua(EMTConfigHandler.sextupleCompressedSolarOutput, WATER, 3, 3,
            "Sextuple_Compressed_Aqua_Solar"),
    Sextuple_Compressed_Ignis(EMTConfigHandler.sextupleCompressedSolarOutput, FIRE, 3, 4,
            "Sextuple_Compressed_Ignis_Solar"),
    Septuple_Compressed(EMTConfigHandler.septupleCompressedSolarOutput, null, 3, 5, "Septuple_Compressed_Solar"),
    Septuple_Compressed_Ordo(EMTConfigHandler.septupleCompressedSolarOutput, ORDER, 3, 6,
            "Septuple_Compressed_Ordo_Solar"),
    Septuple_Compressed_Perditio(EMTConfigHandler.septupleCompressedSolarOutput, ENTROPY, 3, 7,
            "Septuple_Compressed_Perditio_Solar"),
    Septuple_Compressed_Aer(EMTConfigHandler.septupleCompressedSolarOutput, AIR, 3, 8, "Septuple_Compressed_Aer_Solar"),
    Septuple_Compressed_Terra(EMTConfigHandler.septupleCompressedSolarOutput, EARTH, 3, 9,
            "Septuple_Compressed_Terra_Solar"),
    Septuple_Compressed_Aqua(EMTConfigHandler.septupleCompressedSolarOutput, WATER, 3, 10,
            "Septuple_Compressed_Aqua_Solar"),
    Septuple_Compressed_Ignis(EMTConfigHandler.septupleCompressedSolarOutput, FIRE, 3, 11,
            "Septuple_Compressed_Ignis_Solar"),
    Octuple_Compressed(EMTConfigHandler.octupleCompressedSolarOutput, null, 3, 12, "Octuple_Compressed_Solar"),
    Octuple_Compressed_Ordo(EMTConfigHandler.octupleCompressedSolarOutput, ORDER, 3, 13,
            "Octuple_Compressed_Ordo_Solar"),
    Octuple_Compressed_Perditio(EMTConfigHandler.octupleCompressedSolarOutput, ENTROPY, 3, 14,
            "Octuple_Compressed_Perditio_Solar"),
    Octuple_Compressed_Aer(EMTConfigHandler.octupleCompressedSolarOutput, AIR, 3, 15, "Octuple_Compressed_Aer_Solar"),
    Octuple_Compressed_Terra(EMTConfigHandler.octupleCompressedSolarOutput, EARTH, 4, 0,
            "Octuple_Compressed_Terra_Solar"),
    Octuple_Compressed_Aqua(EMTConfigHandler.octupleCompressedSolarOutput, WATER, 4, 1,
            "Octuple_Compressed_Aqua_Solar"),
    Octuple_Compressed_Ignis(EMTConfigHandler.octupleCompressedSolarOutput, FIRE, 4, 2,
            "Octuple_Compressed_Ignis_Solar"),;

    private static final LinkedHashMap<Long, Solars> cachemap = new LinkedHashMap<>();
    final int instance, meta;
    final Aspect aspect;
    final String guiname;
    private final double output;
    private final String unlocalizedName;
    public final long id;

    Solars(double output, Aspect aspect, int instance, int meta, String unlocalizedName) {
        this.aspect = aspect;
        this.output = output;
        this.guiname = this.name().replaceAll("_", " ") + " Solar";
        this.instance = instance;
        this.meta = meta;
        this.unlocalizedName = unlocalizedName;
        this.id = (long) instance << 4 | meta;
    }

    @Nullable
    private static Solars getBaseSolar(double output) {
        for (Solars s : Solars.values()) {
            if (s.output == output && s.aspect == null) return s;
        }
        return null;
    }

    public static void registerReverseRecipes() {
        for (Solars s : Solars.values()) {
            if (s.aspect != null) {
                // ItemStack aInput, FluidStack aBathingFluid, ItemStack aOutput1, ItemStack aOutput2, ItemStack
                // aOutput3, int[] aChances, int aDuration, int aEUt
                GT_Values.RA.addChemicalBathRecipe(
                        new ItemStack(EMTBlocks.solars[s.instance], 1, s.meta),
                        Materials.Chlorine.getGas(1000),
                        new ItemStack(
                                EMTBlocks.solars[getBaseSolar(s.output).instance],
                                1,
                                getBaseSolar(s.output).meta),
                        null,
                        null,
                        null,
                        30,
                        30);
            }
        }
    }

    public static void populateCache() {
        for (Solars s : Solars.values()) {
            cachemap.put(s.id, s);
        }
    }

    public static int getCountOfInstances() {
        int ret = 0;
        for (Solars s : Solars.values()) {
            ret = Math.max(s.instance, ret);
        }
        return ret + 1;
    }

    public static int getCountOfMetas(int instance) {
        int ret = 0;
        for (Solars s : Solars.values()) {
            if (s.instance == instance) {
                ret = Math.max(s.meta, ret);
            }
        }
        return ret + 1;
    }

    @Nullable
    public static TileEntitySolarBase getTileEntitySolarBase(int instance, int meta) {
        Solars solar = getSolar(instance, meta);
        if (solar == null) return null;
        return new TileEntitySolarBase(solar.output, solar.aspect, solar.guiname, instance, meta);
    }

    public static String getNameFromSolar(int instance, int meta) {
        Solars solar = getSolar(instance, meta);
        if (solar == null) return "";
        return solar.guiname;
    }

    public static String getUnlocalizedName(int instance, int meta) {
        Solars solar = getSolar(instance, meta);
        if (solar == null) return "nothing";
        return solar.unlocalizedName;
    }

    @Nullable
    public static Solars getSolar(int instance, int meta) {
        final long id = (long) instance << 4 | meta;
        Solars cached = cachemap.get(id);
        if (cached != null) return cached;
        for (Solars s : Solars.values()) {
            cachemap.put(s.id, s);
            if (s.id == id) return s;
        }
        return null;
    }
}
