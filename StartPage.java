package gacha_game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gacha_game.Math.GameStatistics;
import gacha_game.Math.GachaDrawGenerator;
import gacha_game.Math.ProbabilityCalculator;
import gacha_game.Math.PokemonSelector;

public class StartPage {
    static MyFrame myFrame;
    static JLabel xuBalanceLabel;
    static JTable evTable;
    static GameStatistics gameStats;
    static int xuBalance = 10; // Starting balance

    public static void startPage() throws Exception {
        myFrame.getContentPane().setBackground(new Color(240, 240, 250)); // white background

        // Create xu balance panel
        JPanel balancePanel = createBalancePanel();
        myFrame.add(balancePanel);

        // Create expected value table
        JPanel tablePanel = createTablePanel();
        myFrame.add(tablePanel);

        // Create center pokeball background
        JPanel centerPanel = createCenterPanel();
        myFrame.add(centerPanel);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        myFrame.add(buttonPanel);

        myFrame.setVisible(true);
        myFrame.repaint();
    }

    public static void resetStaticVariables() {
        myFrame = new MyFrame();
        xuBalance = 10;
        gameStats = new GameStatistics(xuBalance);
    }

    private static JPanel createBalancePanel() {
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        balancePanel.setBounds(40, 20, 400, 60);
        balancePanel.setBackground(new Color(240, 240, 250));
        balancePanel.setOpaque(true); // Make opaque so it's visible

        JLabel titleLabel = new JLabel("Coins Balance: ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));

        xuBalanceLabel = new JLabel(String.valueOf(xuBalance));
        xuBalanceLabel.setFont(new Font("Arial", Font.BOLD, 28));
        xuBalanceLabel.setForeground(new Color(220, 20, 60));

        balancePanel.add(titleLabel);
        balancePanel.add(xuBalanceLabel);

        return balancePanel;
    }

    private static JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBounds(40, 100, 500, 250);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setOpaque(true); // Make opaque so it's visible

        // Create table title
        JLabel tableTitle = new JLabel("Expected Value Table", JLabel.CENTER);
        tableTitle.setFont(new Font("Arial", Font.BOLD, 20));
        tableTitle.setOpaque(true);
        tableTitle.setBackground(new Color(220, 20, 60));
        tableTitle.setForeground(Color.WHITE);
        tableTitle.setPreferredSize(new Dimension(500, 40));

        // Create table data from math model
        String[] columnNames = {"Rarity", "Probability", "Expected Value"};
        String[][] initialData = GameStatistics.getInitialProbabilityTable();
        Object[][] data = new Object[initialData.length][initialData[0].length];
        for (int i = 0; i < initialData.length; i++) {
            for (int j = 0; j < initialData[i].length; j++) {
                data[i][j] = initialData[i][j];
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        evTable = new JTable(model);
        evTable.setFont(new Font("Arial", Font.PLAIN, 14));
        evTable.setRowHeight(30);
        evTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        evTable.getTableHeader().setBackground(new Color(255, 182, 193));

        JScrollPane scrollPane = new JScrollPane(evTable);

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private static JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(520, 10, 800, 800);
        centerPanel.setBackground(new Color(240, 240, 250));
        centerPanel.setOpaque(true);
        centerPanel.setLayout(new BorderLayout());

        // Load and scale pokeball image to fit the panel
        JLabel pokeballLabel = new JLabel();
        pokeballLabel.setHorizontalAlignment(JLabel.CENTER);
        
        ImageIcon originalIcon = new ImageIcon("gacha_game/Background/icon.png");
        if (originalIcon.getIconWidth() > 0) {
            // Scale image to fit panel while maintaining aspect ratio
            Image scaledImage = originalIcon.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            pokeballLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            // Fallback if image not found
            pokeballLabel.setText("POKEBALL I MAGE HERE");
            pokeballLabel.setFont(new Font("Arial", Font.BOLD, 32));
            pokeballLabel.setForeground(new Color(150, 150, 150));
        }

        centerPanel.add(pokeballLabel, BorderLayout.CENTER);

        return centerPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBounds(140, 400, 300, 280);
        buttonPanel.setBackground(new Color(240, 240, 250));
        buttonPanel.setOpaque(true); // Make opaque

        // Create Draw 1 button
        JButton draw1Button = createStyledButton("DRAW 1");
        draw1Button.addActionListener(e -> {
            try {
                // Check if player has enough xu
                if (gameStats.getCurrentXuBalance() < 1) {
                    System.out.println("Insufficient coin balance!");
                    return;
                }
                
                // Perform draw
                int starRating = GachaDrawGenerator.drawSingleStar();
                gameStats.recordDraw(starRating);
                
                // Get Pokemon image for this star level
                String pokemonFile = PokemonSelector.getRandomPokemonForStar(starRating);
                
                // Update UI
                updateXuBalance(gameStats.getCurrentXuBalance() - xuBalance);
                xuBalance = gameStats.getCurrentXuBalance();
                updateEVTable(gameStats.getExpectedValueTable());
                
                // Show result
                myFrame.dispose();
                Draw1Page.resetStaticVariables();
                Draw1Page.setRarity(starRating);
                // Draw1Page.setPokemonFile(pokemonFile);
                if (pokemonFile != null) {
                    Draw1Page.setPokemonImage(PokemonSelector.removeExtension(pokemonFile));
                }
                Draw1Page.showDraw1Result();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Create Draw 5 button
        JButton draw5Button = createStyledButton("DRAW 5");
        draw5Button.addActionListener(e -> {
            try {
                // Check if player has enough xu
                if (gameStats.getCurrentXuBalance() < 5) {
                    System.out.println("Insufficient coin balance! Need 5 coin.");
                    return;
                }
                // Perform 5 draws
                int[] starRatings = GachaDrawGenerator.drawMultiple(5);
                gameStats.recordDraws(starRatings);
                
                // Get Pokemon images for each star level
                String[] pokemonFiles = PokemonSelector.getRandomPokemonForStars(starRatings);
                
                // Update UI
                updateXuBalance(gameStats.getCurrentXuBalance() - xuBalance);
                xuBalance = gameStats.getCurrentXuBalance();
                updateEVTable(gameStats.getExpectedValueTable());
                
                // Show results
                myFrame.dispose();
                Draw5Page.resetStaticVariables();
                Draw5Page.setRarities(starRatings);
                Draw5Page.setPokemonFiles(pokemonFiles);
                Draw5Page.showDraw5Results();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Create End Game button
        JButton endGameButton = createStyledButton("END GAME");
        endGameButton.setBackground(new Color(100, 100, 100));
        endGameButton.addActionListener(e -> {
            System.exit(0);
        });

        buttonPanel.add(draw1Button);
        buttonPanel.add(draw5Button);
        buttonPanel.add(endGameButton);

        return buttonPanel;
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 28));
        button.setBackground(new Color(220, 20, 60));
        button.setForeground(Color.WHITE);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setOpaque(true);
        return button;
    }

    // Method to update xu balance display
    public static void updateXuBalance(int amount) {
        xuBalance += amount;
        if (xuBalanceLabel != null) {
            xuBalanceLabel.setText(String.valueOf(xuBalance));
        }
    }

    // Method to update EV table with statistics
    public static void updateEVTable(String[][] newData) {
        if (evTable == null) return;
        
        DefaultTableModel model = (DefaultTableModel) evTable.getModel();
        
        // Clear existing rows
        model.setRowCount(0);
        
        // Add new data rows
        for (String[] row : newData) {
            model.addRow(row);
        }
    }
    
    // Get current game statistics
    public static GameStatistics getGameStats() {
        return gameStats;
    }
}
