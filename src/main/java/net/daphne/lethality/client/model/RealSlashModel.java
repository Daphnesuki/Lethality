package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.RealSlashEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RealSlashModel extends GeoModel<RealSlashEntity> {
    @Override
    public ResourceLocation getModelResource(RealSlashEntity object) {
        return new ResourceLocation("lethality", "geo/real_slash.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RealSlashEntity object) {
        return new ResourceLocation("lethality", "textures/entity/real_slash.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RealSlashEntity animatable) {
        return new ResourceLocation("lethality", "animations/acidic_slash.animation.json");
    }
}


