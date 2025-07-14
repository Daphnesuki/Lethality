package net.daphne.lethality.init;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.potion.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LethalityMod.MOD_ID);

    public static final TagKey<MobEffect> STACKING_EFFECTS = TagKey.create(Registries.MOB_EFFECT, new ResourceLocation(LethalityMod.MOD_ID, "stacking"));

    public static final RegistryObject<MobEffect> RAGE =
            MOB_EFFECTS.register("rage", RageEffect::new);
    public static final RegistryObject<MobEffect> HEX_FLAMES =
            MOB_EFFECTS.register("hex_flames", HexFlamesEffect::new);
    public static final RegistryObject<MobEffect> STAINED_CALAMITY =
            MOB_EFFECTS.register("stained_calamity", StainedCalamityEffect::new);
    public static final RegistryObject<MobEffect> TRANQUILITY =
            MOB_EFFECTS.register("tranquility", TranquilityEffect::new);
    public static final RegistryObject<MobEffect> IRON_WILL =
            MOB_EFFECTS.register("iron_will", IronWillEffect::new);
    public static final RegistryObject<MobEffect> CRUMBLING =
            MOB_EFFECTS.register("crumbling", CrumblingEffect::new);
    public static final RegistryObject<MobEffect> BLADE_MODE =
            MOB_EFFECTS.register("blade_mode", BladeModeEffect::new);
    public static final RegistryObject<MobEffect> CUTENESS =
            MOB_EFFECTS.register("cuteness", CutenessEffect::new);
    public static final RegistryObject<MobEffect> BMMODE =
            MOB_EFFECTS.register("bmmode", BMEffect::new);
    public static final RegistryObject<MobEffect> HFMMODE =
            MOB_EFFECTS.register("hfmmode", HFMEffect::new);
    public static final RegistryObject<MobEffect> ACID_VENOM =
            MOB_EFFECTS.register("acid_venom", AcidVenomEffect::new);
    public static final RegistryObject<MobEffect> HELLFIRE =
            MOB_EFFECTS.register("hellfire", HellfireEffect::new);
    public static final RegistryObject<MobEffect> BRIMSTONE_FLAMES =
            MOB_EFFECTS.register("brimstone_flames", BrimstoneFlamesEffect::new);
    public static final RegistryObject<MobEffect> BRIMSTONE_FLAMES_BUFF =
            MOB_EFFECTS.register("brimstone_flames_buff", BrimstoneFlamesBuffEffect::new);
}
