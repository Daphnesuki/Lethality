package net.daphne.lethality.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> HEX_FLAMES =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("lethality", "hex_flames"));

    public static DamageSource hexFlames(LivingEntity attacker) {
        return new DamageSource(attacker.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(HEX_FLAMES), attacker);
    }

    public static final ResourceKey<DamageType> ACID_VENOM =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("lethality", "acid_venom"));

    public static DamageSource acidVenom(LivingEntity attacker) {
        return new DamageSource(attacker.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(ACID_VENOM), attacker);
    }

    public static final ResourceKey<DamageType> HELLFIRE =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("lethality", "hellfire"));

    public static DamageSource hellfire(LivingEntity attacker) {
        return new DamageSource(attacker.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(HELLFIRE), attacker);
    }

    public static final ResourceKey<DamageType> BRIMSTONE_FLAMES =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("lethality", "brimstone_flames"));

    public static DamageSource brimstoneFlames(LivingEntity attacker) {
        return new DamageSource(attacker.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(BRIMSTONE_FLAMES), attacker);
    }

    public static final ResourceLocation MEOWRASAMA_SLASH = new ResourceLocation("lethality", "meowrasama_slash");

    public static DamageSource meowrasamaSlash(Level level) {
        return new DamageSource(level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, MEOWRASAMA_SLASH)));
    }
}