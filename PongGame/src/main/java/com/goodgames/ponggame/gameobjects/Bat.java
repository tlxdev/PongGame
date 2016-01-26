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
import com.goodgames.ponggame.Shader;
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
        generateBuffers();
        currentShader = new Shader("test");

    }

    private void generateBuffers() {
        vertBuffer = glGenBuffers();
        System.out.println("vertbuffer on " + vertBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, vertBuffer);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(verts.length);
        buffer.put(verts);
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        vertAmount = verts.length / 3; //1 vertex=3 kolmiota, kun käytetään GL_TRIANGLES
        System.out.println("vertamount on " + vertAmount);

        glBindBuffer(GL_ARRAY_BUFFER, -1);
    }

    public void render() {

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, vertBuffer);
        glUseProgram(currentShader.getShaderId());
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        int mvpMatrixId = glGetUniformLocation(currentShader.getShaderId(), "mvp");

        FloatBuffer matrix4x4 = BufferUtils.createFloatBuffer(16);//model view projection matriisi

        Matrix4f modelMatrix = new Matrix4f().translate(new Vector3f(x, 0, y)).scale(1, 0.2f, 0.05f);

        Matrix4f vpm = game.getCamera().getViewProjectionMatrix();
        Matrix4f mvp = new Matrix4f();
        vpm.mul(modelMatrix, mvp);//.get(matrix4x4);
        mvp.get(matrix4x4);
        
        /*
            mvp matriisi shaderille
        */
        
        glUniformMatrix4fv(mvpMatrixId, false, matrix4x4);
       
        glDrawArrays(GL_TRIANGLES, 0, vertAmount);
        glDisableVertexAttribArray(0);
        glUseProgram(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

    }
}
