package no.kh498.util.jackson.serializers;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers;
import no.kh498.util.jackson.serializers.bean.ConfigurationSectionSerializer;
import no.kh498.util.jackson.serializers.bean.ConfigurationSerializableSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * @author Elg
 */
public class BukkitSerializers extends Serializers.Base {

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        if (ConfigurationSerializable.class.isAssignableFrom(type.getRawClass())) {
            return ConfigurationSerializableSerializer.inst;
        }
        if (ConfigurationSection.class.isAssignableFrom(type.getRawClass())) {
            return ConfigurationSectionSerializer.inst;
        }
        return null;
    }

}
