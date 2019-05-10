package com.example.newsapiapp.features.news;

import android.text.format.DateUtils;
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
    private PublishSubject<String> clickNewsSubject = PublishSubject.create();

    public Observable<String> getClickNewsObservable() {
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
        newsViewHolder.clContainer.setOnClickListener(v -> clickNewsSubject.onNext(
                data.get(newsViewHolder.getAdapterPosition()).getUrl())
        );
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.tvLabel.setText(data.get(position).getTitle());
        try {
            holder.tvTime.setText(DateUtils.getRelativeTimeSpanString(data.get(position).getPublishedAt()));
        } catch (Exception ignore) {
        }

        if (data.get(position).getUrlToImage() == null) {
            if (data.get(position).getUrl().contains("youtube")) {
                Glide.with(holder.ivLogo)
                        .load(holder.ivLogo.getContext().getDrawable(R.drawable.yt_logo_rgb_light))
                        .into(holder.ivLogo);
            } else {
                holder.ivLogo.setVisibility(View.GONE);
            }
        } else {
            holder.ivLogo.setVisibility(View.VISIBLE);
            Glide.with(holder.ivLogo)
                    .load(data.get(position).getUrlToImage())
                    .into(holder.ivLogo);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout clContainer;
        private TextView tvLabel, tvTime;
        private ImageView ivLogo;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            clContainer = itemView.findViewById(R.id.clContainer);
            tvLabel = itemView.findViewById(R.id.tv_label);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivLogo = itemView.findViewById(R.id.iv_logo);
        }
    }
}
