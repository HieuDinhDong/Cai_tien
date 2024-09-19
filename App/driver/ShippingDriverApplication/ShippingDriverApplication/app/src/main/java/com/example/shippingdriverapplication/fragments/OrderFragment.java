package com.example.shippingdriverapplication.fragments;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shippingdriverapplication.R;
import com.example.shippingdriverapplication.activities.AddOrderActivity;
import com.example.shippingdriverapplication.adapters.OrderAdapter;
import com.example.shippingdriverapplication.apis.ApiService;
import com.example.shippingdriverapplication.databinding.FragmentOrderBinding;
import com.example.shippingdriverapplication.models.Driver;
import com.example.shippingdriverapplication.models.Order;
import com.example.shippingdriverapplication.models.User;
import com.example.shippingdriverapplication.models.responses.ListOrderResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    FragmentOrderBinding binding;
    private int IdUser;
    private Driver user;
    private ArrayList<Order> listOrders;
    private OrderAdapter orderAdapter;
    private int sta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderBinding.inflate(inflater, container, false);
        IdUser=getArguments().getInt("id_user");
        user=new Driver();
        listOrders=new ArrayList<>();
        orderAdapter=new OrderAdapter(requireActivity());
        orderAdapter.setAction("list");
        ArrayList<String> listNameStatus=new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.rcvListOrder.setLayoutManager(linearLayoutManager);
        orderAdapter.setData(listOrders);
        orderAdapter.setIdUser(IdUser);
        binding.rcvListOrder.setAdapter(orderAdapter);
        sta=0;
        getLastLocation();
        listNameStatus.add("Đơn hàng mới");
        listNameStatus.add("Đang giao");
        listNameStatus.add("Đã giao");
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
                getLastLocation();
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

    private void getLastLocation() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        FusedLocationProviderClient fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireContext());
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null){
                                Geocoder geocoder=new Geocoder(requireContext(), Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    clickCallApiGetListOrder(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                                } catch (IOException e) {
                                    System.out.println(e);
                                    throw new RuntimeException(e);
                                }


                            }
                        }
                    });

        }else{
            askPermisstion();
        }
    }

    private void askPermisstion() {
        ActivityCompat.requestPermissions(requireActivity(),new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(requireContext(), "Required Permisstion", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clickCallApiGetListOrder(double latitude, double longitude) {
        ApiService.apiService.getListOrderOfDriver(IdUser,longitude,latitude,sta).enqueue(new Callback<ListOrderResponse>() {

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

    public boolean checkEqualsString(String parent,String str){
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