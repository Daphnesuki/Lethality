package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.ForbiddenScytheEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ForbiddenScytheModel extends GeoModel<ForbiddenScytheEntity> {
    @Override
    public ResourceLocation getModelResource(ForbiddenScytheEntity object) {
        return new ResourceLocation("lethality", "geo/forbidden_scythe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ForbiddenScytheEntity object) {
        return new ResourceLocation("lethality", "textures/entity/forbidden_scythe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ForbiddenScytheEntity animatable) {
        return null;
    }
}


