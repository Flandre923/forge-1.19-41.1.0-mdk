package net.flandre923.tutorialmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.flandre923.tutorialmod.TutorialMod;
import net.flandre923.tutorialmod.screen.renderer.EnergyInfoArea;
import net.flandre923.tutorialmod.screen.renderer.FluidTankRenderer;
import net.flandre923.tutorialmod.util.MouseUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;

import java.util.Optional;

public class GemInfusingStationScreen extends AbstractContainerScreen<GemInfusingStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TutorialMod.MOD_ID,"textures/gui/gem_infusing_station_gui.png");
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;

    public GemInfusingStationScreen(GemInfusingStationMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(64000,true,16,61);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x+156 , y+13,menu.entity.getEnergyStorage());
    }

    @Override
    protected void renderLabels(PoseStack poseStack,int pMouseX, int pMouseY){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(poseStack,pMouseX,pMouseY,x,y);
        renderFluidAreaTooltips(poseStack,pMouseX,pMouseY,x,y);
    }

    private void renderFluidAreaTooltips(PoseStack poseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX,pMouseY,x,y,55,15)){
            renderTooltip(poseStack,renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    Optional.empty(),pMouseX-x,pMouseY-y);
        }
    }


    private void renderEnergyAreaTooltips(PoseStack poseStack, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX,pMouseY,x,y,156,13,8,64)) {
            renderTooltip(poseStack,energyInfoArea.getTooltips(),
                    Optional.empty(),pMouseX -x ,pMouseY-y);
        }
    }

    private boolean isMouseAboveArea(double pMouseX, double pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX,pMouseY,x+offsetX,y+offsetY,width,height);
    }

    private boolean isMouseAboveArea(double pMouseX,int pMouseY,int x,int y,int offsetX,int offsetY){
        return MouseUtil.isMouseOver(pMouseX,pMouseY,x+offsetX,y+offsetY,renderer.getWidth(),renderer.getHeight());
    }


    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pPoseStack, x, y);
        energyInfoArea.draw(pPoseStack);
        renderer.render(pPoseStack,x+55,y+15,menu.getFluidStack());
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()) {
            blit(pPoseStack, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
