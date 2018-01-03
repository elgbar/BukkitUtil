package no.kh498.util.itemMenus;

/**
 * @author kh498
 * @since 0.1.0
 */
public class MathUtil {

    /**
     * @param i
     *     first number
     * @param j
     *     second number
     *
     * @return If any parameter is less than 1 returns 1. If not it returns the rounded number up how many times j fit
     * in i
     */
    public static int dividedRoundedUp(final int i, final int j) {
        if (i <= 0 || j <= 0) {
            return 1;
        }

        final int modular = i % j;
        final int fit;
        if (modular == 0) {
            fit = i / j;
        }
        else {
            fit = 1 + ((i - modular) / j);
        }
        return fit;
    }

}
