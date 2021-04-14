// -----------------------
// Coded by Pandadoxo
// on 09.04.2021 at 12:12 
// -----------------------

package de.meloneoderso.melonencb.utils;

import com.google.gson.*;
import de.meloneoderso.melonencb.MelonenCB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;
import java.util.logging.Level;

public class JsonUtil {
    private static final Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getAnnotation(Exclude.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        gsonBuilder.registerTypeAdapter(Location.class, (JsonSerializer<Location>) (location, type, jsonSerializationContext) -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("world", location.getWorld().getUID().toString());
            jsonObject.addProperty("x", location.getX());
            jsonObject.addProperty("y", location.getY());
            jsonObject.addProperty("z", location.getZ());
            return jsonObject;
        });

        gsonBuilder.registerTypeAdapter(Location.class, (JsonDeserializer<Location>) (jsonElement, type, jsonDeserializationContext) -> {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = (JsonObject) jsonElement;
                if (jsonObject.has("world") && jsonObject.has("x") && jsonObject.has("y") && jsonObject.has("z")) {
                    try {
                        UUID uuid = UUID.fromString(jsonObject.get("world").getAsString());
                        World world = Bukkit.getWorld(uuid);
                        if (world != null) {
                            return new Location(world, jsonObject.get("x").getAsDouble(), jsonObject.get("y").getAsDouble(), jsonObject.get("z").getAsDouble());
                        }
                    } catch (Exception e) {
                        MelonenCB.getInstance().getLogger().log(Level.SEVERE, "Cannot parse Location (" + jsonObject.toString() + "): " + e.getMessage(),
                                e);
                        return null;
                    }
                }
            }
            return null;
        });

        gson = gsonBuilder.create();
    }

    public static Gson getGson() {
        return gson;
    }

}
