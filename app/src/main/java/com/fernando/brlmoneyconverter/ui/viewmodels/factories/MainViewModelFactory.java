package com.fernando.brlmoneyconverter.ui.viewmodels.factories;

import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.fernando.brlmoneyconverter.services.MoneyAPIService;
import com.fernando.brlmoneyconverter.ui.viewmodels.MainViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final MoneyAPIService moneyAPIService;

    private final ConnectivityManager connectivityManager;

    public MainViewModelFactory(final MoneyAPIService moneyAPIService, final ConnectivityManager connectivityManager) {
        this.moneyAPIService = moneyAPIService;
        this.connectivityManager = connectivityManager;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(moneyAPIService, connectivityManager);
        }
        throw new IllegalArgumentException("Error");
    }
}
