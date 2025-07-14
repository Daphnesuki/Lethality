package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.GaelSkullEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GaelSkullModel extends GeoModel<GaelSkullEntity> {
    @Override
    public ResourceLocation getModelResource(GaelSkullEntity object) {
        return new ResourceLocation("lethality", "geo/gael_skull.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GaelSkullEntity object) {
        return new ResourceLocation("lethality", "textures/entity/gael_skull.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GaelSkullEntity animatable) {
        return new ResourceLocation("lethality", "animations/gael_skull_idle.animation.json");
    }
}


