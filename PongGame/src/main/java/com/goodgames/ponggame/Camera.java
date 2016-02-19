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

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 *
 * @author lahtelat
 */
public class Camera {

    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;

    private Vector3f up = new Vector3f(0, 1, 0);

    private Vector3f cameraPos = new Vector3f(5, 5, 0);

    private Matrix4f viewProjectionMatrix = new Matrix4f();

    public Camera() {
        viewMatrix = new Matrix4f().lookAt(cameraPos, new Vector3f(0, 0, 0), up);
        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians((double) 45), 4.f / 3.f, 0.1f, 100.f);

        projectionMatrix.mul(viewMatrix, viewProjectionMatrix);

    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Vector3f getPosition() {
        return cameraPos;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;

    }

    private float rotX, rotY;

    /**
     *
     * rotates the camera with given deltaX, deltaY mouse deltas
     *
     * @param deltaX mouse x change
     * @param deltaY mouse y change
     */
    public void rotate(double deltaX, double deltaY) {
        rotX += deltaX * 0.015f;
        rotY += deltaY * 0.015f;
        double camX = 5 * Math.cos(rotX) * Math.sin(rotY);
        double camY = 5 * Math.cos(rotY);
        double camZ = 5 * Math.sin(rotX) * Math.sin(rotY);

        cameraPos = new Vector3f((float) camX, (float) camY, (float) camZ);

        viewMatrix = new Matrix4f().lookAt(cameraPos, new Vector3f(0, 0, 0), up);//1

    }

    public Matrix4f getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }

}
