package com.fernando.brlmoneyconverter.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.fernando.brlmoneyconverter.R;
import com.fernando.brlmoneyconverter.databinding.LayoutMoneyCardBinding;
import com.fernando.brlmoneyconverter.utils.MoneyUtils;

import java.math.BigDecimal;
import java.util.Locale;

public class MoneyCard extends CardView {
    private final LayoutMoneyCardBinding binding;

    private TypedArray typedArray;

    private static final Integer USD = 0;
    private static final Integer BRL = 0;


    public MoneyCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.binding = LayoutMoneyCardBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        );

        initTypedArray(attrs);
        initUI();
    }

    private void initTypedArray(AttributeSet attrs) {
        this.typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.MoneyCard);
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        String moneyValue = typedArray.getString(R.styleable.MoneyCard_moneyValue);
        int locationEnumValue = typedArray.getInteger(R.styleable.MoneyCard_currency, USD);

        setMoneyValue(moneyValue);

        if (locationEnumValue == USD) {
            binding.moneyCardTitleTextView.setText("USD");
        }
        else {
            binding.moneyCardTitleTextView.setText("BRL");
        }
    }

    public void setMoneyValue(String moneyValueText) {
        binding.moneyValueTextView.setText(moneyValueText);
    }
}
