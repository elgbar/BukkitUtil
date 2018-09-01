package no.kh498.util;

import org.bukkit.Bukkit;

public class VersionUtil {
    
    public static final String CB_PACKAGE = "org.bukkit.craftbukkit";

    /**
     * @return The net minecraft server version running this code in the form of 'v1_x_x_Rx'
     */
    public static String getNmsVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(CB_PACKAGE, "").replaceFirst(".", "");
    }
}
