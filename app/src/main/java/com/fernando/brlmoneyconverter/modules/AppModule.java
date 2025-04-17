package com.fernando.brlmoneyconverter.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import com.fernando.brlmoneyconverter.services.MoneyQuotationService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ViewModelScoped;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ViewModelComponent.class)
public class AppModule {
    @Provides
    @ViewModelScoped
    public static MoneyQuotationService moneyQuotationService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.invertexto.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoneyQuotationService.class);
    }

    @Provides
    @ViewModelScoped
    public static ConnectivityManager connectivityManager(@ApplicationContext Context context) {
        return context.getSystemService(ConnectivityManager.class);
    }
}