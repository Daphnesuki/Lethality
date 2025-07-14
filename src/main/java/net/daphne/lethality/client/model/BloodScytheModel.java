package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.BloodScytheEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BloodScytheModel extends GeoModel<BloodScytheEntity> {
    @Override
    public ResourceLocation getModelResource(BloodScytheEntity object) {
        return new ResourceLocation("lethality", "geo/blood_scythe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BloodScytheEntity object) {
        return new ResourceLocation("lethality", "textures/entity/blood_scythe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BloodScytheEntity animatable) {
        return null;
    }
}


