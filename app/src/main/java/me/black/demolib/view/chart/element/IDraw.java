package me.black.demolib.view.chart.element;

import android.graphics.Canvas;

/**
 * 图表内绘制的元素接口
 */
public interface IDraw {
    void onDraw(Canvas canvas);
    void setRect(float left, float top, float right, float bottom);
    void setFraction(float fraction);
}
