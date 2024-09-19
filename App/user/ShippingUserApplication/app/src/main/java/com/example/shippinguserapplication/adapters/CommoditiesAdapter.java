package com.example.shippinguserapplication.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shippinguserapplication.R;
import com.example.shippinguserapplication.models.Commodities;

import java.util.ArrayList;
import java.util.List;

public class CommoditiesAdapter extends RecyclerView.Adapter<CommoditiesAdapter.CommoditiesViewHolder> {
    private Activity mActivity;

    private ArrayList<Commodities> listCommodities;


    private CommoditiesAdapter.OnClickListener onClickListener;

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(CommoditiesAdapter.OnClickListener listener) {
        this.onClickListener = listener;
    }

    public CommoditiesAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void setData(ArrayList<Commodities> listCommodities) {
        this.listCommodities = listCommodities;
        notifyDataSetChanged();
    }

    public void addAllData(ArrayList<Commodities> listCommodities) {
        this.listCommodities.addAll(listCommodities);
        notifyDataSetChanged();
    }

    public void removeData(Commodities commodities) {
        this.listCommodities.remove(commodities);
        notifyDataSetChanged();
    }

    public ArrayList<Commodities> getListCommodities() {
        return listCommodities;
    }

    @NonNull
    @Override
    public CommoditiesAdapter.CommoditiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commodities, parent, false);
        return new CommoditiesAdapter.CommoditiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommoditiesViewHolder holder, int position) {
        Commodities commodities = listCommodities.get(position);
        int p = position;
        if (commodities != null) {
            holder.txtNameCom.setText(commodities.getNameCom());
            holder.txtPrice.setText(commodities.getPrice()+"");
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

    @Override
    public int getItemCount() {
        if (listCommodities != null) return listCommodities.size();
        return 0;
    }

    public class CommoditiesViewHolder extends RecyclerView.ViewHolder {
        public View layoutData;
        private TextView txtNameCom, txtPrice;
        private CardView item;

        public CommoditiesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameCom = itemView.findViewById(R.id.txt_name_com);
            txtPrice = itemView.findViewById(R.id.txt_price);
            layoutData = itemView.findViewById(R.id.layout_data);
            item = itemView.findViewById(R.id.layout_item_com);

        }
    }
}