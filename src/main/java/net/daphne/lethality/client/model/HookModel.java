package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.HookEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HookModel extends GeoModel<HookEntity> {
    @Override
    public ResourceLocation getModelResource(HookEntity animatable) {
        return new ResourceLocation("lethality", "geo/hook_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HookEntity animatable) {
        return new ResourceLocation("lethality", "textures/entity/hook.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HookEntity animatable) {
        return null;
    }
}
