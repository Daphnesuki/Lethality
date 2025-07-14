package net.daphne.lethality.client.model;

import net.daphne.lethality.entity.AcidicSlashEntity;
import net.daphne.lethality.entity.HFMeowrasamaSlashEntity;
import net.daphne.lethality.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.GeoModel;

public class HFMeowrasamaSlashModel extends GeoModel<HFMeowrasamaSlashEntity> {
    @Override
    public ResourceLocation getModelResource(HFMeowrasamaSlashEntity object) {
        return new ResourceLocation("lethality", "geo/hf_meowrasama_slash.geo.json");
    }

    public ResourceLocation getTextureResource(HFMeowrasamaSlashEntity entity) {
        int frame = Math.min(entity.tickCount / 1, 3);
        int mode = entity.getMode(); // directamente de la entidad

        return new ResourceLocation("lethality", "textures/entity/hf_meowrasama_slash_" + mode + "_" + frame + ".png");
    }


    @Override
    public ResourceLocation getAnimationResource(HFMeowrasamaSlashEntity animatable) {
        return null;
    }
}


