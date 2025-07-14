package net.daphne.lethality.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RealSlashEntity extends AbstractHurtingProjectile implements GeoEntity {

    private float projdamage;
    private final Set<UUID> messagedEntities = new HashSet<>();
    private int iteration = 0;
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public RealSlashEntity(EntityType<? extends RealSlashEntity> type, Level world) {
        super(type, world);
    }

    public RealSlashEntity(Level world, double x, double y, double z, float Damage) {
        this(ModEntities.REAL_SLASH.get(), world);
        this.setPos(x, y, z);
        this.projdamage = Damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }

    @Override
    protected void defineSynchedData() {

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
        if (!level.isClientSide()) {
            for (int i = 0; i < (int)particleNum; ++i) {
                double angle = i * (arcAngle / particleNum);
                double radAngle = Math.toRadians(angle);

                double vX = (Math.sin(radAngle) * Math.sin(radPitch) * Math.cos(radYaw) + Math.cos(radAngle) * Math.sin(radYaw)) * -1.0;
                double vY = Math.sin(radAngle) * Math.cos(radPitch);
                double vZ = Math.sin(radAngle) * Math.sin(radPitch) * Math.sin(radYaw) * -1.0 + Math.cos(radAngle) * Math.cos(radYaw);

                double x_pos = this.getX() + radius * vX;
                double y_pos = this.getY() + radius * vY;
                double z_pos = this.getZ() + radius * vZ;

                AABB box = new AABB(x_pos, y_pos + 1.6, z_pos, x_pos, y_pos + 1.6, z_pos).inflate(0.5);
                List<LivingEntity> hitEntities = level.getEntitiesOfClass(LivingEntity.class, box, e -> e != this.getOwner());

                for (LivingEntity target : hitEntities) {
                    UUID uuid = target.getUUID();
                    if (!this.messagedEntities.contains(uuid)) {
                        this.messagedEntities.add(uuid);

                        // Aplica daño/efectos normalmente
                        target.hurt(level.damageSources().indirectMagic(this, this.getOwner()), projdamage);
                    }
                }
            }
        }

        if (level.isClientSide() && iteration > 2) {
            Entity owner = this.getOwner();
            UUID ownerUUID = owner != null ? owner.getUUID() : null;

            for (int i = 0; i < (int) particleNum; ++i) {
                double angle = i * (arcAngle / particleNum);
                double radAngle = Math.toRadians(angle);

                double vX = (Math.sin(radAngle) * Math.sin(radPitch) * Math.cos(radYaw) + Math.cos(radAngle) * Math.sin(radYaw)) * -1.0;
                double vY = Math.sin(radAngle) * Math.cos(radPitch);
                double vZ = Math.sin(radAngle) * Math.sin(radPitch) * Math.sin(radYaw) * -1.0 + Math.cos(radAngle) * Math.cos(radYaw);

                double x_pos = this.getX() + radius * vX;
                double y_pos = this.getY() + radius * vY;
                double z_pos = this.getZ() + radius * vZ;

                AABB box2 = new AABB(
                        x_pos - 0.25, y_pos + 1.6 - 0.25, z_pos - 0.25,
                        x_pos + 0.25, y_pos + 1.6 + 0.25, z_pos + 0.25
                );

                List<LivingEntity> hitEntities = level.getEntitiesOfClass(
                        LivingEntity.class,
                        box2,
                        e -> ownerUUID == null || !e.getUUID().equals(ownerUUID)
                );

                for (LivingEntity target : hitEntities) {
                    UUID uuid = target.getUUID();
                    if (!this.messagedEntities.contains(uuid)) {
                        this.messagedEntities.add(uuid);

                    }
                }
            }
        }


//        if (this.level().isClientSide) {
//            for (int i = 0; i < 1; i++) {
//                this.level().addParticle(
//                        TerramityModParticleTypes.EVIL_GLINT.get(),
//                        this.getX(),
//                        this.getY() + 0.9,
//                        this.getZ(),
//                        (this.random.nextDouble() - 0.5) * 0.2,
//                        (this.random.nextDouble() - 0.5) * 0.2,
//                        (this.random.nextDouble() - 0.5) * 0.2
//                );
//            }
//        }

        this.setPos(this.getX() + this.getDeltaMovement().x,
                this.getY() + this.getDeltaMovement().y,
                this.getZ() + this.getDeltaMovement().z);

        iteration++;

        if (iteration >= 20) {
            this.discard();
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



    @Override
    public boolean shouldRender(double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
