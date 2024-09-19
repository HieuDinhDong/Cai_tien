package com.example.shippinguserapplication.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.apis.ApiService;
import com.example.shippinguserapplication.databinding.ActivityLoginBinding;
import com.example.shippinguserapplication.models.responses.ObjectResponse;
import com.example.shippinguserapplication.models.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private boolean isShowPass=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.icSeePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowPass==false) {
                    binding.icSeePassword.setImageDrawable(getResources().getDrawable(R.drawable.icon_show));
                    binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPass=true;
                }else{
                    binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.icSeePassword.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide));
                    isShowPass=false;
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtUsername.getText().toString().length()==0 || binding.edtPassword.getText().toString().length()==0){
                    Toast.makeText(LoginActivity.this, "Bạn cần điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    clickCallApiLogin(binding.edtUsername.getText().toString(),binding.edtPassword.getText().toString());
                }
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
                startActivity(intent,bndlanimation);
            }
        });

    }

    private void clickCallApiLogin(String username, String password) {
        ApiService.apiService.loginUser(username, password).enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    if (userResponse.getCode() == 200) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("id_user",userResponse.getUser().getIdUser());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Mật khẩu hoặc tài khoản không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}