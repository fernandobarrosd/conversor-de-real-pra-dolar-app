package com.fernando.brlmoneyconverter.retrofit;

import com.fernando.brlmoneyconverter.services.MoneyAPIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServices {
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("https://api.invertexto.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final MoneyAPIService MONEY_API_SERVICE = RETROFIT.create(MoneyAPIService.class);

    public static MoneyAPIService getMoneyAPIService() {
        return MONEY_API_SERVICE;
    }
}