package coda.gonefishing.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class WoodenHorseModel<T extends LivingEntity> extends EntityModel<T> {
	private final ModelPart root;

	public WoodenHorseModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(5.0F, -9.0F, -15.0F, 0.0F, 9.0F, 30.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).addBox(-5.0F, -9.0F, -15.0F, 0.0F, 9.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, -2.0F, -15.0F, 10.0F, 0.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(37, 16).addBox(-6.0F, -15.0F, -12.0F, 12.0F, 6.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 39).addBox(-3.0F, -12.0F, -9.0F, 6.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(36, 45).addBox(-1.0F, -14.0F, 1.0F, 2.0F, 14.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(-3.0F, -15.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).addBox(1.0F, -15.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 0).mirror().addBox(-7.0F, -9.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(18, 0).addBox(3.0F, -9.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, -9.0F));

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, -5.0F, -1.0F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.xRot = Mth.cos(limbSwing * 0.6F) * 0.4F * limbSwingAmount * 0.5F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}