package net.daphne.lethality;

import com.mojang.logging.LogUtils;
import net.daphne.lethality.block.ModBlocks;
import net.daphne.lethality.entity.ModEntities;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.init.ModSounds;
import net.daphne.lethality.item.ModCreativeModTabs;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.networking.ModMessages;
import net.daphne.lethality.networking.packets.C2S.BattleMaidAbilityPacket;
import net.daphne.lethality.particles.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LethalityMod.MOD_ID)
public class LethalityMod {
    public static final String MOD_ID = "lethality";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> "1.0",
            version -> version.equals("1.0"),
            version -> version.equals("1.0")
    );

    public LethalityMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModMessages.register();
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSounds.register(modEventBus);
        ModMobEffects.MOB_EFFECTS.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {

        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        private static final String PROTOCOL_VERSION = "1";
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            NETWORK_CHANNEL.registerMessage(
                    0, BattleMaidAbilityPacket.class,
                    BattleMaidAbilityPacket::encode,
                    BattleMaidAbilityPacket::decode,
                    BattleMaidAbilityPacket::handle
            );
        }
        @SubscribeEvent
        public static void registerParticles(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.CUSTOM_PARTICLE.get(), CustomParticle.Provider::new);
            event.registerSpriteSet(ModParticles.ACIDIC_BUBBLE.get(), AcidicBubble.Provider::new);
            event.registerSpriteSet(ModParticles.ACIDIC_BUBBLE_FORK.get(), AcidicBubbleFork.Provider::new);
            event.registerSpriteSet(ModParticles.ACIDIC_IMPACT.get(), AcidicImpact.Provider::new);
            event.registerSpriteSet(ModParticles.REAL_CRIT.get(), AcidicImpact.Provider::new);
            event.registerSpriteSet(ModParticles.FORBIDDEN_GLINT.get(), AcidicImpact.Provider::new);
            event.registerSpriteSet(ModParticles.HEX_FLAME.get(), HexFlame.Provider::new);
            event.registerSpriteSet(ModParticles.GAEL_SMOKE.get(), GaelSmoke.Provider::new);
            event.registerSpriteSet(ModParticles.TRANQUILITY.get(), Tranquility.Provider::new);
            event.registerSpriteSet(ModParticles.CRUMBLING.get(), Crumbling.Provider::new);
            event.registerSpriteSet(ModParticles.CUTE_SPARKLES.get(), CuteSparkles.Provider::new);
        }
    }
}
