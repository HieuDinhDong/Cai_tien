package com.example.shippinguserapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.apis.ApiService;
import com.example.shippinguserapplication.databinding.ActivityInfoUserBinding;
import com.example.shippinguserapplication.models.User;
import com.example.shippinguserapplication.models.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoUserActivity extends AppCompatActivity {

    ActivityInfoUserBinding binding;
    private int IdUser;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInfoUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user=new User();
        IdUser = getIntent().getIntExtra("id_user", 0);
        clickCallApiGetUserDetail();
        binding.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.txtEdit.getText().toString().equals("Sửa")){
                    binding.txtEdit.setText("Lưu");
                    binding.edtName.setEnabled(true);
                    binding.edtPhone.setEnabled(true);
                    binding.edtEmail.setEnabled(true);
                }else{

                    if(binding.edtName.getText().toString().length()==0 || binding.edtEmail.getText().toString().length()==0 || binding.edtPhone.getText().toString().length()==0){
                        Toast.makeText(InfoUserActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else {
                        binding.txtEdit.setText("Sửa");
                        clickCallApiUpdateUser();
                        binding.edtName.setEnabled(false);
                        binding.edtPhone.setEnabled(false);
                        binding.edtEmail.setEnabled(false);
                    }
                }
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void clickCallApiGetUserDetail() {
        ApiService.apiService.getUserDetail(IdUser).enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    if (userResponse.getCode() == 200) {
                        user=userResponse.getUser();
                        binding.edtName.setText(user.getFullName());
                        binding.edtEmail.setText(user.getEmail());
                        binding.edtPhone.setText(user.getPhone());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(InfoUserActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickCallApiUpdateUser() {
        ApiService.apiService.updateUser(IdUser,binding.edtName.getText().toString(),binding.edtEmail.getText().toString(),binding.edtPhone.getText().toString()).enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    if (userResponse.getCode() == 200) {
                        user=userResponse.getUser();
                        Toast.makeText(InfoUserActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(InfoUserActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}