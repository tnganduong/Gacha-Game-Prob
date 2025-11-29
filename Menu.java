package gacha_game;

public class Menu {
    ActionHandler aHandler = new ActionHandler(this);
    MenuUI ui = new MenuUI(this);

    public Menu() {
    }

    // Menu screen management
    private void statusMenu(boolean status) {
        ui.menu_canvas.setVisible(status);
        ui.startButton.setVisible(status);
        ui.rulesButton.setVisible(status);
    }

    private void statusRules(boolean status) {
        ui.rules_canvas.setVisible(status);
        ui.returnButton.setVisible(status);
    }

    public void menuToRules() {
        statusMenu(false);
        statusRules(true);
    }

    public void backToMenu() {
        statusRules(false);
        statusMenu(true);
    }

    public void menuToStart() throws Exception {
        ui.dispose();
        try {
            StartPage.resetStaticVariables();
            StartPage.startPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startToDraw1() throws Exception {
        // TODO: Implement draw 1 logic
        System.out.println("Draw 1 clicked - to be implemented");
    }

    public void startToDraw5() throws Exception {
        // TODO: Implement draw 5 logic
        System.out.println("Draw 5 clicked - to be implemented");
    }

    public void startToEndGame() throws Exception {
        // TODO: Implement end game logic
        System.out.println("End Game clicked - to be implemented");
    }

    public void backToStart() throws Exception {
        // TODO: Implement back to start from draw pages
        System.out.println("Back to Start - to be implemented");
    }
}
