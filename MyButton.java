package gacha_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

public class MyButton extends JButton {
    public MyButton(String text, int x, int y) {
        this.setText(text);
        this.setFont(new Font("Arial", Font.BOLD, 24));
        this.setBounds(x, y, 200, 60);
        this.setFocusPainted(false);
        this.setBackground(new Color(220, 20, 60));
        this.setForeground(Color.WHITE);
        this.setBorder(null);
        this.setOpaque(true);
        this.setVisible(false);
        this.setActionCommand(text);
    }
}
