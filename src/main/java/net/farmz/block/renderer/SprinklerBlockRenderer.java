package net.farmz.block.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.farmz.FarmMain;
import net.farmz.block.SprinklerBlock;
import net.farmz.block.entity.SprinklerBlockEntity;
import net.farmz.init.RenderInit;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SprinklerBlockRenderer implements BlockEntityRenderer<SprinklerBlockEntity> {

    private static final Identifier TEXTURE = FarmMain.identifierOf("textures/entity/sprinkler.png");

    private final ModelPart moveable;
    private final ModelPart box;
    private final ModelPart water;

    public SprinklerBlockRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(RenderInit.SPRINKLER_LAYER);
        this.moveable = modelPart.getChild("moveable");
        this.box = modelPart.getChild("box");
        this.water = modelPart.getChild("water");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("moveable",
                ModelPartBuilder.create().uv(0, 47).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)).uv(43, 11).cuboid(-1.0F, 5.0F, -7.0F, 2.0F, 2.0F, 14.0F, new Dilation(0.0F))
                        .uv(22, 47).cuboid(-2.0F, 4.0F, -9.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.1F)).uv(9, 47).cuboid(-2.0F, 4.0F, 7.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.1F)).uv(43, 0)
                        .cuboid(7.0F, 4.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.1F)).uv(0, 0).cuboid(-9.0F, 4.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.1F)).uv(37, 28)
                        .cuboid(-7.0F, 5.0F, -1.0F, 15.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 16.0F, 0.0F));

        modelPartData.addChild("water", ModelPartBuilder.create().uv(37, 35).cuboid(-6.0F, -8.0F, -6.0F, 12.0F, 1.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 25.0F, 0.0F));
        modelPartData.addChild("box", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -16.0F, -7.0F, 14.0F, 10.0F, 14.0F, new Dilation(0.0F)).uv(0, 25).cuboid(-6.0F, -15.0F, -6.0F, 12.0F, 9.0F,
                12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void render(SprinklerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrices.push();

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));
        matrices.translate(0.5D, -0.5D, 0.5D);
        box.render(matrices, vertexConsumer, light, overlay);
        moveable.yaw = (float) MathHelper.lerp(tickDelta, entity.getLastRotation(), entity.getRotation());
        moveable.render(matrices, vertexConsumer, light, overlay);

        if (entity.getWorld() != null && entity.getWorld().getBlockState(entity.getPos()).contains(SprinklerBlock.WATER) && entity.getWorld().getBlockState(entity.getPos()).get(SprinklerBlock.WATER)) {
            int i = entity.getWorld() != null ? BiomeColors.getWaterColor(entity.getWorld(), entity.getPos()) : -1;
            water.render(matrices, vertexConsumer, light, overlay, i);
        }

        matrices.pop();
    }
}
