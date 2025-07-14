package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.particles.ModParticles;
import net.daphne.lethality.util.ModUtils;
import net.mcreator.terramity.init.TerramityModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Set;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class NightmareSwordEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();
        float damage = event.getAmount();

        if (source instanceof Player player) {
            ItemStack held = player.getMainHandItem();

            if (held.is(ModItems.NIGHTMARE_SWORD.get())) {
                target.invulnerableTime = 0;
                if (target.getPersistentData().getBoolean("lethality.repeating_damage")) return;

                target.getPersistentData().putBoolean("lethality.repeating_damage", true);

                DamageSource source1 = target.level().damageSources().playerAttack(player);
                target.hurt(source1, damage);
                DamageSource source2 = target.level().damageSources().playerAttack(player);
                target.hurt(source2, damage);

                target.level().scheduleTick(target.blockPosition(), target.level().getBlockState(target.blockPosition()).getBlock(), 1);
                target.getPersistentData().remove("lethality.repeating_damage");
            }
        }
    }
}