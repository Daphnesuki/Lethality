package net.daphne.lethality.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.daphne.lethality.client.model.RealSlashModel;
import net.daphne.lethality.entity.RealSlashEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RealSlashRenderer extends GeoEntityRenderer<RealSlashEntity> {
    public RealSlashRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RealSlashModel());
    }

    @Override
    protected void applyRotations(RealSlashEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick);

        // Calcula la rotación en base al movimiento
        double dx = entity.getDeltaMovement().x;
        double dy = entity.getDeltaMovement().y;
        double dz = entity.getDeltaMovement().z;

        float horizontalMagnitude = Mth.sqrt((float)(dx * dx + dz * dz));
        float yaw = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
        float pitch = (float)(Mth.atan2(dy, horizontalMagnitude) * (180F / Math.PI));

        // Aplica la rotación en Yaw (Y) y Pitch (X)
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));   // Yaw
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));  // Pitch
    }

    @Override
    public RenderType getRenderType(RealSlashEntity animatable, net.minecraft.resources.ResourceLocation texture, net.minecraft.client.renderer.MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    @Override
    protected int getBlockLightLevel(RealSlashEntity pEntity, BlockPos pPos) {
        return 15;
    }
}
