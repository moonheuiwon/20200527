package com.example.a20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.a20200527.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setValues();
        setupEvents();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

    }
}
