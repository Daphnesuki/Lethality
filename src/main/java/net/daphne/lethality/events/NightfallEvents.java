package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.custom.NightfallItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class NightfallEvents {
        @SubscribeEvent
        public static void onPlayerDamageEntity(LivingHurtEvent event) {
            if (!(event.getSource().getEntity() instanceof Player player)) return;

            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof NightfallItem)) return;

            float accumulated = event.getAmount() * 0.025f;
            NightfallItem.addAccumulatedShield(stack, player, accumulated);
        }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof NightfallItem)) return;

        // Cooldown interno
        NightfallItem.tickCooldown(stack);

        boolean currentlyHas = player.hasEffect(ModMobEffects.IRON_WILL.get());
        boolean previouslyHad = NightfallItem.hadIronWill(stack);

        // Si tenía el efecto pero ya no lo tiene, entonces acaba de expirar
        if (!currentlyHas && previouslyHad) {
            float absorption = player.getAbsorptionAmount();
            float heal = absorption * 0.5f;

            if (heal > 0) {
                player.heal(heal);
                player.setAbsorptionAmount(0);
                NightfallItem.resetShield(stack);

                SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("lethality", "nightfall_shield_recast"));
                float pitch = Mth.nextFloat(player.getRandom(), 0.8F, 1.2F);
                float volume = 1F;
                player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);

                player.getCooldowns().addCooldown(stack.getItem(), 200);
            }
        }

        // Actualiza flag
        NightfallItem.setHadIronWill(stack, currentlyHas);
    }

}