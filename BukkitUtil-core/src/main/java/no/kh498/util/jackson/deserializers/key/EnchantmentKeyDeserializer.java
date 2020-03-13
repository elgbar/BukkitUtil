package no.kh498.util.jackson.deserializers.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.bukkit.enchantments.Enchantment;

/**
 * @author Elg
 */
public class EnchantmentKeyDeserializer extends KeyDeserializer {

    static {
        inst = new EnchantmentKeyDeserializer();
    }

    public static final EnchantmentKeyDeserializer inst;

    private EnchantmentKeyDeserializer() { }

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) {
        int id;
        try {
            id = Integer.parseInt(key);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Expected to find an integer but found " + key);
        }
        //noinspection deprecation
        return Enchantment.getById(id);
    }
}
