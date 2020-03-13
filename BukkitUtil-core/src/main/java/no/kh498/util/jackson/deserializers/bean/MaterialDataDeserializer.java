package no.kh498.util.jackson.deserializers.bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import org.bukkit.material.MaterialData;

import java.io.IOException;

/**
 * @author Elg
 */
public class MaterialDataDeserializer extends StdDeserializer<MaterialData> {

    protected MaterialDataDeserializer() {
        super(MaterialData.class);
    }

    @Override
    public MaterialData deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException, JsonProcessingException {
        return null;
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer)
    throws IOException {
        return super.deserializeWithType(p, ctxt, typeDeserializer);
    }
}
