package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.Decor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TankTile extends TileEntity implements ITickableTileEntity {

    public static final ModelProperty<Byte> CONNECTED = new ModelProperty<>();
    public static final ModelProperty<Integer> ALGAE = new ModelProperty<>();

    public static final VoxelShape NORTH = VoxelShapes.box(0, 0, 0, 1, 1, 0.03125f);
    public static final VoxelShape EAST = VoxelShapes.box(0.96875f, 0, 0, 1, 1, 1);
    public static final VoxelShape SOUTH = VoxelShapes.box(0, 0, 0.96875f, 1, 1, 1);
    public static final VoxelShape WEST = VoxelShapes.box(0, 0, 0, 0.03125f, 1, 1);
    public static final VoxelShape UP = VoxelShapes.box(0, 0, 0, 0, 0, 0);
    public static final VoxelShape DOWN = VoxelShapes.box(0, 0, 0, 1, 0.03125f, 1);

    // this needs to be the same order as the direction enum
    private static final VoxelShape[] SHAPES = {DOWN, UP, NORTH, SOUTH, WEST, EAST};

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

    public VoxelShape shape = VoxelShapes.or(NORTH, EAST, SOUTH, WEST, UP, DOWN);

    public byte connected = 0;
    public int algae = 0;

    // 24000 ticks in a minecraft day, algae increments every 3 days
    private final long threshold = 20 * 3;
    private long count = 0;

    public TankTile() {
        super(BettasMain.TANK_TILE.get());
    }

    @Override
    public void tick() {
        this.count++;

        if(this.threshold - this.count == 0) {
            this.incrementAlgae();
            this.count = 0;
        }
    }

    public void incrementAlgae() {
        this.algae = Math.min(4, this.algae + 1);
        this.setChanged();
        if(this.level.isClientSide) {
            ModelDataManager.requestModelDataRefresh(this);
        }
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public boolean decrementAlgae() {
        int prev = this.algae;
        this.algae = Math.max(0, this.algae - 1);
        boolean decremented = prev != this.algae;

        this.setChanged();
        if(this.level.isClientSide) {
            ModelDataManager.requestModelDataRefresh(this);
        }
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);

        return decremented;
    }

    public void addConnected(Direction direction) {
        this.connected |= 1 << direction.get3DDataValue();
        this.shape = VoxelShapes.join(this.shape, SHAPES[direction.get3DDataValue()], IBooleanFunction.ONLY_FIRST);
        this.setChanged();
        if(this.level.isClientSide) {
            ModelDataManager.requestModelDataRefresh(this);
        }
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void removeConnected(Direction direction) {
        this.connected &= ~(1 << direction.get3DDataValue());
        this.shape = VoxelShapes.join(this.shape, SHAPES[direction.get3DDataValue()], IBooleanFunction.OR);
        this.setChanged();
        if(this.level.isClientSide) {
            ModelDataManager.requestModelDataRefresh(this);
        }
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

    @Override
    public void setRemoved() {
        BlockPos pos = this.worldPosition.offset(0.5, 0.5, 0.5);
        decor.asStream().forEach(k -> {
            ItemStack itemStack = new ItemStack(Decor.DECOR_TO_ITEM.get(k.getBlock()));
            this.level.addFreshEntity(new ItemEntity(this.level, pos.getX(), pos.getY(), pos.getZ(), itemStack));
        });
        super.setRemoved();
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
        this.count = -threshold;
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

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(CONNECTED, this.connected)
                .withInitial(ALGAE, this.algae)
                .build();
    }
}
