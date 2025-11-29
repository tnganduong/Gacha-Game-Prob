package gacha_game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

class MyFrame extends JFrame {
    public MyFrame() {
        new JFrame();

        this.setTitle("Pokemon Gacha Game");
        this.setSize(1280, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        ImageIcon icon_image = new ImageIcon("gacha_game/Background/icon.jpeg");
        this.setIconImage(icon_image.getImage());

        this.setVisible(true);
    }
}
