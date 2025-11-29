package gacha_game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener {
    Menu game;

    public ActionHandler(Menu game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);

        switch (command) {
            case "START":
                try {
                    game.menuToStart();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "RULES":
                game.menuToRules();
                break;
            case "RETURN":
                game.backToMenu();
                break;
            case "DRAW 1":
                try {
                    game.startToDraw1();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "DRAW 5":
                try {
                    game.startToDraw5();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "END GAME":
                try {
                    game.startToEndGame();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "BACK":
                try {
                    game.backToStart();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
