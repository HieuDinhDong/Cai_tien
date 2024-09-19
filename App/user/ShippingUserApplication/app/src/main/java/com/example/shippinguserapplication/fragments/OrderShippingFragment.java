package com.example.shippinguserapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.databinding.FragmentOrderBinding;
import com.example.shippinguserapplication.databinding.FragmentOrderShippingBinding;

public class OrderShippingFragment extends Fragment {

    FragmentOrderShippingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderShippingBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}