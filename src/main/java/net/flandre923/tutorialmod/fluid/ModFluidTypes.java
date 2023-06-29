package net.flandre923.tutorialmod.fluid;

import com.mojang.math.Vector3f;
import net.flandre923.tutorialmod.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still"); // 静水纹理
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");// 流动水纹理
    public static final ResourceLocation SOAP_OVERLAY_RL = new ResourceLocation(TutorialMod.MOD_ID,"misc/in_soap_water");// 纹理

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES,TutorialMod.MOD_ID);

    public static final RegistryObject<FluidType> SOAP_WATER_FLUID_TYPE = register("soap_water_fluid",
            FluidType.Properties.create().lightLevel(2).density(15).viscosity(5).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK));//亮度，密度，粘性，

    private static RegistryObject<FluidType> register(String name,FluidType.Properties properties){
        /**
         * 1-3 参数是贴图，tinitColor是液体颜色，ARGB,指的是在远处时候显示的样子
         */
        return FLUID_TYPES.register(name,
                ()->new BaseFluidType(WATER_STILL_RL,WATER_FLOWING_RL,SOAP_OVERLAY_RL,
                        0xA1E038D0,new Vector3f(224f/225f,56f/225f,208f/255f),properties));

    }
    public static void register(IEventBus eventBus){
        FLUID_TYPES.register(eventBus);
    }
}
