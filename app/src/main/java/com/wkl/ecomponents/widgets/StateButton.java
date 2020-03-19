package com.wkl.ecomponents.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import com.wkl.ecomponents.R;

/**
 * Created by wangkelei on 2018/8/1.
 */
public class StateButton extends AppCompatButton {
    private static final String TAG = "StateButton";

    private ColorStateList colorStateList;
    private StateListDrawable mStateListDrawable;
    private GradientDrawable mPressedGradientDrawable, mNormalGradientDrawable;
    private int[][] state;
    private int colorNormal;
    private int colorPressed;
    private int pressedStrokSize;
    private int pressedStrokColor;
    private float cornerRadius;
    private int normalStrokSize;
    private int normalStrokColor;
    private boolean isRound;
    private int normalTextColor;
    private int pressedTextColor;

    public StateButton(Context context) {
        this(context, null);
    }

    public StateButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public StateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateButton);
        colorPressed = typedArray.getColor(R.styleable.StateButton_pressedBackgroudColor, -1);
        pressedStrokSize = typedArray.getDimensionPixelSize(R.styleable.StateButton_pressedStrokSize, 0);
        pressedStrokColor = typedArray.getColor(R.styleable.StateButton_pressedStrokColor, -1);
        cornerRadius = typedArray.getDimension(R.styleable.StateButton_cornerRadius, 0);
        normalStrokSize = typedArray.getDimensionPixelSize(R.styleable.StateButton_normalStrokSize, 0);
        normalStrokColor = typedArray.getColor(R.styleable.StateButton_normalStrokColor, -1);
        isRound = typedArray.getBoolean(R.styleable.StateButton_isRound, false);
        normalTextColor = typedArray.getColor(R.styleable.StateButton_normalTextColor, getTextColors().getColorForState(new int[]{android.R.attr.state_enabled}, Color.BLACK));
        pressedTextColor = typedArray.getColor(R.styleable.StateButton_pressedTextColor, getTextColors().getColorForState(new int[]{android.R.attr.state_enabled}, Color.BLACK));
        typedArray.recycle();
        if (getBackground() instanceof ColorDrawable) {
            colorNormal = ((ColorDrawable) getBackground()).getColor();
        } else {
            colorNormal = -1;
        }
        updateDrawable();
        updateColorList();
    }


    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        colorNormal = color;
        updateDrawable();
    }

    public void setRound(boolean round) {
        isRound = round;
        updateDrawable();
    }

    public void setColorNormal(int colorNormal) {
        this.colorNormal = colorNormal;
        updateDrawable();
    }

    public void setColorPressed(int colorPressed) {
        this.colorPressed = colorPressed;
        updateDrawable();
    }

    public void setPressedStrokSize(int pressedStrokSize) {
        this.pressedStrokSize = pressedStrokSize;
        updateDrawable();
    }

    public void setPressedStrokColor(int pressedStrokColor) {
        this.pressedStrokColor = pressedStrokColor;
        updateDrawable();
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        updateDrawable();
    }

    public void setNormalStrokSize(int normalStrokSize) {
        this.normalStrokSize = normalStrokSize;
        updateDrawable();
    }

    public void setNormalStrokColor(int normalStrokColor) {
        this.normalStrokColor = normalStrokColor;
        updateDrawable();
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        updateColorList();
    }

    public void setPressedTextColor(int pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
        updateColorList();
    }

    private void updateDrawable() {
        if (colorPressed == -1)
            colorPressed = colorNormal;
        mStateListDrawable = new StateListDrawable();
        mPressedGradientDrawable = new GradientDrawable();
        if (isRound)
            mPressedGradientDrawable.setShape(GradientDrawable.OVAL);
        if (colorPressed == colorNormal)
            colorPressed = Color.argb(0xd3, Color.red(colorNormal), Color.green(colorNormal), Color.blue(colorNormal));
        mPressedGradientDrawable.setColor(colorPressed);
        mPressedGradientDrawable.setStroke(pressedStrokSize, pressedStrokColor);
        mPressedGradientDrawable.setCornerRadius(cornerRadius);
        mStateListDrawable.addState(new int[]{android.R.attr.state_pressed}, mPressedGradientDrawable);

        mNormalGradientDrawable = new GradientDrawable();
        if (isRound)
            mNormalGradientDrawable.setShape(GradientDrawable.OVAL);
        mNormalGradientDrawable.setColor(colorNormal);
        mNormalGradientDrawable.setStroke(normalStrokSize, normalStrokColor);
        mNormalGradientDrawable.setCornerRadius(cornerRadius);

        mStateListDrawable.addState(new int[]{android.R.attr.state_enabled}, mNormalGradientDrawable);
        setBackground(mStateListDrawable);
    }

    private void updateColorList() {
        state = new int[3][];
        state[0] = new int[]{android.R.attr.state_pressed};
        state[1] = new int[]{android.R.attr.state_enabled};
        state[2] = new int[]{-android.R.attr.state_enabled};
        int[] color = new int[]{pressedTextColor, normalTextColor, normalTextColor};
        colorStateList = new ColorStateList(state, color);
        setTextColor(colorStateList);
    }
}
