package net.flandre923.tutorialmod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EightBallItem extends Item {
    public EightBallItem(Properties properties) {
        super(properties);
    }

    //重写了父类的use方法，这个方法是当玩家使用物品的时候调用。
    // level参数表示当前所处的世界。
    // player表示使用物品的玩家，
    // international表示使用物品的手
    // 判断如果是客户端环境使用并且使用的手是主手，则执行if的代码
    // 其中if的内容是获得一个随机数，并且经这个随机数发送给使用的玩家
    // 给玩家设置一个cooldowns的的冷却时间。
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            outputRandomNumber(player);
            player.getCooldowns().addCooldown(this,20);
        }
        return super.use(level, player, hand);
    }

    // 这段代码是给player的玩家发送一条信息。
    private void outputRandomNumber(Player player){
        player.sendSystemMessage(Component.literal("Your Number is " + getRandomNumber()));
    }
    // 这段代码是获得一个随机整数。0-9之间，
    // 其中RandomSource是Minecraft的一个随机数生成器。提供了很多随机数算法。
    private int getRandomNumber(){
        return RandomSource.createNewThreadLocalInstance().nextInt(10);
    }
}
