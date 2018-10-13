package no.kh498.util;

import info.ronjenkins.slf4bukkit.ColorString;

public interface Debuggable {

    /**
     * @param sb
     *     The StringBuilding to addend to, will be on an empty line
     *
     * @return the given StringBuilder, {@code sb}, and the last appended item must be a newline
     */
    default ColorString appendDebugInfo(ColorString sb) {
        return appendDebugInfo(sb, "");
    }

    /**
     * @param sb
     *     The StringBuilding to addend to, will be on an empty line
     * @param prefix
     *     The prefix that must be added on each newline (excluding the last)
     *
     * @return the given StringBuilder, {@code sb}, and the last appended item must be a newline (without the prefix)
     */
    ColorString appendDebugInfo(ColorString sb, String prefix);

    /**
     *
     * @return
     */
    default String showDebugInfo() {
        return appendDebugInfo(new ColorString()).toString();
    }
}
