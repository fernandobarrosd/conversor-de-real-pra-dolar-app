package com.fernando.brlmoneyconverter.ui.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.fernando.brlmoneyconverter.BuildConfig;
import com.fernando.brlmoneyconverter.databinding.LayoutAppInfoDialogBinding;

public class AppInfoDialog extends DialogFragment {
    private LayoutAppInfoDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = LayoutAppInfoDialogBinding.inflate(inflater, container, false);

        setCancelable(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View dialogView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(dialogView, savedInstanceState);

        binding.btnCloseAlert.setOnClickListener(view -> dismiss());

        initAppVersionText();
    }

    private void initAppVersionText() {
        String appVersion = BuildConfig.VERSION_NAME;
        binding.appVersion.setText(String.format("Versão: %s", appVersion));
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
