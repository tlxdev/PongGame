package com.goodgames.ponggame;

/**
 *
 * @author lahtelat
 */
public class Main {

    private static GameWindow gameWindow;

    public static void main(String[] args) {
        gameWindow = new GameWindow();
        gameWindow.startGame();
    }
    
    public static GameWindow getGameWindow(){
        return gameWindow;
    }

}
