package emt.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import emt.EMT;
import emt.entity.EntityLaser;

public class RenderLaser extends Render {

    private static final ResourceLocation laserTexture = new ResourceLocation(
            EMT.RESOURCE_PATH,
            "textures/models/lasermodel.png");

    @Override
    public void doRender(Entity entityLaser, double x, double y, double z, float par8, float pTick) {
        this.bindEntityTexture(entityLaser);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(entityLaser.rotationYaw - 90, 0, 1, 0);
        GL11.glRotatef(entityLaser.rotationPitch, 0, 0, 1);
        Tessellator tess = Tessellator.instance;
        byte b0 = 0;
        float f2 = 0.4f;
        float f3 = 0.7f;
        float f4 = (float) (0 + b0 * 10) / 32f;
        float f5 = (float) (5 + b0 * 10) / 32f;
        float f10 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        GL11.glRotatef(45, 1, 0, 0);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4, 0, 0);
        GL11.glPushAttrib(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_LIGHTING);

        for (int i = 0; i < 4; ++i) {
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glNormal3f(0, 0, f10);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-8, 0, 0, f2, f4);
            tess.addVertexWithUV(8, 0, 0, f3, f4);
            tess.addVertexWithUV(8, 4, 0, f3, f5);
            tess.addVertexWithUV(-8, 4, 0, f2, f5);
            tess.draw();

            tess.startDrawingQuads();
            tess.addVertexWithUV(8, 0, 0, f2, f4);
            tess.addVertexWithUV(-8, 0, 0, f3, f4);
            tess.addVertexWithUV(-8, 4, 0, f3, f5);
            tess.addVertexWithUV(8, 4, 0, f2, f5);
            tess.draw();
        }
        GL11.glPopAttrib();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getArrowTextures(EntityLaser entityLaser) {
        return laserTexture;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getArrowTextures((EntityLaser) entity);
    }
}
