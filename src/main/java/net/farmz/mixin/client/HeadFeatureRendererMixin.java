package net.farmz.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.farmz.FarmMain;
import net.farmz.init.ItemInit;
import net.farmz.item.model.FarmersHatModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HeadFeatureRenderer.class)
public abstract class HeadFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    public HeadFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Shadow
    @Final
    private float scaleX;
    @Shadow
    @Final
    private float scaleY;
    @Shadow
    @Final
    private float scaleZ;
    @Unique
    private static final Identifier TEXTURE = FarmMain.identifierOf("textures/item/farmers_hat_3d.png");

    @Unique
    private static final FarmersHatModel farmersHatModel = new FarmersHatModel(FarmersHatModel.getTexturedModelData().createModel());

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void renderMixin(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l,
                             CallbackInfo info) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (!itemStack.isEmpty() && itemStack.getItem() == ItemInit.FARMERS_HAT) {
            matrixStack.push();
            matrixStack.scale(this.scaleX, this.scaleY, this.scaleZ);
            ((ModelWithHead) this.getContextModel()).getHead().rotate(matrixStack);
            matrixStack.translate(0.0D, -2.05D, 0.0D);
            matrixStack.scale(1.19F, 1.19F, 1.19F);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, HeadFeatureRendererMixin.farmersHatModel.getLayer(TEXTURE), itemStack.hasGlint());
            HeadFeatureRendererMixin.farmersHatModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
            info.cancel();
        }
    }
}
