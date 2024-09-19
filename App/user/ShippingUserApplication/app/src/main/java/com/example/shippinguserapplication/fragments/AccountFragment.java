package com.example.shippinguserapplication.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.activities.ChangePasswordActivity;
import com.example.shippinguserapplication.activities.InfoAppActivity;
import com.example.shippinguserapplication.activities.InfoUserActivity;
import com.example.shippinguserapplication.activities.LoginActivity;
import com.example.shippinguserapplication.apis.ApiService;
import com.example.shippinguserapplication.databinding.FragmentAccountBinding;
import com.example.shippinguserapplication.models.User;
import com.example.shippinguserapplication.models.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    private int IdUser;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        IdUser=getArguments().getInt("id_user");
        user=new User();
        clickCallApiGetUserDetail();
        binding.cvFeatureInfoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), InfoAppActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
                startActivity(intent,bndlanimation);
            }
        });

        binding.cvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), ChangePasswordActivity.class);
                intent.putExtra("id_user",IdUser);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
                startActivity(intent,bndlanimation);
            }
        });

        binding.cvFeatureLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.cvFeatureInfoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), InfoUserActivity.class);
                intent.putExtra("id_user",IdUser);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
                startActivity(intent,bndlanimation);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        clickCallApiGetUserDetail();
    }

    private void clickCallApiGetUserDetail() {
        ApiService.apiService.getUserDetail(IdUser).enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    if (userResponse.getCode() == 200) {
                        user=userResponse.getUser();
                        binding.txtFullName.setText(user.getFullName());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}