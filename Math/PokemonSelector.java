package gacha_game.Math;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Selects random Pokemon images based on star level.
 * Pokemon images are named: {star}.{index}.png (e.g., 1.1.png, 5.1.png)
 */
public class PokemonSelector {
    
    private static final Random random = new Random();
    private static final String POKEMON_PATH = "gacha_game/Pokemon/";
    
    /**
     * Get a random Pokemon filename for a given star level.
     * Scans the Pokemon folder for all images matching the star level.
     * 
     * @param starLevel Star level (1-5)
     * @return Filename (e.g., "3.1.png") or null if none found
     */
    public static String getRandomPokemonForStar(int starLevel) {
        List<String> availablePokemon = getPokemonForStar(starLevel);
        
        if (availablePokemon.isEmpty()) {
            System.out.println("No Pokemon found for star level: " + starLevel);
            return null;
        }
        
        // Pick a random one from available
        int randomIndex = random.nextInt(availablePokemon.size());
        return availablePokemon.get(randomIndex);
    }
    
    /**
     * Get all Pokemon filenames for a specific star level.
     * 
     * @param starLevel Star level (1-5)
     * @return List of filenames matching the star level
     */
    private static List<String> getPokemonForStar(int starLevel) {
        List<String> pokemonList = new ArrayList<>();
        File pokemonDir = new File(POKEMON_PATH);
        
        if (!pokemonDir.exists() || !pokemonDir.isDirectory()) {
            // Try alternate path (when running from different directory)
            pokemonDir = new File("Pokemon/");
        }
        
        if (!pokemonDir.exists() || !pokemonDir.isDirectory()) {
            System.out.println("Pokemon directory not found!");
            return pokemonList;
        }
        
        File[] files = pokemonDir.listFiles();
        if (files != null) {
            String starPrefix = starLevel + ".";
            for (File file : files) {
                String filename = file.getName();
                if (filename.startsWith(starPrefix) && 
                    (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg"))) {
                    pokemonList.add(filename);
                }
            }
        }
        
        return pokemonList;
    }
    
    /**
     * Get Pokemon filenames for multiple star levels.
     * 
     * @param starLevels Array of star levels
     * @return Array of Pokemon filenames
     */
    public static String[] getRandomPokemonForStars(int[] starLevels) {
        String[] pokemonFiles = new String[starLevels.length];
        
        for (int i = 0; i < starLevels.length; i++) {
            pokemonFiles[i] = getRandomPokemonForStar(starLevels[i]);
            
            // Fallback if no image found
            if (pokemonFiles[i] == null) {
                pokemonFiles[i] = starLevels[i] + ".1.png"; // Default to first index
            }
        }
        
        return pokemonFiles;
    }
    
    /**
     * Remove file extension from filename.
     * 
     * @param filename Filename with extension
     * @return Filename without extension
     */
    public static String removeExtension(String filename) {
        if (filename == null) return null;
        
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
            return filename.substring(0, lastDot);
        }
        return filename;
    }
}
