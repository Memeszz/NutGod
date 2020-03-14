//package me.zeroeightsix.kami.module.modules.render;
//
//import me.zeroeightsix.kami.event.events.RenderEvent;
//import me.zeroeightsix.kami.gui.font.CFontRenderer;
//import me.zeroeightsix.kami.setting.Settings;
//import me.zeroeightsix.kami.util.EntityUtil;
//import me.zeroeightsix.kami.setting.Setting;
//import me.zeroeightsix.kami.command.Command;
//import me.zeroeightsix.kami.module.Module;
//import me.zeroeightsix.kami.util.Friends;
//
//import org.lwjgl.opengl.GL11.*;
//
//import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.client.gui.FontRenderer;
//import net.minecraft.client.renderer.*;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.item.ItemStack;
//import net.minecraft.init.Items;
//
//import java.util.stream.Collectors;
//import java.util.Collections;
//import java.util.Collection;
//import java.util.Comparator;
//import java.util.ArrayList;
//
//// Rina.
//import me.zeroeightsix.kami.util.TurokGL;
//
//// I cant test it by someproblem, its not finished, i recoded all and wheni can test i finish,
//@Module.Info(name = "TagESP", category = Module.Category.HIDDEN)
//public class TagESP extends Module {
//	private Setting<Boolean> durabilidade = register(Settings.b("Durability", false));
//	private Setting<Integer> distansia    = register(Settings.integerBuilder("Distance").withMinimum(0).withValue(6).withMaximum(15).build());
//	private Setting<Integer> escala       = register(Settings.integerBuilder("Scale").withMinimum(0).withValue(6).withMaximum(15).build());
//
//	@Override
//	public void onWorldRender(RenderEvent event) {
//		if (mc.getRenderManager().options == null) return;
//
//		TagESP.enableGL();
//
//		mc.world.loadedEntityList.stream()
//.filter(EntityUtil::isLiving).filter(entity -> !EntityUtil.isFakeLocalPlayer(entity))
//.filter(entity -> (entity instanceof EntityPlayer ? players.getValue()))
//.filter(entity -> mc.player.getDistance(entity) < alcanse.getValue())
//.sorted(Comparator.comparing(entity -> -mc.player.getDistance(entity)))
//.forEach(this::nameTag);
//
//		TagESP.disableGL();
//	}
//
//	public void nameTag(Entity entidade) {
//		GlStateManager.pushMatrix();
//
//		Vec3d tag_inter = EntityUtil.getInterpolatedRenderPos(entityIn, mc.getRenderPartialTicks());
//		float tag_y_add = entityIn.height + 0.5F - (entityIn.isSneaking() ? 0.25F : 0.0F);
//
//		float tag_x = tag_inter.x;
//		float tag_y = tag_inter.y + tag_y_add;
//		float tag_z = tag_inter.z;
//
//		float tag_visto_yaw   = mc.getRenderManager().playerViewY;
//		float tag_visto_pitch = mc.getRenderManager().playerViewX;
//
//		boolean face_frontal = mc.getRenderManager().options.thirdPersonView == 2;
//
//		translate(tag_x, tag_y, tag_z);
//
//		GlStateManager.rotate(- tag_viwer_yaw, 0.0f, 1.0f, 0.0f);
//		GlStateManager.rotate((float) (face_frontal ? -1 : 1) * tag_viwer_pitch, 1.0f, 0.0f, 0.0f);
//
//		float tag_distansia = mc.player.getDistance(entidade);
//		float tag_escala    = (tag_distansia / 8f) * (float) (Math.pow(1.2589254f, escala.getValue()));
//
//		GlStateManager.scale(tag_escala, tag_escala, tag_escala);
//
//		FontRenderer fonte = mc.fontRenderer;
//
//		String entidade_valores = entidade.getName() + (health.getValue() ? " " + Command.SECTIONSIGN() + "c" + Math.round(((EntityLivingBase) entityIn).getHealth() + (entityIn instanceof EntityPlayer ? ((EntityPlayer) entityIn).getAbsorptionAmount() : 0)) : "");
//		int tag_iname = fonte.getStringWidth(entidade_valores) + 1;
//
//		GlStateManager.enableBlend();
//		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//		GlStateManager.disableTexture2D();
//
//		Tessellator simples_tessellator = Tessellator.getInstance();
//
//		BufferBuilder buffador = simples_tessellator.getBuffer();
//
//		TurokGL.turok_Translatef(0, - 20, 0);
//
//		buffador.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
//		buffador.pos(- tag_iname - 1, 8, 0.0d).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
//		buffador.pos(- tag_iname - 1, 19, 0.0d).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
//		buffador.pos(tag_iname + 1, 19, 0.0d).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
//		buffador.pos(tag_iname + 1, 8, 0.0d).color(0.0f, 0.0f, 0.0f, 0.5f).endVertex();
//
//		simples_tessellator.draw();
//
//		buffador.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
//		buffador.pos(- tag_iname - 1, 8, 0.0d).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
//		buffador.pos(- tag_iname - 1, 19, 0.0d).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
//		buffador.pos(tag_iname + 1, 19, 0.0d).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
//		buffador.pos(tag_iname + 1, 8, 0.0d).color(0.1f, 0.1f, 0.1f, 0.1f).endVertex();
//
//		simples_tessellator.draw();
//
//		GlStateManager.enableTexture2D();
//
//		GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
//		CFontRenderer.drawString(entidade_valores, - tag_iname, 10, entidade instanceof EntityPlayer ? Friends.isFriend(entityIn.getName()) ? 0x11ee11 : 0xffffff : 0xffffff);
//		GlStateManager.glNormal3f(0.0f, 0.0f, 0.0f);
//
//		TurokGL.turok_Translatef(0, 20, 0);
//
//		GlStateManager.scale(-40, -40, 40);
//
//		ArrayList<ItemStack> equipamento = new ArrayList<>();
//		entidade.getHeldEquipment().forEach(itemStack -> {
//			if (itemStack != null) equipamento.add(itemStack);
//		});
//
//		ArrayList<ItemStack> armadura = new ArrayList<>();
//		entidade.getArmorInventoryList().forEach(itemStack -> {
//			if (itemStack != null) armadura.add(itemStack);
//		});
//
//		equipamento.addAll(armadura);
//
//		if (equipment.size() == 0) {
//			GlStateManager.popMatrix();
//			return;
//		}
//
//		Collection<ItemStack> tag_add = equipamento.stream().filter(itemStack -> !itemStack.isEmpty()).collect(Collectors.toList());
//		translate(((tag_add.size() - 1) / 2f) * 0.5f, 0.6f, 0.0f);
//
//		RenderItem renderizar = mc.getRenderItem();
//
//		tag_add.forEach(itemStack -> {
//			GlStateManager.pushAttrib();
//
//			RenderHelper.enableStandardItemLighting();
//
//			GlStateManager.scale(0.5, 0.5, 0);
//			GlStateManager.disableLighting();
//
//			renderizar.zLevel = - 5;
//			renderizar.renderItem(itemStack, itemStack.getItem() == Items.SHIELD ? ItemCameraTransforms.TransformType.FIXED : ItemCameraTransforms.TransformType.NONE);
//			renderizar.zLevel = 0;
//
//			GlStateManager.scale(2, 2, 0);
//			GlStateManager.popAttrib();
//			translate(-0.5f, 0, 0);
//		});
//
//		GlStateManager.popMatrix();
//	}
//
//	public void enableGL() {
//		GlStateManager.enableTexture2D();
//		GlStateManager.disableLighting();
//		GlStateManager.disableDepth();
//	}
//
//	public void disableGL() {
//		GlStateManager.disableTexture2D();
//		RenderHelper.disableStandardItemLighting();
//		GlStateManager.enableLighting();
//		GlStateManager.enableDepth();
//	}
//
//	public void translate(float x, float y, float z) {
//		GlStateManager.translate(x, y, z);
//	}
//}