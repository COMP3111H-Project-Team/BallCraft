package hkust.comp3111h.ballcraft.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

public class ValueDisplayView extends SurfaceView {
    
    private int displayValue;
    
    private final int maxValue = 10;
    
    public ValueDisplayView(Context context) {
        super(context);
        this.initLayout();
    }

    public ValueDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initLayout();
    }
    
    private void initLayout() {
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        this.setWillNotDraw(false);
    }
    
    public void setDisplayValue(int dv) {
        this.displayValue = dv;
        this.invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(100, 100, 200));
        paint.setStrokeWidth(2);
        canvas.drawLine(0, 0, this.getWidth(), 0, paint);
        canvas.drawLine(0, this.getHeight(), this.getWidth(), this.getHeight(), paint);
        canvas.drawLine(0, 0, 0, this.getHeight(), paint);
        canvas.drawLine(this.getWidth(), 0, this.getWidth(), this.getHeight(), paint);
        
        int length = this.displayValue * this.getWidth() / this.maxValue;
        Log.w("length", "" + length);
        
        canvas.drawRect(0, 0, length, this.getHeight(), paint);
        
        paint.setColor(Color.WHITE);
        paint.setTextSize(10);
        canvas.drawText("Mass", 0, 0, paint);
        
        /*
        paint.setColor(Color.WHITE);
        canvas.drawText("heihei", 0, 0, paint);
        */
    }
    
}
