package com.example.shippingdriverapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shippingdriverapplication.R;
import com.example.shippingdriverapplication.apis.ApiService;
import com.example.shippingdriverapplication.databinding.FragmentHomeBinding;
import com.example.shippingdriverapplication.models.Driver;
import com.example.shippingdriverapplication.models.User;
import com.example.shippingdriverapplication.models.responses.DriverResponse;
import com.example.shippingdriverapplication.models.responses.UserResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private int IdUser;
    private Driver user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        IdUser=getArguments().getInt("id_user");
        user=new Driver();
        clickCallApiGetDriverDetail();
        binding.txtOnline.setText("Ngoại tuyến");
        binding.swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.txtOnline.setText("Trực tuyến");
                }else{
                    binding.txtOnline.setText("Ngoại tuyến");
                }
            }
        });

        return binding.getRoot();
    }

    private void clickCallApiGetDriverDetail() {
        ApiService.apiService.getDriverDetail(IdUser).enqueue(new Callback<DriverResponse>() {

            @Override
            public void onResponse(Call<DriverResponse> call, Response<DriverResponse> response) {
                DriverResponse userResponse = response.body();
                if (userResponse != null) {
                    if (userResponse.getCode() == 200) {
                        user=userResponse.getDriver();
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