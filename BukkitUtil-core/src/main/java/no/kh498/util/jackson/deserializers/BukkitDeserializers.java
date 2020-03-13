package no.kh498.util.jackson.deserializers;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import no.kh498.util.jackson.deserializers.bean.ConfigurationSerializableDeserializer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.DelegateDeserialization;

/**
 * @author Elg
 */
public class BukkitDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config,
                                                    BeanDescription beanDesc) {
        //If the class implement the serialization interface directly
        // or if the class has a delegate we can reasonably safely use the ConfigurationSerializable deserializer
        if (ConfigurationSerializable.class.isAssignableFrom(type.getRawClass()) ||
            type.getRawClass().isAnnotationPresent(DelegateDeserialization.class)) {
            return ConfigurationSerializableDeserializer.inst;
        }
        return null;
    }
}
