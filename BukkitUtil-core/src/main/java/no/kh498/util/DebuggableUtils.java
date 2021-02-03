package no.kh498.util;

import no.kh498.bukkitutils.shaded.info.ronjenkins.slf4bukkit.ColorString;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public final class DebuggableUtils {

  private DebuggableUtils() {}

  @NotNull
  public static ColorString appendSinglePrefix(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName) {
    return appendSinglePrefix(sb, prefix, objectName, '=');
  }

  @NotNull
  public static ColorString appendSinglePrefix(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName, char suffix) {
    return sb.none(prefix).yellow(objectName).yellow(" " + suffix + " ");
  }

}
