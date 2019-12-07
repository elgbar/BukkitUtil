package no.kh498.util.countdown.api.timeFormat;

import org.jetbrains.annotations.NotNull;

/**
 * {@inheritDoc}
 *
 * @author Elg
 */
public class FormatSeconds implements TimeFormat {

    private final String seconds;

    public FormatSeconds() {
        this("seconds");
    }

    public FormatSeconds(final String seconds) {
        this.seconds = seconds;
    }

    @NotNull
    @Override
    public String formatTime(final long timeLeft) {
        return timeLeft / 1000L + " " + seconds;
    }

    @Override
    public long delay() {
        return 10L;
    }
}
