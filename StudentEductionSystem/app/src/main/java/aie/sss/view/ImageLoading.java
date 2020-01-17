package aie.sss.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.concurrent.atomic.AtomicBoolean;

import aie.sss.R;


public class ImageLoading extends AppCompatImageView {
    private int ProgressBarColor;
    private Paint paint;
    private ValueAnimator animator;
    private float angle, start;
    private RectF rect;

    private Context context;

    public ImageLoading(Context context) {
        this(context, null, 0);
    }

    public ImageLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context c, AttributeSet attrs, int def) {
        this.context = c;
        Log.e("asd", "ass");
        TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.ImageLoading, def, 0);
        ProgressBarColor = array.getColor(R.styleable.ImageLoading_progrssbarColor, Color.WHITE);
        array.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ProgressBarColor);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.5f);


        installAnim();
    }


    public void setAngle(int angle) {
        this.angle = angle;
        invalidate();
    }

    public void setStart(int start) {
        this.start = start;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, start, angle, false, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int offset = (getWidth() - getHeight()) / 2;

        int right = getWidth() - offset;
        int bottom = getHeight();
        Log.e("right left", (getWidth()-offset)+" "+(+getHeight()-offset));

        rect = new RectF(0,0,right,bottom);
        rect.left =0 + 11 / 2f + .5f;
        rect.right = right - 11 / 2f - .5f;
        rect.top = 0+ 11 / 2f + .5f;
        rect.bottom = bottom - 11 / 2f - .5f;
    }

    private void installAnim() {
        animator = ValueAnimator.ofFloat(0, 360.0f);
        AtomicBoolean first = new AtomicBoolean(false);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        int[] second = new int[]{360};
        animator.addUpdateListener(a -> {
            if (first.get()) {
                start = (Float) a.getAnimatedValue();

                angle -= 2;
                if (angle < 0) {
                    angle = 0;

                    first.set(false);

                }
            } else {

                start = (Float) a.getAnimatedValue();

                angle += 2;
                if (angle > 360) {
                    angle = 360;
                    start = (Float) a.getAnimatedValue();
                    first.set(true);
                }
            }

            invalidate();
        });
        animator.start();
    }
}
