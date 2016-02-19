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
import java.nio.DoubleBuffer;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author lahtelat
 */
public class MouseInput {

    private long windowId;

    //private Quaternionf rotate = new Qu aternionf();
    private double lastX, lastY;

    private Game game;

    public MouseInput(long windowId, Game game) {
        this.windowId = windowId;
        this.game = game;

        DoubleBuffer xPos = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yPos = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(windowId, xPos, yPos);
        lastX = xPos.get();
        lastY = yPos.get();

    }

    /*
     mouseinput luokka: hiiren painallukset ja liikutukset
     */
    public MouseInput() {
    }

    /**
     * Updates mouse input called every frame
     *
     * @param deltaTime amount of time between frames
     */
    public void update(double deltaTime) {
        DoubleBuffer xPos = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yPos = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(windowId, xPos, yPos);
        double x = xPos.get();
        double y = yPos.get();

        double dX = lastX - x;
        double dY = lastY - y;
        if (dX != 0 || dY != 0) {
            game.getCamera().rotate(dX, dY);
        }
        //System.out.println("dx dy " + dX + " " + dY);
        lastX = x;
        lastY = y;

        //TODO: kameran rotaatio hiirellä
    }

}
