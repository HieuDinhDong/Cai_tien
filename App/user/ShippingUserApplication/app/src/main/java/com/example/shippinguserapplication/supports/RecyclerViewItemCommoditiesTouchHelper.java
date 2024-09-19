package com.example.shippinguserapplication.supports;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shippinguserapplication.adapters.CommoditiesAdapter;
import com.example.shippinguserapplication.interfaces.ItemTouchHelperListener;

public class RecyclerViewItemCommoditiesTouchHelper extends ItemTouchHelper.SimpleCallback {
    private ItemTouchHelperListener listener;
    private boolean check;

    public RecyclerViewItemCommoditiesTouchHelper(int dragDirs, int swipeDirs, ItemTouchHelperListener listener, boolean check) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
        this.check=check;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (listener != null && check) {
            listener.onSwiped(viewHolder);
        }
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder!=null) {
            View foreGroundView = ((CommoditiesAdapter.CommoditiesViewHolder) viewHolder).layoutData;
            getDefaultUIUtil().onSelected(foreGroundView);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foreGroundView = ((CommoditiesAdapter.CommoditiesViewHolder) viewHolder).layoutData;
        getDefaultUIUtil().onDrawOver(c,recyclerView,foreGroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foreGroundView = ((CommoditiesAdapter.CommoditiesViewHolder) viewHolder).layoutData;
        getDefaultUIUtil().onDraw(c,recyclerView,foreGroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foreGroundView = ((CommoditiesAdapter.CommoditiesViewHolder) viewHolder).layoutData;
        getDefaultUIUtil().clearView(foreGroundView);
    }
}

