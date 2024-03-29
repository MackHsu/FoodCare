package com.example.foodcare.ToolClass;
/********************曾志昊 2017302580214************************/
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class BackGroundSpan implements LineBackgroundSpan {
    private final int color;
    BackGroundSpan(int color){
        this.color = color;
    }
    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#90b8f9"));
        c.drawCircle((right-left)/2,(bottom - top)/2+40,8,paint);
    }
}
