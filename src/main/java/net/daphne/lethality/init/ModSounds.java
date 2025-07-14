package net.daphne.lethality.init;

import net.daphne.lethality.LethalityMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LethalityMod.MOD_ID);

    public static final RegistryObject<SoundEvent> MEGALO_STRIKE_BACK = SOUND_EVENTS.register("megalo_strike_back",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LethalityMod.MOD_ID, "megalo_strike_back")));

    public static final RegistryObject<SoundEvent> REAL_KNIFE_SLASH = SOUND_EVENTS.register("real_knife_slash",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LethalityMod.MOD_ID, "real_knife_slash")));

    public static final RegistryObject<SoundEvent> MATE_DRINK = SOUND_EVENTS.register("mate_drink",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LethalityMod.MOD_ID, "mate_drink")));

    public static final RegistryObject<SoundEvent> NIGHTFALL_SLAM = SOUND_EVENTS.register("nightfall_slam",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LethalityMod.MOD_ID, "nightfall_slam")));

    public static final RegistryObject<SoundEvent> NIGHTFALL_HIT = SOUND_EVENTS.register("nightfall_hit",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LethalityMod.MOD_ID, "nightfall_hit")));

    public static final RegistryObject<SoundEvent> NIGHTFALL_SHIELD_USE = SOUND_EVENTS.register("nightfall_shield_use",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LethalityMod.MOD_ID, "nightfall_shield_use")));

    public static final RegistryObject<SoundEvent> NIGHTFALL_SHIELD_RECAST = SOUND_EVENTS.register("nightfall_shield_recast",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LethalityMod.MOD_ID, "nightfall_shield_recast")));

    public static final RegistryObject<SoundEvent> BLOODSHED_START = SOUND_EVENTS.register("bloodshed_start",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LethalityMod.MOD_ID, "bloodshed_start")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}