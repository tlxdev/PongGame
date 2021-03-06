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
import com.goodgames.ponggame.rendering.Shader;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lahtelat
 */
public class MainTest {

    public MainTest() {
    }

    @Before
    public void setUp() {
        Main.main(new String[]{"test"});

    }

    @Test
    public void testMain() {
        System.out.println("assertequalsbefore");
        assertEquals(Main.getGameWindow().hasStarted(), true);

        System.out.println("assertEqualsAfter");
        Main.getGameWindow().stop();
    }

    @Test
    public void testShader() {
        Shader s = new Shader("test");
        assertNotEquals(s.getShaderId(), -1);

        Main.getGameWindow().stop();

    }

    @Test
    public void testWindowId() {
        Shader s = new Shader("test");
        assertNotEquals(Main.getGameWindow().getWindowId(), -1);

        Main.getGameWindow().stop();

    }

    @Test
    public void testBat() {

        Bat b = new Bat(Main.getGameWindow().getGame());

        b.move(1, 0);

        assertEquals(b.getX(), 1, 0.01);

        Main.getGameWindow().stop();

    }

    @Test
    public void testBall() {

        Ball b = new Ball(Main.getGameWindow().getGame());

        b.move(1, 0);

        assertEquals(b.getX(), 1, 0.01);

        Main.getGameWindow().stop();
    }

}
