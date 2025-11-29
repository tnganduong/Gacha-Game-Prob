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
import javax.swing.Timer;

public class Draw5Page {
    static MyFrame myFrame;
    static JLabel[] pokemonImageLabels = new JLabel[5];
    static JLabel[] starLevelLabels = new JLabel[5];
    static int[] rarities = {5, 4, 3, 2, 1}; // Placeholder rarities
    static String[] pokemonFiles = new String[5]; // Pokemon filenames
    static int currentIndex = 0;
    static JButton backButton;

    public static void showDraw5Results() throws Exception {
        myFrame.getContentPane().setBackground(new Color(240, 240, 250));
        
        // Set background image FIRST
        try {
            ImageIcon bgIcon = new ImageIcon("Background/mainpage.jpeg");
            if (bgIcon.getIconWidth() > 0) {
                JLabel bgLabel = new JLabel(bgIcon);
                bgLabel.setBounds(0, 0, 1280, 800);
                myFrame.getContentPane().add(bgLabel);
            }
        } catch (Exception e) {
            System.out.println("Background image not found: " + e.getMessage());
        }

        // Create 5 Pokemon display panels (initially hidden) - AFTER background
        for (int i = 0; i < 5; i++) {
            JPanel pokemonPanel = createPokemonPanel(i);
            myFrame.add(pokemonPanel);
        }

        // Create back button (initially hidden)
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

        // Start the sequential reveal animation
        startSequentialReveal();
    }

    public static void resetStaticVariables() {
        myFrame = new MyFrame();
        pokemonImageLabels = new JLabel[5];
        starLevelLabels = new JLabel[5];
        // Don't reset rarities here - they'll be set by setRarities()
        pokemonFiles = new String[5];
        currentIndex = 0;
    }

    private static JPanel createPokemonPanel(int index) {
        // Calculate horizontal position for 5 Pokemon in a row
        int xPos = 90 + (index * 240);
        
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(null);
        containerPanel.setBounds(xPos, 250, 220, 400);
        containerPanel.setBackground(new Color(0, 0, 0, 0));
        containerPanel.setOpaque(false);
        containerPanel.setVisible(false); // Hidden initially

        // Star level at top
        String starText = getStarText(rarities[index]);
        starLevelLabels[index] = new JLabel(starText, JLabel.CENTER);
        starLevelLabels[index].setBounds(0, 0, 220, 50);
        starLevelLabels[index].setFont(new Font("Arial", Font.BOLD, 32));
        starLevelLabels[index].setForeground(new Color(255, 215, 0));

        // Pokemon image in center
        pokemonImageLabels[index] = new JLabel("★" + rarities[index], JLabel.CENTER);
        pokemonImageLabels[index].setBounds(10, 60, 200, 250);
        pokemonImageLabels[index].setFont(new Font("Arial", Font.BOLD, 18));
        pokemonImageLabels[index].setForeground(Color.WHITE);
        pokemonImageLabels[index].setOpaque(true);
        pokemonImageLabels[index].setBackground(new Color(60, 60, 60));
        
        // Load actual Pokemon image if available
        if (pokemonFiles[index] != null) {
            try {
                ImageIcon pokemonIcon = new ImageIcon("gacha_game/Pokemon/" + pokemonFiles[index]);
                if (pokemonIcon.getIconWidth() > 0) {
                    Image img = pokemonIcon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH);
                    pokemonImageLabels[index].setIcon(new ImageIcon(img));
                    pokemonImageLabels[index].setText(""); // Clear placeholder
                }
            } catch (Exception e) {
                System.out.println("Cannot load Pokemon: " + pokemonFiles[index]);
            }
        }

        containerPanel.add(starLevelLabels[index]);
        containerPanel.add(pokemonImageLabels[index]);

        return containerPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(490, 680, 300, 100);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setVisible(false); // Hidden until all reveals complete

        backButton = new JButton("BACK");
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

    private static void startSequentialReveal() {
        // Timer to reveal each Pokemon sequentially
        Timer revealTimer = new Timer(1500, null); // 1.5 seconds per reveal
        
        revealTimer.addActionListener(e -> {
            if (currentIndex < 5) {
                // Show the current Pokemon panel
                // Background is last, Pokemon start at 0
                myFrame.getContentPane().getComponent(currentIndex).setVisible(true);
                myFrame.repaint();
                currentIndex++;
            } else {
                // All reveals complete, show back button
                revealTimer.stop();
                myFrame.getContentPane().getComponent(5).setVisible(true);
                myFrame.repaint();
            }
        });

        revealTimer.setInitialDelay(500); // Wait 0.5 seconds before first reveal
        revealTimer.start();
    }

    private static String getStarText(int rarity) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rarity; i++) {
            stars.append("⭐");
        }
        return stars.toString();
    }

    // Method to set rarities (must be called before showDraw5Results)
    public static void setRarities(int[] newRarities) {
        if (newRarities.length == 5) {
            rarities = newRarities;
        }
    }

    // Method to set Pokemon files (must be called before showDraw5Results)
    public static void setPokemonFiles(String[] files) {
        if (files.length == 5) {
            pokemonFiles = files;
        }
    }
}
