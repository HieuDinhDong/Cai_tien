package com.example.shippingdriverapplication.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shippingdriverapplication.fragments.AccountFragment;
import com.example.shippingdriverapplication.fragments.HomeFragment;
import com.example.shippingdriverapplication.fragments.OrderFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int IdUser;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    public void setIdUser(int idUser) {
        IdUser = idUser;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("id_user", IdUser);

        HomeFragment fragHome=new HomeFragment();
        fragHome.setArguments(bundle);
        OrderFragment fragOrder=new OrderFragment();
        fragOrder.setArguments(bundle);
        AccountFragment fragAccount= new AccountFragment();
        fragAccount.setArguments(bundle);

        switch (position) {
            case 0:
                return fragHome;
            case 1:
                return fragOrder;
            case 2:
                return fragAccount;
        }
        return fragHome;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
