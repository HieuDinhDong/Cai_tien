package com.example.shippinguserapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.apis.ApiService;
import com.example.shippinguserapplication.databinding.ActivityChangePasswordBinding;
import com.example.shippinguserapplication.models.User;
import com.example.shippinguserapplication.models.responses.ObjectResponse;
import com.example.shippinguserapplication.models.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;
    private int IdUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user=new User();
        IdUser = getIntent().getIntExtra("id_user", 0);
        clickCallApiGetUserDetail();

        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtPasswordOld.getText().toString().length()==0 || binding.edtPasswordNew.getText().toString().length()==0 || binding.edtConfirmPassword.getText().toString().length()==0){
                    Toast.makeText(ChangePasswordActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(!binding.edtPasswordOld.getText().toString().equals(user.getPassword())){
                        Toast.makeText(ChangePasswordActivity.this, "Bạn nhập mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }else{
                        if(!binding.edtPasswordNew.getText().toString().equals(binding.edtConfirmPassword.getText().toString())){
                            Toast.makeText(ChangePasswordActivity.this, "Xác nhận mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
                        }else{
                            clickCallApiChangePassword();
                        }
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
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickCallApiChangePassword() {
        ApiService.apiService.changePasswordUser(IdUser,binding.edtPasswordNew.getText().toString()).enqueue(new Callback<ObjectResponse>() {

            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if (objectResponse != null) {
                    if (objectResponse.getCode() == 200) {
                        Toast.makeText(ChangePasswordActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}