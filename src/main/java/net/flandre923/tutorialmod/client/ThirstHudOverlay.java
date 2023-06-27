package net.flandre923.tutorialmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.flandre923.tutorialmod.TutorialMod;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ThirstHudOverlay {
    private static final ResourceLocation FILLED_THIRST = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/thirst/filled_thirst.png");
    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/thirst/empty_thirst.png");


    /*
    gui 游戏界面
    poseStack矩阵堆栈对象，用于缩放和变化
    帧间隔用于做平滑处理
    width 宽度
    height 高度
     */
    public static final IGuiOverlay HUD_THIRST = ((gui, poseStack, partialTick, width, height) -> {
        int x = width/2;
        int y = height;
        /*
        RenderSystem类是一个管理渲染的类，提供了静态的方法设置着色器，颜色，纹理，混合模式，裁剪区域。
        setShader 设置当前的shader为位置的shader
        setShaderColor 设置shader的color RGB 透明度
        setShaderTexture 指定shader的第一个Textures为EMPTY_THIRST
         */
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,EMPTY_THIRST);
         /*
         其中GuiComponent类是绘制游戏界面的类，可以绘制进度条，按钮，等界面元素。
         blit方法参数分别是以下的意思，
         绘制的位置坐标x
         绘制的位置坐标y
         绘制的textrues中的u坐标，
         绘制的textures中的v坐标
         绘制的width宽度
         绘制的height高度
         绘制的textrues的宽度
         绘制的textures的高度
          */
        for(int i=0;i<10;i++){
            GuiComponent.blit(poseStack,x-94 + (i * 9),y-54,0,0,12,12,12,12);
        }

        RenderSystem.setShaderTexture(0,FILLED_THIRST);
        for(int i=0;i<10;i++){
            if(ClientThirstData.playerThirst > i){
                GuiComponent.blit(poseStack,x-94+(i*9),y-54,0,0,12,12,12,12);
            }else{
                break;
            }
        }

    });

}
