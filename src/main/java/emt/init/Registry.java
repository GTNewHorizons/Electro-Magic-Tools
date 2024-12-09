package emt.init;

import cpw.mods.fml.common.Loader;
import emt.gthandler.common.loader.EMT_GT_Loader;
import emt.tile.solar.Solars;
import emt.util.EMTEssentiasOutputs;

public class Registry {

    public static boolean enableGTCompat;

    public static void registerPreInit() {
        enableGTCompat = Loader.isModLoaded("gregtech") && !Loader.isModLoaded("gregapi");
        EMTBlocks.registerBlocks();
        EMTItems.registerItems();
        EMTTiles.registerTileEntities();
    }

    public static void registerInit() {
        EMTRecipes.registerEarlyRecipes();
    }

    public static void registerLate() {
        EMTEssentiasOutputs.addOutputs();
        EMTBlocks.addAspects();
        if (enableGTCompat) {
            EMT_GT_Loader.runlate();
        }
        EMTRecipes.registerLateRecipes();
        // new EMT_GTNH_Recipes_And_Researches().run();
        EMTRecipes.registerUUMInfusionRecipes();
        EMTResearches.register();
        if (enableGTCompat) {
            Solars.registerReverseRecipes();
        }
    }
}
