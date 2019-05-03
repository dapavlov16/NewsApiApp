package com.example.newsapiapp.features.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsapiapp.R;
import com.example.newsapiapp.core.BaseFragment;
import com.example.newsapiapp.core.SimpleDisposable;
import com.example.newsapiapp.features.Screens;
import com.example.newsapiapp.features.news.viewmodel.NewsDetailsViewModel;
import com.example.newsapiapp.model.Article;

public class NewsDetailsFragment extends BaseFragment {

    private NewsDetailsViewModel viewModel;
    private int position;

    public static NewsDetailsFragment newInstance(int position) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Screens.ARTICLE_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NewsDetailsViewModel.class);
        position = getArguments().getInt(Screens.ARTICLE_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        // TODO fvbi
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.load(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        addDisposables(
                viewModel.getDetailsObservable()
                        .subscribeWith(new SimpleDisposable<Article>() {
                            @Override
                            public void onNext(Article article) {
                                // TODO init UI
                            }
                        })
        );
    }
}
