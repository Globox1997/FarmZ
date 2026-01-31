package net.farmz.entity;

import net.farmz.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BeethuutEntity extends PathAwareEntity {

    public BeethuutEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 2;
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.BEETROOT_SEEDS), true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new WanderAroundGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
    }

    public static DefaultAttributeContainer.Builder createBeethuutAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0D);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().getTime() % 20 == 0 && this.getRandom().nextFloat() <= 0.05f) {
            if (this.getWorld().getBlockState(this.getBlockPos()).isOf(Blocks.FARMLAND) && this.getWorld().getBlockState(this.getBlockPos().up()).getBlock() instanceof CropBlock cropBlock && cropBlock.getAge(this.getWorld().getBlockState(this.getBlockPos().up())) < cropBlock.getMaxAge()) {
                if (!this.getWorld().isClient()) {
                    cropBlock.grow((ServerWorld) this.getWorld(), this.getRandom(), this.getBlockPos().up(), this.getWorld().getBlockState(this.getBlockPos().up()));
                } else {
                    BoneMealItem.createParticles(this.getWorld(), this.getBlockPos().up(), 5);
                    this.playSound(SoundInit.BEETHUUT_IDLE_EVENT, 1.0f, 1.0f);
                }
            } else if (this.getRandom().nextFloat() < 0.01f) {
                if (!this.getWorld().isClient()) {
                    this.dropStack(new ItemStack(Items.BEETROOT_SEEDS));
                } else {
                    this.playSound(SoundInit.BEETHUUT_IDLE_EVENT, 1.0f, 1.0f);
                }
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundInit.BEETHUUT_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.BEETHUUT_HURT_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.BEETHUUT_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }
}
