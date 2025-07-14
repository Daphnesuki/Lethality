package net.daphne.lethality.entity;

import net.daphne.lethality.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HookEntity extends AbstractHurtingProjectile implements GeoAnimatable {
    private static final EntityDataAccessor<Byte> HOOK_FLAGS = SynchedEntityData.defineId(HookEntity.class, EntityDataSerializers.BYTE);
    private int returnTimer = 0;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private float projdamage;

    public Vec3 getLerpedPos(float partialTicks) {
        return new Vec3(
                Mth.lerp(partialTicks, this.xOld, this.getX()),
                Mth.lerp(partialTicks, this.yOld, this.getY()),
                Mth.lerp(partialTicks, this.zOld, this.getZ())
        );
    }

    public HookEntity(EntityType<? extends HookEntity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public HookEntity(Level level, LivingEntity owner, float Damage) {
        super(ModEntities.HOOK.get(), owner.getX(), owner.getEyeY(), owner.getZ(), 0, 0, 0, level);
        this.setOwner(owner);
        this.setNoGravity(true);
        this.projdamage = Damage;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HOOK_FLAGS, (byte) 0);
    }

    @Override
    public void tick() {
        Entity owner = this.getOwner();
        if (!level().isClientSide) {
            super.tick();
            if (owner == null || !owner.isAlive()) {
                this.discard();
                return;
            }

            if (hasDealtDamage() || this.noPhysics) {
                this.noPhysics = true;
                Vec3 vec = owner.getEyePosition().subtract(this.position());
                this.setDeltaMovement(vec.normalize().scale(Math.min(vec.length(), 6.0)));

                if (vec.lengthSqr() < 1.0) {
                    this.discard();
                }
            }

            if (this.distanceToSqr(owner) > 900) {
                setDealtDamage(true);
                playSound(SoundEvents.TRIDENT_RETURN, 1.0f, 1.0f);
            }

            if (this.onGround() && !hasDealtDamage()) {
                this.setDeltaMovement(Vec3.ZERO);
                this.setDealtDamage(true);

                if (hasReeling() && owner instanceof Player player) {
                    Vec3 direction = this.position().subtract(player.getEyePosition()).normalize();
                    double pullStrength = 1.0;
                    player.setDeltaMovement(
                            player.getDeltaMovement().scale(0.9).add(direction.scale(pullStrength))
                    );
                    player.fallDistance = 0;
                } else {
                    if (level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, getX(), getY(), getZ(), 1, 0.2, 0.2, 0.2, 0);
                    }

                    level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5), e -> e.isAlive() && e != owner)
                            .forEach(target -> {
                                float baseStrength = 1f * (float)(1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));

                                float knockbackMultiplier = 0.4f; // ⚠️ más bajo = más lento/suave
                                float strength = baseStrength * knockbackMultiplier;

                                Vec3 distance = target.position().add(0, target.getBbHeight() / 2, 0).subtract(this.position());
                                Vec3 direction = distance.normalize().scale(strength);

                                // Opcional: reducir el impulso vertical si rebota mucho
                                direction = new Vec3(direction.x, direction.y * 0.5, direction.z);

                                target.push(direction.x, direction.y, direction.z);
                                target.hurtMarked = true;
                                target.fallDistance = 0;
                            });
                }
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        Entity owner = this.getOwner();

        if (entity == owner) return;

        if (!level().isClientSide && owner instanceof LivingEntity livingOwner) {
            float damage = projdamage;

            if (entity instanceof LivingEntity target) {
                DamageSource source = level().damageSources().mobAttack(livingOwner);
                target.invulnerableTime = 0;
                if (target.hurt(source, damage)) {
                    float strength = (float) (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    Vec3 dir = livingOwner.position().subtract(target.position()).scale(strength / 4f);
                    target.push(dir.x, dir.y, dir.z);
                    target.hurtMarked = true;
                    setDealtDamage(true);
                    playSound(SoundEvents.TRIDENT_HIT, 1.0f, 1.0f);
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (result.getType() == HitResult.Type.BLOCK && !hasDealtDamage()) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setDealtDamage(true);
            this.playSound(SoundEvents.TRIDENT_HIT_GROUND, 1.0f, 1.0f);
        }
    }

    private boolean getFlag(int bit) {
        return (this.entityData.get(HOOK_FLAGS) & (1 << bit)) != 0;
    }

    private void setFlag(int bit, boolean value) {
        byte flags = this.entityData.get(HOOK_FLAGS);
        if (value) {
            flags |= 1 << bit;
        } else {
            flags &= ~(1 << bit);
        }
        this.entityData.set(HOOK_FLAGS, flags);
    }

    public boolean hasDealtDamage() {
        return getFlag(0);
    }

    public void setDealtDamage(boolean b) {
        setFlag(0, b);
    }

    public boolean hasReeling() {
        return getFlag(1);
    }

    public void setReeling(boolean b) {
        setFlag(1, b);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        return this.tickCount;
    }

    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}
