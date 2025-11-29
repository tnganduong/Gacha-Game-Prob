# Image Integration Complete

## ✅ Background Images Fixed

### Homepage (Menu)
- **File**: `Background/menu.jpeg`
- **Location**: MenuUI.java - Homepage background
- **Status**: ✅ Already configured

### Game Play Page (Start Page)
- **File**: `Background/mainpage.jpeg`
- **Location**: StartPage.java - Main game screen background
- **Status**: ✅ Now integrated
- **Implementation**: Scaled to 1280x800, layered behind all components

## ✅ Pokemon Images Fixed

### Image Naming Convention
Pokemon images follow the pattern: `{star}.{index}.png`
- `1.1.png` - 1 star Pokemon, index 1
- `2.1.png` - 2 star Pokemon, index 1
- `3.1.png` - 3 star Pokemon, index 1
- `4.1.png` - 4 star Pokemon, index 1
- `5.1.png` - 5 star Pokemon, index 1

### New Component: PokemonSelector.java
Created in `Math/PokemonSelector.java`:

- `getRandomPokemonForStar(starLevel)` - Selects random Pokemon image for a specific star level
- `getRandomPokemonForStars(starLevels[])` - Selects Pokemon for multiple draws
- Scans Pokemon folder for all files matching star level
- Randomly picks one if multiple exist (e.g., 3.1.png, 3.2.png, 3.3.png)
- Returns filename without extension for loading

### Integration Points

#### StartPage.java
**Draw 1 Button**:
```java
int starRating = GachaDrawGenerator.drawSingleStar();
String pokemonFile = PokemonSelector.getRandomPokemonForStar(starRating);
Draw1Page.setPokemonImage(removeExtension(pokemonFile));
```

**Draw 5 Button**:
```java
int[] starRatings = GachaDrawGenerator.drawMultiple(5);
String[] pokemonFiles = PokemonSelector.getRandomPokemonForStars(starRatings);
Draw5Page.setPokemonFiles(pokemonFiles);
```

#### Draw1Page.java
- `setPokemonImage(pokemonName)` - Loads Pokemon from `gacha_game/Pokemon/{name}.png`
- Scales image to 400x400 pixels
- Shows star level placeholder if image fails to load

#### Draw5Page.java
- `setPokemonFiles(files[])` - Sets the 5 Pokemon filenames
- `createPokemonPanel(index)` - Loads each Pokemon image at 200x250 pixels
- Shows star level placeholder if image fails to load

## How It Works

### Draw Flow with Images

1. **Player clicks "Draw 1"**:
   - System generates random star (e.g., 3★)
   - `PokemonSelector` scans for all `3.*.png` files
   - Randomly picks one (e.g., `3.1.png`)
   - Loads image into Draw1Page
   - Shows 3 stars (⭐⭐⭐) above the Pokemon

2. **Player clicks "Draw 5"**:
   - System generates 5 random stars (e.g., [2, 5, 1, 3, 4])
   - `PokemonSelector` picks random Pokemon for each star level
   - Returns array: [`2.1.png`, `5.1.png`, `1.1.png`, `3.1.png`, `4.1.png`]
   - Sequential reveal shows each Pokemon with correct star level
   - 3-second delay between each reveal

### Background Loading

**StartPage** (Main Game):
```java
ImageIcon bgIcon = new ImageIcon("gacha_game/Background/mainpage.jpeg");
Image bgImg = bgIcon.getImage().getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
JLabel bgLabel = new JLabel(new ImageIcon(bgImg));
// Positioned behind all other components
```

**MenuUI** (Homepage):
```java
PaintPanel menu_canvas = new PaintPanel(this, "menu.webp");
// Already configured to load from Background folder
```

## Error Handling

All image loading includes fallback behavior:

1. **Pokemon images not found**: Shows placeholder text "★{star} POKEMON"
2. **Background not found**: Uses solid color fallback
3. **Multiple Pokemon per star**: Randomly selects one
4. **No Pokemon for star level**: Uses default pattern "{star}.1.png"

## Adding More Pokemon

To add more Pokemon for variety:

1. Add images to `Pokemon/` folder
2. Follow naming: `{star}.{index}.png`
3. Examples:
   - More 5★: `5.2.png`, `5.3.png`, `5.4.png`
   - More 3★: `3.2.png`, `3.3.png`
4. System automatically detects and randomly selects

No code changes needed - `PokemonSelector` scans the folder dynamically!
