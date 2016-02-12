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
package com.goodgames.ponggame.rendering;

import com.goodgames.ponggame.rendering.Shader;
import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferData;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author lahtelat
 */
public class Model {

    Vertex[] verts;
    //Vector3f[] verts;
    float[] normals;

    int vertAmount;
    int vertBuffer;

    int normalBuffer;

    Shader currentShader;

    Vector3f[] triangleNormals;

    public Model(float[] vertFloats, String shaderName) {
        this(vertFloats, shaderName, true);
        /*    verts = new Vertex[vertFloats.length / 3];
         for (int i = 0; i < verts.length; i++) {
         verts[i] = new Vertex(vertFloats[i], vertFloats[i + 1], vertFloats[i + 2]);
         }

         calculateNormals();

         generateBuffers(vertFloats);

         currentShader = new Shader(shaderName);*/
    }

    public Model(float[] vertFloats, String shaderName, boolean calcNormals) {

        verts = new Vertex[vertFloats.length / 3];
        for (int i = 0; i < verts.length; i++) {
            verts[i] = new Vertex(vertFloats[3 * i], vertFloats[(3 * i) + 1], vertFloats[(3 * i) + 2]);
        }

        if (calcNormals) {
            calculateNormals();
        }

        generateBuffers(vertFloats);

        currentShader = new Shader(shaderName);
    }

    public void calculateNormals() { //todo korjaa tämä
        System.out.println("verts length " + verts.length);
        normals = new float[verts.length * 3];
        for (int i = 0; i < verts.length; i += 3) {

            Vector3f vertPosNeg = verts[i].getPosition().mul(-1);

            Vector3f edge1 = verts[i + 1].getPosition().add(vertPosNeg);
            Vector3f edge2 = verts[i + 2].getPosition().add(vertPosNeg);

            Vector3f normal = edge1.cross(edge2).normalize();

            /*
             vaikea selittää.. mutta siis normals on käytännössä array, n=x n+1=y n+2 = z, joka n= yksi vertex yms, yhdessä kolmiossa 3 vertex
             todo: refaktoroi alemmat rivit
             */
            normals[3 * i] = normal.x;
            normals[(3 * i) + 1] = normal.y;
            normals[(3 * i) + 2] = normal.z;

            normals[3 * (i + 1)] = normal.x;
            normals[(3 * (i + 1)) + 1] = normal.y;
            normals[(3 * (i + 1)) + 2] = normal.z;

            normals[3 * (i + 2)] = normal.x;
            normals[(3 * (i + 2)) + 1] = normal.y;
            normals[(3 * (i + 2)) + 2] = normal.z;
            verts[i].setNormal(normal);
            verts[i + 1].setNormal(normal);
            verts[i + 2].setNormal(normal);
            /*vertexejen normaali on sama kuin kolmion normaali*/
        }

        generateNormalBuffers();

    }

    private void generateNormalBuffers() {

        normalBuffer = GL15.glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, normalBuffer);

        //glBufferData(GL_ARRAY_BUFFER, normals.size() * sizeof(glm::vec3),  & normals[0], GL_STATIC_DRAW);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(normals.length);
        buffer.put(normals);
        buffer.flip();
        //vertAmount = vertFloats.length / 3; //1 vertex=3 kolmiota
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    private void generateBuffers(float[] vertFloats) {

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertFloats.length);
        buffer.put(vertFloats);
        buffer.flip();

        vertAmount = vertFloats.length / 3; //1 vertex=3 kolmiota

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vertBuffer = GL15.glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertBuffer);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);
    }

    public void render(FloatBuffer matrix4x4, FloatBuffer m, FloatBuffer v, FloatBuffer p, FloatBuffer camera, FloatBuffer light) {

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, vertBuffer);
        glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, normalBuffer);
        glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);

        glUseProgram(currentShader.getShaderId());

        int mvpMatrixId = glGetUniformLocation(currentShader.getShaderId(), "mvp");

        int mLoc = glGetUniformLocation(currentShader.getShaderId(), "m");
        int vLoc = glGetUniformLocation(currentShader.getShaderId(), "v");
        int pLoc = glGetUniformLocation(currentShader.getShaderId(), "p");

        int cameraPos = glGetUniformLocation(currentShader.getShaderId(), "cameraPos");
        int lightPos = glGetUniformLocation(currentShader.getShaderId(), "lightPos");

        //glUniformMatrix4fv(m, false, matrix4x4);
        glUniformMatrix4fv(mvpMatrixId, false, matrix4x4);

        glUniformMatrix4fv(mLoc, false, m);
        glUniformMatrix4fv(vLoc, false, v);
        glUniformMatrix4fv(pLoc, false, p);
        
        System.out.println("lightpos " + lightPos + " cameraPos " + cameraPos);

        GL20.glUniform3fv(cameraPos, camera);
        GL20.glUniform3fv(lightPos, light);

        glDrawArrays(GL11.GL_TRIANGLES, 0, vertAmount);

        glUseProgram(0);

    }

    public void render(FloatBuffer matrix4x4, int renderType) {

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vertBuffer);
        glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        GL20.glEnableVertexAttribArray(0);

        glUseProgram(currentShader.getShaderId());

        int mvpMatrixId = glGetUniformLocation(currentShader.getShaderId(), "mvp");

        glUniformMatrix4fv(mvpMatrixId, false, matrix4x4);

        glDrawArrays(renderType, 0, vertAmount);

        glUseProgram(0);

    }

    class Vertex {

        Vector3f pos;
        Vector3f normal;

        public Vertex(float x, float y, float z) {
            this.pos = new Vector3f(x, y, z);
        }

        public Vector3f getPosition() {
            return pos;
        }

        public void setNormal(Vector3f norm) {
            this.normal = norm;
        }

        @Override
        public String toString() {
            return pos.toString();
        }

    }

}
