package gacha_game;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MenuUI extends JFrame {
    // Menu screen components
    MyButton startButton, rulesButton, returnButton;
    PaintPanel menu_canvas, rules_canvas;

    Menu game;

    public MenuUI(Menu game) {
        this.game = game;

        this.setTitle("Pokemon Gacha Game");
        this.setSize(1280, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.white);


        createMenu();
        createRules();
        
        this.setVisible(true);
    }

    private void createMenu() {
        menu_canvas = new PaintPanel(this, "menu.jpeg");
        menu_canvas.setBounds(0, 0, 1280, 700);
        menu_canvas.setOpaque(false);

        startButton = new MyButton("START", 540, 400);
        startButton.addActionListener(game.aHandler);

        rulesButton = new MyButton("RULES", 540, 490);
        rulesButton.addActionListener(game.aHandler);

        startButton.setVisible(true);
        rulesButton.setVisible(true);

        this.add(startButton);
        this.add(rulesButton);
        this.add(menu_canvas);
    }

    private void createRules() {
        rules_canvas = new PaintPanel(this, "rule.jpeg");
        rules_canvas.setBounds(0, 0, 1280, 700);
        rules_canvas.setOpaque(false);

        returnButton = new MyButton("RETURN", 540, 490);
        returnButton.setForeground(Color.WHITE);
        returnButton.addActionListener(game.aHandler);

        this.add(returnButton);
        this.add(rules_canvas);
    }
}
