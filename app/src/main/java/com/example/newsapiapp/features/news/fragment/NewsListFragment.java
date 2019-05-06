package com.example.newsapiapp.features.news.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapiapp.R;
import com.example.newsapiapp.core.BaseFragment;
import com.example.newsapiapp.core.SimpleDisposable;
import com.example.newsapiapp.features.news.NewsRecyclerViewAdapter;
import com.example.newsapiapp.features.news.viewmodel.NewsListViewModel;
import com.example.newsapiapp.model.Article;

import java.util.List;

import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class NewsListFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter adapter;

    private NewsListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NewsListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        recyclerView = view.findViewById(R.id.rv_news);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.loadNews();
    }

    @Override
    public void onResume() {
        super.onResume();
        addDisposables(
                viewModel.getNewsObservable().subscribeWith(new SimpleDisposable<List<Article>>() {
                    @Override
                    public void onNext(List<Article> articles) {
                        adapter.setData(articles);
                        adapter.notifyDataSetChanged();
                    }
                }),
                adapter.getClickNewsObservable().subscribeWith(new SimpleDisposable<String>() {
                    @Override
                    public void onNext(String url) {
                        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                                .addDefaultShareMenuItem()
                                .setToolbarColor(getView().getResources().getColor(R.color.colorPrimary))
                                .setShowTitle(true)
                                .build();
                        CustomTabsHelper.addKeepAliveExtra(getView().getContext(), customTabsIntent.intent);
                        CustomTabsHelper.openCustomTab(getView().getContext(), customTabsIntent,
                                Uri.parse(url), new WebViewFallback());
                    }
                })
        );
    }
}
