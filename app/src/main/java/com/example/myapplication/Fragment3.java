package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

public class Fragment3 extends Fragment {
    private static final String ARG_NAME = "name";

    public interface OnFragment3Listener {
        void onConfirmationChanged(boolean isChecked);
        void onFinishClicked();
    }

    private Fragment3Binding binding;
    private OnFragment3Listener listener;

    public Fragment3() {
    }

    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment3Listener) {
            listener = (OnFragment3Listener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragment3Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment3Binding.inflate(inflater, container, false);

        String name = getArguments() != null ? getArguments().getString(ARG_NAME) : "";
        binding.textName.setText("Welcome, " + name + "!");

        binding.checkBoxConfirm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onConfirmationChanged(isChecked);
            }
        });

        binding.buttonAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFinishClicked();
            }
        });

        return binding.getRoot();
    }

    public void updateButton(boolean enabled) {
        if (binding != null) {
            binding.buttonAction.setEnabled(enabled);
            binding.buttonAction.setText(enabled ? "Finish" : "Continue");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}