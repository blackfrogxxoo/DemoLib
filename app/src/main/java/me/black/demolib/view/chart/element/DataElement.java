package me.black.demolib.view.chart.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

import me.black.demolib.view.chart.data.AbstractData;
import me.black.library.util.DeviceUtil;

public class DataElement extends Element {
    private static final int MIN_COLUMN_WIDTH = 16;
    private static final int MIN_COLUMN_RADIUS = 8;
    private static final int MONTH_PADDING = 16;
    RectF rectF;

    public DataElement(Paint paint, AbstractData data) {
        super(paint, data);
    }

    @Override
    public void onDraw(Canvas canvas) {
        List<Float> values = data.getValues();
        int i = 0;
        for(Float value : values) {
            float width = (right - left) - 2 * DeviceUtil.dp2px(MONTH_PADDING) * fraction;
            float height = bottom - top;
            // 7 * 5

            if(i / 7 == 1) { // 第二周
                float cx = DeviceUtil.dp2px(MONTH_PADDING) * fraction + width / 14 + width / 7 * (i % 7);
                float cy = height / 10 + height / 5 * (i / 7) + (1 - fraction) * (height / 5 - 1.5f * value);
                // 模拟变形
                float radius = getRadius(value);
//            canvas.drawCircle(cx, cy, radius, paint);
                RectF rectF = getRectF(value, cx, cy);
                paint.setAlpha((int) ((1 - fraction < 0 ? 0 : 1 - fraction) * 100 + 17));
                canvas.drawRoundRect(rectF, radius, radius, paint);
            } else {
                float cx = DeviceUtil.dp2px(MONTH_PADDING) + width / 14 + width / 7 * (i % 7);
                float cy = height / 10 + height / 5 * (i / 7);
                // 模拟消失
                paint.setAlpha(17);
                canvas.drawCircle(cx, cy, fraction * value, paint);
            }
            i++;
        }
    }

    /**
     * 0.0 ~ 1.0： 2dp ~ value
     * @param value
     * @return
     */
    private float getRadius(float value) {
        return fraction * (value - MIN_COLUMN_RADIUS) + MIN_COLUMN_RADIUS;
    }

    /**
     * 0.0 ~ 1.0: 4dp * value ~ value * value
     * @param value
     * @param cx
     * @param cy
     * @return
     */
    private RectF getRectF(float value, float cx, float cy) {
        float dx = fraction * (value - DeviceUtil.dp2px(MIN_COLUMN_WIDTH / 2)) + DeviceUtil.dp2px(MIN_COLUMN_WIDTH / 2);
        float dy = -0.5f * (1 - fraction) * value;
        if(rectF == null) {
            rectF = new RectF(cx - dx, cy - (value - dy), cx + dx, cy + (value - dy));
        } else {
            rectF.set(cx - dx, cy - (value - dy), cx + dx, cy + (value - dy));
        }
        return rectF;
    }
}
