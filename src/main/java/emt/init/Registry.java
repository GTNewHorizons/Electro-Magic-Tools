package emt.init;

public class Registry {
	public static void register() {
		EMTBlocks.registerBlocks();
		EMTItems.registerItems();
		EMTTiles.registerTileEntities();
		EMTRecipes.registerEarlyRecipes();
	}

	public static void registerLate() {
		EMTBlocks.addAspects();
		EMTRecipes.registerLateRecipes();
		EMTRecipes.registerUUMInfusionRecipes();
	}
}
