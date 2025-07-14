package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.DevilsScytheEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DevilsScytheModel extends GeoModel<DevilsScytheEntity> {
    @Override
    public ResourceLocation getModelResource(DevilsScytheEntity object) {
        return new ResourceLocation("lethality", "geo/devils_scythe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DevilsScytheEntity object) {
        return new ResourceLocation("lethality", "textures/entity/devils_scythe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DevilsScytheEntity animatable) {
        return null;
    }
}


