package net.flandre923.tutorialmod.networking.packet;

import net.flandre923.tutorialmod.client.ClientThirstData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ThirstDataSyncS2CPacket {
    public final int thirst;

    public ThirstDataSyncS2CPacket(int thirst){
        this.thirst = thirst;
    }

    public ThirstDataSyncS2CPacket(FriendlyByteBuf buf){
        this.thirst = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(this.thirst);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            // 此处在服务器
            ClientThirstData.set(thirst);
        });
        return true;
    }
}
