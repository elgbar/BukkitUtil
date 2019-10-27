package no.kh498.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signals to {@link InterfaceImplManager} that this class should not be registered
 *
 * @author Elg
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreImplementation { }
