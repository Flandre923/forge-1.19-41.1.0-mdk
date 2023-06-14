package net.flandre923.tutorialmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    // 创建一个新的tab，设置name
    public static final CreativeModeTab TUTORIAL_TAB = new CreativeModeTab("tutorialtab") {
        // 设置tab上的图标的显示图片
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItem.ZIRCON.get());
        }
    };

    // 还需要加入新的tab的话 依旧可以在下面继续输入
}
