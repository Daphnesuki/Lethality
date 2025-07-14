package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.AcidicSlashEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AcidicSlashModel extends GeoModel<AcidicSlashEntity> {
    @Override
    public ResourceLocation getModelResource(AcidicSlashEntity object) {
        return new ResourceLocation("lethality", "geo/acidic_slash.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AcidicSlashEntity object) {
        return new ResourceLocation("lethality", "textures/entity/acidic_slash.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AcidicSlashEntity animatable) {
        return new ResourceLocation("lethality", "animations/acidic_slash.animation.json");
    }
}


