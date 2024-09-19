package com.example.shippingdriverapplication.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shippingdriverapplication.R;
import com.example.shippingdriverapplication.apis.ApiService;
import com.example.shippingdriverapplication.databinding.ActivityAddOrderBinding;
import com.example.shippingdriverapplication.models.Commodities;
import com.example.shippingdriverapplication.models.Order;
import com.example.shippingdriverapplication.models.TypeOrder;
import com.example.shippingdriverapplication.models.responses.ListTypeOrderResponse;
import com.example.shippingdriverapplication.models.responses.ObjectResponse;
import com.example.shippingdriverapplication.models.responses.OrderResponse;
import com.example.shippingdriverapplication.supports.Database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderActivity extends AppCompatActivity {

    ActivityAddOrderBinding binding;
    private int IdUser,IdOrder;
    private Order order;
    private ArrayList<TypeOrder> listTypeOrders;
    private ArrayList<Commodities> listCommodities;
    private Database database;
    private int IdType;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listTypeOrders=new ArrayList<>();
        listCommodities=new ArrayList<>();
        price=0;
        IdType=0;
        database=new Database(this);
        IdUser = getIntent().getIntExtra("id_user", 0);
        IdOrder = getIntent().getIntExtra("id_order", 0);
        if(IdOrder>0) {
            binding.btnConfirm.setVisibility(View.GONE);
            binding.edtNameReceiver.setEnabled(false);
            binding.edtNote.setEnabled(false);
            binding.edtPhone.setEnabled(false);
            binding.edtAddressSend.setEnabled(false);
            binding.edtAddressReceiver.setEnabled(false);
            binding.spTypeOrder.setEnabled(false);
            binding.txtTypePost.setText("Thông tin đơn hàng");
            clickCallApiGetOrderDetail();
        }else{
            binding.edtNameReceiver.setEnabled(true);
            binding.edtNote.setEnabled(true);
            binding.edtPhone.setEnabled(true);
            binding.edtAddressSend.setEnabled(true);
            binding.edtAddressReceiver.setEnabled(true);
            binding.spTypeOrder.setEnabled(true);
            clickCallApiGetListTypeOrder(0);
            binding.btnConfirm.setVisibility(View.VISIBLE);
        }
        order=new Order();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCommodities=database.getAllCommodities();
                for(int i=0;i<listCommodities.size();i++){
                    database.deleteCommodities(listCommodities.get(i).getIdCom());
                }
                finish();
            }
        });

        binding.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddOrderActivity.this, ListCommoditiesActivity.class);
                intent.putExtra("status",IdOrder);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(AddOrderActivity.this, R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
                startActivity(intent,bndlanimation);
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCommodities=database.getAllCommodities();
                if(binding.edtPhone.getText().toString().length()==0 || binding.edtNote.getText().toString().length()==0 || binding.edtAddressReceiver.getText().toString().length()==0 ||
                binding.edtAddressSend.getText().toString().length()==0 || binding.edtNameReceiver.getText().toString().length()==0){
                    Toast.makeText(AddOrderActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if(listCommodities.size()==0){
                        Toast.makeText(AddOrderActivity.this, "Bạn chưa thêm mặt hàng nào", Toast.LENGTH_SHORT).show();
                    }else {
                        clickCallApiAddOrder();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listCommodities=database.getAllCommodities();
        binding.txtNum.setText("Số lượng mặt hàng: "+listCommodities.size());
        double weight=0;
        for(int i=0;i<listCommodities.size();i++){
            weight+=listCommodities.get(i).getWeight();
        }
        if(weight==0){
            price = 0;
            binding.txtPrice.setText("Giá vận chuyển: " + 0);
        }else {
            if (weight < 5) {
                price = 10000;
                binding.txtPrice.setText("Giá vận chuyển: " + 10000);
            } else {
                price = 20000;
                binding.txtPrice.setText("Giá vận chuyển: " + 20000);
            }
        }
    }

    public String getDayNow(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String timeNow=dateFormat.format(cal.getTime());
        return timeNow;
    }

    public static String changeReverDateTime(String inputDate,boolean reverse){
        SimpleDateFormat inputFormat = new SimpleDateFormat((reverse)?"dd-MM-yyyy":"yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat((reverse)?"yyyy-MM-dd":"dd-MM-yyyy");
        try {
            Date date = inputFormat.parse(inputDate);

            String formattedDate = outputFormat.format(date);

            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void clickCallApiAddOrder() {
        ApiService.apiService.addOrder(IdUser,IdType,binding.edtAddressSend.getText().toString(),binding.edtAddressReceiver.getText().toString(),
                binding.edtPhone.getText().toString(),price,binding.edtNote.getText().toString(),changeReverDateTime(getDayNow(),false),0,binding.edtNameReceiver.getText().toString()).enqueue(new Callback<ObjectResponse>() {

            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if (objectResponse != null) {
                    if (objectResponse.getCode() == 201) {
                        int idOrder=Integer.parseInt(objectResponse.getData());
                        listCommodities=database.getAllCommodities();
                        for(int i=0;i<listCommodities.size();i++){
                            clickCallApiAddCommodities(idOrder,listCommodities.get(i));
                            database.deleteCommodities(listCommodities.get(i).getIdCom());
                        }

                        Toast.makeText(AddOrderActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Toast.makeText(AddOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickCallApiAddCommodities(int idOrder,Commodities commodities) {
        ApiService.apiService.addCommodities(idOrder,commodities.getNameCom(),commodities.getDescribeCom(),commodities.getWeight(),commodities.getPrice()).enqueue(new Callback<ObjectResponse>() {

            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if (objectResponse != null) {
                    if (objectResponse.getCode() == 201) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Toast.makeText(AddOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickCallApiGetOrderDetail() {
        ApiService.apiService.getOrderDetail(IdOrder).enqueue(new Callback<OrderResponse>() {

            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                OrderResponse orderResponse = response.body();
                if (orderResponse != null) {
                    if (orderResponse.getCode() == 200) {
                        order=orderResponse.getOrder();
                        listCommodities=order.getListCommodities();
                        binding.edtAddressSend.setText(order.getAddressStart());
                        binding.edtAddressReceiver.setText(order.getAddressEnd());
                        binding.txtNum.setText("Số lượng mặt hàng: "+order.getListCommodities().size());
                        binding.txtPrice.setText("Giá vận chuyển: "+order.getPriceShip());
                        binding.edtNameReceiver.setText(order.getNameReceiver());
                        binding.edtNote.setText(order.getNote());
                        binding.edtPhone.setText(order.getPhoneReceiver());
                        clickCallApiGetListTypeOrder(order.getTypeOrder().getIdType());
                        for(int i=0;i<order.getListCommodities().size();i++){
                            database.insertCommodities(order.getListCommodities().get(i));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(AddOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickCallApiGetListTypeOrder(int idType) {
        ApiService.apiService.getListTypeOrder().enqueue(new Callback<ListTypeOrderResponse>() {

            @Override
            public void onResponse(Call<ListTypeOrderResponse> call, Response<ListTypeOrderResponse> response) {
                ListTypeOrderResponse orderResponse = response.body();
                if (orderResponse != null) {
                    if (orderResponse.getCode() == 200) {
                        listTypeOrders=orderResponse.getListTypeOrders();
                        ArrayList<String> listNameTypeOrders=new ArrayList<>();
                        int position=0;
                        for(int i=0;i<listTypeOrders.size();i++){
                            listNameTypeOrders.add(listTypeOrders.get(i).getNameType());
                            if(listTypeOrders.get(i).getIdType()==idType) position=i;
                        }
                        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(AddOrderActivity.this,  R.layout.item_spinner, listNameTypeOrders);
                        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spTypeOrder.setAdapter(typeAdapter);
                        binding.spTypeOrder.setSelection(position);
                        IdType=listTypeOrders.get(position).getIdType();
                        binding.spTypeOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String selectedType = parentView.getItemAtPosition(position).toString();
                                binding.spTypeOrder.setSelection(position);
                                IdType=listTypeOrders.get(position).getIdType();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ListTypeOrderResponse> call, Throwable t) {
                Toast.makeText(AddOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}