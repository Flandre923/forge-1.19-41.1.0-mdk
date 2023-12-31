package net.flandre923.tutorialmod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.flandre923.tutorialmod.TutorialMod;
import net.flandre923.tutorialmod.entity.ModEntityTypes;
import net.flandre923.tutorialmod.entity.client.ChomperRenderer;
import net.flandre923.tutorialmod.entity.custom.ChomperEntity;
import net.flandre923.tutorialmod.item.ModItem;
import net.flandre923.tutorialmod.networking.ModMessages;
import net.flandre923.tutorialmod.networking.packet.ThirstDataSyncS2CPacket;
import net.flandre923.tutorialmod.thirst.PlayerThirst;
import net.flandre923.tutorialmod.thirst.PlayerThirstProvider;
import net.flandre923.tutorialmod.villager.ModVillagers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID)
    private static class ForgeEvents {
        // 村民交易事件
        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event){
            // 如果会是铁匠职业
            if(event.getType() == VillagerProfession.TOOLSMITH){
                // 获得交易列表，int2ObjectMap对象，不同等级提供的交易项目
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                // 创一个ItemStack，EIGHT_BALL物品，1个
                ItemStack stack = new ItemStack(ModItem.EIGHT_BALL.get(),1);
                int villagerLevel = 1;
                //添加一个新的交易。获得的物品的是 stack，提供的物品是绿宝石，2个，10表示最大的交易次数，8表示交易返回经验值，0.02f表示价格乘区。
                trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD,2),
                        stack,10,8,0.02F
                ));
            }
            // 若果是JUMP_MASTER职业
            if(event.getType() == ModVillagers.JUMP_MASTER.get()) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItem.BLUEBERRY.get(),15);
                int villagerLevel =1 ;
                trades.get(villagerLevel).add((trader,rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD,5),
                        stack,10,8,0.02F
                ));
            }
        }


        /*
        这是一个给实体，方块实体，物品等添加能力的事件，
        Entity仅针对实体
         */
        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
            if(event.getObject() instanceof  Player){
                if(!event.getObject().getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()){
                    event.addCapability(new ResourceLocation(TutorialMod.MOD_ID,"properties"),new PlayerThirstProvider());
                }
            }
        }

        /*
        玩家死亡重生时候回调用此事件，从末地返回主世界时候会发生此事件
        死亡克隆为true，末地返回是false
         */
        @SubscribeEvent
        public static void onPlayCloned(PlayerEvent.Clone event){
            if(event.isWasDeath()){
                event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore->{
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
            event.register(PlayerThirst.class);
        }

        /*
        监听每个玩家每刻更新的动作，
        可以监听该事件做一个更新的操作。
        每秒20tick
        20 *  10 = 200 * 0.005 = 1
         */
        @SubscribeEvent
        public static void onPlayTick(TickEvent.PlayerTickEvent event){
            if (event.side == LogicalSide.SERVER) {
                event.player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst->{
                    if(thirst.getThirst() > 0 && event.player.getRandom().nextFloat() < 0.005f){ // 平均10s
                        thirst.subThirst(1);
                        event.player.sendSystemMessage(Component.literal("Subtracted Thirst"));
                        ModMessages.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()),(ServerPlayer) event.player);
                    }
                });
            }
        }

        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event){
            if(!event.getLevel().isClientSide){
                if(event.getEntity() instanceof ServerPlayer player){
                    player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                        ModMessages.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()),player);
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event){
            if(event.getEntity() instanceof Chicken){
                if(event.getSource().getEntity() instanceof Player player){
                    if(player.getMainHandItem().getItem() == Items.STICK){
                        player.sendSystemMessage(Component.literal(player.getName().getString() + "哎呦你干嘛啊"));
                        // 在受到攻击的鸡的位置生成一个小鸡
                        EntityType.CHICKEN.spawn((ServerLevel) player.level,null,null,event.getEntity().blockPosition(),
                                MobSpawnType.COMMAND,true,false);
                    }else{
                        player.sendSystemMessage(Component.literal("唱，跳，rap，篮球"));
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        //
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event){
            event.put(ModEntityTypes.CHOMPER.get(), ChomperEntity.setAttributes());
        }

    }


}
