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

import com.goodgames.ponggame.gameobjects.Ball;
import com.goodgames.ponggame.gameobjects.Bat;
import org.joml.Vector3f;

/**
 *
 * @author lahtelat
 */
public class Game {

    Bat player1, player2;

    Camera camera;

    private MouseInput input;
    
    public Bat getPlayerBat() {
        return player1;
    }

    public Camera getCamera() {
        return camera;
    }

    private Ball ball;

    
    public Game() {
        camera = new Camera();
        ball = new Ball(this);
        player1 = new Bat(this);
        player2 = new Bat(this);
        player2.move(0, -3);
        player1.move(0, 2);
        
        ball.setDirection(new Vector3f(0.5f, 0, 0.5f).normalize());

    }

    public void render() {

        //renderöi esineet
        player1.render();
        player2.render();
        ball.render();

    }

    /*
    
     Sovelluksen logiikka(mailojen ja pallon törmäys, pallon liikkuminen) tulee tähän
    
     */
    public void update(double deltaTime) {

        input.update(deltaTime);
        ball.update(deltaTime);

            
    }
    
    public void setWindowId(long id){
        input = new MouseInput(id);
    }

}
