package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.PickleBallEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PickleBallModel extends GeoModel<PickleBallEntity> {
    @Override
    public ResourceLocation getModelResource(PickleBallEntity object) {
        return new ResourceLocation("lethality", "geo/pickle_ball.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PickleBallEntity object) {
        return new ResourceLocation("lethality", "textures/entity/pickle_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PickleBallEntity animatable) {
        return null;
    }
}


