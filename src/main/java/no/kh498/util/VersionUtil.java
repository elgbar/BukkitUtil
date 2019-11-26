package no.kh498.util;

import org.bukkit.Bukkit;

/**
 * @author Elg
 */
public class VersionUtil {

    public static final String v1_12_R1 = "v1_12_R1";

    public static final String v1_8_R3 = "v1_8_R3";
    public static final String v1_8_R2 = "v1_8_R2";
    public static final String v1_8_R1 = "v1_8_R1";

    public static final String CB_PACKAGE = "org.bukkit.craftbukkit";

    /**
     * @return The net minecraft server version running this code in the form of 'v1_x_y_Rz'
     */
    public static String getNmsVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(CB_PACKAGE, "").replaceFirst(".", "");
    }
}
