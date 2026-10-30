package net.farmz.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.farmz.FarmMain;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CropGrowthLoader implements SimpleSynchronousResourceReloadListener {

    public static final Map<Block, Integer> CROP_GROWTH_TICKS = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return FarmMain.identifierOf("crop_growth_loader");
    }

    @Override
    public void reload(ResourceManager resourceManager) {
        Map<Block, Integer> cropGrowthTicks = new HashMap<>();

        resourceManager.findAllResources("crop_growth", id -> id.getPath().endsWith(".json")).forEach((id, resources) -> {
            Map<Block, Integer> fileEntries = new HashMap<>();
            for (Resource resource : resources) {
                try (InputStream stream = resource.getInputStream()) {
                    JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                    if (json.has("replace") && json.get("replace").getAsBoolean()) {
                        fileEntries.clear();
                    }

                    for (String key : json.keySet()) {
                        if (key.equals("replace")) {
                            continue;
                        }
                        Identifier blockId = Identifier.of(key);
                        if (!Registries.BLOCK.containsId(blockId)) {
                            FarmLoader.LOGGER.warn("Crop growth JSON references unknown block: {}, skipping.", key);
                            continue;
                        }
                        Block block = Registries.BLOCK.get(blockId);
                        if (!(block instanceof CropBlock)) {
                            FarmLoader.LOGGER.warn("Crop growth JSON references block {} which is not a crop, skipping.", key);
                            continue;
                        }
                        int ticks = json.get(key).getAsInt();
                        if (ticks > 0) {
                            fileEntries.put(block, ticks);
                        } else {
                            fileEntries.remove(block);
                        }
                    }
                } catch (Exception e) {
                    FarmLoader.LOGGER.error("Failed to load crop growth file {} from {}: {}", id, resource.getPackId(), e.toString());
                }
            }
            cropGrowthTicks.putAll(fileEntries);
        });

        CROP_GROWTH_TICKS.clear();
        CROP_GROWTH_TICKS.putAll(cropGrowthTicks);
    }
}
