package no.kh498.util.jackson.deserializers.bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Elg
 */
public class ConfigurationSerializableDeserializer extends StdDeserializer<ConfigurationSerializable> {

    static {
        inst = new ConfigurationSerializableDeserializer();
    }

    public static final ConfigurationSerializableDeserializer inst;

    private ConfigurationSerializableDeserializer() {
        super(ConfigurationSerializable.class);
    }


    @Override
    public ConfigurationSerializable deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializeConfSection(parser, context);
    }

    @Override
    public ConfigurationSerializable deserializeWithType(JsonParser parser, DeserializationContext context,
                                                         TypeDeserializer typeDeserializer) throws IOException {
        return deserializeConfSection(parser, context);
    }

    private ConfigurationSerializable deserializeConfSection(JsonParser parser, DeserializationContext context)
    throws IOException {
        Map<String, Object> map;
        try {
            //noinspection unchecked
            map = ((Map<String, Object>) context.readValue(parser, Map.class));
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                "Expected to find a Map<String,Object> but found" + context.readTree(parser).getNodeType(), e);
        }

        return (ConfigurationSerializable) recSer(map);
    }

    private Object recSer(Map<String, Object> map) {

        Map<String, Object> serializedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                //noinspection unchecked
                serializedMap.put(entry.getKey(), recSer((Map<String, Object>) entry.getValue()));
            }
            else {
                serializedMap.put(entry.getKey(), entry.getValue());
            }
        }
        return ConfigurationSerialization.deserializeObject(serializedMap);
    }
}
