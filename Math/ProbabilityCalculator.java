package gacha_game.Math;

/**
 * Probability model for the Gacha system.
 * Based on fixed star-tier probability distribution:
 * 1★: 45%, 2★: 30%, 3★: 15%, 4★: 7.5%, 5★: 2.5%
 */
public class ProbabilityCalculator {
    
    // Star tier probabilities
    public static final double PROB_1_STAR = 0.45;
    public static final double PROB_2_STAR = 0.30;
    public static final double PROB_3_STAR = 0.15;
    public static final double PROB_4_STAR = 0.075;
    public static final double PROB_5_STAR = 0.025;
    
    // Coin reward constants
    public static final int DRAW_COST = 1;
    public static final int FIVE_STAR_REWARD = 8;
    
    /**
     * Calculate the probability of getting at least one 5★ after N draws.
     * Formula: P(≥1 5★ in N) = 1 - (0.975)^N
     * 
     * @param n Number of draws
     * @return Probability as a decimal (0.0 to 1.0)
     */
    public static double probabilityAtLeastOneFiveStar(int n) {
        return 1 - Math.pow(1 - PROB_5_STAR, n);
    }
    
    /**
     * Calculate the expected number of 5★ by now.
     * Formula: E[X] = 0.025N
     * 
     * @param n Number of draws
     * @return Expected number of 5★
     */
    public static double expectedFiveStarCount(int n) {
        return PROB_5_STAR * n;
    }
    
    /**
     * Calculate the expected coins returned after N draws.
     * Formula: E[coins] = 0.2N
     * 
     * @param n Number of draws
     * @return Expected coins returned
     */
    public static double expectedCoinsReturned(int n) {
        return FIVE_STAR_REWARD * PROB_5_STAR * n;
    }
    
    /**
     * Calculate the expected net coins spent after N draws.
     * Formula: Net = N - 0.2N = 0.8N
     * 
     * @param n Number of draws
     * @return Expected net coins spent
     */
    public static double expectedNetCoinsSpent(int n) {
        return n - expectedCoinsReturned(n);
    }
    
    /**
     * Calculate the expected number of draws until first 5★.
     * Formula: E[T] = 1/p = 40
     * 
     * @return Expected draws until first 5★
     */
    public static double expectedDrawsUntilFirstFiveStar() {
        return 1.0 / PROB_5_STAR;
    }
    
    /**
     * Format probability as percentage string.
     * 
     * @param probability Decimal probability (0.0 to 1.0)
     * @param decimalPlaces Number of decimal places
     * @return Formatted percentage string (e.g., "39.6%")
     */
    public static String formatPercentage(double probability, int decimalPlaces) {
        double percentage = probability * 100;
        String format = "%." + decimalPlaces + "f%%";
        return String.format(format, percentage);
    }
    
    /**
     * Format a double value to specified decimal places.
     * 
     * @param value The value to format
     * @param decimalPlaces Number of decimal places
     * @return Formatted string
     */
    public static String formatDecimal(double value, int decimalPlaces) {
        String format = "%." + decimalPlaces + "f";
        return String.format(format, value);
    }
}
