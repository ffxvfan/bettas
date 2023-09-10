package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.Decor;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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


    // don't ask
    private static final class OrderedSet {
        private final Map<BlockState, BlockState> map = new HashMap<>();
        private BlockState last;

        public void add(BlockState state) {
            map.put(state, last);
            last = state;
        }

        public BlockState remove() {
            BlockState ret = last;
            last = map.remove(ret);
            return ret;
        }

        public CompoundNBT asNBT() {
            CompoundNBT nbt = new CompoundNBT();
            map.keySet().forEach(k -> {
                String name = k.toString();
                Direction direction = k.getValue(Decor.FACING);
                nbt.putInt(name, direction.ordinal());
            });
            return nbt;
        }

        public void fromNBT(CompoundNBT nbt) {
            map.clear();
            last = null;

            nbt.getAllKeys().forEach(k -> {
                Decor block = Decor.NAME_TO_DECOR.get(k);
                Direction direction = Direction.values()[nbt.getInt(k)];

                BlockState state = block.facing(direction);
                map.put(state, last);
                last = state;
            });
        }

        public Stream<BlockState> asStream() {
            return map.keySet().stream();
        }
    }

    private final OrderedSet decor = new OrderedSet();

    public VoxelShape shape = VoxelShapes.or(NORTH, EAST, SOUTH, WEST, BOTTOM);
    public byte connected = 0b0000;

    public int algae = 0;

    // 24000 ticks in a minecraft day, algae increments every 3 days
    private long threshold = 20  * 3;
    private long count = 0;

    public TankTile() {
        super(BettasMain.TANK_TILE.get());
    }

    @Override
    public void tick() {
        if(!this.level.isClientSide) {
            this.count++;

            if(this.threshold - this.count == 0) {
                this.incrementAlgae();
                this.count = 0;
            }
        }
    }

    public void incrementAlgae() {
        this.algae = Math.min(4, this.algae + 1);
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public boolean decrementAlgae() {
        int prev = this.algae;
        this.algae = Math.max(0, this.algae - 1);
        boolean decremented = prev != this.algae;

        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);

        return decremented;
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

    public ItemStack removeDecor() {
        BlockState state = this.decor.remove();
        if(state != null) {
            this.setChanged();
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            return new ItemStack(Decor.DECOR_TO_ITEM.get(state.getBlock()));
        }
        return null;
    }

    public void addDecor(Item item, Direction direction) {
        Decor block = Decor.ITEM_TO_DECOR.get(item);
        this.decor.add(block.facing(direction));
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public Stream<BlockState> allDecor() {
        return this.decor.asStream();
    }

    public void slowGrowth() {
        this.count = 0;
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        super.save(compoundNBT);
        compoundNBT.putInt("Algae", this.algae);
        compoundNBT.putByte("Connected", this.connected);
        compoundNBT.put("Decor", decor.asNBT());
        return compoundNBT;
    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        super.load(state, compoundNBT);
        this.algae = compoundNBT.getInt("Algae");
        this.connected = compoundNBT.getByte("Connected");
        this.decor.fromNBT(compoundNBT.getCompound("Decor"));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Algae", this.algae);
        nbt.putByte("Connected", this.connected);
        nbt.put("Decor", this.decor.asNBT());
        return new SUpdateTileEntityPacket(getBlockPos(), -1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT nbt = pkt.getTag();
        this.algae = nbt.getInt("Algae");
        this.connected = nbt.getByte("Connected");
        this.decor.fromNBT(nbt.getCompound("Decor"));
    }
}
