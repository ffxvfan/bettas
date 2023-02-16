package com.dragn.bettas.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SOpenBettaGuidebook {

    public Hand hand;

    public SOpenBettaGuidebook(Hand hand) {
        this.hand = hand;
    }

    public static void encode(SOpenBettaGuidebook sOpenBettaGuidebook, PacketBuffer buffer) {
        buffer.writeEnum(sOpenBettaGuidebook.hand);
    }

    public static SOpenBettaGuidebook decode(PacketBuffer buffer) {
        return new SOpenBettaGuidebook(buffer.readEnum(Hand.class));
    }

    public static void handle(SOpenBettaGuidebook sOpenBettaGuidebook, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, (DistExecutor.SafeSupplier<DistExecutor.SafeRunnable>)() -> new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                ItemStack itemStack = Minecraft.getInstance().player.getItemInHand(sOpenBettaGuidebook.hand);
                Minecraft.getInstance().setScreen(new ReadBookScreen(new ReadBookScreen.WrittenBookInfo(itemStack)));
            }
        }));
        ctx.get().setPacketHandled(true);
    }



}
