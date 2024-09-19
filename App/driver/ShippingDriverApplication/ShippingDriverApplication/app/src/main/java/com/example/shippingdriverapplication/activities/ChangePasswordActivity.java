package com.example.shippingdriverapplication.activities;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shippingdriverapplication.R;
import com.example.shippingdriverapplication.apis.ApiService;
import com.example.shippingdriverapplication.databinding.ActivityChangePasswordBinding;
import com.example.shippingdriverapplication.models.Driver;
import com.example.shippingdriverapplication.models.User;
import com.example.shippingdriverapplication.models.responses.DriverResponse;
import com.example.shippingdriverapplication.models.responses.ObjectResponse;
import com.example.shippingdriverapplication.models.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;
    private int IdUser;
    private Driver user;
    private boolean isShowPass1=false,isShowPass2=false,isShowPass3=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user=new Driver();
        IdUser = getIntent().getIntExtra("id_user", 0);
        clickCallApiGetDriverDetail();
        binding.icSeePassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowPass1==false) {
                    binding.icSeePassword1.setImageDrawable(getResources().getDrawable(R.drawable.icon_show));
                    binding.edtPasswordOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPass1=true;
                }else{
                    binding.edtPasswordOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.icSeePassword1.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide));
                    isShowPass1=false;
                }
            }
        });

        binding.icSeePassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowPass2==false) {
                    binding.icSeePassword2.setImageDrawable(getResources().getDrawable(R.drawable.icon_show));
                    binding.edtPasswordNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPass2=true;
                }else{
                    binding.edtPasswordNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.icSeePassword2.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide));
                    isShowPass2=false;
                }
            }
        });
        binding.icSeePassword3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowPass3==false) {
                    binding.icSeePassword3.setImageDrawable(getResources().getDrawable(R.drawable.icon_show));
                    binding.edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPass3=true;
                }else{
                    binding.edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.icSeePassword3.setImageDrawable(getResources().getDrawable(R.drawable.icon_hide));
                    isShowPass3=false;
                }
            }
        });
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
                Toast.makeText(ChangePasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickCallApiChangePassword() {
        ApiService.apiService.changePasswordDriver(IdUser,binding.edtPasswordNew.getText().toString()).enqueue(new Callback<ObjectResponse>() {

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