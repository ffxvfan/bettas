package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TankTile extends TileEntity implements ITickableTileEntity {

    public static final byte CONNECTED_SOUTH = 0b1;
    public static final byte CONNECTED_WEST = 0b10;
    public static final byte CONNECTED_NORTH = 0b100;
    public static final byte CONNECTED_EAST = 0b1000;

    public static final VoxelShape NORTH = createBox(TankTileRenderer.NORTH_VERTS);
    public static final VoxelShape EAST = createBox(TankTileRenderer.EAST_VERTS);
    public static final VoxelShape SOUTH = createBox(TankTileRenderer.SOUTH_VERTS);
    public static final VoxelShape WEST = createBox(TankTileRenderer.WEST_VERTS);
    public static final VoxelShape BOTTOM = createBox(TankTileRenderer.BOTTOM_VERTS);

    private static final VoxelShape[] SHAPES = {SOUTH, WEST, NORTH, EAST};

    private static VoxelShape createBox(float[] v) {
        return VoxelShapes.box(v[0], v[1], v[2], v[3], v[4], v[5]);
    }



    public VoxelShape shape = VoxelShapes.or(NORTH, EAST, SOUTH, WEST, BOTTOM);
    public byte connected = 0b0000;

    public int algae = 0;

    //20 ticks per second -> 5 seconds
    private long threshold = 20 * 5;
    private long count = 0;

    public TankTile() {
        super(BettasMain.TANK_TILE.get());
    }

    @Override
    public void tick() {
        if(!this.level.isClientSide) {
            this.count++;

            if(this.count % this.threshold == 0) {
                this.incrementAlgae();
            }
        }
    }

    public void incrementAlgae() {
        this.algae = Math.min(4, this.algae + 1);
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void decrementAlgae() {
        this.algae = Math.max(0, this.algae - 1);
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void addConnected(Direction direction) {
        this.connected |= 1 << direction.get2DDataValue();
        this.shape = VoxelShapes.join(this.shape, SHAPES[direction.get2DDataValue()], IBooleanFunction.ONLY_FIRST);
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void removeConnected(Direction direction) {
        this.connected &= ~(1 << direction.get2DDataValue());
        this.shape = VoxelShapes.join(this.shape, SHAPES[direction.get2DDataValue()], IBooleanFunction.OR);
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        super.save(compoundNBT);
        compoundNBT.putInt("Algae", this.algae);
        compoundNBT.putByte("Connected", this.connected);
        return compoundNBT;
    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        super.load(state, compoundNBT);
        this.algae = compoundNBT.getInt("Algae");
        this.connected = compoundNBT.getByte("Connected");
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Algae", this.algae);
        nbt.putByte("Connected", this.connected);
        return new SUpdateTileEntityPacket(getBlockPos(), -1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT nbt = pkt.getTag();
        this.algae = nbt.getInt("Algae");
        this.connected = nbt.getByte("Connected");
    }
}
