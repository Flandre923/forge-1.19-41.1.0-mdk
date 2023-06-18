package net.flandre923.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ZirconLampBlock extends Block {
    // 表示布尔类型的属性，有true和false两种状态。
    // LIT为true时候发出信号
    public static final BooleanProperty LIT  = BooleanProperty.create("lit");
    public ZirconLampBlock(Properties properties) {
        super(properties);
    }

    // 玩家右键点击时候调用
    // 检查是否是客户端，确保在服务器执行，检查是否是主手。
    // 调用setBlock方法更新状态，其中cycle的作用是将属性值进行取反，即true变为false，false变为true
    // 3 表示更新方块的标志，表示更新方块本身和周围的方块，同时触发更新。？
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            level.setBlock(pos,state.cycle(LIT),3);
        }
        return super.use(state, level, pos, player, hand, result);
    }

    // 该方法用于定义方块的状态，存放的属性和可取值。
    // 将LIT属性添加到放开的定义的状态中。
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(LIT);
        super.createBlockStateDefinition(blockStateBuilder);
    }
}
