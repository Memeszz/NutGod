package me.zeroeightsix.kami.mixin.client;


import me.zeroeightsix.kami.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiScreen.class})
public class MixinGuiScreen {
   @Shadow
   public Minecraft mc;
   RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
   FontRenderer fontRenderer;

   public MixinGuiScreen() {
      this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
   }

   @Inject(
      method = {"renderToolTip"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo info) {
      if (ModuleManager.isModuleEnabled("ShulkerPreview") && stack.getItem() instanceof ItemShulkerBox) {
         NBTTagCompound tagCompound = stack.getTagCompound();
         if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
            NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
            if (blockEntityTag.hasKey("Items", 9)) {
               info.cancel();
               NonNullList nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
               ItemStackHelper.loadAllItems(blockEntityTag, nonnulllist);
               GlStateManager.enableBlend();
               GlStateManager.disableRescaleNormal();
               RenderHelper.disableStandardItemLighting();
               GlStateManager.disableLighting();
               GlStateManager.disableDepth();
               int width = Math.max(144, this.fontRenderer.getStringWidth(stack.getDisplayName()) + 3);
               int x1 = x + 12;
               int y1 = y - 12;
               int height = 57;
               this.itemRender.zLevel = 300.0F;
               this.drawGradientRectP(x1 - 3, y1 - 4, x1 + width + 3, y1 - 3, -267386864, -267386864);
               this.drawGradientRectP(x1 - 3, y1 + height + 3, x1 + width + 3, y1 + height + 4, -267386864, -267386864);
               this.drawGradientRectP(x1 - 3, y1 - 3, x1 + width + 3, y1 + height + 3, -267386864, -267386864);
               this.drawGradientRectP(x1 - 4, y1 - 3, x1 - 3, y1 + height + 3, -267386864, -267386864);
               this.drawGradientRectP(x1 + width + 3, y1 - 3, x1 + width + 4, y1 + height + 3, -267386864, -267386864);
               this.drawGradientRectP(x1 - 3, y1 - 3 + 1, x1 - 3 + 1, y1 + height + 3 - 1, 1347420415, 1344798847);
               this.drawGradientRectP(x1 + width + 2, y1 - 3 + 1, x1 + width + 3, y1 + height + 3 - 1, 1347420415, 1344798847);
               this.drawGradientRectP(x1 - 3, y1 - 3, x1 + width + 3, y1 - 3 + 1, 1347420415, 1347420415);
               this.drawGradientRectP(x1 - 3, y1 + height + 2, x1 + width + 3, y1 + height + 3, 1344798847, 1344798847);
               this.fontRenderer.drawString(stack.getDisplayName(), x + 12, y - 12, 16777215);
               GlStateManager.enableBlend();
               GlStateManager.enableAlpha();
               GlStateManager.enableTexture2D();
               GlStateManager.enableLighting();
               GlStateManager.enableDepth();
               RenderHelper.enableGUIStandardItemLighting();

               for(int i = 0; i < nonnulllist.size(); ++i) {
                  int iX = x + i % 9 * 16 + 11;
                  int iY = y + i / 9 * 16 - 11 + 8;
                  ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                  this.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
                  this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemStack, iX, iY, (String)null);
               }

               RenderHelper.disableStandardItemLighting();
               this.itemRender.zLevel = 0.0F;
               GlStateManager.enableLighting();
               GlStateManager.enableDepth();
               RenderHelper.enableStandardItemLighting();
               GlStateManager.enableRescaleNormal();
            }
         }
      }

   }

   @Inject(
      method = {"Lnet/minecraft/client/gui/GuiScreen;drawWorldBackground(I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void drawWorldBackgroundWrapper(int tint, CallbackInfo ci) {
         ci.cancel();
      }



   private void drawGradientRectP(int left, int top, int right, int bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;
      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      bufferbuilder.pos((double)right, (double)top, 300.0D).color(f1, f2, f3, f).endVertex();
      bufferbuilder.pos((double)left, (double)top, 300.0D).color(f1, f2, f3, f).endVertex();
      bufferbuilder.pos((double)left, (double)bottom, 300.0D).color(f5, f6, f7, f4).endVertex();
      bufferbuilder.pos((double)right, (double)bottom, 300.0D).color(f5, f6, f7, f4).endVertex();
      tessellator.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }
}
