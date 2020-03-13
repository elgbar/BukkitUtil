package no.kh498.util.jackson.serializers.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bukkit.configuration.ConfigurationSection;

import java.io.IOException;

/**
 * @author Elg
 */
public class ConfigurationSectionSerializer extends StdSerializer<ConfigurationSection> {


    static { inst = new ConfigurationSectionSerializer(); }

    public static final ConfigurationSectionSerializer inst;

    protected ConfigurationSectionSerializer() {
        super(ConfigurationSection.class);
    }

    @Override
    public void serialize(ConfigurationSection value, JsonGenerator gen, SerializerProvider provider)
    throws IOException {
        gen.writeObject(value.getValues(false));
    }

    @Override
    public void serializeWithType(ConfigurationSection value, JsonGenerator gen, SerializerProvider serializers,
                                  TypeSerializer typeSer) throws IOException {
        gen.writeObject(value.getValues(false));
    }
}
