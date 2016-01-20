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

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*; 
import static org.lwjgl.system.MemoryUtil.*; 

/**
 *
 * @author lahtelat
 */
public class GameWindow {

    private long windowId;
    private Game game;

    public GameWindow() {

        if (glfwInit() != GLFW_TRUE) {
            System.out.println("init error");
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        windowId = glfwCreateWindow(800, 800, "Pong", NULL, NULL);

        glfwMakeContextCurrent(windowId);
        glfwShowWindow(windowId);

    }

    public void startGame() {

        game = new Game();

        gameLoop();
    }

    private void gameLoop() {
        while (true) {

            render();
        }
    }

    public void render() {
        game.render();
    }

}
