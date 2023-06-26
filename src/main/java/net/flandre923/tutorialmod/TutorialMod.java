package net.flandre923.tutorialmod;

import com.mojang.logging.LogUtils;
import net.flandre923.tutorialmod.block.ModBlocks;
import net.flandre923.tutorialmod.item.ModItem;
import net.flandre923.tutorialmod.networking.ModMessages;
import net.flandre923.tutorialmod.painting.ModPaintings;
import net.flandre923.tutorialmod.villager.ModVillagers;
import net.flandre923.tutorialmod.world.feature.ModConfiguredFeatures;
import net.flandre923.tutorialmod.world.feature.ModPlacedFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TutorialMod.MOD_ID)
public class TutorialMod
{
    public static final String MOD_ID = "tutorialmod";
    private static final Logger LOGGER = LogUtils.getLogger();
    public TutorialMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItem.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModPaintings.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(()->{
            ModVillagers.registerPOIs();
        });

        ModMessages.register();
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
//            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLUEBERRY_CROP.get(),RenderType.cutout());
        }
    }
}
