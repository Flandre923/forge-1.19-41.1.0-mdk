package net.flandre923.tutorialmod.world.feature;

import net.flandre923.tutorialmod.TutorialMod;
import net.flandre923.tutorialmod.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;


public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, TutorialMod.MOD_ID);


    public static final RegistryObject<PlacedFeature> ZIRCON_ORE_PLACED = PLACED_FEATURES.register("zircon_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ZIRCON_ORE.getHolder().get(),
                    commonOrePlacement(7,
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80),VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> END_ZIRCON_ORE_PLACED = PLACED_FEATURES.register("end_zircon_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.END_ZIRCON_ORE.getHolder().get(), commonOrePlacement(7,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> NETHER_ZIRCON_ORE_PLACED = PLACED_FEATURES.register("nether_zircon_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.NETHER_ZIRCON_ORE.getHolder().get(), commonOrePlacement(7,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> RED_MAPLE_CHECKED  = PLACED_FEATURES.register("red_maple_checked",
            ()-> new PlacedFeature(ModConfiguredFeatures.RED_MAPLE.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.RED_MAPLE_SAPLING.get()))));

    public static final RegistryObject<PlacedFeature> RED_MAPLE_PLACED = PLACED_FEATURES.register("red_maple_placed",
            ()-> new PlacedFeature(ModConfiguredFeatures.RED_MAPLE_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(3,0.1f,2))));


    /**
     * RegistryObject<PlacedFeature> 注册 放置特征
     * new PlacedFeature(...)作为放置特征的配置,自定义的放置特征配置
     *      ModConfiguredFeatures.ZIRCON_GEODE.getHolder().get()获取配置好的特征（configured feature）的对象
     *      List.of(...) 提供放置修饰器列表placement modifier list
     *          RarityFilter.onAverageOnceEvery(50) 生成概率
     *          HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50))生成高度范围的放置修饰器，表示这个放置特征会在以区块底部上方6个方块为最低点，绝对高度50个方块为最高点之间均匀分布地生成
     *          生成高度范围的放置修饰器，表示这个放置特征会在以区块底部上方6个方块为最低点，绝对高度50个方块为最高点之间均匀分布地生成
     */
    public static final RegistryObject<PlacedFeature> ZIRCON_GEODE_PLACED = PLACED_FEATURES.register("zircon_geode_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ZIRCON_GEODE.getHolder().get(), List.of(
                    RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50)),
                    BiomeFilter.biome())));


    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }



    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}
