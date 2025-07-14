package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.particles.ModParticles;
import net.daphne.lethality.util.ModUtils;
import net.mcreator.terramity.init.TerramityModItems;
import net.mcreator.terramity.init.TerramityModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Set;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class MidasTouchEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        LivingEntity target = event.getEntity();

        if (!(attacker instanceof LivingEntity livingAttacker)) return;

        ItemStack mainHand = livingAttacker.getMainHandItem();
        if (!mainHand.is(ModItems.MIDAS_TOUCH.get())) return;

        Level level = livingAttacker.level();
        BlockPos pos = livingAttacker.blockPosition();

        SoundEvent critSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity", "coin_rattle"));
        SoundEvent coincritSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity", "bassy_thud"));

        RandomSource random = livingAttacker.getRandom();

        boolean hasCoin = CuriosApi.getCuriosHelper()
                .findEquippedCurio((Item) TerramityModItems.FATEFUL_COIN.get(), livingAttacker)
                .isPresent();
        boolean applyEffect = hasCoin || random.nextDouble() < 0.5;

        if (applyEffect) {
            float baseDamage = event.getAmount(); // Daño base del arma (espada)
            float multiplier = hasCoin ? 2.0f : 1.5f;
            float boostedDamage = baseDamage * multiplier;
            event.setAmount(boostedDamage);

            // Sonido y partículas
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.playSound(null, pos, critSound, SoundSource.PLAYERS, 1.0f, 1.0f);
                serverLevel.sendParticles(
                        TerramityModParticleTypes.COIN_PARTICLE.get(),
                        target.getX(),
                        target.getY() + 1.0,
                        target.getZ(),
                        20, 0.3, 0.5, 0.3, 0.2);

                if (hasCoin) {
                    serverLevel.playSound(null, pos, coincritSound, SoundSource.PLAYERS, 0.75f, 1.0f);
                    serverLevel.sendParticles(
                            TerramityModParticleTypes.GOLD_GLITTER.get(),
                            target.getX(),
                            target.getY() + 1.5,
                            target.getZ(),
                            20, 0.2, 0.2, 0.2, 0.05);
                }
            } else {
                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), critSound, SoundSource.PLAYERS, 1.0f,
                        (float) Mth.nextDouble(random, 0.7, 1.1), false);
            }
        }
    }
}