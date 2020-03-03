package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.GeometryMasks;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static me.zeroeightsix.kami.module.modules.combat.CrystalAura.getPlayerPos;

/**
 * Created 16 November 2019 by hub
 * Updated  28 November 2019 by hub
 */
@Module.Info(name = "HoleESP", category = Module.Category.RENDER, description = "Show safe holes")
public class HoleESP extends Module {

    private final BlockPos[] surroundOffset = {
            new BlockPos(0, -1, 0), // down
            new BlockPos(0, 0, -1), // north
            new BlockPos(1, 0, 0), // east
            new BlockPos(0, 0, 1), // south
            new BlockPos(-1, 0, 0) // west
    };

    private Setting<HoleType> holeType = register(Settings.e("HoleType", HoleType.BOTH));
    private Setting<Boolean> hideOwn = register(Settings.b("HideOwn", false));
    private Setting<Double> renderDistance = register(Settings.doubleBuilder("RenderDistance").withMinimum(1.0).withValue(8.0).withMaximum(32.0).build());
    private Setting<RenderMode> renderMode = register(Settings.e("RenderMode", RenderMode.DOWN));
    private Setting<Integer> obiRed = register(Settings.integerBuilder("ObiRed").withMinimum(0).withValue(104).withMaximum(255).build());
    private Setting<Integer> obiGreen = register(Settings.integerBuilder("ObiGreen").withMinimum(0).withValue(12).withMaximum(255).build());
    private Setting<Integer> obiBlue = register(Settings.integerBuilder("ObiBlue").withMinimum(0).withValue(35).withMaximum(255).build());
    private Setting<Integer> brockRed = register(Settings.integerBuilder("BrockRed").withMinimum(0).withValue(81).withMaximum(255).build());
    private Setting<Integer> brockGreen = register(Settings.integerBuilder("BrockGreen").withMinimum(0).withValue(12).withMaximum(255).build());
    private Setting<Integer> brockBlue = register(Settings.integerBuilder("BrockBlue").withMinimum(0).withValue(104).withMaximum(255).build());
    private Setting<Integer> alpha = register(Settings.integerBuilder("Alpha").withMinimum(0).withValue(169).withMaximum(255).build());

    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;

    @Override
    public void onUpdate() {

        if (safeHoles == null) {
            safeHoles = new ConcurrentHashMap<>();
        } else {
            safeHoles.clear();
        }

        int range = (int) Math.ceil(renderDistance.getValue());

        List<BlockPos> blockPosList = BlockInteractionHelper.getSphere(getPlayerPos(), range, range, false, true, 0);

        for (BlockPos pos : blockPosList) {

            // block gotta be air
            if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            // block 1 above gotta be air
            if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            // block 2 above gotta be air
            if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            if (hideOwn.getValue() && pos.equals(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ))) {
                continue;
            }

            boolean isSafe = true;
            boolean isBedrock = true;

            for (BlockPos offset : surroundOffset) {
                Block block = mc.world.getBlockState(pos.add(offset)).getBlock();
                if (block != Blocks.BEDROCK) {
                    isBedrock = false;
                }
                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    isSafe = false;
                    break;
                }
            }

            if (isSafe) {
                safeHoles.put(pos, isBedrock);
            }

        }

    }

    @Override
    public void onWorldRender(final RenderEvent event) {

        if (mc.player == null || safeHoles == null) {
            return;
        }

        if (safeHoles.isEmpty()) {
            return;
        }

        KamiTessellator.prepare(GL11.GL_QUADS);

        safeHoles.forEach((blockPos, isBedrock) -> {
            if (isBedrock) {
                if (holeType.getValue().equals(HoleType.BOTH) || holeType.getValue().equals(HoleType.BROCK)) {
                    drawBlock(blockPos, brockRed.getValue(), brockGreen.getValue(), brockBlue.getValue());
                }
            } else {
                if (holeType.getValue().equals(HoleType.BOTH) || holeType.getValue().equals(HoleType.OBI)) {
                    drawBlock(blockPos, obiRed.getValue(), obiGreen.getValue(), obiBlue.getValue());
                }
            }
        });

        KamiTessellator.release();

    }

    private void drawBlock(BlockPos blockPos, int r, int g, int b) {
        Color color = new Color(r, g, b, alpha.getValue());
        int mask = GeometryMasks.Quad.DOWN;
        if (renderMode.getValue().equals(RenderMode.BLOCK)) {
            mask = GeometryMasks.Quad.ALL;
        }
        KamiTessellator.drawBox(blockPos, color.getRGB(), mask);
    }

    @Override
    public String getHudInfo() {
        return holeType.getValue().toString();
    }

    private enum RenderMode {
        DOWN, BLOCK
    }

    private enum HoleType {
        BROCK, OBI, BOTH
    }

}
