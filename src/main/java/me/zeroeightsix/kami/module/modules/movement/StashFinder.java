package me.zeroeightsix.kami.module.modules.movement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

@Module.Info(
   name = "StashFinder",
   category = Module.Category.MISC,
   description = "Logs chests and shulker boxes to a file"
)
public class StashFinder extends Module {
   private ArrayList chestPositions = new ArrayList();
   private Map stashMap = new HashMap();
   private Setting density = this.register(Settings.integerBuilder("Chest Density").withMinimum(2).withValue((int)5).withMaximum(10).build());
   private Setting logToFile = this.register(Settings.b("Log to file", true));
   private Setting logShulkers = this.register(Settings.b("Log shulkers", true));

   public void onEnable() {
      this.chestPositions.clear();
      this.stashMap.clear();
   }

   public void onUpdate() {
      Iterator var1 = mc.world.loadedTileEntityList.iterator();

      while(true) {
         TileEntity tileEntity;
         BlockPos pos;
         do {
            if (!var1.hasNext()) {
               return;
            }

            tileEntity = (TileEntity)var1.next();
            pos = tileEntity.getPos();
         } while(!(tileEntity instanceof TileEntityChest) && !(tileEntity instanceof TileEntityShulkerBox));

         boolean alreadyAdded = false;
         Iterator var5 = this.chestPositions.iterator();

         while(var5.hasNext()) {
            BlockPos p = (BlockPos)var5.next();
            if (p.equals(pos)) {
               alreadyAdded = true;
            }
         }

         if (!alreadyAdded) {
            this.chestPositions.add(pos);
            int chunkX = pos.getX() / 16;
            int chunkZ = pos.getZ() / 16;
            long chunk = ChunkPos.asLong(chunkX, chunkZ);
            if (!this.stashMap.containsKey(chunk)) {
               this.stashMap.put(chunk, 0);
            }

            int DENSITY = (Integer)this.density.getValue();
            int count = (Integer)this.stashMap.get(chunk) + 1;
            if ((Boolean)this.logShulkers.getValue() && tileEntity instanceof TileEntityShulkerBox && count < DENSITY) {
               count = DENSITY;
            }

            this.stashMap.put(chunk, count);
            if (count == DENSITY) {
               Command.sendChatMessage("[StashFinder] " + pos.toString());

               }

                {
                  mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
               }

               if ((Boolean)this.logToFile.getValue()) {
                  try {
                     BufferedWriter writer = new BufferedWriter(new FileWriter("AtomStashFinder.txt", true));
                     String line = "";
                     Calendar calendar = Calendar.getInstance();
                     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     line = line + "[" + formatter.format(calendar.getTime()) + "|";
                     if (mc.getCurrentServerData() != null) {
                        line = line + mc.getCurrentServerData().serverIP + "|";
                     }

                     switch(mc.player.dimension) {
                     case -1:
                        line = line + "Nether";
                        break;
                     case 0:
                        line = line + "Overworld";
                        break;
                     case 1:
                        line = line + "End";
                     }

                     line = line + "] ";
                     line = line + pos.toString() + " ";
                     if (tileEntity instanceof TileEntityShulkerBox) {
                        line = line + "Shulker";
                     }

                     writer.write(line);
                     writer.newLine();
                     writer.close();
                  } catch (IOException var16) {
                     var16.printStackTrace();
                  }
               }
            }
         }
      }
   }

