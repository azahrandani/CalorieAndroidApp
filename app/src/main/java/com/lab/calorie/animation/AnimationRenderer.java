package com.lab.calorie.animation;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Parcelable;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AnimationRenderer implements GLSurfaceView.Renderer {

    private TopSquare activeTopSquare, inactiveTopSquare;
    private MiddleSquare activeMiddleSquare, inactiveMiddleSquare;
    private BottomSquare activeBottomSquare, inactiveBottomSquare;

    private String bmiStatus;
    private int counter;

    public void setBmiStatus(String bmiStatus) {
        System.out.println("lewat setBmiStatus di renderer yaitu " + bmiStatus);
        this.bmiStatus = bmiStatus;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        activeTopSquare = new TopSquare(true);
        activeMiddleSquare = new MiddleSquare(true);
        activeBottomSquare = new BottomSquare(true);

        inactiveTopSquare = new TopSquare(false);
        inactiveMiddleSquare = new MiddleSquare(false);
        inactiveBottomSquare = new BottomSquare(false);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        if (counter == 0) {
            bottomSquareOn();
            counter++;
        } else {
            if (counter == 1) {
                middleSquareOn();
                counter++;
            } else if (counter == 2) {
                topSquareOn();
                counter++;
            } else if (counter == 3) {
                if (bmiStatus.equals("underweight")) {
                    bottomSquareOn();
                }
                else {
                    bottomSquareOn();
                    counter++;
                }
            } else if (counter == 4) {
                if (bmiStatus.equals("normal")) {
                    middleSquareOn();
                } else {
                    middleSquareOn();
                    counter++;
                }
            } else {
                topSquareOn();
            }
            SystemClock.sleep(700);
        }

        System.out.println("finish onDraw rendering");
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    private void bottomSquareOn() {
        inactiveTopSquare.draw();
        inactiveMiddleSquare.draw();
        activeBottomSquare.draw();
    }

    private void middleSquareOn() {
        inactiveTopSquare.draw();
        activeMiddleSquare.draw();
        inactiveBottomSquare.draw();
    }

    private void topSquareOn() {
        activeTopSquare.draw();
        inactiveMiddleSquare.draw();
        inactiveBottomSquare.draw();
    }
}
