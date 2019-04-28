package com.example.newsapiapp.features.news;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapiapp.R;
import com.example.newsapiapp.model.Article;

import java.util.ArrayList;
import java.util.List;

import static com.example.newsapiapp.core.NewsApiApplication.INSTANCE;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>{

    private List<Article> data = new ArrayList<>();

    public void setData(List<Article> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.tvLabel.setText(data.get(position).getTitle());
        Glide.with(INSTANCE)
                .load(data.get(position).getUrlToImage())
                .into(holder.ivLogo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLabel;
        private ImageView ivLogo;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.tv_label);
            ivLogo = itemView.findViewById(R.id.iv_logo);
        }
    }
}
