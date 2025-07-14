package net.daphne.lethality.item;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.item.custom.*;
import net.daphne.lethality.item.custom.armor.*;
import net.daphne.lethality.item.custom.curios.FloweyItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LethalityMod.MOD_ID);

    public static final RegistryObject<Item> TAINTED_BLADE = ITEMS.register("tainted_blade",
            () -> new TaintedBladeItem(new Item.Properties()));
    public static final RegistryObject<Item> CAUSTIC_EDGE = ITEMS.register("caustic_edge",
            () -> new CausticEdgeItem(new Item.Properties()));
    public static final RegistryObject<Item> BLIGHTED_CLEAVER = ITEMS.register("blighted_cleaver",
            () -> new BlightedCleaverItem(new Item.Properties()));
    public static final RegistryObject<Item> DEFILED_GREATSWORD = ITEMS.register("defiled_greatsword",
            () -> new DefiledGreatswordItem(new Item.Properties()));

    public static final RegistryObject<Item> BLADECREST_OATHSWORD = ITEMS.register("bladecrest_oathsword",
            () -> new BladecrestOathswordItem(new Item.Properties()));
    public static final RegistryObject<Item> FORBIDDEN_OATHBLADE = ITEMS.register("forbidden_oathblade",
            () -> new ForbiddenOathbladeItem(new Item.Properties()));
    public static final RegistryObject<Item> EXALTED_OATHBLADE = ITEMS.register("exalted_oathblade",
            () -> new ExaltedOathbladeItem(new Item.Properties()));
    public static final RegistryObject<Item> DEVILS_DEVASTATION = ITEMS.register("devils_devastation",
            () -> new DevilsDevastationItem(new Item.Properties()));

    public static final RegistryObject<Item> BROKEN_BIOME_BLADE = ITEMS.register("broken_biome_blade",
            () -> new BrokenBiomeBladeItem(new Item.Properties()));
