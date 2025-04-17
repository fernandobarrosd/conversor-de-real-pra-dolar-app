package com.fernando.brlmoneyconverter.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.fernando.brlmoneyconverter.R;
import com.fernando.brlmoneyconverter.databinding.LayoutCardBinding;

public class Card extends CardView {
    private final LayoutCardBinding binding;

    private TypedArray typedArray;


    public Card(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.binding = LayoutCardBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        );

        initTypedArray(attrs);
        initUI();
    }

    private void initTypedArray(AttributeSet attrs) {
        this.typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.Card);
    }

    private void initUI() {
        String title = typedArray.getString(R.styleable.Card_cardTitle);
        String content = typedArray.getString(R.styleable.Card_cardContent);

        setCardTitle(title);
        setCardContent(content);
    }

    private void setCardTitle(String title) {
        binding.titleTextView.setText(title);
    }

    public void setCardContent(String content) {
        binding.contentTextView.setText(content);
    }
}
