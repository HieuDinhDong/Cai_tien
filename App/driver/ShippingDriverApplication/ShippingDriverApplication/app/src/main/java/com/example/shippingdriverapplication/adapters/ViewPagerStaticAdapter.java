package com.example.shippingdriverapplication.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shippingdriverapplication.fragments.StaticDayFragment;
import com.example.shippingdriverapplication.fragments.StaticMonthFragment;

public class ViewPagerStaticAdapter extends FragmentStatePagerAdapter {

    private int IdUser;

    public ViewPagerStaticAdapter(@NonNull FragmentManager fm, int behavior) {
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

        StaticDayFragment day=new StaticDayFragment();
        day.setArguments(bundle);
        StaticMonthFragment month=new StaticMonthFragment();
        month.setArguments(bundle);

        switch (position){
            case 0:
                return day;
            case 1:
                return month;
            default:
                return day;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Ngày";
                break;
            case 1:
                title="Tháng";
                break;
        }
        return title;
    }
}
