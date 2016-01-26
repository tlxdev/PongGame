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
package com.goodgames.ponggame.gameobjects;

import com.goodgames.ponggame.Game;
import org.joml.Vector3f;

/**
 *
 * @author lahtelat
 */
public class GameObject {

    protected Game game;

    public GameObject(Game game) {
        this.game = game;
    }
    protected float x = 0, y = 1;//koordinaatit 2D

    public void move(float xMove, float yMove) {
        this.x += xMove;
        this.y += yMove;
    }

    private float speed = 2.5f;
    protected Vector3f direction = new Vector3f(0, 0, 0);

    public void setDirection(Vector3f newDirection) {
        this.direction = newDirection;
    }

    public void update(double deltaTime) {

        Vector3f dDir = new Vector3f();
        direction.mul(((float) deltaTime * speed), dDir);

        move(dDir.x, dDir.z);

    }

}
