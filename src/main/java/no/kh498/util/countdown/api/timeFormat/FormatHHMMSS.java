package no.kh498.util.countdown.api.timeFormat;

/**
 * {@inheritDoc}
 *
 * @author karl henrik
 */
public class FormatHHMMSS implements TimeFormat {

    private final String hoursStr;
    private final String minutesStr;
    private final String secondsStr;

    public FormatHHMMSS() {
        this("hours", "minutes", "seconds");
    }

    public FormatHHMMSS(final String hours, final String minutes, final String seconds) {

        this.hoursStr = hours;
        this.minutesStr = minutes;
        this.secondsStr = seconds;
    }

    private static void ensureDoubleDig(final StringBuilder sb, final long toCheck) {
        if (toCheck < 10) {
            sb.append("0");
        }
    }

    @Override
    public String formatTime(final long timeLeft) {
        final long time = timeLeft / 1000L; //time in seconds

        final long hours = time / 3600;
        final long secondsLeft = time - (hours * 3600);
        final long minutes = secondsLeft / 60;
        final long seconds = secondsLeft - (minutes * 60);

        final StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            ensureDoubleDig(sb, hours);
            sb.append(hours).append(" ").append(this.hoursStr).append(" ");
        }
        if (minutes > 0) {
            ensureDoubleDig(sb, minutes);
            sb.append(minutes).append(" ").append(this.minutesStr).append(" ");
        }
        ensureDoubleDig(sb, seconds);
        sb.append(seconds).append(" ").append(this.secondsStr).append(" ");
        return sb.toString();
    }

    @Override
    public long delay() {
        return 10L;
    }
}
