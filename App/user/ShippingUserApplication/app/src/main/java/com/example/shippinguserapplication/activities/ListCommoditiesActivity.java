package com.example.shippinguserapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.adapters.CommoditiesAdapter;
import com.example.shippinguserapplication.adapters.OrderAdapter;
import com.example.shippinguserapplication.apis.ApiService;
import com.example.shippinguserapplication.databinding.ActivityListCommoditiesBinding;
import com.example.shippinguserapplication.interfaces.ItemTouchHelperListener;
import com.example.shippinguserapplication.models.Commodities;
import com.example.shippinguserapplication.models.Order;
import com.example.shippinguserapplication.models.responses.ListOrderResponse;
import com.example.shippinguserapplication.supports.Database;
import com.example.shippinguserapplication.supports.RecyclerViewItemCommoditiesTouchHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCommoditiesActivity extends AppCompatActivity implements ItemTouchHelperListener{

    ActivityListCommoditiesBinding binding;
    Database database;
    private ArrayList<Commodities> list;
    private CommoditiesAdapter commoditiesAdapter;
    private String action;
    private int isView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListCommoditiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=new Database(this);
        isView = getIntent().getIntExtra("status", -1);
        action = getIntent().getStringExtra("action");
        list=database.getAllCommodities();

        commoditiesAdapter=new CommoditiesAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvListCommodities.setLayoutManager(linearLayoutManager);
        commoditiesAdapter.setData(list);
        binding.rcvListCommodities.setAdapter(commoditiesAdapter);
        if(commoditiesAdapter.getListCommodities().size()>0){
            hidenNodata();
        }else{
            showNodata();
        }
        if(isView!=0) binding.txtAdd.setVisibility(View.GONE);
        else binding.txtAdd.setVisibility(View.VISIBLE);
        if(isView==0) {
            ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerViewItemCommoditiesTouchHelper(0, ItemTouchHelper.LEFT, ListCommoditiesActivity.this, (isView != 0) ? false : true);
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rcvListCommodities);

        }

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                commoditiesAdapter.setData(searchListCom(list,binding.edtSearch.getText().toString()));
            }
        });
        binding.txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListCommoditiesActivity.this, AddCommoditiesActivity.class);
                intent.putExtra("status",isView);
                intent.putExtra("action","Add");
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ListCommoditiesActivity.this, R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
                startActivity(intent,bndlanimation);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commoditiesAdapter.setOnClickListener(position -> {
            Intent intent=new Intent(ListCommoditiesActivity.this, AddCommoditiesActivity.class);
            intent.putExtra("id_com",commoditiesAdapter.getListCommodities().get(position).getIdCom());
            intent.putExtra("status",isView);
            intent.putExtra("action","Edit");
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ListCommoditiesActivity.this, R.anim.slide_in_right,R.anim.slide_out_left).toBundle();
            startActivity(intent,bndlanimation);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list=database.getAllCommodities();
        commoditiesAdapter.setData(database.getAllCommodities());
        if(commoditiesAdapter.getListCommodities().size()>0){
            hidenNodata();
        }else{
            showNodata();
        }
    }

    public void showNodata(){
        binding.imgNoData.setVisibility(View.VISIBLE);
        binding.txtNoData.setVisibility(View.VISIBLE);
    }

    public void hidenNodata(){
        binding.imgNoData.setVisibility(View.GONE);
        binding.txtNoData.setVisibility(View.GONE);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        if(isView==0) {
            if (viewHolder instanceof CommoditiesAdapter.CommoditiesViewHolder) {

                int indexDelete = viewHolder.getAdapterPosition();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Bạn có muốn xoá mặt hàng này không không?");
                Commodities commodities = commoditiesAdapter.getListCommodities().get(indexDelete);
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.deleteCommodities(commodities.getIdCom());
                        commoditiesAdapter.removeData(commodities);
                        if(commoditiesAdapter.getListCommodities().size()==0){
                            showNodata();
                        }
                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        commoditiesAdapter.setData(list);
                    }
                });
                alertDialog.show();

            }
        }
    }
    public boolean checkEqualsString(String parent,String str){
        for(int i=0;i<parent.length()-str.length()+1;i++){
            if(parent.substring(i,i+str.length()).equalsIgnoreCase(str)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Commodities> searchListCom(ArrayList<Commodities> list, String keysearch){
        if(keysearch.length()==0) return this.list;
        ArrayList<Commodities>listCom=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(checkEqualsString(list.get(i).getNameCom(),keysearch)){
                listCom.add(list.get(i));
            }
        }
        return listCom;
    }



}