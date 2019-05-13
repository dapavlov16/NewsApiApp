package com.example.newsapiapp.features.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private TextView tvTest;
    private NewsDetailsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NewsDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        tvTest = view.findViewById(R.id.tv_test);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
