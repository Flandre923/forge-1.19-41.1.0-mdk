package net.flandre923.tutorialmod.villager;

import com.google.common.collect.ImmutableSet;
import net.flandre923.tutorialmod.TutorialMod;
import net.flandre923.tutorialmod.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class ModVillagers {
    // 注册一个POI类型，POI用于定义一些特殊的方块。可以被村民感知和利用，例如床，工作台等。
    public static final DeferredRegister<PoiType> POT_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, TutorialMod.MOD_ID);
    // 注册事件，用于注册村民的职业
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS,TutorialMod.MOD_ID);

    // 注册一个POI类型，名字是jumpy_block_poi，对应的方块是之前的JUMPY_BLOCK，最大数目是1，最远距离是1,
    // 用于村民感知该方块
    // 其中ImmutableSet用于创建一个不可变的集合。其中getStateDefinition获得方块的属性和值。getPossibleStates返回所有的状态的可能性。
    // 最大数目指的是最多可以被多少村民使用，最远距离是村民可以隔离该方块多远可以工作。
    public static final RegistryObject<PoiType> JUMPY_BLOCK_POI = POT_TYPES.register("jumpy_block_poi",
            ()->new PoiType(ImmutableSet.copyOf(ModBlocks.JUMPY_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1,1));
    // 注册一个村民的职业
    // 名称是jumpy_master，
    // pot表示了工作的方块
    // 最后一个表示工作的发出的声音
    public static final RegistryObject<VillagerProfession> JUMP_MASTER = VILLAGER_PROFESSION.register("jumpy_master",
            () -> new VillagerProfession("jumpy_master",x->x.get() == JUMPY_BLOCK_POI.get(),
                    x -> x.get() == JUMPY_BLOCK_POI.get(),ImmutableSet.of(),ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_ARMORER));


    public static void registerPOIs(){
        try{
            // 获得PoiType类的一个registerBlockStates方法,通过invoke调用将JUMPY_BLOCK_POI添加到poiType类中。
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates",PoiType.class).invoke(null,JUMPY_BLOCK_POI.get());
        }catch (InvocationTargetException | IllegalAccessException exception){
            exception.printStackTrace();
        }
    }


    public static void register(IEventBus eventBus){
        POT_TYPES.register(eventBus);
        VILLAGER_PROFESSION.register(eventBus);
    }
}
