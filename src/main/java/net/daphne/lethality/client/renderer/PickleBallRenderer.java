package net.daphne.lethality.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.daphne.lethality.client.model.PickleBallModel;
import net.daphne.lethality.entity.PickleBallEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PickleBallRenderer extends GeoEntityRenderer<PickleBallEntity> {
    public PickleBallRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PickleBallModel());
    }

    @Override
    protected void applyRotations(PickleBallEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick);

        // Rotación basada en movimiento
        double dx = entity.getDeltaMovement().x;
        double dy = entity.getDeltaMovement().y;
        double dz = entity.getDeltaMovement().z;

        float horizontalMagnitude = Mth.sqrt((float)(dx * dx + dz * dz));
        float yaw = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
        float pitch = (float)(Mth.atan2(dy, horizontalMagnitude) * (180F / Math.PI));

        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
    }

    @Override
    public RenderType getRenderType(PickleBallEntity animatable, net.minecraft.resources.ResourceLocation texture, net.minecraft.client.renderer.MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    @Override
    protected int getBlockLightLevel(PickleBallEntity pEntity, BlockPos pPos) {
        return 0;
    }
}
