package emt.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ResearchPage.PageType;

public class EMTResearchItem extends ResearchItem {

    public EMTResearchItem(String par1, String par2) {
        super(par1, "EMT");
    }

    public EMTResearchItem(String researchName, AspectList tags, int x, int y, int z, ItemStack icon) {
        super(researchName, "EMT", tags, x, y, z, icon);
    }

    public EMTResearchItem(String researchName, int x, int y, int z, ItemStack icon) {
        super(researchName, "EMT", new AspectList(), x, y, z, icon);
    }

    public EMTResearchItem(String researchName, AspectList tags, int x, int y, int z, ResourceLocation icon) {
        super(researchName, "EMT", tags, x, y, z, icon);
    }

    public EMTResearchItem(String researchName, int x, int y, int z, ResourceLocation icon) {
        super(researchName, "EMT", new AspectList(), x, y, z, icon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getName() {
        return EMTTextHelper.localize("emt.rsname." + key);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getText() {
        return "[EMT] " + EMTTextHelper.localize("emt.tag." + key);
    }

    @Override
    public ResearchItem setPages(ResearchPage... pages) {
        for (ResearchPage page : pages) {

            if (page.type == PageType.TEXT)
                page.text = "emt.text." + key + (page.text.equals("") ? "" : "." + page.text);

            if (page.type == PageType.INFUSION_CRAFTING) {
                if (parentsHidden == null || parentsHidden.length == 0) parentsHidden = new String[] { "INFUSION" };
                else {
                    String[] newParents = new String[parentsHidden.length + 1];
                    newParents[0] = "INFUSION";
                    for (int i2 = 0; i2 < parentsHidden.length; i2++) newParents[i2 + 1] = parentsHidden[i2];
                    parentsHidden = newParents;
                }
            }
        }

        return super.setPages(pages);
    }
}
