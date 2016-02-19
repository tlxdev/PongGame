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
package com.goodgames.ponggame.input;

import com.goodgames.ponggame.Game;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author lahtelat
 */
public class KeyboardInput {

    private long id;
    private Game game;

    public KeyboardInput(long windowId, Game game) {
        this.id = windowId;
        this.game = game;

    }

    /**
     * Updates input called every frame
     *
     * @param deltaTime amount of time between frames
     */
    public void update(double deltaTime) {

        if (glfwGetKey(id, GLFW_KEY_UP) == GLFW_PRESS) {
            game.getPlayerBat().move(-2.f * (float) deltaTime, 0);
        }

        if (glfwGetKey(id, GLFW_KEY_DOWN) == GLFW_PRESS) {
            game.getPlayerBat().move(2.f * (float) deltaTime, 0);
        }

    }

    /**
     * called by GameWindow.
     *
     * @param key key id
     * @param action action id
     * @param mods modifiers
     */
    public void onInput(int key, int action, int mods) {
            if(key == GLFW_KEY_P && action == GLFW_RELEASE){
                game.pause();
            }
    }

}
