package no.kh498.util.jackson

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import kotlin.reflect.KClass

/**
 * @author Elg
 */
fun KClass<*>.type(mapper: ObjectMapper): JavaType {
    return this.type(mapper.typeFactory)
}

fun KClass<*>.type(typeFactory: TypeFactory): JavaType {
    return this.java.type(typeFactory)
}

fun Class<*>.type(typeFactory: TypeFactory): JavaType {
    return typeFactory.constructType(this)
}
