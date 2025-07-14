package net.daphne.lethality.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class CuteSparkles extends TextureSheetParticle {
    protected CuteSparkles(ClientLevel level, double x, double y, double z,
                           double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.setSize(0.1F, 0.1F);
        this.lifetime = 50 + this.random.nextInt(10);
        this.gravity = -0.025F;
        this.hasPhysics = false;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            float lifeRatio = (float)this.age / (float)this.lifetime;
            this.alpha = 1.0F - lifeRatio;
        }
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240; // máxima luz
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            CuteSparkles particle = new CuteSparkles(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}
