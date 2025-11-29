package gacha_game.Math;

import java.util.Random;

/**
 * Handles random draw generation for the Gacha system.
 * Generates star ratings based on the fixed probability distribution.
 */
public class GachaDrawGenerator {
    
    private static final Random random = new Random();
    
    /**
     * Draw a single random Pokemon with star rating based on probability distribution.
     * Distribution:
     * 1★: 45% (0.00 - 0.45)
     * 2★: 30% (0.45 - 0.75)
     * 3★: 15% (0.75 - 0.90)
     * 4★: 7.5% (0.90 - 0.975)
     * 5★: 2.5% (0.975 - 1.00)
     * 
     * @return Star rating (1-5)
     */
    public static int drawSingleStar() {
        double rand = random.nextDouble();
        
        if (rand < 0.45) {
            return 1;
        } else if (rand < 0.75) {
            return 2;
        } else if (rand < 0.90) {
            return 3;
        } else if (rand < 0.975) {
            return 4;
        } else {
            return 5;
        }
    }
    
    /**
     * Draw multiple Pokemon and return their star ratings.
     * 
     * @param count Number of draws
     * @return Array of star ratings
     */
    public static int[] drawMultiple(int count) {
        int[] results = new int[count];
        for (int i = 0; i < count; i++) {
            results[i] = drawSingleStar();
        }
        return results;
    }
    
    /**
     * Count the number of each star tier in the results.
     * 
     * @param results Array of star ratings
     * @return Array where index represents star rating (index 0 unused, 1-5 are counts)
     */
    public static int[] countStarTiers(int[] results) {
        int[] counts = new int[6]; // Index 0 unused, 1-5 for star tiers
        
        for (int star : results) {
            if (star >= 1 && star <= 5) {
                counts[star]++;
            }
        }
        
        return counts;
    }
    
    /**
     * Calculate coin reward from draw results.
     * Each 5★ gives 8 xu reward.
     * 
     * @param results Array of star ratings
     * @return Total coins rewarded
     */
    public static int calculateCoinReward(int[] results) {
        int fiveStarCount = 0;
        for (int star : results) {
            if (star == 5) {
                fiveStarCount++;
            }
        }
        return fiveStarCount * ProbabilityCalculator.FIVE_STAR_REWARD;
    }
}
