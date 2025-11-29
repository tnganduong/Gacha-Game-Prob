# Pokemon Gacha Game

## Project Structure

This is a Java Swing-based Pokemon Gacha game where players spend "coin" currency to draw random Pokemon with different rarity levels.

## Folder Structure

```
gacha_game/
├── Main.java                 # Entry point
├── MyFrame.java              # Base JFrame (1280x700, fixed size)
├── MyButton.java             # Custom styled button component
├── PaintPanel.java           # Custom panel for background images
├── ActionHandler.java        # Event handler for button clicks
├── Menu.java                 # Menu logic and navigation
├── MenuUI.java               # Homepage and Rules page UI
├── StartPage.java            # Main game page with Draw buttons
├── Draw1Page.java            # Single draw result display
├── Draw5Page.java            # Five sequential draw results display
├── Background/               # Background images (menu.png, rules.png, icon.png, pokeball.png)
├── Pokemon/                  # Pokemon images (name.png format)
├── Img/                      # Other images
└── Math/                     # Math logic (for probability calculations)
```

## Required Images

### Background/
- `icon.png` - Window icon
- `menu.png` - Homepage background
- `rules.png` - Rules page background
- `pokeball.png` - Pokeball image for Start page center

### Pokemon/
- Individual Pokemon images named like: `pikachu.png`, `charizard.png`, etc.

## How to Run

1. Ensure all required background images are in `gacha_game/Background/`
2. Add Pokemon images to `gacha_game/Pokemon/`
3. Run `Main.java`

<!-- java gacha_game/Main.java -->

## Development Notes

- Window size: 1280 x 800 (fixed, centered)
- Color scheme: Red buttons (220, 20, 60), light background (240, 240, 250)
- Timer delays: 1.5 seconds per Pokemon reveal in Draw 5 mode
- Star ratings: ⭐ symbols (1-5 stars)
