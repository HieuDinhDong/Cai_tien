package com.example.shippingdriverapplication.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.shippingdriverapplication.R;
import com.example.shippingdriverapplication.apis.ApiService;
import com.example.shippingdriverapplication.databinding.ActivityRegisterBinding;
import com.example.shippingdriverapplication.models.responses.ObjectResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private boolean isShowPass1=false;
    private boolean isShowPass2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.icSeePassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowPass1==false) {
                    binding.icSeePassword2.setImageDrawable(getResources().getDrawable(R.drawable.icon_show));
                    binding.edtPasswordNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPass1=true;
                }else{
                    binding.edtPasswordNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.icSeePassword2.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide));
                    isShowPass1=false;
                }
            }
        });

        binding.icSeePassword3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowPass2==false) {
                    binding.icSeePassword3.setImageDrawable(getResources().getDrawable(R.drawable.icon_show));
                    binding.edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPass2=true;
                }else{
                    binding.edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.icSeePassword3.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide));
                    isShowPass2=false;
                }
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtFullname.getText().toString().length()==0 || binding.edtEmail.getText().toString().length()==0 ||
                        binding.edtPhone.getText().toString().length()==0 || binding.edtPasswordNew.getText().toString().length()==0|| binding.edtConfirmPassword.getText().toString().length()==0){
                    Toast.makeText(RegisterActivity.this, "Bạn cần nhập đầu đủ thôn tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(binding.edtPasswordNew.getText().toString().equals(binding.edtConfirmPassword.getText().toString())){
                        getLastLocation();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void getLastLocation() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        FusedLocationProviderClient fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(RegisterActivity.this);
        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null){
                                Geocoder geocoder=new Geocoder(RegisterActivity.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    clickCallApiRegister(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                                } catch (IOException e) {
                                    System.out.println(e);
                                    throw new RuntimeException(e);
                                }


                            }
                        }
                    });

        }else{
            askPermisstion();
        }
    }

    private void askPermisstion() {
        ActivityCompat.requestPermissions(RegisterActivity.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(RegisterActivity.this, "Required Permisstion", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clickCallApiRegister(double latitude, double longitude) {
        ApiService.apiService.registrationDriver(binding.edtFullname.getText().toString(),binding.edtEmail.getText().toString(),
                binding.edtPhone.getText().toString(),longitude,latitude,binding.edtPasswordNew.getText().toString()).enqueue(new Callback<ObjectResponse>() {

            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if (objectResponse != null) {
                    if (objectResponse.getCode() == 200) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}