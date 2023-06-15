package net.flandre923.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class JumpBlock extends Block {

    public JumpBlock(Properties properties) {
        super(properties);
    }


    // 该方法在玩家右键方块调用，其中参数有方块的状态，方块的世界，方块的位置，使用方块的玩家，玩家使用的左手还是右手，方块的碰撞的结果
    // 当玩家右键之后会向右键的玩家发送一条消息。
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
//        if(level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            player.sendSystemMessage(Component.literal("Right Clicked this"));
//        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);

    }
    // 该方法当有实体站在方块上时候调用
    // 参数是传入的世界，方块的位置，方块的状态，在方块的上的实体
    // 这里判断站上去的实体是否是一个活着的实体，即可移动的，例如玩家和怪物都是该实体下的对象。具体可以看该类的继承关系。
    // 这里如果是livingEntity就给该实体加上一个跳跃的药水效果。
    // 其中MobEffectInstance类是实体身上的药水的效果类。作用是给实体药水的效果。
    // MobEffects类定义了各个药水的效果。200代表的是持续时间
    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if(entity instanceof LivingEntity livingEntity){
            livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP,200));
        }
        super.stepOn(level, blockPos, blockState, entity);
    }
}
