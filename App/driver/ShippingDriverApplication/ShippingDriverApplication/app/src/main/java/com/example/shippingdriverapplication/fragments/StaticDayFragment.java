package com.example.shippingdriverapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shippingdriverapplication.R;
import com.example.shippingdriverapplication.activities.LoginActivity;
import com.example.shippingdriverapplication.activities.MainActivity;
import com.example.shippingdriverapplication.adapters.OrderAdapter;
import com.example.shippingdriverapplication.apis.ApiService;
import com.example.shippingdriverapplication.databinding.FragmentHomeBinding;
import com.example.shippingdriverapplication.databinding.FragmentStaticDayBinding;
import com.example.shippingdriverapplication.models.Order;
import com.example.shippingdriverapplication.models.responses.DriverResponse;
import com.example.shippingdriverapplication.models.responses.ListOrderResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StaticDayFragment extends Fragment {

    FragmentStaticDayBinding binding;
    ArrayList<Order> listOrders;
    OrderAdapter adapter;
    int IdUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStaticDayBinding.inflate(inflater, container, false);
        listOrders=new ArrayList<>();
        IdUser=getArguments().getInt("id_user");
        adapter=new OrderAdapter(requireActivity());
        adapter.setAction("static");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.listOrder.setLayoutManager(linearLayoutManager);
        adapter.setData(listOrders);
        binding.listOrder.setAdapter(adapter);
        clickCallApiGetListOrder();
        return binding.getRoot();
    }

    public String getDayNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String timeNow=dateFormat.format(cal.getTime());
        return timeNow;
    }

    private void clickCallApiGetListOrder() {
        ApiService.apiService.staticOrderDay(IdUser, getDayNow()).enqueue(new Callback<ListOrderResponse>() {

            @Override
            public void onResponse(Call<ListOrderResponse> call, Response<ListOrderResponse> response) {
                ListOrderResponse listOrderResponse = response.body();
                if (listOrderResponse != null) {
                    if (listOrderResponse.getCode() == 200) {
                        listOrders=listOrderResponse.getListOrders();
                        double sum=0;
                        for(Order order:listOrders){
                            sum+=order.getPriceShip();
                        }
                        binding.doanhthu.setText((int)sum+"");
                        adapter.setData(listOrders);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListOrderResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}