package me.black.demolib.view.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.black.demolib.view.chart.data.AbstractData;
import me.black.demolib.view.chart.element.DataElement;
import me.black.demolib.view.chart.element.Element;
import me.black.library.util.DeviceUtil;

public class Chart extends View {
    private static final String TAG = "Chart";
    private static final float PADDING = DeviceUtil.dp2px(16);
    private float width, height;
    private List<Element> elements;
    private Paint paint;
    private Paint textPaint;
    private Calendar calendar;
    private Point[] positions;
    private boolean isMonth = true;
    private float fraction = 1.0f;

    public Chart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#110000ff"));
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#666666"));
        textPaint.setTextSize(DeviceUtil.dp2px(13));
        calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinate(canvas);
        drawElements(canvas, fraction);
    }

    private void drawCoordinate(Canvas canvas) {
        // TODO 月历、周历、日历
        if(isMonth) {
            // 判断当前状态，计算每个元素的位置，并下发到元素
            Log.i(TAG, "===============================");
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.DAY_OF_MONTH));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.DAY_OF_WEEK));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.WEEK_OF_MONTH));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.HOUR_OF_DAY));
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // 月份+1
            int today = calendar.get(Calendar.DAY_OF_MONTH);
            String week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINA);
            int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            Log.i(TAG, "drawCoordinate: " + year + "-" + (month + 1) + "-" + today + "-" + week);
            calendar.set(year, month, firstDay);
            Log.i(TAG, "===============================");
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.DAY_OF_MONTH));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.DAY_OF_WEEK));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.WEEK_OF_MONTH));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.HOUR_OF_DAY));

            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH); // 月份+1
            today = calendar.get(Calendar.DAY_OF_MONTH); // 日+1
            today = calendar.get(Calendar.DAY_OF_MONTH); // 日+1
            week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINA);
            Log.i(TAG, "drawCoordinate: " + year + "-" + (month + 1) + "-" + today + "-" + week);
            int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            calendar.set(year, month, lastDay);
            Log.i(TAG, "===============================");
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.DAY_OF_MONTH));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.DAY_OF_WEEK));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.WEEK_OF_MONTH));
            Log.i(TAG, "drawCoordinate: " + calendar.get(Calendar.HOUR_OF_DAY));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH); // 月份+1
            today = calendar.get(Calendar.DAY_OF_MONTH); // 日+1
            week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINA);
            Log.i(TAG, "drawCoordinate: " + year + "-" + (month + 1) + "-" + today + "-" + week);
            int lastDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int weekCount = calendar.get(Calendar.WEEK_OF_MONTH);
            calendar.set(year, month, today);
            positions = new Point[lastDay];
            for (int i = firstDay; i <= lastDay; i++) {
                calendar.set(Calendar.DAY_OF_MONTH, i);
                positions[i - 1] = new Point();
                positions[i - 1].x = (int) (-(width - 2 * PADDING) / 14 + PADDING + (width - 2 * PADDING) / 7 * (calendar.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1));
                positions[i - 1].y = (int) (height / (2 * weekCount) + height / weekCount * (calendar.get(Calendar.WEEK_OF_MONTH) - 1));
                float textWidth = textPaint.measureText("" + i);
                canvas.drawText("" + i, positions[i - 1].x - textWidth / 2, positions[i - 1].y + DeviceUtil.dp2px(6), textPaint);
            }
        } else {
            for(int i = 1; i <= 7; i ++) {
                String text = getWeekDayString(i);
                float textWidth = textPaint.measureText(text);
                canvas.drawText(text, width / 14 + width / 7 * (i - 1) - textWidth / 2, height * 1 / 2 + DeviceUtil.dp2px(15), textPaint);
            }
        }
    }

    private static String getWeekDayString(int index) {
        switch (index) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
        }
        return "";
    }

    private void drawElements(Canvas canvas, float fraction) {
        for(Element element : elements) {
            element.setRect(0, 0, width, height);
            element.setFraction(fraction);
            element.onDraw(canvas);
        }
    }

    public void setData(List<AbstractData> data) {
        elements = new ArrayList<>();
        for(AbstractData item : data) {
            Element element = new DataElement(paint, item);
            elements.add(element);
        }
        invalidate();
    }

    public void showMonth(boolean show) {
        if(isMonth != show) {
            isMonth = show;
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
            valueAnimator.addUpdateListener(animation -> {
                if(show) {
                    fraction = animation.getAnimatedFraction();
                } else {
                    fraction = 1 - animation.getAnimatedFraction();
                }
                invalidate();
            });
            valueAnimator.setInterpolator(new OvershootInterpolator());
            valueAnimator.start();
        }
    }
}
