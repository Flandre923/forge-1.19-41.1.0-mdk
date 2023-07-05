package net.flandre923.tutorialmod.block;

import com.mojang.blaze3d.shaders.Uniform;
import net.flandre923.tutorialmod.TutorialMod;
import net.flandre923.tutorialmod.block.custom.*;
import net.flandre923.tutorialmod.fluid.ModFluids;
import net.flandre923.tutorialmod.item.ModCreativeModeTab;
import net.flandre923.tutorialmod.item.ModItem;
import net.flandre923.tutorialmod.world.feature.tree.RedMapleTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.awt.desktop.AppForegroundEvent;
import java.util.function.Supplier;

public class ModBlocks {
    // 创建一个注册Minecraft方块的DeferredRegister，用于mod加载时候，注册方块
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

    // 注册一个方块，zicron_block
    // new Block时候需要设置方块的一些属性，通过BlockBehaviour进行设置
    // 不同的材质可以在Material下面进行调整。例如AIR 等等通 .就可以看到，或者按住ctrl在点击Material，就可以看到可以用的Material
    // 可以看到of返回的依旧是properties的对象
    // 通过properties对象还可以设置 硬度
    // 需要正确的工具才可以采集
    // 设置的内容都可以在properties下面找到，按住ctrl点击Properties可以看到。
    // tab是之前自己自己创建的tab
    public static final RegistryObject<Block> ZIRCON_BLOCK = registerBlock("zircon_block",
            ()->new Block(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.TUTORIAL_TAB);

    // 注册一个矿物的方块，这里new的是一个Block的子类，这个类指明了是一个破坏后会掉落经验的Block
    // 如果是其他具有自己效果的Block，也可以自己去实现一个Block类。
    // 其中 new DropExperienceBlock 的第三个参数指明的是掉落经验值的范围。
    public static final RegistryObject<Block> ZIRCON_ORE = registerBlock("zircon_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops(),UniformInt.of(3,7)),
            ModCreativeModeTab.TUTORIAL_TAB);

    public static final RegistryObject<Block> DEEPSLATE_ZIRCON_ORE = registerBlock("deepslate_zircon_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops(),
            UniformInt.of(3,7)),
            ModCreativeModeTab.TUTORIAL_TAB);

    public static final RegistryObject<Block> ENDSTONE_ZIRCON_ORE = registerBlock("endstone_zircon_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)), ModCreativeModeTab.TUTORIAL_TAB);
    public static final RegistryObject<Block> NETHERRACK_ZIRCON_ORE = registerBlock("netherrack_zircon_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)), ModCreativeModeTab.TUTORIAL_TAB);

    public static final RegistryObject<Block> ZIRCON_LAMP = registerBlock("zircon_lamp",
            () -> new ZirconLampBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(ZirconLampBlock.LIT) ? 15:0)),
            ModCreativeModeTab.TUTORIAL_TAB);

    public static final RegistryObject<Block> JUMPY_BLOCK = registerBlock("jumpy_block",
            () -> new JumpBlock(BlockBehaviour.Properties.of(Material.STONE).strength(6f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.TUTORIAL_TAB);

    public  static final RegistryObject<Block> BLUEBERRY_CROP = BLOCKS.register("blueberry_crop",
            () -> new BlueberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    public static final RegistryObject<LiquidBlock> SOAP_WATER_BLOCK = BLOCKS.register("soap_water_block",
            ()->new LiquidBlock(ModFluids.SOURCE_SOAP_WATER,BlockBehaviour.Properties.copy(Blocks.WATER)));// liquidBlock用于创建流体的方块。属性直接复制的water的属性。

    public static final RegistryObject<Block> GEM_INFUSING_STATION = registerBlock("gen_infusing_station",
            ()->new GemInfusingStationBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6F)
                    .requiresCorrectToolForDrops().noOcclusion()),ModCreativeModeTab.TUTORIAL_TAB);

    // 花朵
    // 药水的效果，持续时间5，
    public static final RegistryObject<Block> JASMINE = registerBlock("jasmine",
            () -> new FlowerBlock(MobEffects.GLOWING,5,BlockBehaviour.Properties.copy(Blocks.DANDELION)),
            ModCreativeModeTab.TUTORIAL_TAB);

    // 盆栽花
    public static final RegistryObject<Block> POTTED_JASMINE = BLOCKS.register("potted_jasmine",
            ()-> new FlowerPotBlock(()->((FlowerPotBlock) Blocks.FLOWER_POT),ModBlocks.JASMINE,
                    BlockBehaviour.Properties.copy(Blocks.DANDELION)));

    // 注册Block，由于需要注册Block以及该Block的Item所以这里写了一个函数用于同时注册Block和Item
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registryBlockItem(name,toReturn,tab);
        return toReturn;
    }
    ///  树
    public static final RegistryObject<Block> RED_MAPLE_LOG = registerBlock("red_maple_log",
            ()->new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)
                    .requiresCorrectToolForDrops()),ModCreativeModeTab.TUTORIAL_TAB);

    public static final RegistryObject<Block> RED_MAPLE_WOOD = registerBlock("red_maple_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .requiresCorrectToolForDrops()), ModCreativeModeTab.TUTORIAL_TAB);
    public static final RegistryObject<Block> STRIPPED_RED_MAPLE_LOG = registerBlock("stripped_red_maple_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)
                    .requiresCorrectToolForDrops()), ModCreativeModeTab.TUTORIAL_TAB);
    public static final RegistryObject<Block> STRIPPED_RED_MAPLE_WOOD = registerBlock("stripped_red_maple_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)
                    .requiresCorrectToolForDrops()), ModCreativeModeTab.TUTORIAL_TAB);

    public static final RegistryObject<Block> RED_MAPLE_PLANKS = registerBlock("red_maple_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .requiresCorrectToolForDrops()) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }
            }, ModCreativeModeTab.TUTORIAL_TAB);
    public static final RegistryObject<Block> RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                    .requiresCorrectToolForDrops()){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }
            }, ModCreativeModeTab.TUTORIAL_TAB);

    // 树苗
    public static final RegistryObject<Block> RED_MAPLE_SAPLING = registerBlock("red_maple_sapling",
            () -> new SaplingBlock(new RedMapleTreeGrower(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModCreativeModeTab.TUTORIAL_TAB);


    // 用于注册方块对应的物品，由于方块和物品是分开的，需要都注册，通过之前的ModItem.ITEMS的注册方法注册Item，使用BlockItem创建方块的Item
    private static <T extends Block>RegistryObject<Item> registryBlockItem(String name,RegistryObject<T> block,CreativeModeTab tab){
        return ModItem.ITEMS.register(name,()->new BlockItem(block.get(),new Item.Properties().tab(tab)));
    }
    // 用于给总线注册。
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
