package net.daphne.lethality.client.renderer;

import com.mojang.math.Axis;
import net.daphne.lethality.client.model.AcidicSlashModel;
import net.daphne.lethality.entity.AcidicSlashEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import com.mojang.blaze3d.vertex.PoseStack;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AcidicSlashRenderer extends GeoEntityRenderer<AcidicSlashEntity> {
    public AcidicSlashRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AcidicSlashModel());
    }

    @Override
    protected void applyRotations(AcidicSlashEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
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
    public RenderType getRenderType(AcidicSlashEntity animatable, net.minecraft.resources.ResourceLocation texture, net.minecraft.client.renderer.MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    @Override
    protected int getBlockLightLevel(AcidicSlashEntity pEntity, BlockPos pPos) {
        return 15;
    }
}
