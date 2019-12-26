package com.lab.calorie.animation;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class Stage extends GLSurfaceView {

    private final AnimationRenderer renderer;
    private String bmiStatus = "normal";
    private int counter = 0;

    public Stage(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        renderer = new AnimationRenderer();
        renderer.setBmiStatus(bmiStatus);
        setRenderer(renderer);
    }

    public void setBmiStatus(String bmiStatus) {
        System.out.println("lewat set bmi status di stage yaitu " + bmiStatus);
        this.bmiStatus = bmiStatus;
        renderer.setBmiStatus(bmiStatus);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (counter == 0) {
            this.setBmiStatus("normal");
            requestRender();
            counter++;
        } else {
            this.setBmiStatus("underweight");
            requestRender();
        }

        return super.onTouchEvent(event);
    }
}
