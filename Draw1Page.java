package gacha_game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Draw1Page {
    static MyFrame myFrame;
    static JLabel pokemonImageLabel;
    static JLabel starLevelLabel;
    static int currentRarity = 5; // Default to 5-star for placeholder
    static String pokemonFile; // Store the pokemon file to load

    public static void showDraw1Result() throws Exception {
        myFrame.getContentPane().setBackground(new Color(240, 240, 250));
        
        // Set background image FIRST
        try {
            ImageIcon bgIcon = new ImageIcon("mainpage.jpeg");
            if (bgIcon.getIconWidth() > 0) {
                JLabel bgLabel = new JLabel(bgIcon);
                bgLabel.setBounds(0, 0, 1280, 700);
                myFrame.getContentPane().add(bgLabel);
            }
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }

        // Create star level panel at top
        JPanel starPanel = createStarPanel();
        myFrame.add(starPanel);

        // Create Pokemon image panel in center
        JPanel pokemonPanel = createPokemonPanel();
        myFrame.add(pokemonPanel);

        // Create back button at bottom
        JPanel buttonPanel = createButtonPanel();
        myFrame.add(buttonPanel);
        
        // Move background to back
        int componentCount = myFrame.getContentPane().getComponentCount();
        if (componentCount > 0) {
            myFrame.getContentPane().setComponentZOrder(
                myFrame.getContentPane().getComponent(componentCount - 1),
                componentCount - 1
            );
        }

        myFrame.setVisible(true);
        myFrame.repaint();
    }

    public static void resetStaticVariables() {
        myFrame = new MyFrame();
        currentRarity = 5; // Will be set by random logic later
        pokemonFile = null;
    }

    private static JPanel createStarPanel() {
        JPanel starPanel = new JPanel(new BorderLayout());
        starPanel.setBounds(390, 80, 500, 80);
        starPanel.setBackground(new Color(0, 0, 0, 0));
        starPanel.setOpaque(false);

        String starText = getStarText(currentRarity);
        starLevelLabel = new JLabel(starText, JLabel.CENTER);
        starLevelLabel.setFont(new Font("Arial", Font.BOLD, 48));
        starLevelLabel.setForeground(new Color(255, 215, 0));

        starPanel.add(starLevelLabel, BorderLayout.CENTER);
        return starPanel;
    }

    private static JPanel createPokemonPanel() {
        JPanel pokemonPanel = new JPanel(new BorderLayout());
        pokemonPanel.setBounds(390, 200, 500, 400);
        pokemonPanel.setBackground(new Color(0, 0, 0, 0));
        pokemonPanel.setOpaque(false);

        pokemonImageLabel = new JLabel("", JLabel.CENTER);
        pokemonImageLabel.setFont(new Font("Arial", Font.BOLD, 28));
        pokemonImageLabel.setForeground(Color.WHITE);

        // Load Pokemon image if available
        if (pokemonFile != null && !pokemonFile.isEmpty()) {
            try {
                String imagePath = "gacha_game/Pokemon/" + pokemonFile;
                ImageIcon pokemonIcon = new ImageIcon(imagePath);
                
                if (pokemonIcon.getIconWidth() > 0) {
                    Image img = pokemonIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                    pokemonImageLabel.setIcon(new ImageIcon(img));
                } else {
                    System.out.println("Pokemon image not found: " + imagePath);
                    pokemonImageLabel.setText("★" + currentRarity + " POKEMON");
                }
            } catch (Exception e) {
                System.out.println("Error loading Pokemon image: " + e.getMessage());
                pokemonImageLabel.setText("★" + currentRarity + " POKEMON");
            }
        } else {
            pokemonImageLabel.setText("★" + currentRarity + " POKEMON");
        }

        pokemonPanel.add(pokemonImageLabel, BorderLayout.CENTER);
        return pokemonPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(490, 650, 300, 100);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.setOpaque(false);

        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 28));
        backButton.setPreferredSize(new Dimension(200, 60));
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(null);
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);

        backButton.addActionListener(e -> {
            myFrame.dispose();
            try {
                // Don't reset - preserve game statistics
                StartPage.myFrame = new MyFrame();
                StartPage.startPage();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        buttonPanel.add(backButton);
        return buttonPanel;
    }

    private static String getStarText(int rarity) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rarity; i++) {
            stars.append("⭐");
        }
        return stars.toString();
    }

    // Method to set rarity (for later integration with random logic)
    public static void setRarity(int rarity) {
        currentRarity = rarity;
        if (starLevelLabel != null) {
            starLevelLabel.setText(getStarText(rarity));
        }
    }

    // Method to set Pokemon image (integration with Pokemon folder)
    public static void setPokemonImage(String pokemonName) {
        // pokemonName should be without extension (e.g., "3.1")
        pokemonFile = pokemonName + ".png";
    }
}
