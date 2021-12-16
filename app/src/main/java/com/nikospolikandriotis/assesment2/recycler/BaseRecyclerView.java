package com.nikospolikandriotis.assesment2.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract  class BaseRecyclerView extends RecyclerView.ViewHolder {
    public BaseRecyclerView(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindData(int id);
}
