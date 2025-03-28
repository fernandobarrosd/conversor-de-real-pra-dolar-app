package com.fernando.brlmoneyconverter.ui.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.fernando.brlmoneyconverter.services.MoneyAPIService;
import com.fernando.brlmoneyconverter.ui.viewmodels.MainViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final MoneyAPIService moneyAPIService;

    public MainViewModelFactory(MoneyAPIService moneyAPIService) {
        this.moneyAPIService = moneyAPIService;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(moneyAPIService);
        }
        throw new IllegalArgumentException("Error");
    }
}
