package com.example.newsapiapp.features.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapiapp.R;
import com.example.newsapiapp.model.Article;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private List<Article> data = new ArrayList<>();
    private PublishSubject<Integer> clickNewsSubject = PublishSubject.create();

    public Observable<Integer> getClickNewsObservable() {
        return clickNewsSubject.hide();
    }

    public void setData(List<Article> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final NewsViewHolder newsViewHolder = new NewsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false));
        newsViewHolder.clContainer.setOnClickListener(v -> clickNewsSubject.onNext(newsViewHolder.getAdapterPosition()));
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.tvLabel.setText(data.get(position).getTitle());
        Glide.with(holder.ivLogo)
                .load(data.get(position).getUrlToImage())
                .into(holder.ivLogo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout clContainer;
        private TextView tvLabel;
        private ImageView ivLogo;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            clContainer = itemView.findViewById(R.id.clContainer);
            tvLabel = itemView.findViewById(R.id.tv_label);
            ivLogo = itemView.findViewById(R.id.iv_logo);
        }
    }
}
