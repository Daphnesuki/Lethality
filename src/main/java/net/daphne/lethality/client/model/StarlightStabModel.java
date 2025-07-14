package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.StarlightStabEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class StarlightStabModel extends GeoModel<StarlightStabEntity> {
    @Override
    public ResourceLocation getModelResource(StarlightStabEntity object) {
        return new ResourceLocation("lethality", "geo/starlight_stab.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StarlightStabEntity object) {
        int variant = object.getTextureVariant();
        return new ResourceLocation("lethality", "textures/entity/starlight_stab_" + variant + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(StarlightStabEntity animatable) {
        return null;
    }
}


