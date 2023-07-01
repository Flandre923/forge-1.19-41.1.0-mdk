package net.flandre923.tutorialmod.block.entity;

import net.flandre923.tutorialmod.block.custom.GemInfusingStationBlock;
import net.flandre923.tutorialmod.item.ModItem;
import net.flandre923.tutorialmod.recipe.GemInfusingStationRecipe;
import net.flandre923.tutorialmod.screen.GemInfusingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class GemInfusingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        // 判断对应的slot是否可以放入物品。
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() == ModItem.ZIRCON.get();
                case 1 -> stack.getItem() == ModItem.RAW_ZIRCON.get();
                case 2 -> false;
                default -> super.isItemValid(slot,stack);
            };
        }

    };// 3 表示机器中的3个槽位


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    // 方向玩家朝向是北方向（相对于玩家的朝向定位，不是世界里面的方向）默认方向，
    // 输出对应的slot,如果是true可以输出，如果是false不能输出
    // 输入对应的slot，如果是true则可以插入内容，如果是false则不能插入内容
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (index) -> index == 1,
                            (index, stack) -> itemStackHandler.isItemValid(1, stack))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> i == 1,
                            (index, stack) -> itemStackHandler.isItemValid(1, stack))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (index) -> index == 0 || index == 1,
                            (index, stack) -> itemStackHandler.isItemValid(0, stack) || itemStackHandler.isItemValid(1, stack))));


    public GemInfusingStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.GEM_INFUSION_STATION.get(), blockPos, blockState);
        // 返回和设置合成进度的数据
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0-> GemInfusingStationBlockEntity.this.progress;
                    case 1->GemInfusingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0->GemInfusingStationBlockEntity.this.progress = value;
                    case 1->GemInfusingStationBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Gem Infusing Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new GemInfusingStationMenu(id,inventory,this,this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if(cap == ForgeCapabilities.ITEM_HANDLER){
            if(side == null ){
                return lazyItemHandler.cast();
            }

            if(directionWrappedHandlerMap.containsKey(side)){
                Direction localDirection = this.getBlockState().getValue(GemInfusingStationBlock.FACING);

                if(side == Direction.UP || side == Direction.DOWN){
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDirection){
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };

            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()->itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory",itemStackHandler.serializeNBT());
        nbt.putInt("gem_infusing_station.progress",this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("gem_infusing_station.progress");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for(int i = 0;i<itemStackHandler.getSlots();i++){
            inventory.setItem(i,itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, GemInfusingStationBlockEntity entity) {
        if (level.isClientSide){
            return;
        }
        if(hasRecipe(entity)){
            entity.progress ++ ;
            setChanged(level,blockPos,state);

            if(entity.progress >= entity.maxProgress){
                craftItem(entity);
            }
        }else{
            entity.resetProgress();
            setChanged(level,blockPos,state);
        }


    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(GemInfusingStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getSlots());
        for(int i=0;i < entity.itemStackHandler.getSlots() ; i++){
            inventory.setItem(i,entity.itemStackHandler.getStackInSlot(i));
        }
        Optional<GemInfusingStationRecipe> recipe = level.getRecipeManager().getRecipeFor(GemInfusingStationRecipe.Type.INSTANCE,inventory,level);

        if(hasRecipe(entity)){
            entity.itemStackHandler.extractItem(1,1,false);
            entity.itemStackHandler.setStackInSlot(2,new ItemStack(recipe.get().getResultItem().getItem(),
                    entity.itemStackHandler.getStackInSlot(2).getCount() + 1));
            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(GemInfusingStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getSlots());
        for(int i=0;i<entity.itemStackHandler.getSlots();i++){
            inventory.setItem(i,entity.itemStackHandler.getStackInSlot(i));
        }
        Optional<GemInfusingStationRecipe> recipe = level.getRecipeManager().getRecipeFor(GemInfusingStationRecipe.Type
                .INSTANCE,inventory,level);

        return recipe.isPresent() && canInsertAmountInToOutputSlot(inventory)&&
                canInsertItemToOutputSlot(inventory,recipe.get().getResultItem());
    }

    private static boolean canInsertItemToOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountInToOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
}
