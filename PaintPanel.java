package gacha_game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public class PaintPanel extends JPanel implements ActionListener {
    JFrame frame;
    BufferedImage bg_img;
    String image_name;

    public PaintPanel(JFrame frame, String image_name) {
        this.frame = frame;
        this.image_name = image_name;
        try {
            bg_img = ImageIO.read(getClass().getClassLoader().getResource("gacha_game/Background/" + image_name));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Cannot load image: " + image_name);
            e.printStackTrace();
        }
        this.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (bg_img != null) {
            // Fill screen without stretching - use actual panel dimensions
            g2d.drawImage(bg_img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
    }
}
