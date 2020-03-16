package no.kh498.util.jackson.serializers

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.ser.Serializers
import no.kh498.util.jackson.serializers.bean.ConfigurationSectionSerializer
import no.kh498.util.jackson.serializers.bean.ConfigurationSerializableSerializer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.serialization.ConfigurationSerializable

/**
 * @author Elg
 */
object BukkitSerializers : Serializers.Base() {
    override fun findSerializer(config: SerializationConfig, type: JavaType, beanDesc: BeanDescription): JsonSerializer<*>? {
        return when {
//            ConfigurationSerializable::class.java.isAssignableFrom(type.rawClass) -> {
//                ConfigurationSerializableSerializer
//            }
            ConfigurationSection::class.java.isAssignableFrom(type.rawClass) -> {
                ConfigurationSectionSerializer
            }
            else -> null
        }
    }
}
