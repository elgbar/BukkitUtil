package no.kh498.util;

import org.bukkit.Bukkit;

/**
 * @author Elg
 */
public final class VersionUtil {


    public static final String v1_15_R1 = "v1_15_R1";

    public static final String v1_14_R1 = "v1_14_R1";

    public static final String v1_13_R2 = "v1_13_R2";
    public static final String v1_13_R1 = "v1_13_R1";

    public static final String v1_12_R1 = "v1_12_R1";

    public static final String v1_11_R1 = "v1_11_R1";

    public static final String v1_10_R1 = "v1_10_R1";

    public static final String v1_9_R2 = "v1_9_R2";
    public static final String v1_9_R1 = "v1_9_R1";

    public static final String v1_8_R3 = "v1_8_R3";
    public static final String v1_8_R2 = "v1_8_R2";
    public static final String v1_8_R1 = "v1_8_R1";

    public static final String v1_7_R4 = "v1_7_R4";
    public static final String v1_7_R3 = "v1_7_R3";
    public static final String v1_7_R2 = "v1_7_R2";
    public static final String v1_7_R1 = "v1_7_R1";

    public static final String BUKKIT_PACKAGE = "org.bukkit";
    public static final String CB_PACKAGE = BUKKIT_PACKAGE + ".craftbukkit";
    public static final String NMS_PACKAGE = "net.minecraft.server";


    /**
     * @return The net minecraft server version running this code in the form of 'v1_x_Ry'
     */
    public static String getNmsVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(CB_PACKAGE, "").replaceFirst(".", "");
    }

    public static Class<?> getVersionedClass(String packageName, String className) throws ClassNotFoundException {
        return Class.forName(packageName + "." + getNmsVersion() + "." + className);
    }

    public static Class<?> getCBClass(String className) throws ClassNotFoundException {
        return getVersionedClass(CB_PACKAGE, className);
    }

    public static Class<?> getNMSClass(String className) throws ClassNotFoundException {
        return getVersionedClass(NMS_PACKAGE, className);
    }

    public static Class<?> getBukkitClass(String className) throws ClassNotFoundException {
        return getVersionedClass(BUKKIT_PACKAGE, className);
    }
}
