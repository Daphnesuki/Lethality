package net.daphne.lethality.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.daphne.lethality.client.model.HookModel;
import net.daphne.lethality.entity.HookEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class HookRenderer extends GeoEntityRenderer<HookEntity> {
    private static final ResourceLocation CHAIN_TEXTURE = new ResourceLocation("lethality", "textures/entity/hook_chain.png");

    public HookRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HookModel());
        this.shadowRadius = 0.25f;
    }

    @Override
    protected void applyRotations(HookEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick);

        double dx = entity.getDeltaMovement().x;
        double dy = entity.getDeltaMovement().y;
        double dz = entity.getDeltaMovement().z;

        float horizontalMagnitude = Mth.sqrt((float) (dx * dx + dz * dz));
        float yaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
        float pitch = (float) (Mth.atan2(dy, horizontalMagnitude) * (180F / Math.PI));

        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
    }

//    @Override
//    public void render(HookEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
//
//        if (entity.getOwner() instanceof LivingEntity owner) {
//            poseStack.pushPose();
//
//            Vec3 hookPos = entity.getLerpedPos(partialTicks);
//            Vec3 ownerPos = owner.getEyePosition(partialTicks);
//            Vec3 ownerOffset = ownerPos.subtract(hookPos);
//
//            float yaw = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
//            float pitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
//
//            // Posición de anclaje en el gancho
//            Vec3 ringOffset = new Vec3(1.0, 0, 0)
//                    .zRot((float) Math.toRadians(pitch))
//                    .yRot((float) Math.toRadians(yaw + 90))
//                    .add(0, entity.getBbHeight() / 2f, 0);
//            Vec3 ringPos = hookPos.add(ringOffset);
//
//            float length = (float) ringPos.distanceTo(ownerPos);
//
//            PoseStack.Pose pose = poseStack.last();
//            Matrix4f matrix = pose.pose();
//            Matrix3f normal = pose.normal();
//
//            float minU = 0, maxU = 1;
//            float minV = 0, maxV = length / 8f;
//
//            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(CHAIN_TEXTURE));
//            Vec3 offset = ownerPos.subtract(ringPos).normalize().scale(0.25).yRot((float) (Math.PI / 2));
//
//            Vec3 v1 = ringPos.add(offset);
//            Vec3 v2 = ownerPos.add(offset);
//            Vec3 v3 = ownerPos.subtract(offset);
//            Vec3 v4 = ringPos.subtract(offset);
//
//            int chainLight = LightTexture.pack(entity.level().getBrightness(LightLayer.BLOCK, entity.blockPosition()),
//                    entity.level().getBrightness(LightLayer.SKY, entity.blockPosition()));
//
//            vertex(v1, consumer, minU, minV, matrix, normal, chainLight);
//            vertex(v2, consumer, minU, maxV, matrix, normal, chainLight);
//            vertex(v3, consumer, maxU, maxV, matrix, normal, chainLight);
//            vertex(v4, consumer, maxU, minV, matrix, normal, chainLight);
//
//            poseStack.popPose();
//        }
//    }


    private void vertex(Vec3 vec, VertexConsumer consumer, float u, float v, Matrix4f modelMatrix, Matrix3f normal, int light) {
        consumer.vertex(modelMatrix, (float) vec.x, (float) vec.y, (float) vec.z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normal, 0, 1, 0)
                .endVertex();
    }

    @Override
    public RenderType getRenderType(HookEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
