package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    private static final String ARG_NAME = "name";

    private String mName;
    private EditText editName;

    public Fragment1() {
    }

    public static Fragment1 newInstance(String name) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        editName = view.findViewById(R.id.editName);
        if (mName != null) {
            editName.setText(mName);
        }
        return view;
    }

    public String getEnteredName() {
        return editName != null ? editName.getText().toString().trim() : "";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editName = null;
    }
}