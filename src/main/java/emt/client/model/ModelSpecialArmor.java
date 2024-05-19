package emt.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSpecialArmor extends ModelBiped {

    private static ModelSpecialArmor instance;

    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer center;
    public ModelRenderer jetpack;
    public boolean isJumping;
    public int type = 0; // 0 - Nothing, 1 - Jetpack, 2 - Wings

    public ModelSpecialArmor() {
        this(1, 0);
    }

    public ModelSpecialArmor(float scale, int type) {
        this(scale, 0.0F, type);
    }

    public ModelSpecialArmor(float scale, float offset, int type) {
        super(scale, offset, type == 1 ? 128 : 64, type == 0 ? 32 : type == 1 ? 96 : 64);
        this.type = type;

        this.textureWidth = 128;
        this.textureHeight = 96;
        this.jetpack = new ModelRenderer(this, 16, 69);
        this.jetpack.setTextureSize(64, 48);
        this.jetpack.addBox(-4.0F, -12.0F, -2.0F, 8, 12 * 2, 4, scale * 2);
        this.jetpack.setRotationPoint(0.0F, 0.0F + offset, 0.0F);

        this.textureWidth = 64;
        this.textureHeight = 64;
        rightWing = new ModelRenderer(this, 24, 0 + 32);
        rightWing.addBox(-11F, -1F, 0F, 11, 13, 1);
        rightWing.setRotationPoint(-2F, 0F, 3F);
        rightWing.setTextureSize(64, 32);
        rightWing.mirror = false;
        setRotation(rightWing, 0F, 0.1570796F, 0F);

        leftWing = new ModelRenderer(this, 0, 0 + 32);
        leftWing.addBox(0F, -1F, 0F, 11, 13, 1);
        leftWing.setRotationPoint(2F, 0F, 3F);
        leftWing.setTextureSize(64, 32);
        leftWing.mirror = true;
        setRotation(leftWing, 0F, -0.1570796F, 0F);

        center = new ModelRenderer(this, 0, 14 + 32);
        center.addBox(0F, 0F, 1F, 8, 14, 1);
        center.setRotationPoint(-4F, 0F, 3F);
        center.setTextureSize(64, 32);
        center.mirror = true;
        setRotation(center, 0F, 0F, 0F);

    }

    public static ModelSpecialArmor getInstance() {
        if (instance == null) instance = new ModelSpecialArmor();

        return instance;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
            float p_78088_6_, float scale) {
        EntityPlayer player = (EntityPlayer) entity;
        this.heldItemRight = player.inventory.getCurrentItem() != null ? 1 : 0;
        this.isRiding = player.isRiding();
        this.isSneak = player.isSneaking();
        this.aimedBow = player.inventory.getCurrentItem() != null
                && player.inventory.getCurrentItem().getItem().getItemUseAction(player.inventory.getCurrentItem())
                        == EnumAction.bow
                && player.isUsingItem();
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entity);

        this.bipedBody.render(scale);
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        switch (this.type) {
            case 1:
                this.jetpack.render(scale);
                break;
            case 2:
                this.center.render(scale);
                this.leftWing.render(scale);
                this.rightWing.render(scale);
                break;
        }
    }

    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
            float p_78087_5_, float p_78087_6_, Entity entity) {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entity);
        if (this.isJumping) {
            this.rightWing.rotateAngleY = 0.5f;
            this.leftWing.rotateAngleY = -0.5f;
        } else {
            this.rightWing.rotateAngleY = 0.1570796F;
            this.leftWing.rotateAngleY = -0.1570796F;
        }

        if (this.isSneak) {
            switch (this.type) {
                case 1:
                    this.jetpack.rotateAngleX = 0.5F;
                    break;
                case 2:
                    this.center.rotateAngleX = 0.5F;
                    this.rightWing.rotateAngleX = 0.5F;
                    this.leftWing.rotateAngleX = 0.5F;
                    break;
            }
        } else {
            switch (this.type) {
                case 1:
                    this.jetpack.rotateAngleX = 0F;
                    break;
                case 2:
                    this.center.rotateAngleX = 0F;
                    this.rightWing.rotateAngleX = 0F;
                    this.leftWing.rotateAngleX = 0F;
                    break;
            }
        }
    }
}
