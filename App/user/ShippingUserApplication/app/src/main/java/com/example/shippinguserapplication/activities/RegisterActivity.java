package com.example.shippinguserapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.apis.ApiService;
import com.example.shippinguserapplication.databinding.ActivityRegisterBinding;
import com.example.shippinguserapplication.models.responses.ObjectResponse;
import com.example.shippinguserapplication.models.responses.UserResponse;

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
                        clickCallApiRegister();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void clickCallApiRegister() {
        ApiService.apiService.registrationUser(binding.edtFullname.getText().toString(),binding.edtEmail.getText().toString(),
                binding.edtPhone.getText().toString(),binding.edtPasswordNew.getText().toString()).enqueue(new Callback<ObjectResponse>() {

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