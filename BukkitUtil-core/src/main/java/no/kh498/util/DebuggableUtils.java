package no.kh498.util;

import info.ronjenkins.slf4bukkit.ColorString;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public final class DebuggableUtils {

  private DebuggableUtils() {}

  public static ColorString appendSinglePrefix(@NotNull ColorString sb, @NotNull String prefix,
                                               @NotNull String objectName) {
    return sb.none(prefix).yellow(objectName + " = ");
  }

}
