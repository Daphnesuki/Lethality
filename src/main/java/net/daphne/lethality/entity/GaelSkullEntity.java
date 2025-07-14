package net.daphne.lethality.entity;

import net.daphne.lethality.particles.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GaelSkullEntity extends AbstractHurtingProjectile implements GeoEntity {

    private static final EntityDataAccessor<Integer> ID_TARGET = SynchedEntityData.defineId(BloodScytheEntity.class, EntityDataSerializers.INT);
    private float projdamage;
    private final Set<UUID> messagedEntities = new HashSet<>();
    private int iteration = 0;
    private boolean slowingDown = false;
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private static final double seekDistance = 35.0;
    private static final double seekThreshold = 0.4;

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> {
            state.setAnimation(
                    software.bernie.geckolib.core.animation.RawAnimation.begin()
                            .then("idle", software.bernie.geckolib.core.animation.Animation.LoopType.LOOP)
            );
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public GaelSkullEntity(EntityType<? extends GaelSkullEntity> type, Level world) {
        super(type, world);
    }

    public GaelSkullEntity(Level world, double x, double y, double z, float Damage) {
        this(ModEntities.GAEL_SKULL.get(), world);
        this.setPos(x, y, z);
        this.projdamage = Damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ID_TARGET, -1);
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public void tick() {

        double radius = 1.0;
        double particleNum = 30.0;
        double arcAngle = 200.0;

        double yaw = this.getYRot() + 90.0;
        double pitch = -1.0 * (this.getXRot() + 90.0);

        double radYaw = Math.toRadians(yaw);
        double radPitch = Math.toRadians(pitch);

        Level level = this.level();
        Entity owner = this.getOwner();

        if (!level.isClientSide()) {
            for (int i = 0; i < (int) particleNum; ++i) {
                double angle = i * (arcAngle / particleNum);
                double radAngle = Math.toRadians(angle);

                double vX = (Math.sin(radAngle) * Math.sin(radPitch) * Math.cos(radYaw) + Math.cos(radAngle) * Math.sin(radYaw)) * -1.0;
                double vY = Math.sin(radAngle) * Math.cos(radPitch);
                double vZ = Math.sin(radAngle) * Math.sin(radPitch) * Math.sin(radYaw) * -1.0 + Math.cos(radAngle) * Math.cos(radYaw);

                double x_pos = this.getX() + radius * vX;
                double y_pos = this.getY() + radius * vY;
                double z_pos = this.getZ() + radius * vZ;

                AABB box = new AABB(x_pos, y_pos, z_pos, x_pos, y_pos, z_pos).inflate(0.5);
                List<LivingEntity> hitEntities = level.getEntitiesOfClass(LivingEntity.class, box, e -> e != this.getOwner());

                for (LivingEntity target : hitEntities) {
                    UUID uuid = target.getUUID();
                    if (!this.messagedEntities.contains(uuid)) {
                        this.messagedEntities.add(uuid);
                        target.hurt(level.damageSources().indirectMagic(this, this.getOwner()), projdamage);
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(
                                    ModParticles.GAEL_SMOKE.get(),
                                    target.getX(),
                                    target.getY() + 1.0,
                                    target.getZ(),
                                    25, 0.3, 0.5, 0.3, 0.2);
                        }
                        this.discard();
                    }
                }
            }
        }

        if (owner != null && !this.level().isClientSide) {
            this.updateTarget();
            Entity target = this.getTarget();
            if (target instanceof LivingEntity targetEntity) {
                Vec3 toTarget = targetEntity.position().add(0, targetEntity.getBbHeight() / 2.0, 0).subtract(this.position());
                Vec3 currentMotion = this.getDeltaMovement();
                double steerStrength = 0.2;
                Vec3 desiredMotion = toTarget.normalize().scale(currentMotion.length());
                Vec3 newMotion = currentMotion.scale(1 - steerStrength).add(desiredMotion.scale(steerStrength));
                newMotion = newMotion.normalize().scale(currentMotion.length());
                this.setDeltaMovement(newMotion);
            }
        }

        if (this.level().isClientSide) {
            this.level().addParticle(
                    ModParticles.GAEL_SMOKE.get(),
                    this.getX(),
                    this.getY() + 0.9,
                    this.getZ(),
                    (this.random.nextDouble() - 0.5) * 0.1,
                    (this.random.nextDouble() - 0.5) * 0.1,
                    (this.random.nextDouble() - 0.5) * 0.1
            );
        }

        this.setPos(this.getX() + this.getDeltaMovement().x,
                this.getY() + this.getDeltaMovement().y,
                this.getZ() + this.getDeltaMovement().z);

        iteration++;

        if (!slowingDown && iteration >= 20) {
            slowingDown = true; // Inicia desaceleración
        }

        if (slowingDown) {
            Vec3 motion = this.getDeltaMovement();
            Vec3 slowed = motion.scale(0.8); // Reduce velocidad gradualmente
            this.setDeltaMovement(slowed);

            if (slowed.lengthSqr() < 0.3) { // Muy lento -> descartamos
                detonateEntity(level(), this.getX() + 0.2, this.getY() + 0.9, this.getZ(), 50, 0.35f, ModParticles.GAEL_SMOKE.get());
            }
            if (slowed.lengthSqr() < 0.15) { // Muy lento -> descartamos
                this.discard();
            }
        }
    }

    public void detonateEntity(Level level, double x, double y, double z, double points, float sizeModifier, ParticleOptions particleType) {
        double phi = Math.PI * (3. - Math.sqrt(5.));
        for (int i = 0; i < points; i++) {
            double velocityY = 1 - (i/(points - 1)) * 2;
            double radius = Math.sqrt(1 - velocityY*velocityY);
            double theta = phi * i;
            double velocityX = Math.cos(theta) * radius;
            double velocityZ = Math.sin(theta) * radius;

            level.addParticle(
                    particleType,
                    x, y, z,
                    velocityX * sizeModifier,
                    velocityY * sizeModifier,
                    velocityZ * sizeModifier
            );
        }
    }

    private void updateTarget() {
        Entity target = this.getTarget();
        if (target != null && !target.isAlive()) {
            this.setTarget(null);
            target = null;
        }

        if (target == null) {
            Vec3 motion = this.getDeltaMovement().normalize();
            AABB searchArea = this.getBoundingBox().expandTowards(motion.scale(seekDistance)).inflate(1.5, seekDistance * 0.5, 1.5);

            double closestDot = -1;
            LivingEntity bestTarget = null;

            for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, searchArea)) {
                if (!entity.isAlive() || !entity.hasLineOfSight(this) || entity == this.getOwner()) continue;
                if (this.getOwner() instanceof Player player && entity instanceof TamableAnimal pet && pet.getOwner() == player)
                    continue;
                if (!this.canHitEntity(entity)) continue;

                Vec3 toTarget = entity.position().add(0, entity.getBbHeight() / 2.0, 0).subtract(this.position()).normalize();
                double dot = motion.dot(toTarget);

                if (entity instanceof Monster) dot += 0.1; // bonus para monstruos

                if (dot > seekThreshold && dot > closestDot) {
                    closestDot = dot;
                    bestTarget = entity;
                }
            }

            if (bestTarget != null) {
                this.setTarget(bestTarget);
            }
        }
    }

    @Nullable
    private Entity getTarget() {
        return this.level().getEntity(this.getEntityData().get(ID_TARGET));
    }

    private void setTarget(@Nullable Entity entity) {
        this.getEntityData().set(ID_TARGET, entity == null ? -1 : entity.getId());
    }

    @Override
    public boolean shouldRender(double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
