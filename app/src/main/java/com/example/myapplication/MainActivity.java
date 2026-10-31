package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements Fragment2.OnFragment2Listener, Fragment3.OnFragment3Listener {

    private static final String TAG_FRAGMENT1 = "FRAGMENT1";
    private static final String TAG_FRAGMENT3 = "FRAGMENT3";

    private ActivityMainBinding binding;
    private String name;
    private String email;
    private String gender;
    private String university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, Fragment1.newInstance(null), TAG_FRAGMENT1)
                    .commit();
        }

        binding.buttonContinue.setOnClickListener(view -> {
            Fragment f = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT1);
            String enteredName = "";
            if (f instanceof Fragment1) {
                enteredName = ((Fragment1) f).getEnteredName();
            }

            if (enteredName.isEmpty()) {
                Toast.makeText(this, "Enter name!", Toast.LENGTH_SHORT).show();
                return;
            }

            name = enteredName;

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, Fragment2.newInstance(name))
                    .addToBackStack(null)
                    .commit();
            binding.buttonContinue.setVisibility(View.GONE);
        });

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                binding.buttonContinue.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onContinueClicked(String name, String email, String gender, String university) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.university = university;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment3.newInstance(name), TAG_FRAGMENT3)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onConfirmationChanged(boolean isChecked) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT3);
        if (f instanceof Fragment3) {
            ((Fragment3) f).updateButton(isChecked);
        }
    }

    @Override
    public void onFinishClicked() {
        Toast.makeText(this, "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();
    }
}