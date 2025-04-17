package com.fernando.brlmoneyconverter.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.fernando.brlmoneyconverter.databinding.ActivityMainBinding;
import com.fernando.brlmoneyconverter.ui.dialogs.AppInfoDialog;
import com.fernando.brlmoneyconverter.ui.viewmodels.MainViewModel;
import com.fernando.brlmoneyconverter.ui.dialogs.InternetNotConnectedDialog;
import com.fernando.brlmoneyconverter.utils.MoneyUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initMainViewModel();
        checkInternetConnection();
    }

    private void initMainViewModel() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    private void initObservers() {
        mainViewModel.getBrlValueText().observe(this, brlValueText ->
            binding.brlMoneyCard.setCardContent(brlValueText));

        mainViewModel.getUsdValueText().observe(this, usdValueText ->
                binding.usdMoneyCard.setCardContent(usdValueText));

        mainViewModel.getDollarQuotationValueText().observe(this, dollarQuotationText ->
                binding.quotationMoneyCard.setCardContent(dollarQuotationText));

        mainViewModel.getIsLoading().observe(this, isLoading -> {
            hideKeyboard();

            if (isLoading) {
                binding.btnConverter.showLoading();
                binding.brlInput.setEnabled(false);
                return;
            }
            binding.btnConverter.removeLoading();
            binding.brlInput.setEnabled(true);
        });
    }

    private void initListeners() {
        binding.brlInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence moneyCharSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String moneyValueText = editable.toString();

                if (moneyValueText.isEmpty()) {
                    mainViewModel.updateBRLValue(new BigDecimal("0.00"));
                    return;
                }


                binding.brlInput.removeTextChangedListener(this);
                String moneyCleanString = moneyValueText.replaceAll("[,.]", "");

                BigDecimal moneyBigDecimal = new BigDecimal(moneyCleanString)
                        .setScale(2, RoundingMode.FLOOR)
                        .divide(new BigDecimal(100), RoundingMode.FLOOR);


                String moneyFormatted = MoneyUtils.convertMoneyToBrFormat(moneyBigDecimal);

                String finalMoneyFormatted = moneyFormatted.replace("R$\u00A0", "");

                if (finalMoneyFormatted.contentEquals("0,00")) {
                    binding.brlInput.setText("");
                }
                else {
                    binding.brlInput.setText(finalMoneyFormatted);
                    binding.brlInput.setSelection(finalMoneyFormatted.length());
                    binding.brlInput.setSaveEnabled(true);
                }

                mainViewModel.updateBRLValue(moneyBigDecimal);
                binding.brlInput.addTextChangedListener(this);
            }
        });

        binding.btnConverter.setOnClickListener(view -> {
            checkInternetConnection();
            mainViewModel.calculateQuotation();
        });

        binding.btnShowAppInfoDialog.setOnClickListener(view -> {
            AppInfoDialog appInfoDialog = new AppInfoDialog();
            appInfoDialog.show(getSupportFragmentManager(), appInfoDialog.getTag());
        });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view == null) return;

        InputMethodManager inputMethodManager = getSystemService(InputMethodManager.class);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void checkInternetConnection() {
        if (!mainViewModel.hasInternetConnection()) {
            InternetNotConnectedDialog internetNotConnectedDialog = new InternetNotConnectedDialog();
            internetNotConnectedDialog.show(getSupportFragmentManager(), internetNotConnectedDialog.getTag());
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            mainViewModel.calculateQuotation();
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initObservers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}