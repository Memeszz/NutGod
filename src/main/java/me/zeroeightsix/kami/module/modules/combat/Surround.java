package me.zeroeightsix.kami.module.modules.combat;


import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Module.Info(name = "Surround", description = "Places obby around your black feet", category = Module.Category.COMBAT)
public class Surround extends Module {

    private List<Block> whiteList = Arrays.asList(Blocks.OBSIDIAN, Blocks.ENDER_CHEST);
    private Setting<Boolean> sneak = register(Settings.b("SneakToggle", false));
    private Setting<Boolean> alert = register(Settings.b("ChatAlert", true));

    private Setting<Boolean> rotate = register(Settings.b("Rotate", true));
    private Setting<Double> bpt = this.register(Settings.d("BlocksPerTick", 7.0));
    private Setting<AutoCenter> autoCenter = register(Settings.e("AutoCenter", AutoCenter.TP));
    private Vec3d playerPos;
    private BlockPos basePos;
    private int offsetStep = 0;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;

    RenderItem itemRenderer = mc.getRenderItem();

    public static boolean hasNeighbour(BlockPos blockPos) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = blockPos.offset(side);
            if (!mc.world.getBlockState(neighbour).getMaterial().isReplaceable())
                return true;
        }
        return false;
    }
    private enum AutoCenter {
        OFF, TP
    }
    @Override

    public void onUpdate() {
        if (sneak.getValue() && !mc.gameSettings.keyBindSneak.isKeyDown()) return;
        if (!isEnabled() || mc.player == null) return;
        Vec3d vec3d = getInterpolatedPos(mc.player, 0);
        BlockPos northBlockPos = new BlockPos(vec3d).north();
        BlockPos southBlockPos = new BlockPos(vec3d).south();
        BlockPos eastBlockPos = new BlockPos(vec3d).east();
        BlockPos westBlockPos = new BlockPos(vec3d).west();

        int blocksPlaced = 0;


        int newSlot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack =
                    mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }
            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (!whiteList.contains(block)) {
                continue;
            }

            newSlot = i;
            break;
        }

        if (newSlot == -1)
            return;

        int oldSlot = mc.player.inventory.currentItem;
        mc.player.inventory.currentItem = newSlot;

        A:
        if (!hasNeighbour(northBlockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = northBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    northBlockPos = neighbour;
                    break A;
                }
            }
            return;
        }

        // check if we don't have a block adjacent to South blockpos
        B:
        if (!hasNeighbour(southBlockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = southBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    southBlockPos = neighbour;
                    break B;
                }
            }
            return;
        }

        // check if we don't have a block adjacent to East blockpos
        C:
        if (!hasNeighbour(eastBlockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = eastBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    eastBlockPos = neighbour;
                    break C;
                }
            }
            return;
        }

        // check if we don't have a block adjacent to West blockpos
        D:
        if (!hasNeighbour(westBlockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = westBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    westBlockPos = neighbour;
                    break D;
                }
            }
            return;
        }


        // place blocks
        if (mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable()) {
            if (isEntitiesEmpty(northBlockPos)) {
                placeBlockScaffold(northBlockPos, rotate.getValue());
                blocksPlaced++;
            } else if (isEntitiesEmpty(northBlockPos.north()) && mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable()) {
                placeBlockScaffold(northBlockPos.north(), rotate.getValue());
                blocksPlaced++;
            }
        }
        if (blocksPlaced >= bpt.getValue()) {
            mc.player.inventory.currentItem = oldSlot;
            return;
        }

        if (mc.world.getBlockState(southBlockPos).getMaterial().isReplaceable()) {
            if (isEntitiesEmpty(southBlockPos)) {
                placeBlockScaffold(southBlockPos, rotate.getValue());
                blocksPlaced++;
            } else if (isEntitiesEmpty(southBlockPos.south()) && mc.world.getBlockState(southBlockPos.south()).getMaterial().isReplaceable()) {
                placeBlockScaffold(southBlockPos.south(), rotate.getValue());
                blocksPlaced++;
            }
        }
        if (blocksPlaced >= bpt.getValue()) {
            mc.player.inventory.currentItem = oldSlot;
            return;
        }

        if (mc.world.getBlockState(eastBlockPos).getMaterial().isReplaceable()) {
            if (isEntitiesEmpty(eastBlockPos)) {
                placeBlockScaffold(eastBlockPos, rotate.getValue());
                blocksPlaced++;
            } else if (isEntitiesEmpty(eastBlockPos.east()) && mc.world.getBlockState(eastBlockPos.east()).getMaterial().isReplaceable()) {
                placeBlockScaffold(eastBlockPos.east(), rotate.getValue());
                blocksPlaced++;
            }
        }
        if (blocksPlaced >= bpt.getValue()) {
            mc.player.inventory.currentItem = oldSlot;
            return;
        }

        if (mc.world.getBlockState(westBlockPos).getMaterial().isReplaceable()) {
            if (isEntitiesEmpty(westBlockPos)) {
                placeBlockScaffold(westBlockPos, rotate.getValue());
                blocksPlaced++;
            } else if (isEntitiesEmpty(westBlockPos.west()) && mc.world.getBlockState(westBlockPos.west()).getMaterial().isReplaceable()) {
                placeBlockScaffold(westBlockPos.west(), rotate.getValue());
                blocksPlaced++;
            }
        }
        if (blocksPlaced >= bpt.getValue()) {
            mc.player.inventory.currentItem = oldSlot;
            return;
        }

        // reset slot
        mc.player.inventory.currentItem = oldSlot;
    }

    private boolean isEntitiesEmpty(BlockPos pos) {
        List<Entity> entities = mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream()
                .filter(e -> !(e instanceof EntityItem))
                .filter(e -> !(e instanceof EntityXPOrb))
                .collect(Collectors.toList());
        return entities.isEmpty();
    }

    public static boolean placeBlockScaffold(BlockPos pos, boolean rotate) {
        Vec3d eyesPos = new Vec3d(mc.player.posX,
                mc.player.posY + mc.player.getEyeHeight(),
                mc.player.posZ);

        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();


            if (!canBeClicked(neighbor))
                continue;

            Vec3d hitVec = new Vec3d(neighbor).add(0.5, 0.5, 0.5)
                    .add(new Vec3d(side2.getDirectionVec()).scale(0.5));


            if (rotate)
                faceVectorPacketInstant(hitVec);
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            processRightClickBlock(neighbor, side2, hitVec);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.rightClickDelayTimer = 0;
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

            return true;
        }

        return false;
    }

    private static PlayerControllerMP getPlayerController() {
        return mc.playerController;
    }

    public static void processRightClickBlock(BlockPos pos, EnumFacing side,
                                              Vec3d hitVec) {
        getPlayerController().processRightClickBlock(mc.player,
                mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    public static IBlockState getState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static boolean canBeClicked(BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = getNeededRotations2(vec);

        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0],
                rotations[1], mc.player.onGround));
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[]{
                mc.player.rotationYaw
                        + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw),
                mc.player.rotationPitch + MathHelper
                        .wrapDegrees(pitch - mc.player.rotationPitch)};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(mc.player.posX,
                mc.player.posY + mc.player.getEyeHeight(),
                mc.player.posZ);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d(
                (entity.posX - entity.lastTickPosX) * x,
                (entity.posY - entity.lastTickPosY) * y,
                (entity.posZ - entity.lastTickPosZ) * z
        );
    }

    /* Autocenter */
    private void centerPlayer(double x, double y, double z) {

        mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, true));
        mc.player.setPosition(x, y, z);
    }

    double getDst(Vec3d vec) {
        return playerPos.distanceTo(vec);
    }
    /* End of Autocenter */

    public void onEnable() {
            if (this.alert.getValue() && NutGodCA.mc.world != null) {
                Command.sendRawChatMessage("\u00A7aSurround ON");
            }


        if (mc.player == null) return;
        /* Autocenter */
        BlockPos centerPos = mc.player.getPosition();
        playerPos = mc.player.getPositionVector();
        double y = centerPos.getY();
        double x = centerPos.getX();
        double z = centerPos.getZ();

        final Vec3d plusPlus = new Vec3d(x + 0.5, y, z + 0.5);
        final Vec3d plusMinus = new Vec3d(x + 0.5, y, z - 0.5);
        final Vec3d minusMinus = new Vec3d(x - 0.5, y, z - 0.5);
        final Vec3d minusPlus = new Vec3d(x - 0.5, y, z + 0.5);

        if (autoCenter.getValue().equals(AutoCenter.TP)) {
            if (getDst(plusPlus) < getDst(plusMinus) && getDst(plusPlus) < getDst(minusMinus) && getDst(plusPlus) < getDst(minusPlus)) {
                x = centerPos.getX() + 0.5;
                z = centerPos.getZ() + 0.5;
                centerPlayer(x, y, z);
            }
            if (getDst(plusMinus) < getDst(plusPlus) && getDst(plusMinus) < getDst(minusMinus) && getDst(plusMinus) < getDst(minusPlus)) {
                x = centerPos.getX() + 0.5;
                z = centerPos.getZ() - 0.5;
                centerPlayer(x, y, z);
            }
            if (getDst(minusMinus) < getDst(plusPlus) && getDst(minusMinus) < getDst(plusMinus) && getDst(minusMinus) < getDst(minusPlus)) {
                x = centerPos.getX() - 0.5;
                z = centerPos.getZ() - 0.5;
                centerPlayer(x, y, z);
            }
            if (getDst(minusPlus) < getDst(plusPlus) && getDst(minusPlus) < getDst(plusMinus) && getDst(minusPlus) < getDst(minusMinus)) {
                x = centerPos.getX() - 0.5;
                z = centerPos.getZ() + 0.5;
                centerPlayer(x, y, z);
            }
        }
        /* End of Autocenter*/

        playerHotbarSlot = Wrapper.getPlayer().inventory.currentItem;
        lastHotbarSlot = -1;


       }
        public void onDisable() {
    if (this.alert.getValue() && NutGodCA.mc.world != null) {
        Command.sendRawChatMessage("\u00A7cSurround OFF");

    }
}}
