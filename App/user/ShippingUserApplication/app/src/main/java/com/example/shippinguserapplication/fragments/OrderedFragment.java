package com.example.shippinguserapplication.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.activities.AddOrderActivity;
import com.example.shippinguserapplication.activities.ChangePasswordActivity;
import com.example.shippinguserapplication.adapters.OrderAdapter;
import com.example.shippinguserapplication.apis.ApiService;
import com.example.shippinguserapplication.databinding.FragmentOrderedBinding;
import com.example.shippinguserapplication.models.Order;
import com.example.shippinguserapplication.models.User;
import com.example.shippinguserapplication.models.responses.ListOrderResponse;
import com.example.shippinguserapplication.models.responses.ObjectResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderedFragment extends Fragment {

    FragmentOrderedBinding binding;
    private OrderAdapter orderAdapter;
    private int IdUser;
    private ArrayList<Order> listOrders;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderedBinding.inflate(inflater, container, false);
        IdUser=getArguments().getInt("id_user");
        user=new User();
        listOrders=new ArrayList<>();
        orderAdapter=new OrderAdapter(requireActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.rcvListOrdered.setLayoutManager(linearLayoutManager);
        orderAdapter.setData(listOrders);
        binding.rcvListOrdered.setAdapter(orderAdapter);
        clickCallApiGetListOrder();
        orderAdapter.setOnClickListener(position -> {
            Intent intent=new Intent(requireActivity(), AddOrderActivity.class);
            intent.putExtra("id_user",IdUser);
            intent.putExtra("id_order",orderAdapter.getListOrders().get(position).getIdOrder());
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
            startActivity(intent,bndlanimation);
        });
        return binding.getRoot();
    }

    private void clickCallApiGetListOrder() {
        ApiService.apiService.getListOrderOfUser(IdUser,0).enqueue(new Callback<ListOrderResponse>() {

            @Override
            public void onResponse(Call<ListOrderResponse> call, Response<ListOrderResponse> response) {
                ListOrderResponse listOrderResponse = response.body();
                if (listOrderResponse != null) {
                    if (listOrderResponse.getCode() == 200) {
                        listOrders=listOrderResponse.getListOrders();
                        orderAdapter.setData(listOrders);
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