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
 * Handles AI
 *
 * @author lahtelat
 */
public class AI {

    private Difficulty difficulty;

    private Bat ourBat;
    private Ball ball;

    /**
     * AI.
     *
     * @param difficulty the difficulty of the ai
     * @param game the game instance
     * @param ourbat the bat that the ai will control
     *
     */
    
    public AI(Difficulty difficulty, Game game, Bat ourbat) {
        this.difficulty = difficulty;
        this.ball = game.getBall();
        this.ourBat = ourbat;
        setupDifficulty(difficulty);
    }

    private void setupDifficulty(Difficulty t) {
        switch (t) {
            case EASY:

                ourBat.setSpeed(1);
                break;
            case HARD:

                ourBat.setSpeed(2);
                break;
            case IMPOSSIBLE:

                ourBat.setSpeed(5);
                break;
        }
    }

    public void setBall(Ball ball) {
        this.ball = ball;

    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

   /**
    * Called every frame, AI logic.
    * @param deltaTime time between frames
    */
    
    public void update(double deltaTime) {

        if (ball.getX() > ourBat.getX()) {
            ourBat.setDirection(new Vector3f(1, 0, 0));
        } else {

            ourBat.setDirection(new Vector3f(-1, 0, 0));
        }

    }

    public enum Difficulty {

        EASY,
        HARD,
        IMPOSSIBLE
    };

}
