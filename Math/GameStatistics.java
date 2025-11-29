package gacha_game.Math;

/**
 * Tracks game statistics including draws, star counts, and xu balance.
 */
public class GameStatistics {
    
    private int totalDraws;
    private int[] starCounts; // Index 0 unused, 1-5 for star tiers
    private int totalXuSpent;
    private int totalXuReturned;
    private int currentXuBalance;
    
    public GameStatistics(int initialBalance) {
        this.totalDraws = 0;
        this.starCounts = new int[6]; // Index 0 unused
        this.totalXuSpent = 0;
        this.totalXuReturned = 0;
        this.currentXuBalance = initialBalance;
    }
    
    /**
     * Record a single draw result.
     * 
     * @param starRating The star rating drawn (1-5)
     */
    public void recordDraw(int starRating) {
        totalDraws++;
        totalXuSpent += ProbabilityCalculator.DRAW_COST;
        
        if (starRating >= 1 && starRating <= 5) {
            starCounts[starRating]++;
        }
        
        // Deduct cost
        currentXuBalance -= ProbabilityCalculator.DRAW_COST;
        
        // Add reward if 5★
        if (starRating == 5) {
            int reward = ProbabilityCalculator.FIVE_STAR_REWARD;
            totalXuReturned += reward;
            currentXuBalance += reward;
        }
    }
    
    /**
     * Record multiple draws at once.
     * 
     * @param starRatings Array of star ratings
     */
    public void recordDraws(int[] starRatings) {
        for (int star : starRatings) {
            recordDraw(star);
        }
    }
    
    /**
     * Get the current expected value table data.
     * Returns array of strings for display in the table.
     * 
     * @return 2D array [row][column] where columns are: Metric, Formula, Value
     */
    public String[][] getExpectedValueTable() {
        String[][] table = new String[4][3];
        
        // Row 0: Chance you'll get your next 5★ soon
        table[0][0] = "Chance you'll get your next 5★ soon";
        table[0][1] = "1 - 0.975^N";
        double chance = ProbabilityCalculator.probabilityAtLeastOneFiveStar(totalDraws + 1);
        table[0][2] = ProbabilityCalculator.formatPercentage(chance, 1);
        
        // Row 1: Average 5★ you should have by now
        table[1][0] = "Average 5★ you should have by now";
        table[1][1] = "0.025N";
        double expected = ProbabilityCalculator.expectedFiveStarCount(totalDraws);
        table[1][2] = ProbabilityCalculator.formatDecimal(expected, 2);
        
        // Row 2: Expected coins returned
        table[2][0] = "Expected coins returned";
        table[2][1] = "0.2N";
        double returned = ProbabilityCalculator.expectedCoinsReturned(totalDraws);
        table[2][2] = ProbabilityCalculator.formatDecimal(returned, 2);
        
        // Row 3: Coins you spent
        table[3][0] = "Coins you spent";
        table[3][1] = "N";
        table[3][2] = String.valueOf(totalXuSpent);
        
        return table;
    }
    
    /**
     * Get the initial probability table (before any draws).
     * 
     * @return 2D array for the 5 star tiers showing probability and expected value
     */
    public static String[][] getInitialProbabilityTable() {
        String[][] table = new String[5][3];
        
        table[0][0] = "⭐⭐⭐⭐⭐ (5 Star)";
        table[0][1] = "2.5%";
        table[0][2] = "+8 xu";
        
        table[1][0] = "⭐⭐⭐⭐ (4 Star)";
        table[1][1] = "7.5%";
        table[1][2] = "0 xu";
        
        table[2][0] = "⭐⭐⭐ (3 Star)";
        table[2][1] = "15%";
        table[2][2] = "0 xu";
        
        table[3][0] = "⭐⭐ (2 Star)";
        table[3][1] = "30%";
        table[3][2] = "0 xu";
        
        table[4][0] = "⭐ (1 Star)";
        table[4][1] = "45%";
        table[4][2] = "0 xu";
        
        return table;
    }
    
    // Getters
    public int getTotalDraws() {
        return totalDraws;
    }
    
    public int getStarCount(int starRating) {
        if (starRating >= 1 && starRating <= 5) {
            return starCounts[starRating];
        }
        return 0;
    }
    
    public int[] getAllStarCounts() {
        return starCounts.clone();
    }
    
    public int getTotalXuSpent() {
        return totalXuSpent;
    }
    
    public int getTotalXuReturned() {
        return totalXuReturned;
    }
    
    public int getNetXuSpent() {
        return totalXuSpent - totalXuReturned;
    }
    
    public int getCurrentXuBalance() {
        return currentXuBalance;
    }
    
    public void setCurrentXuBalance(int balance) {
        this.currentXuBalance = balance;
    }
    
    /**
     * Reset all statistics.
     */
    public void reset(int initialBalance) {
        this.totalDraws = 0;
        this.starCounts = new int[6];
        this.totalXuSpent = 0;
        this.totalXuReturned = 0;
        this.currentXuBalance = initialBalance;
    }
}
