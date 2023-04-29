package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.Decor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.*;

public class TankTile extends TileEntity implements ITickableTileEntity {

    public LinkedHashMap<String, Direction> decor = new LinkedHashMap<>();

    public TankTile() {
        super(BettasMain.TANK_TILE.get());
    }


    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        CompoundNBT decorNBT = new CompoundNBT();
        decor.forEach((name, direction) -> decorNBT.putInt(name, direction.ordinal()));
        compoundNBT.put("decor", decorNBT);
        return super.save(compoundNBT);
    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        CompoundNBT decorNBT = compoundNBT.getCompound("decor");
        for(String decorName : decorNBT.getAllKeys()) {
            decor.put(decorName, Direction.values()[decorNBT.getInt(decorName)]);
        }
        super.load(state, compoundNBT);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT decorNBT = new CompoundNBT();
        decor.forEach((name, direction) -> decorNBT.putInt(name, direction.ordinal()));
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("decor", decorNBT);
        return new SUpdateTileEntityPacket(worldPosition, -1, compoundNBT);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT compoundNBT = pkt.getTag();

        CompoundNBT decorNBT = compoundNBT.getCompound("decor");
        for(String decorName : decorNBT.getAllKeys()) {
            decor.put(decorName, Direction.values()[decorNBT.getInt(decorName)]);
        }
    }

    @Override
    public void setRemoved() {
        if(!level.isClientSide) {
            decor.forEach((name, direction) -> level.addFreshEntity(new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, Tank.decorMap(name))));
        }
        super.setRemoved();
    }

    @Override
    public void tick() {
        if(!level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
}
