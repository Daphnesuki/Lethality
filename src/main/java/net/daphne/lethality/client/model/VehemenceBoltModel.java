package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.VehemenceBoltEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VehemenceBoltModel extends GeoModel<VehemenceBoltEntity> {
    @Override
    public ResourceLocation getModelResource(VehemenceBoltEntity object) {
        return new ResourceLocation("lethality", "geo/vehemence_bolt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VehemenceBoltEntity object) {
        return new ResourceLocation("lethality", "textures/entity/vehemence_bolt.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VehemenceBoltEntity animatable) {
        return null;
    }
}


