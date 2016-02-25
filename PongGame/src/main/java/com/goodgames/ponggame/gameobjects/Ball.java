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

    private float timeToLive = -1;
    
    private boolean needsDeleted = false;
    
    public void setTimeToLive(float time){
        this.timeToLive=time;
        needsDeleted=true;
    }
    
    public boolean needsToBeDeleted(){
        return needsDeleted && timeToLive <= 0;
    }
    
    private int vertAmount;
    private int vertBuffer = 0;

    private float[] verts;

    private int collisionTime = 0;

    public Ball(Game game) {
        super(game);
        generateBallVerts();
        model = new Model(verts, "test2", false);

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        
        if(needsDeleted){
        timeToLive -= deltaTime;
        }
       
        /*
         törmäykset( atm hardcoded ja seinät on näkymättömät )
         */
        //System.out.println("ball y " + y);
        float batX1 = game.getPlayerBat().getX();
        float batX2 = game.getEnemyBat().getX();
        if (collisionTime == 0) {
            if (y > 2.75 || y < -1.75) {
                if (y > 2.75 && batX1 < x + 0.75 && batX1 > x - 0.75 || y < -1.75 && batX2 < x + 0.75 && batX2 > x - 0.75f) //if( y < batZ  +2 && y >  batZ-2 ) {
                {
                    this.speed += 0.05f;
                    this.direction.z = this.direction.z * -1;
                    collisionTime = 5;
                }
            }
            if (x > 1.75 || x < -1.75) {

                this.speed += 0.05f;
                this.direction.x = this.direction.x * -1;

                collisionTime = 10;
            }
        } else {
            collisionTime--;
        }
    }

    private void generateBallVerts() {
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

    @Override
    public void render() {

        FloatBuffer matrix4x4 = BufferUtils.createFloatBuffer(16);//model matrix TODO: perspektiivi ja projektio

        Matrix4f modelMatrix = new Matrix4f().translate(new Vector3f(x, 0, y)).scale(0.1f, 0.1f, 0.1f);

        Matrix4f vpm = game.getCamera().getViewProjectionMatrix();
        Matrix4f mvp = new Matrix4f();
        vpm.mul(modelMatrix, mvp);//.get(matrix4x4);
        mvp.get(matrix4x4);

        model.render(matrix4x4, GL11.GL_LINE_LOOP);
    }

}
