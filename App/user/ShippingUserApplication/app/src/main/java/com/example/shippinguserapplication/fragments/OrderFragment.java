package com.example.shippinguserapplication.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.activities.AddOrderActivity;
import com.example.shippinguserapplication.adapters.OrderAdapter;
import com.example.shippinguserapplication.adapters.ViewPagerOrderAdapter;
import com.example.shippinguserapplication.apis.ApiService;
import com.example.shippinguserapplication.databinding.FragmentOrderBinding;
import com.example.shippinguserapplication.models.Order;
import com.example.shippinguserapplication.models.User;
import com.example.shippinguserapplication.models.responses.ListOrderResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    FragmentOrderBinding binding;
    private int IdUser;
    private User user;
    private ArrayList<Order> listOrders;
    private OrderAdapter orderAdapter;
    private int sta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderBinding.inflate(inflater, container, false);
        IdUser=getArguments().getInt("id_user");
        user=new User();
        listOrders=new ArrayList<>();
        orderAdapter=new OrderAdapter(requireActivity());
        ArrayList<String> listNameStatus=new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.rcvListOrder.setLayoutManager(linearLayoutManager);
        orderAdapter.setData(listOrders);
        binding.rcvListOrder.setAdapter(orderAdapter);
        sta=0;
        clickCallApiGetListOrder();
        listNameStatus.add("Đơn hàng");
        listNameStatus.add("Đang giao");
        listNameStatus.add("Đã nhận");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireActivity(),  R.layout.item_spinner, listNameStatus);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spTypeOrder.setAdapter(typeAdapter);
        binding.spTypeOrder.setSelection(0);
        binding.spTypeOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedType = parentView.getItemAtPosition(position).toString();
                binding.spTypeOrder.setSelection(position);
                sta=position;
                clickCallApiGetListOrder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                orderAdapter.setData(searchListOrder(orderAdapter.getListOrders(),binding.edtSearch.getText().toString()));
            }
        });

        binding.iconAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), AddOrderActivity.class);
                intent.putExtra("id_user",IdUser);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
                startActivity(intent,bndlanimation);
            }
        });
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
        ApiService.apiService.getListOrderOfUser(IdUser,sta).enqueue(new Callback<ListOrderResponse>() {

            @Override
            public void onResponse(Call<ListOrderResponse> call, Response<ListOrderResponse> response) {
                ListOrderResponse listOrderResponse = response.body();
                if (listOrderResponse != null) {
                    if (listOrderResponse.getCode() == 200) {
                        listOrders=listOrderResponse.getListOrders();
                        if(listOrders.size()>0){
                            hidenNodata();
                        }else{
                            showNodata();
                        }
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

    @Override
    public void onResume() {
        super.onResume();
        clickCallApiGetListOrder();
    }

    public boolean checkEqualsString(String parent, String str){
        for(int i=0;i<parent.length()-str.length()+1;i++){
            if(parent.substring(i,i+str.length()).equalsIgnoreCase(str)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Order> searchListOrder(ArrayList<Order> list, String keysearch){
        if(keysearch.length()==0) return listOrders;
        ArrayList<Order>listOrder=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(checkEqualsString(list.get(i).getAddressStart(),keysearch) || checkEqualsString(list.get(i).getAddressEnd(),keysearch)){
                listOrder.add(list.get(i));
            }
        }
        return listOrder;
    }

    public void showNodata(){
        binding.imgNoData.setVisibility(View.VISIBLE);
        binding.txtNoData.setVisibility(View.VISIBLE);
    }

    public void hidenNodata(){
        binding.imgNoData.setVisibility(View.GONE);
        binding.txtNoData.setVisibility(View.GONE);
    }
}