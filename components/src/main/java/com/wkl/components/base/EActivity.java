package com.wkl.components.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class EActivity extends AppCompatActivity {
    public abstract int getLayoutId();

    protected String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != -1)
            setContentView(getLayoutId());
        initView(savedInstanceState);
        setListener();
    }

    protected void initView(Bundle bundle) {

    }

    protected void setListener() {

    }

}
