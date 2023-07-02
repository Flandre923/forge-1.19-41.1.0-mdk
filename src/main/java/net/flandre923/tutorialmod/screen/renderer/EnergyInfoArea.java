package net.flandre923.tutorialmod.screen.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;


public class EnergyInfoArea extends InfoArea {
    private final IEnergyStorage energy;

    public EnergyInfoArea(int  xMin,int yMin) {
        this(xMin,yMin,null,8,64);
    }

    public EnergyInfoArea(int xMin,int yMin,IEnergyStorage energy){
        this(xMin,yMin,energy,8,64);
    }

    public EnergyInfoArea(int xMin,int yMin,IEnergyStorage storage,int width,int height){
        super(new Rect2i(xMin,yMin,width,height));
        this.energy  = storage;
    }

    public List<Component> getTooltips(){
        return List.of(Component.literal(energy.getEnergyStored() + "/" +energy.getMaxEnergyStored() + "FE"));
    }

    @Override
    public void draw(PoseStack transform) {
        final int height = this.area.getHeight();
        int stored = (int)(height*(energy.getEnergyStored() /(float)energy.getMaxEnergyStored()));
        fillGradient(
                transform,
                area.getX(),area.getY() + (height - stored),
                area.getX()+area.getWidth(),area.getY()+area.getHeight(),
                0xffb51590,0xff600b00
        );
    }
}