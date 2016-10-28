package com.nilhcem.shammane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.nilhcem.shammane.core.WatchMode;

public class MyWatchFaceRenderer {

    private final Context context;

    private final Paint minutesIndicatorPaint = new Paint();
    private final Paint minutesFillPaint = new Paint();
    private final Paint minutesBorderPaint = new Paint();
    private final Paint ambientMinutesIndicatorPaint = new Paint();
    private final Paint ambientMinutesBorderPaint = new Paint();
    private final Path minutesIndicatorPath = new Path();
    private final Path minutesCirclePath = new Path();

    private final Paint hoursIndicatorPaint = new Paint();
    private final Paint hoursFillPaint = new Paint();
    private final Paint hoursBorderPaint = new Paint();
    private final Paint ambientHoursIndicatorPaint = new Paint();
    private final Paint ambientHoursBorderPaint = new Paint();
    private final Path hoursIndicatorPath = new Path();
    private final Path hoursCirclePath = new Path();

    private float centerX;
    private float centerY;
    private WatchMode mode = WatchMode.INTERACTIVE;

    public MyWatchFaceRenderer(Context context) {
        this.context = context;

        initMinutesPaintObjects(context);
        initHoursPaintObjects(context);
        initAmbientPaintObjects(context);
    }

    public void setSize(int width, int height, int chinSize) {
        float newCenterX = 0.5f * width;
        float newCenterY = 0.5f * (height + chinSize);

        if (Math.abs(centerX - newCenterX) > 0.5f) {
            centerX = newCenterX;
            centerY = newCenterY;

            int min = Math.min(width, height - chinSize);
            setPaths(minutesIndicatorPath, minutesCirclePath, centerX, centerY, 0.4f * min);
            setPaths(hoursIndicatorPath, hoursCirclePath, centerX, centerY, 0.28f * min);
        }
    }

    public void setMode(WatchMode mode) {
        this.mode = mode;
        boolean lowBit = mode == WatchMode.LOW_BIT;
        int color = ContextCompat.getColor(context, lowBit ? R.color.white : R.color.grey);

        ambientMinutesIndicatorPaint.setColor(color);
        ambientMinutesBorderPaint.setColor(color);
        ambientMinutesIndicatorPaint.setAntiAlias(!lowBit);
        ambientMinutesBorderPaint.setAntiAlias(!lowBit);

        ambientHoursIndicatorPaint.setAntiAlias(!lowBit);
        ambientHoursBorderPaint.setAntiAlias(!lowBit);
    }

    public void drawTime(Canvas canvas, float angleHours, float angleMinutes, float angleSeconds) {
        boolean interactive = mode == WatchMode.INTERACTIVE;

        // Background
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // Minutes
        canvas.save();
        canvas.rotate(angleMinutes, centerX, centerY);
        if (interactive) {
            canvas.drawPath(minutesCirclePath, minutesFillPaint);
        }
        canvas.drawPath(minutesCirclePath, interactive ? minutesBorderPaint : ambientMinutesBorderPaint);
        canvas.drawPath(minutesIndicatorPath, interactive ? minutesIndicatorPaint : ambientMinutesIndicatorPaint);
        canvas.restore();

        // Hours
        canvas.save();
        canvas.rotate(angleHours, centerX, centerY);
        if (interactive) {
            canvas.drawPath(hoursCirclePath, hoursFillPaint);
        }
        canvas.drawPath(hoursCirclePath, interactive ? hoursBorderPaint : ambientHoursBorderPaint);
        canvas.drawPath(hoursIndicatorPath, interactive ? hoursIndicatorPaint : ambientHoursIndicatorPaint);
        canvas.restore();
    }

    private void initMinutesPaintObjects(Context context) {
        minutesIndicatorPaint.setStyle(Paint.Style.STROKE);
        minutesIndicatorPaint.setColor(ContextCompat.getColor(context, R.color.beige));
        minutesIndicatorPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.time_stroke_width));
        minutesIndicatorPaint.setAntiAlias(true);

        minutesBorderPaint.set(minutesIndicatorPaint);
        minutesBorderPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.circle_stroke_width));

        minutesFillPaint.set(minutesIndicatorPaint);
        minutesFillPaint.setStyle(Paint.Style.FILL);
        Bitmap dotPattern = BitmapFactory.decodeResource(context.getResources(), R.drawable.dot_pattern);
        Shader dotShader = new BitmapShader(dotPattern, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        minutesFillPaint.setShader(dotShader);
    }

    private void initHoursPaintObjects(Context context) {
        hoursIndicatorPaint.setStyle(Paint.Style.STROKE);
        hoursIndicatorPaint.setColor(ContextCompat.getColor(context, R.color.white));
        hoursIndicatorPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.time_stroke_width));
        hoursIndicatorPaint.setAntiAlias(true);

        hoursBorderPaint.set(hoursIndicatorPaint);
        hoursBorderPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.circle_stroke_width));

        hoursFillPaint.set(hoursIndicatorPaint);
        hoursFillPaint.setStyle(Paint.Style.FILL);
        float shaderSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());
        Shader strokeShader = new LinearGradient(0f, 0f, shaderSize, shaderSize, new int[]{Color.TRANSPARENT, Color.TRANSPARENT, Color.WHITE, Color.WHITE}, new float[]{0, 0.85f, 0.85f, 1f}, Shader.TileMode.REPEAT);
        hoursFillPaint.setShader(strokeShader);
    }

    private void initAmbientPaintObjects(Context context) {
        ambientMinutesIndicatorPaint.setStyle(Paint.Style.STROKE);
        ambientMinutesIndicatorPaint.setColor(ContextCompat.getColor(context, R.color.white));
        ambientMinutesIndicatorPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.time_stroke_width_ambient));
        ambientMinutesIndicatorPaint.setAntiAlias(true);

        ambientMinutesBorderPaint.set(ambientMinutesIndicatorPaint);
        ambientMinutesBorderPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.circle_stroke_width_ambient));

        ambientHoursIndicatorPaint.set(ambientMinutesIndicatorPaint);
        ambientHoursBorderPaint.set(ambientMinutesBorderPaint);
    }

    private void setPaths(Path indicator, Path circle, float centerX, float centerY, float radius) {
        indicator.reset();
        indicator.moveTo(centerX, centerY);
        indicator.lineTo(centerX, centerY - radius);

        circle.reset();
        circle.arcTo(new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius), 90f, 180f);
        circle.close();
    }
}
