package net.farmz.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.farmz.FarmMain;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FarmLoader implements SimpleSynchronousResourceReloadListener {

    public static final Logger LOGGER = LogManager.getLogger("FarmZ");

    public static final Map<Identifier, Integer> GOLD_CHANCE_ITEMS = new HashMap<>();
    public static final Map<Identifier, Integer> CLIENT_GOLD_CHANCE_ITEMS = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return FarmMain.identifierOf("farm_loader");
    }

    @Override
    public void reload(ResourceManager resourceManager) {
        Map<Identifier, Integer> goldChanceItems = new HashMap<>();

        resourceManager.findAllResources("farmer", id -> id.getPath().endsWith(".json")).forEach((id, resources) -> {
            Map<Identifier, Integer> fileEntries = new HashMap<>();
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
                        Identifier itemId = Identifier.of(key);
                        if (!Registries.ITEM.containsId(itemId)) {
                            LOGGER.warn("Item JSON references unknown item: {}, skipping.", key);
                            continue;
                        }
                        fileEntries.put(itemId, json.get(key).getAsInt());
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to load item file {} from {}: {}", id, resource.getPackId(), e.toString());
                }
            }
            goldChanceItems.putAll(fileEntries);
        });

        GOLD_CHANCE_ITEMS.clear();
        GOLD_CHANCE_ITEMS.putAll(goldChanceItems);
    }
}
