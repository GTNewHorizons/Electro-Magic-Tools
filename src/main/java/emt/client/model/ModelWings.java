package emt.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModelWings extends ModelBiped {

    @SideOnly(Side.CLIENT)
    private static ModelWings instance = null;

    public boolean isJumping = false;
    ModelRenderer rightWing;
    ModelRenderer leftWing;
    ModelRenderer center;

    public ModelWings() {
        textureWidth = 64;
        textureHeight = 64;

        rightWing = new ModelRenderer(this, 24, 0);
        rightWing.addBox(-11F, -1F, -1.5F, 11, 13, 1);
        rightWing.setRotationPoint(-2F, 0F, 3F);
        rightWing.setTextureSize(64, 32);
        rightWing.mirror = false;
        setRotation(rightWing, 0F, 0.1570796F, 0F);

        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.addBox(0F, -1F, -1.5F, 11, 13, 1);
        leftWing.setRotationPoint(2F, 0F, 3F);
        leftWing.setTextureSize(64, 32);
        leftWing.mirror = true;
        setRotation(leftWing, 0F, -0.1570796F, 0F);

        center = new ModelRenderer(this, 0, 14);
        center.addBox(0F, 0F, -0.5F, 8, 14, 1);
        center.setRotationPoint(-4F, 0F, 3F);
        center.setTextureSize(64, 32);
        center.mirror = true;
        setRotation(center, 0F, 0F, 0F);

        this.bipedBody.addChild(rightWing);
        this.bipedBody.addChild(leftWing);
        this.bipedBody.addChild(center);
    }

    public static ModelWings getInstance() {
        if (instance == null) instance = new ModelWings();

        return instance;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        rightWing.render(f5);
        leftWing.render(f5);
        center.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float playerTicksExisted,
            float playerRotationYawHead, float playerRotationPitch, float f5, Entity entity) {

        if (isJumping) {
            this.rightWing.rotateAngleY = 0.5f;
            this.leftWing.rotateAngleY = -0.5f;
        } else {
            this.rightWing.rotateAngleY = 0.1570796F;
            this.leftWing.rotateAngleY = -0.1570796F;
        }

        if (entity.isSneaking()) {
            this.center.rotateAngleX = 0.5F;
            this.rightWing.rotateAngleX = 0.5F;
            this.leftWing.rotateAngleX = 0.5F;
        } else {
            this.center.rotateAngleX = 0.0F;
            this.rightWing.rotateAngleX = 0;
            this.leftWing.rotateAngleX = 0;
        }
    }
}
