package no.kh498.util;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Elg
 */
public class EntityUtil {

    /**
     * @return The Metadata set by the given plugin
     */
    public static @Nullable
    MetadataValue getPluginMetadata(@NotNull Entity entity, @Nullable String metadataKey, @NotNull Plugin plugin) {
        for (MetadataValue metadata : entity.getMetadata(metadataKey)) {
            if (metadata.getOwningPlugin() == plugin) {
                return metadata;
            }
        }
        return null;
    }

    /**
     * @return If the player has metadata from the given plugin
     */
    public static boolean hasPluginMetadata(@NotNull Entity entity, @Nullable String metadataKey,
                                            @NotNull Plugin plugin) {
        return getPluginMetadata(entity, metadataKey, plugin) == null;
    }
}
