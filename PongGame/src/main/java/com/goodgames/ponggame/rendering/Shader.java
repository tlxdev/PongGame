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

import com.goodgames.ponggame.Main;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL20.*;

/**
 * A class for loading shaders.
 *
 * @author lahtelat
 */
public class Shader {

    private int shaderId = 0;

    /**
     * A new shader.
     *
     * @param shaderName the name of the shader(don't include .vert or .frag)
     */
    public Shader(String shaderName) {
        makeShader(shaderName);
    }

    public int getShaderId() {
        return shaderId;
    }

    private void checkShaderStatus(int id, String type) {

        if (GL20.glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            String infoLog = GL20.glGetShaderInfoLog(id);
            System.out.println("failed to compile " + type + " shader error: \n" + infoLog);
        }   
    }

    private void makeShader(String shaderName) {

        /*  
         shaderit tallennetaan shaders/nimi.vert ja shaders/nimi.frag
         */
        String vert = readFile(Main.SHADER_DIRECTORY + shaderName + ".vert");

        String frag = readFile(Main.SHADER_DIRECTORY + shaderName + ".frag");
        int vertId;
        int fragId;

        vertId = glCreateShader(GL_VERTEX_SHADER);
        fragId = glCreateShader(GL_FRAGMENT_SHADER);

        /*
         käännä vertex- ja fragmentshaderit        
         */
        glShaderSource(vertId, vert);
        glCompileShader(vertId);

        glShaderSource(fragId, frag);
        glCompileShader(fragId);

        checkShaderStatus(vertId, "vertex");
        checkShaderStatus(fragId, "fragment");

        shaderId = glCreateProgram();
        glAttachShader(shaderId, vertId);
        glAttachShader(shaderId, fragId);

        /*
         attribuutti 0 tällä hetkellä shadereissa aina position
         */
        glBindAttribLocation(getShaderId(), 0, "position");
        glBindAttribLocation(getShaderId(), 1, "normal");

        glLinkProgram(shaderId);
        String infoLog = GL20.glGetProgramInfoLog(shaderId);
        IntBuffer info = BufferUtils.createIntBuffer(5);
        GL20.glGetProgramiv(shaderId, GL_LINK_STATUS, info);
        if (info.get() == GL_FALSE) {
            //jos shaderin linkityksessä virhe: 
            System.out.println("SHADER ERROR: " + infoLog);
            glDeleteProgram(shaderId);
            glDeleteShader(vertId);
            glDeleteShader(fragId);
            return;
        }

        glDetachShader(shaderId, vertId);
        glDetachShader(shaderId, fragId);
    }

    private String readFile(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
            String shader = "";
            for (String line : lines) {
                shader += line + "\n";
            }
            return shader;
        } catch (Exception ex) {
            System.out.println("Failed to find shader " + fileName);
        }
        return null;
    }

}
