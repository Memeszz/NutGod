package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * @author hub
 * @since 2019-11-29
 */
@Module.Info(name = "LegCrystals", category = Module.Category.COMBAT)
public class LegCrystals extends Module {

    private Setting<Double> range = register(Settings.doubleBuilder("Range").withMinimum(1.0).withValue(5.5).withMaximum(10.0).build());

    // we need this cooldown to not place from old hotbar slot, before we have switched to crystals.
    // otherwise we will attempt to click with whatever was in the last slot.
    private boolean switchCooldown = false;

    @Override
    public void onUpdate() {

        if (mc.player == null) {
            return;
        }

        int crystalSlot = -1;
        if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            crystalSlot = mc.player.inventory.currentItem;
        } else {
            for (int slot = 0; slot < 9; slot++) {
                if (mc.player.inventory.getStackInSlot(slot).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = slot;
                    break;
                }
            }
        }

        if (crystalSlot == -1) {
            return;
        }

        EntityPlayer closestTarget = findClosestTarget();

        if (closestTarget == null) {
            return;
        }

        Vec3d targetVector = findPlaceableBlock(closestTarget.getPositionVector().add(0, -1, 0));

        if (targetVector == null) {
            return;
        }

        BlockPos targetBlock = new BlockPos(targetVector);

        if (mc.player.inventory.currentItem != crystalSlot) {
            mc.player.inventory.currentItem = crystalSlot;
            switchCooldown = true;
            return;
        }

        // return after we did an autoswitch
        if (switchCooldown) {
            switchCooldown = false;
            return;
        }

        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(targetBlock, EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));

    }

    private Vec3d findPlaceableBlock(Vec3d startPos) {

        // NORTH
        if (canPlaceCrystal(startPos.add(Offsets.NORTH2)) && !isExplosionProof(startPos.add(Offsets.NORTH1).add(0, 1, 0))) {
            return startPos.add(Offsets.NORTH2);
        } else if (canPlaceCrystal(startPos.add(Offsets.NORTH1))) {
            return startPos.add(Offsets.NORTH1);
        }

        // EAST
        if (canPlaceCrystal(startPos.add(Offsets.EAST2)) && !isExplosionProof(startPos.add(Offsets.EAST1).add(0, 1, 0))) {
            return startPos.add(Offsets.EAST2);
        } else if (canPlaceCrystal(startPos.add(Offsets.EAST1))) {
            return startPos.add(Offsets.EAST1);
        }

        // SOUTH
        if (canPlaceCrystal(startPos.add(Offsets.SOUTH2)) && !isExplosionProof(startPos.add(Offsets.SOUTH1).add(0, 1, 0))) {
            return startPos.add(Offsets.SOUTH2);
        } else if (canPlaceCrystal(startPos.add(Offsets.SOUTH1))) {
            return startPos.add(Offsets.SOUTH1);
        }

        // WEST
        if (canPlaceCrystal(startPos.add(Offsets.WEST2)) && !isExplosionProof(startPos.add(Offsets.WEST1).add(0, 1, 0))) {
            return startPos.add(Offsets.WEST2);
        } else if (canPlaceCrystal(startPos.add(Offsets.WEST1))) {
            return startPos.add(Offsets.WEST1);
        }

        return null;

    }

    private EntityPlayer findClosestTarget() {

        EntityPlayer closestTarget = null;

        for (EntityPlayer target : mc.world.playerEntities) {

            if (target == mc.player) {
                continue;
            }

            if (Friends.isFriend(target.getName())) {
                continue;
            }

            if (!EntityUtil.isLiving(target)) {
                continue;
            }

            if ((target).getHealth() <= 0) {
                continue;
            }

            if (mc.player.getDistance(target) > range.getValue()) {
                continue;
            }

            if (closestTarget == null) {
                closestTarget = target;
                continue;
            }

            if (mc.player.getDistance(target) < mc.player.getDistance(closestTarget)) {
                closestTarget = target;
            }

        }

        return closestTarget;

    }

    private boolean canPlaceCrystal(Vec3d vec3d) {

        BlockPos blockPos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);

        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);

        return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
                || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
                && mc.world.getBlockState(boost).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();

    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isExplosionProof(Vec3d vec3d) {

        BlockPos blockPos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);

        Block block = mc.world.getBlockState(blockPos).getBlock();

        if (block == Blocks.BEDROCK) {
            return true;
        }

        if (block == Blocks.OBSIDIAN) {
            return true;
        }

        if (block == Blocks.ANVIL) {
            return true;
        }

        if (block == Blocks.ENDER_CHEST) {
            return true;
        }

        //noinspection RedundantIfStatement
        if (block == Blocks.BARRIER) {
            return true;
        }

        return false;

    }

    private static class Offsets {

        private static final Vec3d NORTH1 = new Vec3d(0, 0, -1);
        private static final Vec3d NORTH2 = new Vec3d(0, 0, -2);
        private static final Vec3d EAST1 = new Vec3d(1, 0, 0);
        private static final Vec3d EAST2 = new Vec3d(2, 0, 0);
        private static final Vec3d SOUTH1 = new Vec3d(0, 0, 1);
        private static final Vec3d SOUTH2 = new Vec3d(0, 0, 2);
        private static final Vec3d WEST1 = new Vec3d(-1, 0, 0);
        private static final Vec3d WEST2 = new Vec3d(-2, 0, 0);

    }

}
