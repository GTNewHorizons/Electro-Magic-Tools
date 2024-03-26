package emt.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityLaser extends Entity implements IThrowableEntity, IProjectile {

    public Entity shootingEntity;
    private Block inTile;
    private int xTile;
    private int yTile;
    private int zTile;
    private int meta;
    private boolean inGround = false;
    private int ticksInGround;
    private int ticksInAir;
    private float explosionStrength = 2f;

    public EntityLaser(World world) {
        super(world);

        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }

    public EntityLaser(World world, EntityLivingBase entity, float speed) {
        super(world);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = entity;

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(
                entity.posX,
                entity.posY + (double) entity.getEyeHeight(),
                entity.posZ,
                entity.rotationYaw,
                entity.rotationPitch);
        this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
        this.yOffset = 0.0F;

        this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
        this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
        this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));

        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, speed, 1.0F);
    }

    public void setExplosionStrengthModifier(float f) {
        explosionStrength = f;
    }

    @Override
    protected void entityInit() {}

    @Override
    public void setThrowableHeading(double x, double y, double z, float speed, float scale) {
        this.motionX = x * speed;
        this.motionY = y * speed;
        this.motionZ = z * speed;
        this.ticksInGround = 0;
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float rotX, float rotY, int rotZ) {
        this.setPosition(x, y, z);
        this.setRotation(rotX, rotY);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void setVelocity(double velX, double velY, double velZ) {
        this.motionX = velX;
        this.motionY = velY;
        this.motionZ = velZ;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB axisalignedbb = block
                    .getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (axisalignedbb != null
                    && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }

        block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
        AxisAlignedBB axisalignedbb = block
                .getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

        if (axisalignedbb != null
                && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
            this.inGround = true;
        }

        if (this.inGround) {
            int k = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (!this.worldObj.isRemote) {
                this.worldObj.createExplosion(
                        this,
                        (int) this.posX,
                        (int) this.posY,
                        (int) this.posZ,
                        explosionStrength,
                        true);
                this.setDead();
            }

            if (block == this.inTile && k == this.meta) {
                ++this.ticksInGround;

                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
            } else {
                this.inGround = false;
                this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
                this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            ++this.ticksInAir;
            Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec3next = Vec3
                    .createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec3, vec3next, false, true, false);
            vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec3next = Vec3
                    .createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null) {
                vec3next = Vec3.createVectorHelper(
                        movingobjectposition.hitVec.xCoord,
                        movingobjectposition.hitVec.yCoord,
                        movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(
                    this,
                    this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int l;
            float down;

            for (l = 0; l < list.size(); ++l) {
                Entity entity1 = list.get(l);

                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
                    down = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox
                            .expand((double) down, (double) down, (double) down);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3next, vec3);

                    if (movingobjectposition1 != null) {
                        double d1 = vec3next.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D) {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null
                    && movingobjectposition.entityHit instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer
                        && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
                    movingobjectposition = null;
                }
            }

            float f2;
            float f3;

            if (movingobjectposition != null) {
                if (movingobjectposition.entityHit != null) {
                    f2 = MathHelper.sqrt_double(
                            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    if (!this.worldObj.isRemote) {
                        this.worldObj.createExplosion(
                                this,
                                (int) this.posX,
                                (int) this.posY,
                                (int) this.posZ,
                                explosionStrength,
                                true);
                        this.setDead();
                    }
                } else {
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
                    this.meta = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = movingobjectposition.hitVec.xCoord - this.posX;
                    this.motionY = movingobjectposition.hitVec.yCoord - this.posY;
                    this.motionZ = movingobjectposition.hitVec.zCoord - this.posZ;
                    f2 = MathHelper.sqrt_double(
                            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double) f2 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double) f2 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double) f2 * 0.05000000074505806D;
                    this.inGround = true;
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f2) * 180.0D
                    / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
                ;

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                this.prevRotationYaw += 360.0F;
            }

            if (this.isInWater()) {
                for (int j1 = 0; j1 < 4; ++j1) {
                    f3 = 0.25F;
                    this.worldObj.spawnParticle(
                            "bubble",
                            this.posX - this.motionX * (double) f3,
                            this.posY - this.motionY * (double) f3,
                            this.posZ - this.motionZ * (double) f3,
                            this.motionX,
                            this.motionY,
                            this.motionZ);
                }
            }
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setShort("xTile", (short) this.xTile);
        nbt.setShort("yTile", (short) this.yTile);
        nbt.setShort("zTile", (short) this.zTile);
        nbt.setShort("inTile", (short) Block.getIdFromBlock(this.inTile));
        nbt.setByte("inData", (byte) this.meta);
        nbt.setBoolean("inGround", this.inGround);
        nbt.setFloat("explosionStrength", this.explosionStrength);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.xTile = nbt.getShort("xTile");
        this.yTile = nbt.getShort("yTile");
        this.zTile = nbt.getShort("zTile");
        this.inTile = Block.getBlockById(nbt.getShort("inTile"));
        this.meta = nbt.getByte("inData") & 255;
        this.inGround = nbt.getBoolean("inGround");
        this.explosionStrength = nbt.getFloat("explosionStrength");
    }

    public void onCollide(Entity entity) {
        if (!this.worldObj.isRemote) {
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, explosionStrength, true);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getShadowSize() {
        return 0.0F;
    }

    @Override
    public Entity getThrower() {
        return shootingEntity;
    }

    @Override
    public void setThrower(Entity entity) {
        shootingEntity = entity;
    }
}
