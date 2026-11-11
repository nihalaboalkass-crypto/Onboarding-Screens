package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

public class Fragment2 extends Fragment {

    private static final String ARG_NAME = "name";

    public interface OnFragment2Listener {
        void onContinueClicked(String name, String email, String gender,
                               String university, String age);
    }

    private Fragment2Binding binding;
    private OnFragment2Listener listener;
    private String name;

    public Fragment2() {
    }

    public static Fragment2 newInstance(String name) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME, "");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment2Listener) {
            listener = (OnFragment2Listener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragment2Listener");
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
        binding = Fragment2Binding.inflate(inflater, container, false);

        binding.buttonContinueF2.setOnClickListener(v -> {
            String email = binding.editEmail.getText().toString().trim();
            String university = binding.editUniversity.getText().toString().trim();
            String age = binding.editAge.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editEmail.setError("Enter a valid email");
                return;
            }
            if (university.isEmpty()) {
                binding.editUniversity.setError("University is required");
                return;
            }
            if (age.isEmpty()) {
                binding.editAge.setError("Age is required");
                return;
            }
            int ageNumber = Integer.parseInt(age);
            if (ageNumber < 10 || ageNumber > 100) {
                binding.editAge.setError("Enter a valid age");
                return;
            }

            int checkedId = binding.radioGroupGender.getCheckedRadioButtonId();
            if (checkedId == -1) {
                Toast.makeText(requireContext(), "Select gender", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton rb = binding.getRoot().findViewById(checkedId);
            String gender = rb.getText().toString();

            if (listener != null) {
                listener.onContinueClicked(name, email, gender, university, age);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
