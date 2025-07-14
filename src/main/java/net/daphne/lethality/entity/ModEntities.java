package net.daphne.lethality.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "lethality");

    public static final RegistryObject<EntityType<AcidicSlashEntity>> ACIDIC_SLASH =
            ENTITIES.register("acidic_slash", () ->
                    EntityType.Builder.<AcidicSlashEntity>of(AcidicSlashEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("acidic_slash"));

    public static final RegistryObject<EntityType<StarlightStabEntity>> STARLIGHT_STAB =
            ENTITIES.register("starlight_stab", () ->
                    EntityType.Builder.<StarlightStabEntity>of(StarlightStabEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("starlight_stab"));

    public static final RegistryObject<EntityType<RealSlashEntity>> REAL_SLASH =
            ENTITIES.register("real_slash", () ->
                    EntityType.Builder.<RealSlashEntity>of(RealSlashEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("real_slash"));

    public static final RegistryObject<EntityType<BloodScytheEntity>> BLOOD_SCYTHE =
            ENTITIES.register("blood_scythe", () ->
                    EntityType.Builder.<BloodScytheEntity>of(BloodScytheEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) //PLEASE DON'T FORGET TO CHANGE IT LATER
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("blood_scythe"));

    public static final RegistryObject<EntityType<ForbiddenScytheEntity>> FORBIDDEN_SCYTHE =
            ENTITIES.register("forbidden_scythe", () ->
                    EntityType.Builder.<ForbiddenScytheEntity>of(ForbiddenScytheEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("forbidden_scythe"));

    public static final RegistryObject<EntityType<DevilsScytheEntity>> DEVILS_SCYTHE =
            ENTITIES.register("devils_scythe", () ->
                    EntityType.Builder.<DevilsScytheEntity>of(DevilsScytheEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("devils_scythe"));

    public static final RegistryObject<EntityType<DevilsPitchforkEntity>> DEVILS_PITCHFORK =
            ENTITIES.register("devils_pitchfork", () ->
                    EntityType.Builder.<DevilsPitchforkEntity>of(DevilsPitchforkEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("devils_pitchfork"));

    public static final RegistryObject<EntityType<GaelSkullEntity>> GAEL_SKULL =
            ENTITIES.register("gael_skull", () ->
                    EntityType.Builder.<GaelSkullEntity>of(GaelSkullEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("gael_skull"));

    public static final RegistryObject<EntityType<VehemenceBoltEntity>> VEHEMENCE_BOLT =
            ENTITIES.register("vehemence_bolt", () ->
                    EntityType.Builder.<VehemenceBoltEntity>of(VehemenceBoltEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("vehemence_bolt"));

    public static final RegistryObject<EntityType<PickleBallEntity>> PICKLE_BALL =
            ENTITIES.register("pickle_ball", () ->
                    EntityType.Builder.<PickleBallEntity>of(PickleBallEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("pickle_ball"));

    public static final RegistryObject<EntityType<BBBProjectionEntity>> BBB_PROJECTION =
            ENTITIES.register("bbb_projection", () ->
                    EntityType.Builder.<BBBProjectionEntity>of(BBBProjectionEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("bbb_projection"));

    public static final RegistryObject<EntityType<HFMeowrasamaSlashEntity>> HF_MEOWRASAMA_SLASH =
            ENTITIES.register("hf_meowrasama_slash", () ->
                    EntityType.Builder.<HFMeowrasamaSlashEntity>of(HFMeowrasamaSlashEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("hf_meowrasama_slash"));

    public static final RegistryObject<EntityType<HookEntity>> HOOK =
            ENTITIES.register("hook", () ->
                    EntityType.Builder.<HookEntity>of(HookEntity::new, MobCategory.MISC)
                            .sized(1f, 1f) // pequeño e invisible
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .build("hook"));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}

