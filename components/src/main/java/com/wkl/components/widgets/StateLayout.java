package com.wkl.components.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wkl.components.R;

/**
 * Created by wangkelei on 2018/6/8.
 */
public class StateLayout extends FrameLayout {
    private static final String TAG = "StateLayout";
    private View loadingView, contentView, errorView, emptyView, ll_error;
    private TextView tvLoading, tvEmpty, tvError;

    private String loadingStr, emptyStr, errorStr;
    private States cState = States.CONTENT;
    private OnStateClickListener mOnStateClickListener;
    private boolean use_state = true;

    public enum States {
        LOADING,
        CONTENT,
        ERROR,
        EMPTY
    }

    public StateLayout(@NonNull Context context) {
        this(context, null, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateLayout);
        use_state = typedArray.getBoolean(R.styleable.StateLayout_use_state, use_state);

        if (use_state) {
            loadingStr = typedArray.getString(R.styleable.StateLayout_loading_text);
            emptyStr = typedArray.getString(R.styleable.StateLayout_empty_text);
            errorStr = typedArray.getString(R.styleable.StateLayout_error_text);
            typedArray.recycle();
            loadingView = LayoutInflater.from(context).inflate(R.layout.view_loading, null);
            tvLoading = loadingView.findViewById(R.id.loading_text);
            if (!TextUtils.isEmpty(loadingStr))
                tvLoading.setText(loadingStr);
            addView(loadingView);

            emptyView = LayoutInflater.from(context).inflate(R.layout.view_empty, null);
            tvEmpty = emptyView.findViewById(R.id.tv_empty_text);
            if (!TextUtils.isEmpty(emptyStr))
                tvEmpty.setText(emptyStr);
            addView(emptyView);

            errorView = LayoutInflater.from(context).inflate(R.layout.view_error, null);
            tvError = errorView.findViewById(R.id.tv_empty_text);
            if (!TextUtils.isEmpty(errorStr))
                tvError.setText(errorStr);
            ll_error = errorView.findViewById(R.id.fl_error);
            ll_error.setOnClickListener(v -> {
                if (mOnStateClickListener != null)
                    mOnStateClickListener.onErrorStateClick();
            });
            addView(errorView);

            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(300);
            alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            alphaAnimation.setFillAfter(true);
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(getChildCount() - 1);
        if (contentView == errorView) {
            try {
                throw new Throwable("please add a content in statelayut");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        setId(R.id.state_layout);
        if (use_state) {
            loadingView.setVisibility(GONE);
            emptyView.setVisibility(GONE);
            errorView.setVisibility(GONE);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (alphaAnimation != null && alphaAnimation.hasStarted())
            alphaAnimation.cancel();
    }

    public void show(States state) {
        if (cState == state || !use_state)
            return;
        View[] views = new View[2];

        if (cState == States.LOADING) {
            views[0] = loadingView;
        } else if (cState == States.ERROR) {
            views[0] = errorView;
        } else if (cState == States.EMPTY) {
            views[0] = emptyView;
        } else {
            views[0] = contentView;
        }

        if (state == States.LOADING) {
            views[1] = loadingView;
        } else if (state == States.ERROR) {
            views[1] = errorView;
        } else if (state == States.EMPTY) {
            views[1] = emptyView;
        } else {
            views[1] = contentView;
        }
        if (views[0] == contentView && views[1] == loadingView) {
            contentView.setVisibility(GONE);
            loadingView.setVisibility(VISIBLE);
            loadingView.setAlpha(1.0f);
        } else {
            anim(views[0], views[1]);
        }
        cState = state;
    }

    private AlphaAnimation alphaAnimation;

    private void anim(View hidden, View show) {
        if (alphaAnimation.hasStarted())
            alphaAnimation.cancel();
        hidden.clearAnimation();
        show.clearAnimation();
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                hidden.setAlpha(0.0f);
                hidden.setVisibility(GONE);
                show.setAlpha(1.0f);
                show.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hidden.startAnimation(alphaAnimation);
    }

    public void setOnStateClickListener(OnStateClickListener onStateClickListener) {
        mOnStateClickListener = onStateClickListener;
    }

    public static class OnStateClickListener {
        public void onErrorStateClick() {

        }
    }
}
