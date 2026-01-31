package net.farmz.entity.model;

import com.google.common.collect.ImmutableList;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.farmz.entity.BeethuutEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BeethuutModel<T extends BeethuutEntity> extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart crest;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public BeethuutModel(ModelPart root) {
        this.body = root.getChild("body");
        this.rightLeg = body.getChild("rightLeg");
        this.leftLeg = body.getChild("leftLeg");
        this.crest = body.getChild("crest");
        this.rightWing = body.getChild("rightWing");
        this.leftWing = body.getChild("leftWing");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new Dilation(0.0F))
                .uv(24, 0).cuboid(-4.0F, -5.0F, -6.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, 1.0F, 3.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 20.0F, 0.0F));

        ModelPartData rightLeg = body.addChild("rightLeg", ModelPartBuilder.create().uv(24, 4).mirrored().cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.5F, 2.0F, 1.0F));

        ModelPartData leftLeg = body.addChild("leftLeg", ModelPartBuilder.create().uv(24, 4).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 2.0F, 1.0F));

        ModelPartData crest = body.addChild("crest", ModelPartBuilder.create().uv(0, 12).mirrored().cuboid(0.0F, -5.0F, -4.0F, 0.0F, 6.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, -10.0F, 0.0F));

        ModelPartData cube_r1 = crest.addChild("cube_r1", ModelPartBuilder.create().uv(0, 12).cuboid(0.0F, -15.0F, -4.0F, 0.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 10.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData rightWing = body.addChild("rightWing", ModelPartBuilder.create().uv(0, 21).mirrored().cuboid(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, -3.0F, -1.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData leftWing = body.addChild("leftWing", ModelPartBuilder.create().uv(0, 21).cuboid(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -3.0F, -1.0F, 0.0F, 0.0F, 0.3927F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T fungus, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;

        this.leftWing.roll = 0.3927F+ MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;
        this.rightWing.roll =-0.3927F -MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;

        this.body.roll =  MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.3F;
    }

}
