package com.nikospolikandriotis.assesment2.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikospolikandriotis.assesment2.R;
import com.nikospolikandriotis.assesment2.network.Cast;
import com.nikospolikandriotis.assesment2.network.Movie;

import java.util.List;


public class CastListViewAdapter extends RecyclerView.Adapter<CastListViewHolder> {

    @NonNull
    private List<Cast> castList;

    public CastListViewAdapter(List<Cast> castList) {
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new CastListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastListViewHolder holder, int position) {
        Cast cast = this.castList.get(position);
        if(cast == null) {
            return;
        }

        holder.bindData(cast);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.movie_row;
    }
}
