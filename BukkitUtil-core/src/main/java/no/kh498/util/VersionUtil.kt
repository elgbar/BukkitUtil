package no.kh498.util

import org.bukkit.Bukkit

/**
 * @author Elg
 */
object VersionUtil {
    const val v1_15_R1 = "v1_15_R1"
    const val v1_14_R1 = "v1_14_R1"
    const val v1_13_R2 = "v1_13_R2"
    const val v1_13_R1 = "v1_13_R1"
    const val v1_12_R1 = "v1_12_R1"
    const val v1_11_R1 = "v1_11_R1"
    const val v1_10_R1 = "v1_10_R1"
    const val v1_9_R2 = "v1_9_R2"
    const val v1_9_R1 = "v1_9_R1"
    const val v1_8_R3 = "v1_8_R3"
    const val v1_8_R2 = "v1_8_R2"
    const val v1_8_R1 = "v1_8_R1"
    const val v1_7_R4 = "v1_7_R4"
    const val v1_7_R3 = "v1_7_R3"
    const val v1_7_R2 = "v1_7_R2"
    const val v1_7_R1 = "v1_7_R1"
    const val BUKKIT_PACKAGE = "org.bukkit"
    const val CB_PACKAGE = "$BUKKIT_PACKAGE.craftbukkit"
    const val NMS_PACKAGE = "net.minecraft.server"

    /**
     * @return The net minecraft server version running this code in the form of 'v1_x_y_Rz'
     */
    @JvmStatic
    val nmsVersion: String = Bukkit.getServer().javaClass.getPackage().name.replace(CB_PACKAGE, "").replaceFirst(".".toRegex(), "")

    @JvmStatic
    @Throws(ClassNotFoundException::class)
    fun getVersionedClass(packageName: String, className: String): Class<*> {
        val fullClassPath = "$packageName.$nmsVersion.$className"
        return Class.forName(fullClassPath)
    }

    @JvmStatic
    @Throws(ClassNotFoundException::class)
    fun getCBClass(className: String): Class<*> {
        return getVersionedClass(CB_PACKAGE, className)
    }
}
