package liushilive.github.io.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liush
 */
public class FingerView extends View {

    private final Paint paint;
    private final HashMap<Integer, Path> pointerPathMap;

    public FingerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        pointerPathMap = new HashMap<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // A pointer's index may change throughout touch events, so track id
        int idx = event.getActionIndex();
        int id = event.getPointerId(idx);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Path pth = new Path();
                pth.moveTo(event.getX(idx), event.getY(idx));
                pointerPathMap.put(id, pth);
                break;
            case MotionEvent.ACTION_MOVE:
                // Update all pointers since ACTION_MOVE events don't provide a pointer index
                for (Map.Entry<Integer, Path> entry : pointerPathMap.entrySet()) {
                    idx = event.findPointerIndex(entry.getKey());
                    try {
                        entry.getValue().lineTo(event.getX(idx), event.getY(idx));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
//                pointerPathMap.remove(id);
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Path p : pointerPathMap.values()) {
            canvas.drawPath(p, paint);
        }
    }
}