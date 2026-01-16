package net.farmz.item.model;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class FarmersHatModel extends Model {

    private final ModelPart hat;

    public FarmersHatModel(ModelPart root) {
        super(RenderLayer::getArmorCutoutNoCull);
        this.hat = root.getChild("hat");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("hat",
                ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.hat.render(matrices, vertices, light, overlay, color);
    }

}
