# Math Integration Documentation

## Overview
The gacha game now includes complete probability calculations and statistics tracking based on your mathematical model.

## Math Components

### 1. ProbabilityCalculator.java
Contains all probability formulas:

- **Star Distribution**:
  - 1★: 45% (0.45)
  - 2★: 30% (0.30)
  - 3★: 15% (0.15)
  - 4★: 7.5% (0.075)
  - 5★: 2.5% (0.025)

- **Key Formulas**:
  - `probabilityAtLeastOneFiveStar(N)`: Returns 1 - (0.975)^N
  - `expectedFiveStarCount(N)`: Returns 0.025N
  - `expectedCoinsReturned(N)`: Returns 0.2N (8 xu × 2.5%)
  - `expectedNetCoinsSpent(N)`: Returns 0.8N
  - `expectedDrawsUntilFirstFiveStar()`: Returns 40 (1/0.025)

### 2. GachaDrawGenerator.java
Handles random draws:

- `drawSingleStar()`: Generates a random star rating (1-5) based on probability distribution
- `drawMultiple(count)`: Performs multiple draws
- `countStarTiers(results)`: Counts how many of each star tier were drawn
- `calculateCoinReward(results)`: Calculates xu reward (8 xu per 5★)

### 3. GameStatistics.java
Tracks game progress:

- **Tracked Data**:
  - Total draws performed
  - Count of each star tier (1-5★)
  - Total xu spent
  - Total xu returned from 5★ rewards
  - Current xu balance

- **Key Methods**:
  - `recordDraw(starRating)`: Records a single draw, updates balance
  - `recordDraws(starRatings[])`: Records multiple draws at once
  - `getExpectedValueTable()`: Returns updated table data for UI
  - `getInitialProbabilityTable()`: Returns initial probability table

## How It Works

### Game Flow with Math Integration

1. **Game Start**:
   - Player starts with 100 xu
   - `GameStatistics` initialized with balance
   - Initial probability table shows: 5★: 2.5%, 4★: 7.5%, etc.

2. **Draw 1 Button**:
   ```java
   // Check balance (need 1 xu)
   if (balance < 1) return;
   
   // Generate random star (1-5)
   int star = GachaDrawGenerator.drawSingleStar();
   
   // Update statistics
   gameStats.recordDraw(star);
   // This automatically:
   // - Deducts 1 xu
   // - Adds 8 xu if star == 5
   // - Updates counts
   
   // Show result page with correct star level
   ```

3. **Draw 5 Button**:
   ```java
   // Check balance (need 5 xu)
   if (balance < 5) return;
   
   // Generate 5 random stars
   int[] stars = GachaDrawGenerator.drawMultiple(5);
   
   // Update statistics for all 5
   gameStats.recordDraws(stars);
   
   // Show sequential reveal with correct stars
   ```

4. **Expected Value Table Updates**:
   After each draw, the table shows:
   
   | Metric | Formula | Value |
   |--------|---------|-------|
   | Chance you'll get your next 5★ soon | 1 - 0.975^N | [calculated %] |
   | Average 5★ you should have by now | 0.025N | [calculated #] |
   | Expected coins returned | 0.2N | [calculated xu] |
   | Coins you spent | N | [actual xu] |

## Example Scenario

**Starting**: 100 xu, 0 draws

**After 10 draws** (unlucky, no 5★):
- Balance: 90 xu (100 - 10)
- Table shows:
  - Next 5★ chance: 1 - 0.975^10 = 22.4%
  - Expected 5★: 0.025 × 10 = 0.25
  - Expected returned: 0.2 × 10 = 2 xu
  - Coins spent: 10 xu

**After 20 draws** (got one 5★):
- Balance: 88 xu (100 - 20 + 8)
- Table shows:
  - Next 5★ chance: 1 - 0.975^20 = 39.6%
  - Expected 5★: 0.025 × 20 = 0.5
  - Expected returned: 0.2 × 20 = 4 xu
  - Coins spent: 20 xu

**After 40 draws** (average):
- Expected to have gotten ~1 5★ (maybe 0-2)
- Balance: ~92 xu if exactly 1 5★ (100 - 40 + 8)
- Next 5★ probability: ~63.6%

## Testing the Math

To verify probabilities work correctly, you can add this test method to ProbabilityCalculator:

```java
public static void runTest(int trials) {
    int[] counts = new int[6];
    for (int i = 0; i < trials; i++) {
        int star = GachaDrawGenerator.drawSingleStar();
        counts[star]++;
    }
    
    System.out.println("Results after " + trials + " draws:");
    System.out.println("1★: " + (counts[1] * 100.0 / trials) + "% (expected 45%)");
    System.out.println("2★: " + (counts[2] * 100.0 / trials) + "% (expected 30%)");
    System.out.println("3★: " + (counts[3] * 100.0 / trials) + "% (expected 15%)");
    System.out.println("4★: " + (counts[4] * 100.0 / trials) + "% (expected 7.5%)");
    System.out.println("5★: " + (counts[5] * 100.0 / trials) + "% (expected 2.5%)");
}
```

Run with 10,000 or 100,000 trials to verify distribution is correct.

## UI Integration Points

### StartPage.java
- Uses `GameStatistics` to track progress
- Updates xu balance display in real-time
- Updates Expected Value table after each draw
- Checks balance before allowing draws

### Draw1Page.java
- Displays the star rating from `GachaDrawGenerator`
- Shows reward notification (for 5★ draws)

### Draw5Page.java
- Displays 5 star ratings sequentially
- Each Pokemon shows correct star level
- Timing: 5 seconds per reveal (adjustable)

## Mathematical Properties

1. **Expected Return**: 20% cost recovery
   - Player spends 1 xu per draw
   - Gets back 8 xu per 5★ (2.5% chance)
   - Expected return: 8 × 0.025 = 0.2 xu per draw

2. **Time to First 5★**: 40 draws on average
   - Geometric distribution: E[T] = 1/p = 1/0.025 = 40

3. **Probability After N Draws**:
   - 10 draws: 22.4% chance of ≥1 5★
   - 20 draws: 39.6%
   - 40 draws: 63.6%
   - 80 draws: 86.7%
   - 100 draws: 91.8%

4. **Binomial Distribution for N Draws**:
   - Number of 5★ follows Binomial(N, 0.025)
   - Mean: 0.025N
   - Standard deviation: √(N × 0.025 × 0.975)
