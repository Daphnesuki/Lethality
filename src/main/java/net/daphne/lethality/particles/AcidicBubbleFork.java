package net.daphne.lethality.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class AcidicBubbleFork extends TextureSheetParticle {
    protected AcidicBubbleFork(ClientLevel level, double x, double y, double z,
                               double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.setSize(0.2F, 0.2F);
        this.lifetime = 40 + this.random.nextInt(10);
        this.gravity = -0.025F; // Flotación suave
        this.hasPhysics = false;

        this.xd = xd * 0.2; // Movimiento inicial reducido
        this.yd = yd * 0.2;
        this.zd = zd * 0.2;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            // Fade-out de opacidad
            float lifeRatio = (float)this.age / (float)this.lifetime;
            this.alpha = 1.0F - lifeRatio;

            // Movimiento más suave, como las partículas de efecto
            this.xd *= 0.85;
            this.yd *= 0.85;
            this.zd *= 0.85;
        }
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240; // Luz máxima
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
            AcidicBubbleFork particle = new AcidicBubbleFork(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}
