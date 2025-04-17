package com.fernando.brlmoneyconverter.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.fernando.brlmoneyconverter.databinding.LayoutLoadingButtonBinding;
import com.fernando.brlmoneyconverter.R;

public class LoadingButton extends LinearLayoutCompat {
    private final LayoutLoadingButtonBinding binding;
    private TypedArray typedArray;

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.binding = LayoutLoadingButtonBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        );

        initTypedArray(context, attrs);
        initUI();
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        this.typedArray = context
                .obtainStyledAttributes(attrs, R.styleable.LoadingButton);
    }

    private void initUI() {
        String buttonText = typedArray.getString(R.styleable.LoadingButton_text);
        boolean loadingIsVisible = typedArray.getBoolean(R.styleable.LoadingButton_loadingIsVisible, false);

        if (loadingIsVisible) {
            showLoading();
        }
        else {
            removeLoading();
        }

        setText(buttonText);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        binding.button.setOnClickListener(onClickListener);
    }

    private void setText(String text) {
        binding.button.setText(text);
    }

    public void showLoading() {
        binding.button.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    public void removeLoading() {
        binding.button.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
    }
}