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

import org.joml.Vector3f;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lahtelat
 */
public class GameObjectTest {

    public GameObjectTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of move method, of class GameObject.
     */
    @Test
    public void testMoveX() {
        GameObject testObject = new GameObject(null);
        testObject.move(2, 0);
        assertEquals(2.f, testObject.getX(), 0.01f);
    }

    @Test
    public void testMoveY() {
        GameObject testObject = new GameObject(null);
        testObject.move(0, 2);
        assertEquals(2.f, testObject.getY(), 0.01f);
    }

    @Test
    public void testMoveXY() {
        GameObject testObject = new GameObject(null);
        testObject.move(2, 2);
        assertEquals(2.f, testObject.getY(), 0.01f);
        assertEquals(2.f, testObject.getX(), 0.01f);
    }

    /*
     testaa, toimiiko entity-tyylinen pöivittäminen oikein
     */
    @Test
    public void testDirectionAndUpdate() {
        GameObject testObject = new GameObject(null);
        testObject.setDirection(new Vector3f(1, 0, 0));
        testObject.update(1);
        assertEquals(testObject.getX(), 1 * testObject.getSpeed(), 0.01f);

        assertEquals(testObject.getY(), 0 * testObject.getSpeed(), 0.01f);
    }

    @Test
    public void testSpeed() {
        GameObject testObject = new GameObject(null);
        testObject.setDirection(new Vector3f(1, 0, 0));
        testObject.update(1);
        assertEquals(testObject.getX(), 1 * testObject.getSpeed(), 0.01f);

        assertEquals(testObject.getY(), 0 * testObject.getSpeed(), 0.01f);

        testObject = new GameObject(null);
        testObject.setDirection(new Vector3f(1, 0, 0));
        testObject.setSpeed(1.0f);
        testObject.update(1);
        assertEquals(testObject.getX(), 1 * testObject.getSpeed(), 0.01f);

        assertEquals(testObject.getY(), 0 * testObject.getSpeed(), 0.01f);

    }

}
