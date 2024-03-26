package emt;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import emt.command.CommandOutputs;
import emt.init.EMTEntities;
import emt.init.Registry;
import emt.network.PacketEMTKeys;
import emt.network.PacketNodeInfo;
import emt.proxy.CommonProxy;
import emt.tile.solar.Solars;
import emt.util.EMTClientEventHandler;
import emt.util.EMTConfigHandler;
import emt.util.EMTCreativeTab;
import emt.util.EMTDungeonChestGenerator;
import emt.util.EMTEssentiasOutputs;
import emt.util.EMTEventHandler;

@Mod(
        modid = EMT.MOD_ID,
        name = EMT.NAME,
        version = EMT.VERSION,
        guiFactory = EMT.GUI_FACTORY,
        dependencies = EMT.DEPENDS)
public class EMT {

    public static final String NAME = "Electro-Magic Tools";
    public static final String MOD_ID = "EMT";
    public static final String VERSION = "GRADLETOKEN_VERSION";
    public static final String TEXTURE_PATH = "emt";
    public static final String GUI_FACTORY = "emt.client.gui.config.EMTGuiFactory";
    public static final String CLIENT_PROXY = "emt.proxy.ClientProxy";
    public static final String COMMON_PROXY = "emt.proxy.CommonProxy";
    public static final String CHANNEL = "EMT";
    public static final String DEPENDS = """
            required-after:Thaumcraft;\
            required-after:IC2;\
            required-after:modularui;\
            after:gregtech;\
            after:Avaritia;\
            after:MagicBees;\
            after:ForbiddenMagic;\
            after:dreamcraft""";
    public static final CreativeTabs TAB = new EMTCreativeTab("EMT.creativeTab");
    public static final Logger LOGGER = LogManager.getLogger("Electro-Magic Tools");
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("EMT");

    @SidedProxy(clientSide = "emt.proxy.ClientProxy", serverSide = "emt.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance("EMT")
    public static EMT instance;

    public boolean isSimulating() {
        return !FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        EMTConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new EMTEventHandler());
        if (FMLCommonHandler.instance().getSide().isClient()) {
            FMLCommonHandler.instance().bus().register(new EMTClientEventHandler());
        }
        Registry.registerPreInit();
        EMTEssentiasOutputs.addPrimalOutputs();
        registerPackets();
        if (Registry.enableGTCompat) {
            Solars.populateCache();
        }
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        Registry.registerInit();
        proxy.load();
        MinecraftForge.EVENT_BUS.register(new EMTEventHandler());
        if (FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new EMTClientEventHandler());
        }
        EMTDungeonChestGenerator.generateLoot();
        EMTEntities.registerEMTEntities();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Registry.registerLate();
    }

    @Mod.EventHandler
    public void onFMLServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandOutputs());
    }

    public void registerPackets() {
        INSTANCE.registerMessage(PacketEMTKeys.class, PacketEMTKeys.class, 0, Side.SERVER);
        if (Registry.enableGTCompat) {
            INSTANCE.registerMessage(PacketNodeInfo.class, PacketNodeInfo.class, 1, Side.CLIENT);
        }
    }
}
