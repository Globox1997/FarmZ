package net.farmz.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.farmz.FarmMain;
import net.farmz.entity.BeethuutEntity;
import net.farmz.entity.model.BeethuutModel;
import net.farmz.init.RenderInit;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BeethuutRenderer extends MobEntityRenderer<BeethuutEntity, BeethuutModel<BeethuutEntity>> {
    private static final Identifier TEXTURE = FarmMain.identifierOf("textures/entity/beethuut.png");

    public BeethuutRenderer(EntityRendererFactory.Context context) {
        super(context, new BeethuutModel<>(context.getPart(RenderInit.BEETHUUT_LAYER)), 0.35F);
    }

    @Override
    public Identifier getTexture(BeethuutEntity beethuutEntity) {
        return TEXTURE;
    }
}

