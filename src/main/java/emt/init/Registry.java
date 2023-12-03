package emt.init;

import emt.gthandler.common.loader.EMT_GT_Loader;
import emt.tile.solar.Solars;
import emt.util.EMTEssentiasOutputs;

public class Registry {

    private static final EMT_GT_Loader gtloader = new EMT_GT_Loader();

    public static void registerPreInit() {
        EMTBlocks.registerBlocks();
        EMTItems.registerItems();
        EMTTiles.registerTileEntities();}

    public static void registerInit() {
        gtloader.run();
        EMTRecipes.registerEarlyRecipes();
    }

    public static void registerLate() {
        EMTEssentiasOutputs.addOutputs();
        EMTBlocks.addAspects();
        gtloader.runlate();
        EMTRecipes.registerLateRecipes();
        // new EMT_GTNH_Recipes_And_Researches().run();
        EMTRecipes.registerUUMInfusionRecipes();
        EMTResearches.register();
        Solars.registerReverseRecipes();
    }
}
