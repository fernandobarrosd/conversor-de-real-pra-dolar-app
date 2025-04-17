package com.fernando.brlmoneyconverter.ui.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.fernando.brlmoneyconverter.databinding.LayoutInternetNotConnectedDialogBinding;

public class InternetNotConnectedDialog extends DialogFragment {
    private LayoutInternetNotConnectedDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LayoutInternetNotConnectedDialogBinding.inflate(
                inflater, container, false
        );

        setCancelable(false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View dialogView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(dialogView, savedInstanceState);
        binding.okButton.setOnClickListener(view -> dismiss());
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