//    public static final RegistryObject<Item> BIOME_BLADE = ITEMS.register("biome_blade",
//            () -> new BiomeBladeItem(new Item.Properties()));
//    public static final RegistryObject<Item> TRUE_BIOME_BLADE = ITEMS.register("true_biome_blade",
//            () -> new TrueBiomeBladeItem(new Item.Properties()));
//    public static final RegistryObject<Item> GALAXIA = ITEMS.register("galaxia",
//            () -> new GalaxiaItem(new Item.Properties()));

    public static final RegistryObject<Item> GAELS_GREATSWORD = ITEMS.register("gaels_greatsword",
            () -> new GaelsGreatswordItem(new Item.Properties()));

    public static final RegistryObject<Item> VIOLENCE = ITEMS.register("violence",
            () -> new ViolenceItem(new Item.Properties()));
    public static final RegistryObject<Item> SACRIFICE = ITEMS.register("sacrifice",
            () -> new SacrificeItem(new Item.Properties()));
    public static final RegistryObject<Item> VEHEMENCE = ITEMS.register("vehemence",
            () -> new VehemenceItem(new Item.Properties()));
    public static final RegistryObject<Item> GRIEVANCE = ITEMS.register("grievance",
            () -> new GrievanceItem(new Item.Properties()));

    public static final RegistryObject<Item> STARLIGHT = ITEMS.register("starlight",
            () -> new StarlightItem(new Item.Properties()));
    public static final RegistryObject<Item> REAL_KNIFE = ITEMS.register("real_knife",
            () -> new RealKnifeItem(new Item.Properties()));
    public static final RegistryObject<Item> PICKLE_PADDLE = ITEMS.register("pickle_paddle",
            () -> new PicklePaddleItem(new Item.Properties()));
    public static final RegistryObject<Item> MIDAS_TOUCH = ITEMS.register("midas_touch",
            () -> new MidasTouchItem(new Item.Properties()));
    public static final RegistryObject<Item> NIGHTMARE_SWORD = ITEMS.register("nightmare_sword",
            () -> new NightmareSwordItem(new Item.Properties()));
    public static final RegistryObject<Item> NIGHTFALL = ITEMS.register("nightfall",
            () -> new NightfallItem(new Item.Properties()));
    public static final RegistryObject<Item> GAMBLERS_BLADE = ITEMS.register("gamblers_blade",
            () -> new GamblersBladeItem(new Item.Properties()));
    public static final RegistryObject<Item> HF_MEOWRASAMA = ITEMS.register("hf_meowrasama",
            () -> new HFMeowrasamaItem(new Item.Properties()));
    public static final RegistryObject<Item> STOP_SIGN = ITEMS.register("stop_sign",
            () -> new StopSignItem(new Item.Properties()));
    public static final RegistryObject<Item> FOREIGN_HOOK = ITEMS.register("foreign_hook",
            () -> new ForeignHookItem(new Item.Properties()));

    public static final RegistryObject<Item> FAIRY_PICKLE = ITEMS.register("fairy_pickle",
            () -> new FairyPickleItem());
    public static final RegistryObject<Item> MATE = ITEMS.register("mate",
            () -> new MateItem());
    public static final RegistryObject<Item> MATE_DULCE = ITEMS.register("mate_dulce",
            () -> new MateDulceItem());
    public static final RegistryObject<Item> ALFAJOR_DE_MAICENA = ITEMS.register("alfajor_de_maicena",
            () -> new AlfajorDeMaicenaItem());

    public static final RegistryObject<Item> ESCARAPELA = ITEMS.register("escarapela",
            () -> new EscarapelaItem(new Item.Properties()));
    public static final RegistryObject<Item> PIXIE_ALLOY = ITEMS.register("pixie_alloy",
            () -> new PixieAlloyItem(new Item.Properties()));
    public static final RegistryObject<Item> ANNIHILATION_ALLOY = ITEMS.register("annihilation_alloy",
            () -> new AnnihilationAlloyItem(new Item.Properties()));
    public static final RegistryObject<Item> IMPROBABILITY_STEEL = ITEMS.register("improbability_steel",
            () -> new ImprobabilitySteelItem(new Item.Properties()));
    public static final RegistryObject<Item> SOFT_CLOTH = ITEMS.register("soft_cloth",
            () -> new SoftClothItem(new Item.Properties()));
    public static final RegistryObject<Item> BLOOD_COIN = ITEMS.register("blood_coin",
            () -> new BloodCoinItem(new Item.Properties()));
    public static final RegistryObject<Item> FLOWEY = ITEMS.register("flowey",
            () -> new FloweyItem(new Item.Properties()));

    public static final RegistryObject<Item> BATTLE_MAID_HELMET = ITEMS.register("battle_maid_helmet",
            () -> new BattleMaidHelmetItem(ModArmorMaterials.BATTLE_MAID, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> BATTLE_MAID_CHESTPLATE = ITEMS.register("battle_maid_chestplate",
            () -> new BattleMaidChestplateItem(ModArmorMaterials.BATTLE_MAID, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> BATTLE_MAID_LEGGINGS = ITEMS.register("battle_maid_leggings",
            () -> new BattleMaidLeggingsItem(ModArmorMaterials.BATTLE_MAID, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> BATTLE_MAID_BOOTS = ITEMS.register("battle_maid_boots",
            () -> new BattleMaidBootsItem(ModArmorMaterials.BATTLE_MAID, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> HF_BATTLE_MAID_HELMET = ITEMS.register("hf_battle_maid_helmet",
            () -> new HFBattleMaidHelmetItem(ModArmorMaterials.HF_BATTLE_MAID, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> HF_BATTLE_MAID_CHESTPLATE = ITEMS.register("hf_battle_maid_chestplate",
            () -> new HFBattleMaidChestplateItem(ModArmorMaterials.HF_BATTLE_MAID, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> HF_BATTLE_MAID_LEGGINGS = ITEMS.register("hf_battle_maid_leggings",
            () -> new HFBattleMaidLeggingsItem(ModArmorMaterials.HF_BATTLE_MAID, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> HF_BATTLE_MAID_BOOTS = ITEMS.register("hf_battle_maid_boots",
            () -> new HFBattleMaidBootsItem(ModArmorMaterials.HF_BATTLE_MAID, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
