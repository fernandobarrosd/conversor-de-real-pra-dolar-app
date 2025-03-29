package com.fernando.brlmoneyconverter.ui;

import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.fernando.brlmoneyconverter.databinding.ActivityMainBinding;
import com.fernando.brlmoneyconverter.ui.viewmodels.MainViewModel;
import com.fernando.brlmoneyconverter.ui.viewmodels.factories.MainViewModelFactory;
import com.fernando.brlmoneyconverter.retrofit.RetrofitServices;
import com.fernando.brlmoneyconverter.utils.MoneyUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        mainViewModel = new ViewModelProvider(
                this,
                new MainViewModelFactory(RetrofitServices.getMoneyAPIService(), connectivityManager))
                .get(MainViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mainViewModel.checkWifiConnection();

        mainViewModel.getInternetIsConnected().observe(this, internetIsConnected -> {
            if (!internetIsConnected) {
                AlertDialog internetNotConnectedAlert = new AlertDialog.Builder(this)
                        .setTitle("Internet não disponível")
                        .setMessage("Conecte-se a internet e tente novamente")
                        .setPositiveButton("Ok", (dialogInterface, i) -> {})
                        .setNegativeButton("Fechar", ((dialogInterface, i) -> finish()))
                        .create();
                internetNotConnectedAlert.show();
            }
        });

        mainViewModel.getBrlValueText().observe(this, brlValueText ->
                binding.brlMoneyCard.setMoneyValue(brlValueText));

        mainViewModel.getUsdValueText().observe(this, usdValueText ->
                binding.usdMoneyCard.setMoneyValue(usdValueText));

        mainViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.btnConverter.setVisibility(View.GONE);
                return;
            }
            binding.progressBar.setVisibility(View.GONE);
            binding.btnConverter.setVisibility(View.VISIBLE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mainViewModel.checkWifiConnection();

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
            mainViewModel.checkWifiConnection();
            mainViewModel.calculateQuotation();
        });

    }
}