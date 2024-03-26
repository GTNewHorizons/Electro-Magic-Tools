package emt.client.renderer;

import static org.lwjgl.opengl.GL11.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import emt.EMT;
import emt.entity.EntityShield;

public class RenderShield extends Render {

    private static final ResourceLocation shieldTexture = new ResourceLocation(
            EMT.TEXTURE_PATH,
            "textures/models/shield.png");
    static int glCallList;

    static {
        glCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(glCallList, GL11.GL_COMPILE);

        Tessellator tessellator = Tessellator.instance;

        float sin1;
        float cos1;

        float sin2;
        float cos2;

        float hsin1 = -1;
        float hcos1 = 0;

        float hsin2;
        float hcos2;

        tessellator.startDrawingQuads();
        for (float h = -90; h < 90; h += 15) {
            sin1 = 0;
            cos1 = 1;

            hsin2 = MathHelper.sin((float) Math.toRadians(h + 15));
            hcos2 = MathHelper.cos((float) Math.toRadians(h + 15));

            for (float angle = 0; angle < 360; angle += 15) {
                sin2 = MathHelper.sin((float) Math.toRadians(angle + 15));
                cos2 = MathHelper.cos((float) Math.toRadians(angle + 15));

                tessellator.addVertexWithUV(sin1 * hcos1 * 2, hsin1 * 2, cos1 * hcos1 * 2, 1, 1);
                tessellator.addVertexWithUV(sin1 * hcos2 * 2, hsin2 * 2, cos1 * hcos2 * 2, 1, 0);
                tessellator.addVertexWithUV(sin2 * hcos2 * 2, hsin2 * 2, cos2 * hcos2 * 2, 0, 0);
                tessellator.addVertexWithUV(sin2 * hcos1 * 2, hsin1 * 2, cos2 * hcos1 * 2, 0, 1);

                sin1 = sin2;
                cos1 = cos2;
            }

            hsin1 = hsin2;
            hcos1 = hcos2;
        }
        tessellator.draw();

        GL11.glEndList();
    }

    @Override
    public void doRender(Entity entityShield, double x, double y, double z, float par8, float par9) {
        this.bindEntityTexture(entityShield);

        glEnable(GL_ALPHA_TEST);
        glAlphaFunc(GL_GREATER, 0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPushAttrib(GL_LIGHTING);
        glDisable(GL_LIGHTING);
        glDisable(GL_CULL_FACE);

        glPushMatrix();
        if (((EntityShield) entityShield).owner != null && !Minecraft.getMinecraft().thePlayer.getDisplayName()
                .equals(((EntityShield) entityShield).owner.getDisplayName())) {
            glTranslated(x, y, z);
        }

        glCallList(glCallList);

        glPopMatrix();
        glPopAttrib();
        glDisable(GL12.GL_RESCALE_NORMAL);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity texture) {
        return shieldTexture;
    }
}
