package emt.client.gui.config;

import static emt.util.EMTConfigHandler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import emt.EMT;

public class EMTGuiConfig extends GuiConfig {

    public EMTGuiConfig(GuiScreen parentScreen) {
        super(
                parentScreen,
                getConfigElements(parentScreen),
                EMT.MOD_ID,
                false,
                false,
                StatCollector.translateToLocal("gui." + EMT.MOD_ID + ".config.title"));
    }

    @SuppressWarnings("rawtypes")
    private static List<IConfigElement> getConfigElements(GuiScreen parent) {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        list.add(new ConfigElement<ConfigCategory>(config.getCategory(RANDOM.toLowerCase(Locale.US))));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(VALUES.toLowerCase(Locale.US))));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(RESEARCH.toLowerCase(Locale.US))));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(OUTPUTS.toLowerCase(Locale.US))));

        return list;
    }
}
