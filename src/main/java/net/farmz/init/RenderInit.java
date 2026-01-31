package net.farmz.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.farmz.FarmMain;
import net.farmz.block.renderer.SprinklerBlockRenderer;
import net.farmz.entity.model.BeethuutModel;
import net.farmz.entity.render.BeethuutRenderer;
import net.farmz.particle.SprinklerParticle;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@Environment(EnvType.CLIENT)
public class RenderInit {

    public static final EntityModelLayer SPRINKLER_LAYER = new EntityModelLayer(FarmMain.identifierOf("sprinkler_render_layer"), "sprinkler_render_layer");

    public static SimpleParticleType SPRINKLER_PARTICLE;

    public static final EntityModelLayer BEETHUUT_LAYER = new EntityModelLayer(FarmMain.identifierOf("beethuut_render_layer"), "beethuut_render_layer");

    public static void init() {
        EntityModelLayerRegistry.registerModelLayer(SPRINKLER_LAYER, SprinklerBlockRenderer::getTexturedModelData);
        // Blocks
        BlockEntityRendererFactories.register(BlockInit.SPRINKLER_ENTITY, SprinklerBlockRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GLISTERING_SWEET_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GOLDEN_POTATOES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GOLDEN_BEETROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GOLDEN_CARROTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GLISTERING_CAVE_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GLISTERING_CAVE_VINES_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GOLDEN_CHORUS_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GOLDEN_WHEAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GOLDEN_COCOA, RenderLayer.getCutout());

        // Particle
        SPRINKLER_PARTICLE = Registry.register(Registries.PARTICLE_TYPE, FarmMain.identifierOf("sprinkler"), FabricParticleTypes.simple());
        ParticleFactoryRegistry.getInstance().register(SPRINKLER_PARTICLE, SprinklerParticle.DefaultFactory::new);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && world.getBlockEntity(pos) != null ? BiomeColors.getWaterColor(world, pos) : 3708358, BlockInit.SPRINKLER);

        EntityRendererRegistry.register(EntityInit.BEETHUUT, BeethuutRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BEETHUUT_LAYER, BeethuutModel::getTexturedModelData);
    }

}
