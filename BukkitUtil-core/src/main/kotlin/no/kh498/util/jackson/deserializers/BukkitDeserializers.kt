package no.kh498.util.jackson.deserializers

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.Deserializers
import no.kh498.util.jackson.BukkitModule
import no.kh498.util.jackson.deserializers.bean.ConfigurationSerializableDeserializer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.DelegateDeserialization

/**
 * @author Elg
 */
class BukkitDeserializers(private val bukkitModule: BukkitModule) : Deserializers.Base() {
    override fun findBeanDeserializer(type: JavaType, config: DeserializationConfig,
                                      beanDesc: BeanDescription): JsonDeserializer<*>? {
        //If the class implement the serialization interface directly
        // or if the class has a delegate we can reasonably safely use the ConfigurationSerializable deserializer
        return if (ConfigurationSerializable::class.java.isAssignableFrom(type.rawClass) ||
                type.rawClass.isAnnotationPresent(DelegateDeserialization::class.java)) {
            ConfigurationSerializableDeserializer
        } else if (bukkitModule.colorizeStringsByDefault && String::class.java.isAssignableFrom(type.rawClass)) {
            ColoredStringDeserializer
        } else {
            null
        }
    }
}
