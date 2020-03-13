package no.kh498.util.jackson.serializers.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.bukkit.configuration.serialization.ConfigurationSerialization.SERIALIZED_TYPE_KEY;
import static org.bukkit.configuration.serialization.ConfigurationSerialization.getAlias;

/**
 * @author Elg
 */
public class ConfigurationSerializableSerializer extends StdSerializer<ConfigurationSerializable> {

    static { inst = new ConfigurationSerializableSerializer(); }

    public static final ConfigurationSerializableSerializer inst;

    protected ConfigurationSerializableSerializer() {
        super(ConfigurationSerializable.class);
    }

    @Override
    public void serialize(ConfigurationSerializable ser, JsonGenerator gen, SerializerProvider provider)
    throws IOException {


        Map<String, Object> map = new LinkedHashMap<>();
        map.put(SERIALIZED_TYPE_KEY, getAlias(ser.getClass()));
        map.putAll(ser.serialize());
        gen.writeObject(map);
    }

//    private Object serializeConfSer(Object ser) {
//
//    }
//
//    private class ConfigurationSerializableWrapper implements ConfigurationSerializable {
//
//        private final Object o;
//
//        ConfigurationSerializableWrapper(Object o) {this.o = o;}
//
//        @Override
//        public Map<String, Object> serialize() {
//            Map<String, Object> map = new HashMap<>();
//            YamlConfiguration conf = new YamlConfiguration();
//            conf. return map;
//        }
//    }
}
