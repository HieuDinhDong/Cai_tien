package com.example.shippinguserapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.adapters.ViewPagerAdapter;
import com.example.shippinguserapplication.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ViewPagerAdapter viewPagerAdapter;
    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        IdUser = getIntent().getIntExtra("id_user", 0);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.setIdUser(IdUser);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        binding.bottomMenuApp.getMenu().findItem(R.id.item_home).setChecked(true);
                        break;
                    case 1:
                        binding.bottomMenuApp.getMenu().findItem(R.id.item_order).setChecked(true);
                        break;
                    case 2:
                        binding.bottomMenuApp.getMenu().findItem(R.id.item_account).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.bottomMenuApp.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.item_home){
                    binding.viewPager.setCurrentItem(0);
                }
                if(item.getItemId()==R.id.item_order){
                    binding.viewPager.setCurrentItem(1);
                }
                if(item.getItemId()==R.id.item_account){
                    binding.viewPager.setCurrentItem(2);
                }
                return true;
            }
        });

        viewPagerAdapter.notifyDataSetChanged();
    }
}