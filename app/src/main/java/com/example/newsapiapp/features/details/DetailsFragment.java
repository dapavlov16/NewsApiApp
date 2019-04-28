package com.example.newsapiapp.features.details;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsapiapp.core.BaseFragment;
import com.example.newsapiapp.features.Screens;
import com.example.newsapiapp.model.Article;

import io.reactivex.observers.DisposableObserver;

public class DetailsFragment extends BaseFragment {

    private DetailsViewModel viewModel;
    private int position;

    public static DetailsFragment newInstance(int position) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Screens.ARTICLE_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        position = getArguments().getInt(Screens.ARTICLE_POSITION);
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
                .subscribeWith(new DisposableObserver<Article>(){

                    @Override
                    public void onNext(Article article) {
                        // todo init UI
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }
}
