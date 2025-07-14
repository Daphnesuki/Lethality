package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.client.model.ModelCatEarsHelmet;
import net.daphne.lethality.client.renderer.*;
import net.daphne.lethality.entity.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventHandler {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ACIDIC_SLASH.get(), AcidicSlashRenderer::new);
        event.registerEntityRenderer(ModEntities.STARLIGHT_STAB.get(), StarlightStabRenderer::new);
        event.registerEntityRenderer(ModEntities.REAL_SLASH.get(), RealSlashRenderer::new);
        event.registerEntityRenderer(ModEntities.BLOOD_SCYTHE.get(), BloodScytheRenderer::new);
        event.registerEntityRenderer(ModEntities.FORBIDDEN_SCYTHE.get(), ForbiddenScytheRenderer::new);
        event.registerEntityRenderer(ModEntities.DEVILS_SCYTHE.get(), DevilsScytheRenderer::new);
        event.registerEntityRenderer(ModEntities.DEVILS_PITCHFORK.get(), DevilsPitchforkRenderer::new);
        event.registerEntityRenderer(ModEntities.GAEL_SKULL.get(), GaelSkullRenderer::new);
        event.registerEntityRenderer(ModEntities.VEHEMENCE_BOLT.get(), VehemenceBoltRenderer::new);
        event.registerEntityRenderer(ModEntities.PICKLE_BALL.get(), PickleBallRenderer::new);
        event.registerEntityRenderer(ModEntities.BBB_PROJECTION.get(), BBBProjectionRenderer::new);
        event.registerEntityRenderer(ModEntities.HF_MEOWRASAMA_SLASH.get(), HFMeowrasamaSlashRenderer::new);
        event.registerEntityRenderer(ModEntities.HOOK.get(), HookRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelCatEarsHelmet.LAYER_LOCATION, ModelCatEarsHelmet::createBodyLayer);
    }
}

