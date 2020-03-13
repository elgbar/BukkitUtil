package no.kh498.util.jackson.deserializers;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import no.kh498.util.jackson.deserializers.key.EnchantmentKeyDeserializer;
import org.bukkit.enchantments.Enchantment;

/**
 * @author Elg
 */
public class BukkitKeyDeserializers implements KeyDeserializers {

    @Override
    public KeyDeserializer findKeyDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc)
    throws JsonMappingException {

        if (Enchantment.class.isAssignableFrom(type.getRawClass())) {
            return EnchantmentKeyDeserializer.inst;
        }
        return null;
    }
}
