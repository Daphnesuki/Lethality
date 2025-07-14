package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.DevilsPitchforkEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DevilsPitchforkModel extends GeoModel<DevilsPitchforkEntity> {
    @Override
    public ResourceLocation getModelResource(DevilsPitchforkEntity object) {
        return new ResourceLocation("lethality", "geo/devils_pitchfork.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DevilsPitchforkEntity object) {
        return new ResourceLocation("lethality", "textures/entity/devils_pitchfork.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DevilsPitchforkEntity animatable) {
        return null;
    }
}


