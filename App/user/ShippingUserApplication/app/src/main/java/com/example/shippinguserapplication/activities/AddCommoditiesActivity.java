package com.example.shippinguserapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.databinding.ActivityAddCommoditiesBinding;
import com.example.shippinguserapplication.models.Commodities;
import com.example.shippinguserapplication.supports.Database;

public class AddCommoditiesActivity extends AppCompatActivity {

    ActivityAddCommoditiesBinding binding;
    Database database;
    private int isView;
    private String action;
    private int IdCom;
    private Commodities commodities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddCommoditiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=new Database(this);
        commodities=new Commodities();
        isView = getIntent().getIntExtra("status", -1);
        action = getIntent().getStringExtra("action");
        IdCom = getIntent().getIntExtra("id_com", -1);

        if(IdCom>0){
            commodities=database.getCommoditiesDetail(IdCom);
            binding.edtName.setText(commodities.getNameCom());
            binding.edtDescribe.setText(commodities.getDescribeCom());
            binding.edtWeight.setText(commodities.getWeight()+"");
            binding.edtPrice.setText(commodities.getPrice()+"");
        }

        if(isView>0) {
            binding.btnConfirm.setVisibility(View.GONE);
            binding.edtName.setEnabled(false);
            binding.edtDescribe.setEnabled(false);
            binding.edtPrice.setEnabled(false);
            binding.edtWeight.setEnabled(false);
        }
        else{
            binding.btnConfirm.setVisibility(View.VISIBLE);
            binding.edtName.setEnabled(true);
            binding.edtDescribe.setEnabled(true);
            binding.edtPrice.setEnabled(true);
            binding.edtWeight.setEnabled(true);
        }
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtName.getText().toString().length()==0 || binding.edtDescribe.getText().toString().length()==0||
                binding.edtPrice.getText().toString().length()==0 || binding.edtWeight.getText().toString().length()==0){
                    Toast.makeText(AddCommoditiesActivity.this, "Bạn cần điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(IdCom<1) {
                        database.insertCommodities(new Commodities(0, binding.edtName.getText().toString(), binding.edtDescribe.getText().toString(), Double.parseDouble(binding.edtWeight.getText().toString()),
                                Double.parseDouble(binding.edtPrice.getText().toString())));
                    }else{
                        database.updateCommodities(new Commodities(IdCom, binding.edtName.getText().toString(), binding.edtDescribe.getText().toString(), Double.parseDouble(binding.edtWeight.getText().toString()),
                                Double.parseDouble(binding.edtPrice.getText().toString())));
                    }
                    finish();
                }
            }
        });

    }
}