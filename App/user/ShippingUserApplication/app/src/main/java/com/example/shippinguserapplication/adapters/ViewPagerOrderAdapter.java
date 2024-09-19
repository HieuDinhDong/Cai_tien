package com.example.shippinguserapplication.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shippinguserapplication.fragments.OrderShippingFragment;
import com.example.shippinguserapplication.fragments.OrderSuccessFragment;
import com.example.shippinguserapplication.fragments.OrderedFragment;

public class ViewPagerOrderAdapter extends FragmentStatePagerAdapter {

    private int idUser=0;

    public ViewPagerOrderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setIdUser(int idUser){
        this.idUser=idUser;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_user", this.idUser);
        OrderedFragment orderedFragment = new OrderedFragment();
        orderedFragment.setArguments(bundle);
        OrderShippingFragment orderShippingFragment=new OrderShippingFragment();
        orderShippingFragment.setArguments(bundle);
        OrderSuccessFragment orderSuccessFragment=new OrderSuccessFragment();
        orderSuccessFragment.setArguments(bundle);

        switch (position){
            case 0:
                return orderedFragment;
            case 1:
                return orderShippingFragment;
            case 2:
                return orderSuccessFragment;
        }
        return orderedFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title = "Đơn hàng";
                break;
            case 1:
                title = "Đang giao";
                break;
            case 2:
                title = "Đã nhận";
                break;
        }
        return title;
    }
}
