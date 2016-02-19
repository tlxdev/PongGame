package com.goodgames.ponggame;

/**
 * Main class, for starting the game.
 * @author lahtelat
 */
public class Main {

    private static GameWindow gameWindow;

   
   
    /**
     * void main.
     * @param args arguments 
     */
    public static void main(String[] args) {
        gameWindow = new GameWindow();
        if (args != null && args.length > 0 && args[0].contains("test")) {
            gameWindow.setTest();
        }
        gameWindow.startGame();
    }

    public static GameWindow getGameWindow() {
        return gameWindow;
    }

}
