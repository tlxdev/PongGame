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

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Game window class, takes care of creating and setting up both window and the game.
 * @author lahtelat
 */




public class GameWindow implements Runnable {

    private long windowId = -1;
    private Game game;

    private double lastFrameTime;//fps laskentaan

    private GLFWKeyCallback keyCallback;

    private boolean started = false;

    private boolean isTest = false;

    public boolean hasStarted() {
        return started;
    }

    public void setTest() {
        isTest = true;
    }

    public long getWindowId() {
        return windowId;
    }

    public Game getGame() {
        return game;
    }

    public void stop() {
        this.started = false;
        System.out.println("stop");
    }

    public GameWindow() {

        //ikkunan luonti lwjgl-kirjastolla
        if (glfwInit() != GLFW_TRUE) {
            System.out.println("init error");
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 2);//antialiasisointi
        windowId = glfwCreateWindow(800, 600, "Pong", NULL, NULL);

        glfwMakeContextCurrent(windowId);
        glfwShowWindow(windowId);

        glfwSwapInterval(1);

        glfwSetKeyCallback(windowId, keyCallback = new GLFWKeyCallback() {

            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                game.getKeyboardInput().onInput(key, action, mods);

            }
        });

        GL.createCapabilities();

        started = true;
    }

    /**
     * Starts a new game.
     *
     */
    public void startGame() {

        game = new Game();
        game.setWindowId(windowId);

        lastFrameTime = glfwGetTime();
        run();

    }

    private void render() {
        glClearColor(0.0f, 0.5f, 0.5f, 1.0f);//aseta clearcolor rgb 1,1,1(valkoinen)
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //tyhjennä puskurit
        game.render();
        glfwSwapBuffers(windowId);

    }

    private void input() {
        glfwPollEvents();

    }

    private void update(double deltaTime) {
        input();

        game.update(deltaTime);
    }

    @Override
    public void run() {

        glEnable(GL_DEPTH_TEST); //jotta toisten esineiden takana olevia ei renderöidä

        while (glfwWindowShouldClose(windowId) == GLFW_FALSE && started && !isTest) {
            if (!game.isPaused()) {
                double currentTime = glfwGetTime();
                render();
                double deltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                update(deltaTime);
            } else {
                try {

                    double currentTime = glfwGetTime();
                    lastFrameTime = currentTime;

                    input();
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

}
