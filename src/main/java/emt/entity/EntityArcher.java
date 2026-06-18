package emt.entity;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityArcher extends EntitySnowman implements IExtendedEntityProperties {

    public EntityArcher(World world) {
        super(world);

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.targetTasks.addTask(
                3,
                new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
        this.tasks.addTask(4, new EntityAIWander(this, 0.30D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1, true);
        this.setDead();
    }

    @Override
    protected Item getDropItem() {
        return Items.arrow;
    }

    @Override
    protected void dropFewItems(boolean hitByPlayer, int levelOfLooting) {
        int j;
        int k;

        j = this.rand.nextInt(3 + levelOfLooting);

        for (k = 0; k < j; ++k) {
            this.dropItem(Items.arrow, 1);
        }
        j = this.rand.nextInt(3 + levelOfLooting);

        for (k = 0; k < j; ++k) {
            this.dropItem(Items.bone, 1);
        }
    }

    @Override
    protected void dropRareDrop(int ch) {
        this.entityDropItem(new ItemStack(Items.snowball, 1, 1), 0.0F);
    }

    protected void addRandomArmor() {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase entity, float par2) {
        EntityArrow entityarrow = new EntityArrow(
                this.worldObj,
                this,
                entity,
                1.6F,
                (float) (14 - this.worldObj.difficultySetting.getDifficultyId() * 4));
        int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityarrow.setDamage(
                (double) (par2 * 2.0F) + this.rand.nextGaussian() * 0.25D
                        + (double) ((float) this.worldObj.difficultySetting.getDifficultyId() * 0.11F));

        if (powerLevel > 0) {
            entityarrow.setDamage(entityarrow.getDamage() + (double) powerLevel * 0.5D + 0.5D);
        }

        if (punchLevel > 0) {
            entityarrow.setKnockbackStrength(punchLevel);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0) {
            entityarrow.setFire(100);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(entityarrow);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {}

    @Override
    public void loadNBTData(NBTTagCompound compound) {}

    @Override
    public void init(Entity entity, World world) {}
}
