package com.fernando.brlmoneyconverter.ui.viewmodels;

import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fernando.brlmoneyconverter.BuildConfig;
import com.fernando.brlmoneyconverter.responses.DollarQuotationResponse;
import com.fernando.brlmoneyconverter.services.MoneyQuotationService;
import com.fernando.brlmoneyconverter.utils.MoneyUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@HiltViewModel
public class MainViewModel extends ViewModel {
    private final MoneyQuotationService moneyQuotationService;

    private final ConnectivityManager connectivityManager;

    private BigDecimal brlValue = new BigDecimal("0.00");

    private final MutableLiveData<String> brlValueText = new MutableLiveData<>("R$ 0,00");
    private final MutableLiveData<String> usdValueText = new MutableLiveData<>("$0,00");

    private final MutableLiveData<String> dollarQuotationValueText = new MutableLiveData<>("$0,00");

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


    @Inject
    public MainViewModel(final MoneyQuotationService moneyQuotationService, final ConnectivityManager connectivityManager) {
        this.moneyQuotationService = moneyQuotationService;
        this.connectivityManager = connectivityManager;
    }

    public void calculateQuotation() {
        if (!hasInternetConnection()) {
            return;
        }
        if (brlValue.toString().contentEquals("0.00")) {
            return;
        }

        isLoading.postValue(true);

        moneyQuotationService.getDollarQuotation(BuildConfig.API_TOKEN)
                .enqueue(new Callback<DollarQuotationResponse>() {
            @Override
            public void onResponse(@NonNull Call<DollarQuotationResponse> call, @NonNull Response<DollarQuotationResponse> response) {
                if (response.body() != null) {
                    double dollarQuotation = response.body().getUsdBRLQuotation().getPrice();

                    BigDecimal dollarQuotationBigDecimal = BigDecimal.valueOf(dollarQuotation);
                    BigDecimal usdValueBigDecimal = Objects.requireNonNull(brlValue).divide(dollarQuotationBigDecimal, RoundingMode.FLOOR);

                    String usdValueFormatted = MoneyUtils.convertMoneyToBrFormat(usdValueBigDecimal)
                            .replaceAll("[R\u00A0]", "");

                    String dollarQuotationFormatted = MoneyUtils.convertMoneyToBrFormat(dollarQuotationBigDecimal);

                    isLoading.postValue(false);
                    usdValueText.postValue(usdValueFormatted);
                    dollarQuotationValueText.postValue(dollarQuotationFormatted);
                }
            }


            @Override
            public void onFailure(@NonNull Call<DollarQuotationResponse> call, @NonNull Throwable throwable) {
                isLoading.postValue(false);

            }
        });
    }

    public void updateBRLValue(BigDecimal newBsdValue) {
        this.brlValue = newBsdValue;
        String brlValueTextConverted = MoneyUtils.convertMoneyToBrFormat(brlValue);
        brlValueText.postValue(brlValueTextConverted);
    }

    public boolean hasInternetConnection() {
        return connectivityManager.getActiveNetwork() != null;
    }

    public LiveData<String> getBrlValueText() {
        return brlValueText;
    }

    public LiveData<String> getUsdValueText() {
        return usdValueText;
    }

    public LiveData<String> getDollarQuotationValueText() {
        return dollarQuotationValueText;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}