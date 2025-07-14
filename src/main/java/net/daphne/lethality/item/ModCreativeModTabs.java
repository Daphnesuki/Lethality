package net.daphne.lethality.item;

import net.daphne.lethality.LethalityMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LethalityMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("lethality_tab_weapons",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VIOLENCE.get()))
                    .title(Component.translatable("creativetab.lethality_tab_weapons"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.TAINTED_BLADE.get());
                        pOutput.accept(ModItems.CAUSTIC_EDGE.get());
                        pOutput.accept(ModItems.BLIGHTED_CLEAVER.get());
                        pOutput.accept(ModItems.DEFILED_GREATSWORD.get());

                        pOutput.accept(ModItems.BLADECREST_OATHSWORD.get());
                        pOutput.accept(ModItems.FORBIDDEN_OATHBLADE.get());
                        pOutput.accept(ModItems.EXALTED_OATHBLADE.get());
                        pOutput.accept(ModItems.DEVILS_DEVASTATION.get());

//                        pOutput.accept(ModItems.BROKEN_BIOME_BLADE.get());
//                        pOutput.accept(ModItems.BIOME_BLADE.get());
//                        pOutput.accept(ModItems.TRUE_BIOME_BLADE.get());
//                        pOutput.accept(ModItems.GALAXIA.get());

                        pOutput.accept(ModItems.GAELS_GREATSWORD.get());

                        pOutput.accept(ModItems.VIOLENCE.get());
                        pOutput.accept(ModItems.SACRIFICE.get());
                        pOutput.accept(ModItems.VEHEMENCE.get());
                        pOutput.accept(ModItems.GRIEVANCE.get());

                        pOutput.accept(ModItems.STARLIGHT.get());
                        pOutput.accept(ModItems.REAL_KNIFE.get());
                        pOutput.accept(ModItems.PICKLE_PADDLE.get());
                        pOutput.accept(ModItems.MIDAS_TOUCH.get());
                        pOutput.accept(ModItems.GAMBLERS_BLADE.get());
                        pOutput.accept(ModItems.NIGHTMARE_SWORD.get());
                        pOutput.accept(ModItems.NIGHTFALL.get());
                        pOutput.accept(ModItems.HF_MEOWRASAMA.get());
                        pOutput.accept(ModItems.STOP_SIGN.get());
                        pOutput.accept(ModItems.FOREIGN_HOOK.get());

                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> MATERIALS = CREATIVE_MODE_TABS.register("lethality_tab_materials",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ANNIHILATION_ALLOY.get()))
                    .title(Component.translatable("creativetab.lethality_tab_materials"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.ESCARAPELA.get());
                        pOutput.accept(ModItems.BLOOD_COIN.get());
                        pOutput.accept(ModItems.PIXIE_ALLOY.get());
                        pOutput.accept(ModItems.ANNIHILATION_ALLOY.get());
                        pOutput.accept(ModItems.IMPROBABILITY_STEEL.get());
                        pOutput.accept(ModItems.SOFT_CLOTH.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> FOOD = CREATIVE_MODE_TABS.register("lethality_tab_food",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALFAJOR_DE_MAICENA.get()))
                    .title(Component.translatable("creativetab.lethality_tab_food"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.FAIRY_PICKLE.get());
                        pOutput.accept(ModItems.MATE.get());
                        pOutput.accept(ModItems.MATE_DULCE.get());
                        pOutput.accept(ModItems.ALFAJOR_DE_MAICENA.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> ARMOR = CREATIVE_MODE_TABS.register("lethality_tab_armor",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BATTLE_MAID_CHESTPLATE.get()))
                    .title(Component.translatable("creativetab.lethality_tab_armor"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.BATTLE_MAID_HELMET.get());
                        pOutput.accept(ModItems.BATTLE_MAID_CHESTPLATE.get());
                        pOutput.accept(ModItems.BATTLE_MAID_LEGGINGS.get());
                        pOutput.accept(ModItems.BATTLE_MAID_BOOTS.get());
                        pOutput.accept(ModItems.HF_BATTLE_MAID_HELMET.get());
                        pOutput.accept(ModItems.HF_BATTLE_MAID_CHESTPLATE.get());
                        pOutput.accept(ModItems.HF_BATTLE_MAID_LEGGINGS.get());
                        pOutput.accept(ModItems.HF_BATTLE_MAID_BOOTS.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> CURIOS = CREATIVE_MODE_TABS.register("lethality_tab_curios",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FLOWEY.get()))
                    .title(Component.translatable("creativetab.lethality_tab_curios"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.FLOWEY.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
