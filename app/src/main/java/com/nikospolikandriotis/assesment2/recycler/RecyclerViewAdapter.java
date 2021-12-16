package com.nikospolikandriotis.assesment2.recycler;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikospolikandriotis.assesment2.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerView> {
    public interface Listener {
        public void onItemClick(View view, int data);
    }

    @NonNull
    private TypedArray productIds;
    private Listener callback;

    public RecyclerViewAdapter(TypedArray productIds, Listener callback) {
        this.productIds = productIds;
        this.callback = callback;
    }

    @NonNull
    @Override
    public BaseRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerView holder, int position) {
        int id = productIds.getResourceId(position, R.array.product1);
        holder.bindData(id);
    }

    @Override
    public int getItemCount() {
        return productIds.length();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.product_row;
    }
}
