package coda.gonefishing.client;

import coda.gonefishing.NaughtyList;
import coda.gonefishing.client.model.CandyCaneModel;
import coda.gonefishing.client.model.NutcrackerGeneralModel;
import coda.gonefishing.client.model.NutcrackerModel;
import coda.gonefishing.client.model.WoodenHorseModel;
import coda.gonefishing.client.renderer.NutcrackerGeneralRenderer;
import coda.gonefishing.client.renderer.NutcrackerRenderer;
import coda.gonefishing.client.renderer.ThrownCandyCaneRenderer;
import coda.gonefishing.client.renderer.WoodenHorseRenderer;
import coda.gonefishing.registry.NLEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NaughtyList.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenders(FMLClientSetupEvent e) {
        EntityRenderers.register(NLEntities.NUTCRACKER.get(), NutcrackerRenderer::new);
        EntityRenderers.register(NLEntities.WOODEN_HORSE.get(), WoodenHorseRenderer::new);
        EntityRenderers.register(NLEntities.CANDY_CANE.get(), ThrownCandyCaneRenderer::new);
        EntityRenderers.register(NLEntities.NUTCRACKER_GENERAL.get(), NutcrackerGeneralRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(NLModelLayers.WOODEN_HORSE, WoodenHorseModel::createBodyLayer);
        e.registerLayerDefinition(NLModelLayers.NUTCRACKER, NutcrackerModel::createBodyLayer);
        e.registerLayerDefinition(NLModelLayers.CANDY_CANE, CandyCaneModel::createBodyLayer);
        e.registerLayerDefinition(NLModelLayers.NUTCRACKER_GENERAL, NutcrackerGeneralModel::createBodyLayer);
    }
}
