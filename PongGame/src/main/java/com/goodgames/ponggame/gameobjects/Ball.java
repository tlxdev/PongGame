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
import java.util.ArrayList;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author lahtelat
 */
public class Ball extends GameObject {

    private Shader currentShader;

    private int vertAmount;
    private int vertBuffer = 0;

    private float[] verts;

    public Ball(Game game) {
        super(game);
        currentShader = new Shader("test2");
        generateBallVerts();
        generateBuffers();

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        /*
          törmäykset( atm hardcoded ja seinät on näkymättömät )
         */
        if (y > 2.75 || y < -1.75) {
            this.direction.z = this.direction.z * -1;
        }
        if (x > 2 || x < -2) {

            this.direction.x = this.direction.x * -1;
        }
    }

    /*
     generoi 3d pisteet pallosta. kaava otettu: http://stackoverflow.com/a/4082020
     */
    public void generateBallVerts() {
        ArrayList<Float> vertList = new ArrayList<>();
        for (float i = 0; i < 16; i++) {
            for (float ii = 0; ii < 15; ii++) {
                /*x*/ vertList.add((float) (Math.sin(Math.PI * (i / 16)) * Math.cos(2 * Math.PI * (ii / 16))));
                /*y*/ vertList.add((float) (Math.sin(Math.PI * (i / 16)) * Math.sin(2 * Math.PI * (ii / 16))));
                /*z*/ vertList.add((float) (Math.cos(Math.PI * (i / 16))));

            }
        }
        verts = new float[vertList.size()];
        for (int i = 0; i < vertList.size(); i++) {
            verts[i] = vertList.get(i);
        }
    }

    private void generateBuffers() {

        FloatBuffer buffer = BufferUtils.createFloatBuffer(verts.length);
        buffer.put(verts);
        buffer.flip();

        vertAmount = verts.length / 3; //1 vertex=3 kolmiota

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vertBuffer = GL15.glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertBuffer);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);
    }

    public void render() {

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vertBuffer);
        glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(0);

        glUseProgram(currentShader.getShaderId());

        int mvpMatrixId = glGetUniformLocation(currentShader.getShaderId(), "mvp");

        FloatBuffer matrix4x4 = BufferUtils.createFloatBuffer(16);//model matrix TODO: perspektiivi ja projektio

        Matrix4f modelMatrix = new Matrix4f().translate(new Vector3f(x, 0, y)).scale(0.2f, 0.2f, 0.2f);

        Matrix4f vpm = game.getCamera().getViewProjectionMatrix();
        Matrix4f mvp = new Matrix4f();
        vpm.mul(modelMatrix, mvp);//.get(matrix4x4);
        mvp.get(matrix4x4);
        glUniformMatrix4fv(mvpMatrixId, false, matrix4x4);

        glDrawArrays(GL11.GL_LINE_LOOP, 0, vertAmount);

        glUseProgram(0);
    }

    public void renderOld() {

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER, vertBuffer);
        glUseProgram(currentShader.getShaderId());
        glEnableVertexAttribArray(0);
        int mvpMatrixId = glGetUniformLocation(currentShader.getShaderId(), "mvp");

        FloatBuffer matrix4x4 = BufferUtils.createFloatBuffer(16);//model matrix TODO: perspektiivi ja projektio

        Matrix4f modelMatrix = new Matrix4f().translate(new Vector3f(x, 0, y)).scale(2, 0.5f, 0.1f);

        Matrix4f vpm = game.getCamera().getViewProjectionMatrix();
        Matrix4f mvp = new Matrix4f();
        vpm.mul(modelMatrix, mvp);//.get(matrix4x4);
        mvp.get(matrix4x4);
        glUniformMatrix4fv(mvpMatrixId, false, matrix4x4);

        FloatBuffer back = BufferUtils.createFloatBuffer(verts.length);
        GL15.glGetBufferSubData(GL_ARRAY_BUFFER, 0, back);

        while (back.hasRemaining()) {
            System.out.println(back.get());
        }

        glDrawArrays(GL11.GL_TRIANGLES, 0, vertAmount);
        glDisableVertexAttribArray(0);
        glUseProgram(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

}
