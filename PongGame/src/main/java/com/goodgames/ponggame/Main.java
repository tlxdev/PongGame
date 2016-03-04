package com.goodgames.ponggame;

import java.io.File;

/**
 * Main class, for starting the game.
 *
 * @author lahtelat
 */
public class Main {

    public static String SHADER_DIRECTORY = "shaders/";

    private static GameWindow gameWindow;

    /**
     * void main.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        if (shadersExist()) {
            gameWindow = new GameWindow();
            if (args != null && args.length > 0 && args[0].contains("test")) {
                gameWindow.setTest();
            }
            gameWindow.startGame();
        }
    }

    public static boolean shadersExist() {
        File f = new File("shaders/");
        File ff = new File("../shaders/");
        if (f.exists()) {
            return true;

            /*
            netbeans tekee .jar kansioon /target/ , shaderit voivat siis olla joissain 
            tapauksissa yhden kansion alempana  
             */
        } else if (ff.exists()) {
            SHADER_DIRECTORY = "../shaders/";
            return true;
        }
        System.out.println("Shaders don't exist, you have received an invalid .jar");
        return false;

    }

    public static GameWindow getGameWindow() {
        return gameWindow;
    }

}
