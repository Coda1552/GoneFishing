package coda.gonefishing.client.renderer;

import coda.gonefishing.NaughtyList;
import coda.gonefishing.client.NLModelLayers;
import coda.gonefishing.client.model.NutcrackerModel;
import coda.gonefishing.common.entity.Nutcracker;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NutcrackerRenderer extends MobRenderer<Nutcracker, NutcrackerModel<Nutcracker>> {
    private static final ResourceLocation TEX = new ResourceLocation(NaughtyList.MOD_ID, "textures/entity/nutcracker.png");

    public NutcrackerRenderer(EntityRendererProvider.Context p_173956_) {
        super(p_173956_, new NutcrackerModel<>(p_173956_.bakeLayer(NLModelLayers.NUTCRACKER)), 0.45F);
    }

    public ResourceLocation getTextureLocation(Nutcracker p_114029_) {
        return TEX;
    }
}