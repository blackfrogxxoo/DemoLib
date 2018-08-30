package me.black.demolib.view.chart.element;

import android.graphics.Paint;

import me.black.demolib.view.chart.data.AbstractData;

public abstract class Element implements IDraw {
    protected Paint paint;
    protected float left, top, right, bottom;
    protected AbstractData data;
    protected float fraction; // 0.0(柱状) ~ 1.0(圆形)

    public Element(Paint paint, AbstractData data) {
        this.paint = paint;
        this.data = data;
    }

    @Override
    public void setRect(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void setFraction(float fraction) {
        this.fraction = fraction;
    }

}
