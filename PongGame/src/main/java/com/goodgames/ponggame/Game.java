/*
 * The MIT License
 *
 * Copyright 2016 lahtelat.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.goodgames.ponggame;

import com.goodgames.ponggame.AI.Difficulty;
import com.goodgames.ponggame.gameobjects.Ball;
import com.goodgames.ponggame.gameobjects.Bat;
import com.goodgames.ponggame.gameobjects.GameObject;
import com.goodgames.ponggame.gameobjects.Wall;
import com.goodgames.ponggame.input.KeyboardInput;
import com.goodgames.ponggame.input.MouseInput;
import java.util.ArrayList;
import java.util.Random;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 * Handles game logic, rendering etc.
 *
 * @author lahtelat
 */
public class Game {

    private int player1Score = 0, player2Score = 0;

    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    private ArrayList<GameObject> toDelete = new ArrayList<>();

    private Bat player1, player2;

    private Wall wall1, wall2;

    private Camera camera;

    private MouseInput input;

    private KeyboardInput keyboardInput;

    private long windowId;

    private long frameCounter = 0;

    private boolean paused = false;

    public boolean isPaused() {
        return paused;
    }

    /**
     * Pauses or unpauses the game.
     */
    public void pause() {
        this.paused = !this.paused;
    }

    private AI ai;

    /**
     * Returns the player's bat.
     *
     * @return the player's bat
     */
    public Bat getPlayerBat() {
        return player1;
    }

    public KeyboardInput getKeyboardInput() {
        return keyboardInput;
    }

    /**
     * Returns the enemy's bat.
     *
     * @return the enemy's bat
     */
    public Bat getEnemyBat() {
        return player2;
    }

    public Camera getCamera() {
        return camera;
    }

    public Ball getBall() {
        return ball;
    }

    public AI getAi() {
        return ai;
    }

    private Ball ball;

    /**
     * Game instance.
     */
    public Game() {
        camera = new Camera();
        ball = new Ball(this);
        gameObjects.add(ball);
        player1 = new Bat(this);
        player2 = new Bat(this);
        wall1 = new Wall(this);
        wall2 = new Wall(this);
        wall1.move(2, 0);
        wall2.move(-2, 0);
        player2.move(0, -2);
        player1.move(0, 3);

        ball.setDirection(new Vector3f(0.5f, 0, 0.5f).normalize());

        ai = new AI(Difficulty.EASY, this, player2);
    }

    /**
     * Renders the scene.
     *
     */
    public void render() {

        //render??i esineet
        player1.render();
        player2.render();
        wall1.render();
        wall2.render();
        for (GameObject go : gameObjects) {
            go.render();
        }
        //ball.render();

    }

    public void setAiDifficulty(Difficulty newDiff) {
        ai.setDifficulty(newDiff);
    }

    /**
     * Updates the game logic and checks for input.
     *
     * @param deltaTime the amount of time passed between frames
     */
    public void update(double deltaTime) {

        //ball.update(deltaTime);
        for (GameObject go : gameObjects) {
            go.update(deltaTime);
            Ball b = (Ball) go;
            if (b.needsToBeDeleted()) {
                toDelete.add(b);
            }
        }

        for (GameObject go : toDelete) {
            gameObjects.remove(go);
        }

        player2.update(deltaTime);
        /*
         P??ivit?? fps vain joka viidestoista frame(muuten se on liian vaikea lukea)     
         */
        if (ai.getDifficulty().equals(Difficulty.EASY)) {
            ai.update(deltaTime);
        } else if (frameCounter % 6 == 0) {
            ai.update(deltaTime);
        }
        if (frameCounter == 15) {
            frameCounter = 0;
        }
        if (frameCounter == 0) {
            GLFW.glfwSetWindowTitle(windowId, "Pong FPS: " + (int) (1 / deltaTime) + " scores " + player1Score + ":" + player2Score);
        }

        frameCounter++;

        input.update(deltaTime);
        keyboardInput.update(deltaTime);

        if (ball.getY() > 3.2 || ball.getY() < -2) {
            if (ball.getY() > 3.2) {
                player2Score++;
            } else {
                player1Score++;
            }
            System.out.println("lose");
            ball.setTimeToLive(0.5f);
            ball = new Ball(this);
            gameObjects.add(ball);
            ball.setDirection(new Vector3f(Math.max(new Random().nextFloat(), 0.5f), 0, Math.max(new Random().nextFloat(), 0.5f)).normalize());
            ai.setBall(ball);
        }

    }

    public void addBall() {

        Ball newBall = new Ball(this);
        gameObjects.add(newBall);
        newBall.setDirection(new Vector3f(Math.max(new Random().nextFloat(), 0.5f), 0, Math.max(new Random().nextFloat(), 0.5f)).normalize());

    }

    /**
     * Sets the window id, sets up input listeners for the window.
     *
     * @param id the id of the window
     */
    public void setWindowId(long id) {
        this.windowId = id;
        input = new MouseInput(id, this);
        keyboardInput = new KeyboardInput(id, this);

    }

}
