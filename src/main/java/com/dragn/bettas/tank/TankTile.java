package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.Decor;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.Stack;

public class TankTile extends TileEntity implements ITickableTileEntity {

    public final Stack<Decor> decor = new Stack<>();

    public TankTile() {
        super(BettasMain.TANK_TILE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        CompoundNBT decorNBT = new CompoundNBT();
        while(!decor.empty()) {
            Decor d = decor.pop();
            decorNBT.putInt(d.name, d.getFacing());
        }
        compoundNBT.put("decor", decorNBT);
        return super.save(compoundNBT);
    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        CompoundNBT decorNBT = compoundNBT.getCompound("decor");
        for(String decorName : decorNBT.getAllKeys()) {
            Decor d = new Decor(decorName, decorNBT.getInt(decorName));
            decor.push(d);
        }
        super.load(state, compoundNBT);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT decorNBT = new CompoundNBT();
        while(!decor.empty()) {
            Decor d = decor.pop();
            decorNBT.putInt(d.name, d.getFacing());
        }

        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("decor", decorNBT);
        return new SUpdateTileEntityPacket(worldPosition, -1, compoundNBT);

    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT compoundNBT = pkt.getTag();

        CompoundNBT decorNBT = compoundNBT.getCompound("decor");
        for(String decorName : decorNBT.getAllKeys()) {
            Decor d = new Decor(decorName, decorNBT.getInt(decorName));
            decor.push(d);
        }
    }

    @Override
    public void tick() {

    }
}
