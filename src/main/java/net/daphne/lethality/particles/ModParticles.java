package net.daphne.lethality.particles;

import com.mojang.serialization.Codec;
import net.daphne.lethality.LethalityMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraftforge.registries.ForgeRegistries.PARTICLE_TYPES;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(PARTICLE_TYPES, LethalityMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> CUSTOM_PARTICLE =
            PARTICLES.register("custom_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ACIDIC_BUBBLE =
            PARTICLES.register("acidic_bubble", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ACIDIC_BUBBLE_FORK =
            PARTICLES.register("acidic_bubble_fork", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ACIDIC_IMPACT =
            PARTICLES.register("acidic_impact", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> REAL_CRIT =
            PARTICLES.register("real_crit", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> FORBIDDEN_GLINT =
            PARTICLES.register("forbidden_glint", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> HEX_FLAME =
            PARTICLES.register("hex_flame", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> GAEL_SMOKE =
            PARTICLES.register("gael_smoke", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> TRANQUILITY =
            PARTICLES.register("tranquility", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> CRUMBLING =
            PARTICLES.register("crumbling", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> CUTE_SPARKLES =
            PARTICLES.register("cute_sparkles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
