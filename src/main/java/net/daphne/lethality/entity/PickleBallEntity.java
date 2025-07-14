package net.daphne.lethality.entity;

import net.daphne.lethality.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
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

public class PickleBallEntity extends ThrowableItemProjectile implements GeoEntity {

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private int bounceCount = 0;
    private float roll, pitch, yaw;
    private float prevRoll, prevPitch, prevYaw;

    private float projDamage;
    private final Set<UUID> hitEntities = new HashSet<>();

    public static final EntityDataAccessor<ItemStack> ITEM_STACK =
            SynchedEntityData.defineId(PickleBallEntity.class, EntityDataSerializers.ITEM_STACK);

    public PickleBallEntity(EntityType<? extends PickleBallEntity> type, Level world) {
        super(type, world);
    }

    public PickleBallEntity(Level world, double x, double y, double z, float damage, ItemStack item) {
        this(ModEntities.PICKLE_BALL.get(), world);
        this.setPos(x, y, z);
        this.projDamage = damage;
        this.setItem(item);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ITEM_STACK, ItemStack.EMPTY);
    }

    public void setItem(ItemStack stack) {
        this.entityData.set(ITEM_STACK, stack);
    }

    @Override
    public ItemStack getItem() {
        return this.entityData.get(ITEM_STACK);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PICKLE_PADDLE.get();
    }

    @Override
    public void tick() {
        super.tick();

        // Actualización de rotación basada en movimiento
        Vec3 motion = this.getDeltaMovement();
        double speed = motion.length();
        if (speed > 0.01) {
            this.prevRoll = this.roll;
            this.prevPitch = this.pitch;
            this.prevYaw = this.yaw;

            this.roll += (float) (speed * 30.0f);
            this.yaw = (float) Math.toDegrees(Math.atan2(motion.z, motion.x)) - 90.0f;
            this.pitch = (float) Math.toDegrees(Math.atan2(motion.y, Math.sqrt(motion.x * motion.x + motion.z * motion.z)));
        }

        if (!this.level().isClientSide) {
            this.checkEntityCollisions();
        }
    }

    private void checkEntityCollisions() {
        AABB bounds = this.getBoundingBox();
        List<LivingEntity> hit = this.level().getEntitiesOfClass(LivingEntity.class, bounds, e -> e != this.getOwner());
        for (LivingEntity entity : hit) {
            if (hitEntities.add(entity.getUUID())) {
                entity.hurt(this.damageSources().thrown(this, this.getOwner()), projDamage + (projDamage * bounceCount));
                this.discard(); // si no quieres que se descarte al primer hit, remueve esto
                break;
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        if (bounceCount >= 3) {
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.ITEM_SLIME,
                        this.getX(), this.getY(), this.getZ(),
                        15, // cantidad
                        0.2, 0.1, 0.2, // spread X, Y, Z
                        0.05 // velocidad
                );
            }
            this.discard();
            return;
        }

        // rebote con normal
        Vec3 normal = Vec3.atLowerCornerOf(hitResult.getDirection().getNormal());
        Vec3 incoming = this.getDeltaMovement();
        Vec3 reflected = incoming.subtract(normal.scale(2 * incoming.dot(normal)));

        this.setDeltaMovement(reflected.scale(getBounciness()));
        this.setPos(hitResult.getLocation().add(reflected.normalize().scale(0.05)));

        bounceCount++;
        this.playSound(SoundEvents.SLIME_BLOCK_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.ITEM_SLIME,
                    this.getX(), this.getY(), this.getZ(),
                    15, // cantidad
                    0.2, 0.1, 0.2, // spread X, Y, Z
                    0.05 // velocidad
            );
        }
    }

    protected float getBounciness() {
        return 0.8F; // más rebote = más cercano a 1
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity living && hitEntities.add(living.getUUID())) {
            living.hurt(this.damageSources().thrown(this, this.getOwner()), projDamage);
            living.invulnerableTime = 0;
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.ITEM_SLIME,
                        living.getX(),
                        living.getY() + 1.0,
                        living.getZ(),
                        25, 0.3, 0.5, 0.3, 2.0);
            }
            this.discard(); // opcional
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public boolean shouldRender(double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

