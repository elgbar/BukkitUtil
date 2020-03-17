package no.kh498.util;

import org.jetbrains.annotations.Nullable;

import static no.kh498.util.VersionUtil.getNmsVersion;

/**
 * An exception to force developers to handle NMS exception
 *
 * @author Elg
 */
public class UnsupportedNMSVersionException extends RuntimeException {

    public UnsupportedNMSVersionException() {
        this(null);
    }

    public UnsupportedNMSVersionException(@Nullable Exception e) {
        super("Error while executing NMS code, the following version is not yet supported: " + getNmsVersion(), e);
    }
}
