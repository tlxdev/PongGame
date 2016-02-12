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

import com.goodgames.ponggame.input.MouseInput;
import com.goodgames.ponggame.input.KeyboardInput;
import com.goodgames.ponggame.gameobjects.Ball;
import com.goodgames.ponggame.gameobjects.Bat;
import com.goodgames.ponggame.gameobjects.GameObject;
import com.goodgames.ponggame.gameobjects.Wall;
import java.util.ArrayList;
import java.util.Random;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author lahtelat
 */
public class Game {

    ArrayList<GameObject> objects = new ArrayList<>();

    Bat player1, player2;

    Wall wall1, wall2;

    Camera camera;

    private MouseInput input;

    private KeyboardInput keyboardInput;

    private long windowId;

    private long frameCounter = 0;

    public Bat getPlayerBat() {
        return player1;
    }

    public Bat getEnemyBat() {
        return player2;
    }

    public Camera getCamera() {
        return camera;
    }
    
    public Ball getBall(){
        return ball;
    }

    private Ball ball;

    public Game() {
        camera = new Camera();
        ball = new Ball(this);
        player1 = new Bat(this);
        player2 = new Bat(this);
        wall1 = new Wall(this);
        wall2 = new Wall(this);
        wall1.move(2, 0);
        wall2.move(-2, 0);
        player2.move(0, -2);
        player1.move(0, 3);

        ball.setDirection(new Vector3f(0.5f, 0, 0.5f).normalize());

    }

    public void render() {

        //renderöi esineet
        player1.render();
        player2.render();
        wall1.render();
        wall2.render();
        ball.render();

    }

    /*
    
     Sovelluksen logiikka(mailojen ja pallon törmäys, pallon liikkuminen) tulee tähän
    
     */
    public void update(double deltaTime) {
        ball.update(deltaTime);

        /*
         Päivitä fps vain joka viidestoista frame(muuten se on liian vaikea lukea)     
         */
        if (frameCounter == 15) {
            frameCounter = 0;
        }
        if (frameCounter == 0) {
            GLFW.glfwSetWindowTitle(windowId, "Pong FPS: " + (int) (1 / deltaTime));
        }

        frameCounter++;

        input.update(deltaTime);
        keyboardInput.update(deltaTime);

        if (ball.getY() > 3.2 || ball.getY() < -2) {
            System.out.println("lose");
            ball = new Ball(this);

            ball.setDirection(new Vector3f(Math.max(new Random().nextFloat(), 0.5f), 0, Math.max(new Random().nextFloat(), 0.5f)).normalize());

        }

    }

    public void setWindowId(long id) {
        this.windowId = id;
        input = new MouseInput(id);
        keyboardInput = new KeyboardInput(id, this);
    }

}
