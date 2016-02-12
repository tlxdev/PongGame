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

import com.goodgames.ponggame.rendering.Model;
import com.goodgames.ponggame.Game;
import com.goodgames.ponggame.rendering.Shader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 *
 * @author lahtelat
 */
public class Bat extends GameObject {

    float[] verts2 = { //2d neliö
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f,
        -0.5f, 0.5f, 0f
    };

    Shader currentShader;

    float[] verts = { //mailan renderöinti: kuutio, jota scaletetaan leveysakselilla
        //koska 3d-model formaatin tekeminen menisi jo liian pitkälle, käytetään
        //hardcoded koordinaatteja kuutioon

        -1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,
        1.0f, 1.0f, -1.0f,
        -1.0f, -1.0f, -1.0f,
        -1.0f, 1.0f, -1.0f,
        1.0f, -1.0f, 1.0f,
        -1.0f, -1.0f, -1.0f,
        1.0f, -1.0f, -1.0f,
        1.0f, 1.0f, -1.0f,
        1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f, -1.0f,
        -1.0f, 1.0f, 1.0f,
        -1.0f, 1.0f, -1.0f,
        1.0f, -1.0f, 1.0f,
        -1.0f, -1.0f, 1.0f,
        -1.0f, -1.0f, -1.0f,
        -1.0f, 1.0f, 1.0f,
        -1.0f, -1.0f, 1.0f,
        1.0f, -1.0f, 1.0f,
        1.0f, 1.0f, 1.0f,
        1.0f, -1.0f, -1.0f,
        1.0f, 1.0f, -1.0f,
        1.0f, -1.0f, -1.0f,
        1.0f, 1.0f, 1.0f,
        1.0f, -1.0f, 1.0f,
        1.0f, 1.0f, 1.0f,
        1.0f, 1.0f, -1.0f,
        -1.0f, 1.0f, -1.0f,
        1.0f, 1.0f, 1.0f,
        -1.0f, 1.0f, -1.0f,
        -1.0f, 1.0f, 1.0f,
        1.0f, 1.0f, 1.0f,
        -1.0f, 1.0f, 1.0f,
        1.0f, -1.0f, 1.0f

    };

    int vertBuffer;

    int vertAmount;

    public Bat(Game game) {
        super(game);
        model = new Model(verts, "lighting");
        // generateBuffers();
        //currentShader = new Shader("test");

    }

    public void render() {
        FloatBuffer matrix4x4 = BufferUtils.createFloatBuffer(16);//model view projection matriisi

        FloatBuffer modMatrix = BufferUtils.createFloatBuffer(16);//model view projection matriisi
        Matrix4f modelMatrix = new Matrix4f().translate(new Vector3f(x, 0, y)).scale(0.5f, 0.2f, 0.05f);
        modelMatrix.get(modMatrix);

        Matrix4f vpm = game.getCamera().getViewProjectionMatrix();
        Matrix4f viewMatrixM = game.getCamera().getViewMatrix();
        Matrix4f projectionMatrixM = game.getCamera().getProjectionMatrix();

        FloatBuffer viewMatrix = BufferUtils.createFloatBuffer(16);//model view projection matriisi
        viewMatrixM.get(viewMatrix);
        FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);//model view projection matriisi
        projectionMatrixM.get(projectionMatrix);

        Matrix4f mvp = new Matrix4f();
        vpm.mul(modelMatrix, mvp);//.get(matrix4x4);
        mvp.get(matrix4x4);

        FloatBuffer camera = BufferUtils.createFloatBuffer(3);
        camera.put(game.getCamera().getPosition().x);
        camera.put(game.getCamera().getPosition().y);
        camera.put(game.getCamera().getPosition().z);
        camera.flip();

        FloatBuffer ball = BufferUtils.createFloatBuffer(3);
        ball.put(game.getBall().getX());
        ball.put(0);
        ball.put(game.getBall().getY());
        ball.flip();

        model.render(matrix4x4, modMatrix, viewMatrix, projectionMatrix, camera, ball);

    }
}
