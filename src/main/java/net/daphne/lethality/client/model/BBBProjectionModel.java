package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.BBBProjectionEntity;
import net.daphne.lethality.entity.StarlightStabEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BBBProjectionModel extends GeoModel<BBBProjectionEntity> {
    @Override
    public ResourceLocation getModelResource(BBBProjectionEntity object) {
        return new ResourceLocation("lethality", "geo/bbb_projection.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BBBProjectionEntity object) {
        return new ResourceLocation("lethality", "textures/entity/bbb_projection.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BBBProjectionEntity animatable) {
        return null;
    }
}


