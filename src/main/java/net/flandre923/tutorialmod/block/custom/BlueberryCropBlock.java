package net.flandre923.tutorialmod.block.custom;

import net.flandre923.tutorialmod.item.ModItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BlueberryCropBlock extends CropBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age",0,6);

    public BlueberryCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItem.BLUEBERRY_SEEDS.get();
    }



    // 获得当前的作物生长的阶段。
    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    // 获得作物最大的生长阶段。
    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(AGE);
//        super.createBlockStateDefinition(blockStateBuilder);
    }
}
