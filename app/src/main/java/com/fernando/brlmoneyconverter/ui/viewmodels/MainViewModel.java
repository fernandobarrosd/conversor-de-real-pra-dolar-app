package com.fernando.brlmoneyconverter.ui.viewmodels;

import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fernando.brlmoneyconverter.BuildConfig;
import com.fernando.brlmoneyconverter.responses.DollarQuotationResponse;
import com.fernando.brlmoneyconverter.services.MoneyAPIService;
import com.fernando.brlmoneyconverter.utils.MoneyUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewModel extends ViewModel {
    private final MoneyAPIService moneyAPIService;

    private final ConnectivityManager connectivityManager;

    private final MutableLiveData<String> brlValueText = new MutableLiveData<>("R$ 0,00");

    private BigDecimal brlValue = new BigDecimal("0.00");

    private final MutableLiveData<String> usdValueText = new MutableLiveData<>("$ 0,00");

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> internetIsConnected = new MutableLiveData<>();

    public MainViewModel(final MoneyAPIService moneyAPIService, final ConnectivityManager connectivityManager) {
        this.moneyAPIService = moneyAPIService;
        this.connectivityManager = connectivityManager;
    }

    public void calculateQuotation() {
        if (brlValue.toString().contentEquals("0.00")) {
            return;
        }

        isLoading.postValue(true);

        moneyAPIService.getDollarQuotation(BuildConfig.API_TOKEN)
                .enqueue(new Callback<DollarQuotationResponse>() {
            @Override
            public void onResponse(@NonNull Call<DollarQuotationResponse> call, @NonNull Response<DollarQuotationResponse> response) {
                if (response.body() != null) {
                    double dollarQuotation = response.body().getUSD_BRL().getPrice();

                    BigDecimal dollarQuotationBigDecimal = BigDecimal.valueOf(dollarQuotation);
                    BigDecimal usdValueBigDecimal = Objects.requireNonNull(brlValue).divide(dollarQuotationBigDecimal, RoundingMode.FLOOR);

                    String moneyFormatted = MoneyUtils.convertMoneyToBrFormat(usdValueBigDecimal)
                            .replaceAll("[R\u00A0]", "");

                    usdValueText.postValue(moneyFormatted);
                    isLoading.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DollarQuotationResponse> call, @NonNull Throwable throwable) {
                isLoading.postValue(false);

            }
        });
    }

    public void checkWifiConnection() {
        if (connectivityManager.getActiveNetwork() != null) {
            internetIsConnected.postValue(true);
            return;
        }

        internetIsConnected.postValue(false);
    }

    public LiveData<String> getBrlValueText() {
        return brlValueText;
    }

    public LiveData<String> getUsdValueText() {
        return usdValueText;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getInternetIsConnected() {
        return internetIsConnected;
    }

    public void updateBRLValue(BigDecimal newBsdValue) {
        this.brlValue = newBsdValue;
        this.brlValueText.postValue(MoneyUtils.convertMoneyToBrFormat(newBsdValue));
    }
}