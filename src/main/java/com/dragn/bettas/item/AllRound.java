package com.dragn.bettas.item;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.tank.TankTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AllRound extends Item {
    public AllRound() {
        super(new Properties().tab(BettasMain.BETTAS_TAB));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        TileEntity tile = world.getBlockEntity(context.getClickedPos());

        if(tile instanceof TankTile) {
            PlayerEntity player = context.getPlayer();
            player.getItemInHand(context.getHand()).shrink(1);

            ((TankTile) tile).slowGrowth();

            BlockPos pos = tile.getBlockPos();
            for(int i = 0; i < 7; i++) {
                double x = pos.getX() + (Math.random() * 2 - 1) + 0.5;
                double y = pos.getY() + (Math.random() * 2 - 1) + 0.5;
                double z = pos.getZ() + (Math.random() * 2 - 1) + 0.5;

                double dx = (Math.random() * 2 - 1);
                double dy = (Math.random() * 2 - 1);
                double dz = (Math.random() * 2 - 1);
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, dx, dy, dz);
            }
        }
        return ActionResultType.sidedSuccess(world.isClientSide);
    }
}
