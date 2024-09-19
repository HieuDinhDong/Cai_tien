package com.example.shippingdriverapplication.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.shippingdriverapplication.R;
import com.example.shippingdriverapplication.activities.ChangePasswordActivity;
import com.example.shippingdriverapplication.activities.InfoAppActivity;
import com.example.shippingdriverapplication.activities.InfoUserActivity;
import com.example.shippingdriverapplication.activities.LoginActivity;
import com.example.shippingdriverapplication.activities.StatisticalActivity;
import com.example.shippingdriverapplication.apis.ApiService;
import com.example.shippingdriverapplication.databinding.FragmentAccountBinding;
import com.example.shippingdriverapplication.models.Driver;
import com.example.shippingdriverapplication.models.User;
import com.example.shippingdriverapplication.models.responses.DriverResponse;
import com.example.shippingdriverapplication.models.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    private int IdUser;
    private Driver user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        IdUser=getArguments().getInt("id_user");
        user=new Driver();
        clickCallApiGetDriverDetail();
        binding.cvFeatureInfoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), InfoAppActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
                startActivity(intent,bndlanimation);
            }
        });

        binding.cvFeatureStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), StatisticalActivity.class);
                intent.putExtra("id_user",IdUser);
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
        clickCallApiGetDriverDetail();
    }

    private void clickCallApiGetDriverDetail() {
        ApiService.apiService.getDriverDetail(IdUser).enqueue(new Callback<DriverResponse>() {

            @Override
            public void onResponse(Call<DriverResponse> call, Response<DriverResponse> response) {
                DriverResponse userResponse = response.body();
                if (userResponse != null) {
                    if (userResponse.getCode() == 200) {
                        user=userResponse.getDriver();
                        binding.txtFullName.setText(user.getFullName());
                    }
                }
            }

            @Override
            public void onFailure(Call<DriverResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}