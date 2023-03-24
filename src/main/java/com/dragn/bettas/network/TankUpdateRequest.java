package com.dragn.bettas.network;

import com.dragn.bettas.tank.TankTile;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class TankUpdateRequest {

    private final String decorName;
    private final BlockPos pos;

    public TankUpdateRequest(String decorName, BlockPos pos) {
        this.decorName = decorName;
        this.pos = pos;
    }

    public static void encode(TankUpdateRequest msg, PacketBuffer buffer) {
        buffer.writeByteArray(msg.decorName.getBytes(StandardCharsets.UTF_8));
        buffer.writeInt(msg.pos.getX());
        buffer.writeInt(msg.pos.getY());
        buffer.writeInt(msg.pos.getZ());
    }

    public static TankUpdateRequest decode(PacketBuffer buffer) {
        String decorName = buffer.readUtf();
        BlockPos pos = new BlockPos(
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt()
        );
        return new TankUpdateRequest(decorName, pos);
    }

    public static void handle(TankUpdateRequest msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ((TankTile)Minecraft.getInstance().level.getBlockEntity(msg.pos)).decor.remove(msg.decorName);
        }));
        ctx.get().setPacketHandled(true);
    }
}
