package net.flandre923.tutorialmod.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.flandre923.tutorialmod.TutorialMod;
import net.flandre923.tutorialmod.block.ModBlocks;
import net.flandre923.tutorialmod.recipe.GemInfusingStationRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

// 自定义一个JEI的合成分类
public class GemInfusingStationRecipeCategory implements IRecipeCategory<GemInfusingStationRecipe> {
    // 区分合成分类的ID
    public static final ResourceLocation UID = new ResourceLocation(TutorialMod.MOD_ID,
            "gem_infusing");
    // png file texture
    public static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/gui/gem_infusing_station_gui.png");

    // 合成分类的背景图片
    private final IDrawable background;
    // 合成分类的图标
    private final IDrawable icon;

    // 构造方法
    public GemInfusingStationRecipeCategory(IGuiHelper helper){
        // 渲染背景图片。图片的开始位置和图片的结束的位置 u,v,width,height
        this.background  = helper.createDrawable(TEXTURE,0,0,176,85);
        // 图标
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ModBlocks.GEM_INFUSING_STATION.get()));

    }

    // 返回JEITutorialModPlugin定义的type
    @Override
    public RecipeType<GemInfusingStationRecipe> getRecipeType() {
        return JEITutorialModPlugin.INFUSION_TYPE;
    }

    // 合成界面的标题是什么
    @Override
    public Component getTitle() {
        return Component.literal("Gem Infusing Station");
    }
    //
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    // 添加合成表的输入slot和输出的slot
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GemInfusingStationRecipe recipe, IFocusGroup focuses) {
        // 在86,15的位置设置一个用于输入的slot，内容为recipe的ingredients的第0个。
        builder.addSlot(RecipeIngredientRole.INPUT,86,15).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,55,15)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getFluid()))
                        .setFluidRenderer(64000,false,16,61);
        // 在86,60的位置设置一个用于输出的slot，内容为recipe的合成结果。
        builder.addSlot(RecipeIngredientRole.OUTPUT,86,60).addItemStack(recipe.getResultItem());
    }
}
