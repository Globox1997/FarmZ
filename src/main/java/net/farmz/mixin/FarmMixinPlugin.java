package net.farmz.mixin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.fabricmc.loader.api.FabricLoader;

public class FarmMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!FabricLoader.getInstance().isModLoaded("farm_and_charm")) {
            if (mixinClassName.contains("BarleyCropBlockMixin")) {
                return false;
            }
            if (mixinClassName.contains("ClimbingCropBlockMixin")) {
                return false;
            }
            if (mixinClassName.contains("CornCropBlockMixin")) {
                return false;
            }
            if (mixinClassName.contains("LettuceCropBlockMixin")) {
                return false;
            }
            if (mixinClassName.contains("OatCropBlockMixin")) {
                return false;
            }
            if (mixinClassName.contains("OnionCropBlockMixin")) {
                return false;
            }
            if (mixinClassName.contains("StrawberryCropBlockMixin")) {
                return false;
            }
            if (mixinClassName.contains("TomatoCropHeadBlockMixin")) {
                return false;
            }
            if (mixinClassName.contains("TomatoCropBodyBlockMixin")) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

}
