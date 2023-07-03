package net.flandre923.tutorialmod.item;

import net.flandre923.tutorialmod.TutorialMod;
import net.flandre923.tutorialmod.block.ModBlocks;
import net.flandre923.tutorialmod.entity.ModEntityTypes;
import net.flandre923.tutorialmod.fluid.ModFluids;
import net.flandre923.tutorialmod.item.custom.EightBallItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem {
    // 创建一个注册表，类型为Item表示物品，MOD_ID表示我们的mod的名称。
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);
    // 创建了一个物品，使用注册表对该物品进行注册，注册的name为zircon,使用lambda返回一个新的Item对象
    // 这个物品的tab可以使用游戏中的tab，也可以自己创建，并注册。
    public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon" ,
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));

    // 修改物品的tab为自己创建的Tab
    public static final RegistryObject<Item> RAW_ZIRCON = ITEMS.register("raw_zircon",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));
    // 添加eight_ball 物品
    public static final RegistryObject<Item> EIGHT_BALL = ITEMS.register("eight_ball",
            () -> new EightBallItem(new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BLUEBERRY_SEEDS = ITEMS.register("blueberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_CROP.get(),
                    new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));

    public static final RegistryObject<Item> BLUEBERRY = ITEMS.register("blueberry",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(2f).build())));

    public static final RegistryObject<Item> SOAP_WATER_BUCKET = ITEMS.register("soap_water_bucket",
            ()->new BucketItem(ModFluids.SOURCE_SOAP_WATER,
                    new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB).stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> TUTORIAL_SWORD = ITEMS.register("tutorial_sword",
            ()->new SwordItem(Tiers.DIAMOND,10,5f,new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB).stacksTo(1)));


    public static final RegistryObject<Item> CHOMPER_SPAWN_EGG = ITEMS.register("chomper_spawn_egg",
            ()->new ForgeSpawnEggItem(ModEntityTypes.CHOMPER,0x22b341,0x19732e,
                    new Item.Properties().tab(ModCreativeModeTab.TUTORIAL_TAB)));

    // 将注册表加入到Forge总线上，只有这样才能把物品加入到游戏中，被TutorialMod进行调用。
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
