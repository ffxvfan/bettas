package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

public class TankTile extends TileEntity implements ITickableTileEntity {

    public boolean substrate = false;

    public boolean heater = false;
    public Direction heaterDir = Direction.NORTH;

    public boolean filter = false;
    public Direction filterDir = Direction.NORTH;

    public boolean smallLog = false;
    public Direction smallLogDir = Direction.NORTH;

    public boolean bigLog = false;
    public Direction bigLogDir = Direction.NORTH;

    public boolean smallRock = false;
    public Direction smallRockDir = Direction.NORTH;

    public boolean mediumRock = false;
    public Direction mediumRockDir = Direction.NORTH;

    public boolean largeRock = false;
    public Direction largeRockDir = Direction.NORTH;

    public boolean seagrass = false;
    public Direction seagrassDir = Direction.NORTH;

    public boolean kelp = false;
    public Direction kelpDir = Direction.DOWN;

    private int count = 0;
    private boolean updated = false;

    public TankTile() {
        super(BettasMain.TANK_TILE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        compoundNBT.putBoolean("substrate", substrate);

        compoundNBT.putBoolean("heater", heater);
        compoundNBT.putInt("heater_dir", heaterDir.ordinal());

        compoundNBT.putBoolean("filter", filter);
        compoundNBT.putInt("filter_dir", filterDir.ordinal());

        compoundNBT.putBoolean("small_log", smallLog);
        compoundNBT.putInt("small_log_dir", smallLogDir.ordinal());

        compoundNBT.putBoolean("big_log", bigLog);
        compoundNBT.putInt("big_log_dir", bigLogDir.ordinal());

        compoundNBT.putBoolean("small_rock", smallRock);
        compoundNBT.putInt("small_rock_dir", smallRockDir.ordinal());

        compoundNBT.putBoolean("medium_rock_dir", mediumRock);
        compoundNBT.putInt("medium_rock_dir", mediumRockDir.ordinal());

        compoundNBT.putBoolean("large_rock", largeRock);
        compoundNBT.putInt("large_rock_dir", largeRockDir.ordinal());

        compoundNBT.putBoolean("seagrass", seagrass);
        compoundNBT.putInt("seagrass_dir", seagrassDir.ordinal());

        compoundNBT.putBoolean("kelp", kelp);
        compoundNBT.putInt("kelp_dir", kelpDir.ordinal());

        return super.save(compoundNBT);
    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        substrate = compoundNBT.getBoolean("substrate");

        heater = compoundNBT.getBoolean("heater");
        heaterDir = Direction.values()[compoundNBT.getInt("heater_dir")];

        filter = compoundNBT.getBoolean("filter");
        filterDir = Direction.values()[compoundNBT.getInt("filter_dir")];

        smallLog = compoundNBT.getBoolean("small_log");
        smallLogDir = Direction.values()[compoundNBT.getInt("small_log_dir")];

        bigLog = compoundNBT.getBoolean("big_log");
        bigLogDir = Direction.values()[compoundNBT.getInt("big_log_dir")];

        smallRock = compoundNBT.getBoolean("small_rock");
        smallRockDir = Direction.values()[compoundNBT.getInt("small_rock_dir")];

        mediumRock = compoundNBT.getBoolean("medium_rock");
        mediumRockDir = Direction.values()[compoundNBT.getInt("medium_rock_dir")];

        largeRock = compoundNBT.getBoolean("large_rock");
        largeRockDir = Direction.values()[compoundNBT.getInt("large_rock_dir")];

        seagrass = compoundNBT.getBoolean("seagrass");
        seagrassDir = Direction.values()[compoundNBT.getInt("seagrass_dir")];

        kelp = compoundNBT.getBoolean("kelp");
        kelpDir = Direction.values()[compoundNBT.getInt("kelp_dir")];

        super.load(state, compoundNBT);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putBoolean("substrate", substrate);

        compoundNBT.putBoolean("heater", heater);
        compoundNBT.putInt("heater_dir", heaterDir.ordinal());

        compoundNBT.putBoolean("filter", filter);
        compoundNBT.putInt("filter_dir", filterDir.ordinal());

        compoundNBT.putBoolean("small_log", smallLog);
        compoundNBT.putInt("small_log_dir", smallLogDir.ordinal());

        compoundNBT.putBoolean("big_log", bigLog);
        compoundNBT.putInt("big_log_dir", bigLogDir.ordinal());

        compoundNBT.putBoolean("small_rock", smallRock);
        compoundNBT.putInt("small_rock_dir", smallRockDir.ordinal());

        compoundNBT.putBoolean("medium_rock", mediumRock);
        compoundNBT.putInt("medium_rock_dir", mediumRockDir.ordinal());

        compoundNBT.putBoolean("large_rock", largeRock);
        compoundNBT.putInt("large_rock_dir", largeRockDir.ordinal());

        compoundNBT.putBoolean("seagrass", seagrass);
        compoundNBT.putInt("seagrass_dir", seagrassDir.ordinal());

        compoundNBT.putBoolean("kelp", kelp);
        compoundNBT.putInt("kelp_dir", kelpDir.ordinal());

        return new SUpdateTileEntityPacket(worldPosition, -1, compoundNBT);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT compoundNBT = pkt.getTag();
        substrate = compoundNBT.getBoolean("substrate");

        heater = compoundNBT.getBoolean("heater");
        heaterDir = Direction.values()[compoundNBT.getInt("heater_dir")];

        filter = compoundNBT.getBoolean("filter");
        filterDir = Direction.values()[compoundNBT.getInt("filter_dir")];

        smallLog = compoundNBT.getBoolean("small_log");
        smallLogDir = Direction.values()[compoundNBT.getInt("small_log_dir")];

        bigLog = compoundNBT.getBoolean("big_log");
        bigLogDir = Direction.values()[compoundNBT.getInt("big_log_dir")];

        smallRock = compoundNBT.getBoolean("small_rock");
        smallRockDir = Direction.values()[compoundNBT.getInt("small_rock_dir")];

        mediumRock = compoundNBT.getBoolean("medium_rock");
        mediumRockDir = Direction.values()[compoundNBT.getInt("medium_rock_dir")];

        largeRock = compoundNBT.getBoolean("large_rock");
        largeRockDir = Direction.values()[compoundNBT.getInt("large_rock_dir")];

        seagrass = compoundNBT.getBoolean("seagrass");
        seagrassDir = Direction.values()[compoundNBT.getInt("seagrass_dir")];

        kelp = compoundNBT.getBoolean("kelp");
        kelpDir = Direction.values()[compoundNBT.getInt("kelp_dir")];
    }

    @Override
    public void tick() {
        if(!updated) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), -1);
            updated = true;
        }

        count++;
    }


}
