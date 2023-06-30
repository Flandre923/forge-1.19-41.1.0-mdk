package net.flandre923.tutorialmod.screen;

import net.flandre923.tutorialmod.TutorialMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.*;
import java.awt.*;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, TutorialMod.MOD_ID);

    public static final RegistryObject<MenuType<GemInfusingStationMenu>> GEM_INFUSING_STATION_MENU =
            registerMenuType(GemInfusingStationMenu::new,"gem_infusing_station_menu");
    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name){
        return MENU_TYPES.register(name,()-> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus){
        MENU_TYPES.register(eventBus);
    }
}
