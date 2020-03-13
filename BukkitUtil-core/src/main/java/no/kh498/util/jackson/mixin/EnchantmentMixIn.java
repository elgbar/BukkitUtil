package no.kh498.util.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import no.kh498.util.jackson.deserializers.key.EnchantmentKeyDeserializer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.EnchantmentTarget;

/**
 * @author Elg
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = ConfigurationSerialization.SERIALIZED_TYPE_KEY)
@JsonDeserialize(keyUsing = EnchantmentKeyDeserializer.class)
public abstract class EnchantmentMixIn {

    @JsonValue
    public abstract String getId();

    @JsonIgnore
    public abstract String getName();

    @JsonIgnore
    public abstract int getMaxLevel();

    @JsonIgnore
    public abstract int getStartLevel();

    @JsonIgnore
    public abstract EnchantmentTarget getItemTarget();


}
