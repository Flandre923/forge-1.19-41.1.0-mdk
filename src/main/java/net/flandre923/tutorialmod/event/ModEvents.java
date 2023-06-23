package net.flandre923.tutorialmod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.flandre923.tutorialmod.TutorialMod;
import net.flandre923.tutorialmod.item.ModItem;
import net.flandre923.tutorialmod.villager.ModVillagers;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModEvents {
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
}
