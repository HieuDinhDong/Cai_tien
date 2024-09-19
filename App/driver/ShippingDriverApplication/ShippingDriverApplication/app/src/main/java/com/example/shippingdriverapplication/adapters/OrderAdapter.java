package com.example.shippingdriverapplication.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shippingdriverapplication.R;
import com.example.shippingdriverapplication.apis.ApiService;
import com.example.shippingdriverapplication.models.Order;
import com.example.shippingdriverapplication.models.responses.ObjectResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Activity mActivity;

    private ArrayList<Order> listOrders;
    private int IdUser;
    private String action;


    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public OrderAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        this.listOrders=new ArrayList<>();
    }

    public void setData(ArrayList<Order> listOrders) {
        this.listOrders = listOrders;
        notifyDataSetChanged();
    }

    public void addAllData(ArrayList<Order> listOrders) {
        this.listOrders.addAll(listOrders);
        notifyDataSetChanged();
    }

    public void removeData(Order order) {
        this.listOrders.remove(order);
        notifyDataSetChanged();
    }

    public ArrayList<Order> getListOrders() {
        return listOrders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = listOrders.get(position);
        int p = position;
        if (order != null) {
            if(action.equals("static")){
                holder.btnStatus.setVisibility(View.GONE);
            }else{
                holder.btnStatus.setVisibility(View.VISIBLE);
            }

            if(order.getStatus()==0){
                holder.btnStatus.setText("Nhận đơn hàng");
                holder.btnTuChoi.setVisibility(View.VISIBLE);
            }else{
                holder.btnTuChoi.setVisibility(View.GONE);
                if(order.getStatus()==1){
                    holder.btnStatus.setText("Hoàn thành");
                }else{
                    holder.btnStatus.setText("Đã giao hàng");
                }
            }
            holder.txtTime.setText(order.getTime());

            holder.btnTuChoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallApiTuChoi(IdUser,order.getIdOrder(),p);
                }
            });

            holder.btnStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(order.getStatus()==0) {
                        clickCallApiReceiveOrder(order.getIdOrder(),p);
                    }else{
                        if(order.getStatus()==1) {
                            clickCallApiSuccessOrder(order.getIdOrder(),p);
                        }
                    }
                }
            });

            holder.txtPrice.setText(order.getPriceShip()+"");
            holder.txtNum.setText("Số lượng gói hàng: "+order.getListCommodities().size());
            if(order.getTypeOrder().getNameType().equals("Siêu tốc")){
                holder.imgStatus.setImageDrawable(mActivity.getDrawable(R.drawable.icon_fast));
            }else{
                holder.imgStatus.setImageDrawable(mActivity.getDrawable(R.drawable.car_green));
            }
            holder.txtType.setText(order.getTypeOrder().getNameType());
            holder.txtAddStart.setText(order.getAddressStart());
            holder.txtAddEnd.setText(order.getAddressEnd());

            if (onClickListener != null) {
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.onItemClick(p);
                    }
                });
            }
        }
    }

    private void clickCallApiReceiveOrder(int IdOrder,int p) {
        ApiService.apiService.receiveOrderByDriver(IdUser,IdOrder).enqueue(new Callback<ObjectResponse>() {

            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if (objectResponse != null) {
                    if (objectResponse.getCode() == 200) {
                        listOrders.remove(p);
                        Toast.makeText(mActivity, "Nhận thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Toast.makeText(mActivity, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickCallApiTuChoi(int IdUser,int IdOrder,int p) {
        ApiService.apiService.refuseOrder(IdUser,IdOrder).enqueue(new Callback<ObjectResponse>() {

            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if (objectResponse != null) {
                    if (objectResponse.getCode() == 200) {
                        listOrders.remove(p);
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Toast.makeText(mActivity, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickCallApiSuccessOrder(int IdOrder,int p) {
        ApiService.apiService.successOrderByDriver(IdUser,IdOrder).enqueue(new Callback<ObjectResponse>() {

            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if (objectResponse != null) {
                    if (objectResponse.getCode() == 200) {
                        listOrders.remove(p);
                        Toast.makeText(mActivity, "Nhận thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Toast.makeText(mActivity, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listOrders != null) return listOrders.size();
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public View layoutData;
        private TextView btnStatus, txtPrice,txtNum,txtType,txtAddStart,txtAddEnd,txtDotStart,txtDotEnd,txtTime,btnTuChoi;
        private ImageView imgStatus;
        private ConstraintLayout item;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            btnStatus = itemView.findViewById(R.id.btn_status);
            txtPrice = itemView.findViewById(R.id.txt_price);
            txtTime = itemView.findViewById(R.id.txt_time);
            txtNum = itemView.findViewById(R.id.txt_num_goods);
            txtType = itemView.findViewById(R.id.txt_type_order);
            txtAddStart = itemView.findViewById(R.id.txt_address_start);
            txtAddEnd = itemView.findViewById(R.id.txt_address_end);
            txtDotStart = itemView.findViewById(R.id.txt_dot_start);
            txtDotEnd = itemView.findViewById(R.id.txt_dot_end);
            imgStatus = itemView.findViewById(R.id.img_img);
            item = itemView.findViewById(R.id.layout_item_order);
            btnTuChoi = itemView.findViewById(R.id.btn_tu_choi);

        }
    }
}