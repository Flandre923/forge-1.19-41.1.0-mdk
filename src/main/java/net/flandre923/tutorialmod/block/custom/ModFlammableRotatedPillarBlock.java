package net.flandre923.tutorialmod.block.custom;


import net.flandre923.tutorialmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

//用于创建像石英柱和原木那样的可以旋转的方块。
public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {
    public ModFlammableRotatedPillarBlock(Properties pProperties) {
        super(pProperties);
    }

    // 是否能够起火
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }
    // 返回点燃的能力
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }
    // 火蔓延的速度
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    // 方块被特定的工具右键时候发生状态的变化方法,返回新的状态。
    // context 是上下文文本信息。
    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem ){
            if(state.is(ModBlocks.RED_MAPLE_LOG.get())){
                return ModBlocks.STRIPPED_RED_MAPLE_WOOD.get().defaultBlockState().setValue(AXIS,
                        state.getValue(AXIS));
            }

            if(state.is(ModBlocks.RED_MAPLE_WOOD.get())){
                return ModBlocks.STRIPPED_RED_MAPLE_WOOD.get().defaultBlockState().setValue(AXIS,
                        state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}

