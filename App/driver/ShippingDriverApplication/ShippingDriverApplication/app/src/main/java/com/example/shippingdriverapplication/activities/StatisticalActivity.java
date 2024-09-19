package com.example.shippingdriverapplication.activities;

import android.os.Bundle;

import com.example.shippingdriverapplication.adapters.ViewPagerAdapter;
import com.example.shippingdriverapplication.adapters.ViewPagerStaticAdapter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shippingdriverapplication.databinding.ActivityStatisticalBinding;

import com.example.shippingdriverapplication.R;
import com.google.android.material.tabs.TabLayout;

public class StatisticalActivity extends AppCompatActivity {

    private ActivityStatisticalBinding binding;
    private int IdUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStatisticalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        IdUser = getIntent().getIntExtra("id_user", 0);
        ViewPagerStaticAdapter adapter=new ViewPagerStaticAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.setIdUser(IdUser);
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}