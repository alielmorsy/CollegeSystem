package aie.sss.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import aie.sss.R;
import aie.sss.view.animations.CircularAnimatedDrawable;


/**
 * @author netodevel
 * @edit by ali ibrahim elmorsy
 */
public class ProgressImageView extends AppCompatImageView {
    private CircularAnimatedDrawable mAnimatedDrawable;
    private State mState;
    private Boolean autoHide = true;
    private int borderColor = Color.WHITE;
    private int borderSize = 5;
    private int mOffset = 0;
    private float progress = 0;

    private Context c;
    public ProgressImageView(Context context) {
        super(context);
        init(context);
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public float getProgress() {
        return progress;
    }

    public ProgressImageView setProgress(float progress) {
        this.progress = progress;
    return this;}



    public void init(Context context) {
        this.c=context;
        mState = State.IDLE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == State.PROGRESS) {
            if (mAnimatedDrawable == null || !mAnimatedDrawable.isRunning()) {
                mAnimatedDrawable = new CircularAnimatedDrawable(this, borderSize, borderColor, progress, false);
                int offset = (getWidth() - getHeight()) / 2;

                int left = offset + mOffset;
                int right = getWidth() - offset - mOffset;
                int bottom = getHeight() - mOffset;
                int top = mOffset;

                mAnimatedDrawable.setBounds(left, top, right, bottom);
                mAnimatedDrawable.setCallback(this);
                mAnimatedDrawable.start();
            } else {
                if (autoHide) {
                    if (hasImage(this)) {
                        if (mAnimatedDrawable != null) {
                            mAnimatedDrawable.stop();
                        }
                    } else {
                        mAnimatedDrawable.draw(canvas);
                    }
                } else {
                    mAnimatedDrawable.draw(canvas);
                }
            }

        } else if (mState == State.RUNNING) {

            mAnimatedDrawable = new CircularAnimatedDrawable(this, borderSize, borderColor, progress, true);
            int offset = (getWidth() - getHeight()) / 2;

            int left = offset + mOffset;
            int right = getWidth() - offset - mOffset;
            int bottom = getHeight() - mOffset;
            int top = mOffset;

            mAnimatedDrawable.setBounds(left, top, right, bottom);
            mAnimatedDrawable.setCallback(this);

            mAnimatedDrawable.draw(canvas);
            mAnimatedDrawable.start();
        } else if (mState == State.STOP) {
            if (mAnimatedDrawable != null) {
                mAnimatedDrawable.stop();
            }
        }
    }

    public ProgressImageView showLoading() {
        this.mState = State.PROGRESS;
        mAnimatedDrawable = null;
        invalidate();
        return this;
    }

    public ProgressImageView hideLoading() {
        this.mState = State.STOP;
        return this;
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);
        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }
        return hasImage;
    }

    public ProgressImageView withAutoHide(Boolean autoHide) {
        this.autoHide = autoHide;
        return this;
    }

    public ProgressImageView withBorderColor(int color) {
        this.borderColor = color;
        return this;
    }

    public ProgressImageView withBorderSize(int size) {
        this.borderSize = size;
        return this;
    }

    public ProgressImageView withOffset(int offset) {
        this.mOffset = offset;
        return this;
    }

    public ProgressImageView showProgress() {
        this.mState = State.RUNNING;
        invalidate();
        return this;
    }
    public void success(){
        this.mState=State.STOP;
        setImageDrawable(c.getDrawable(R.drawable.done));
        invalidate();
    }

    public void failure(){
        this.mState=State.STOP;
        setImageDrawable(c.getDrawable(R.drawable.error));
        invalidate();
    }
    private enum State {
        PROGRESS, IDLE, RUNNING, STOP
    }

}
