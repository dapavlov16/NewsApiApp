package com.example.newsapiapp.features.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapiapp.R;
import com.example.newsapiapp.core.BaseFragment;
import com.example.newsapiapp.model.Article;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

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
                viewModel.getNewsObservable().subscribeWith(new DisposableObserver<List<Article>>() {
                    @Override
                    public void onNext(List<Article> articles) {
                        adapter.setData(articles);
                        adapter.notifyDataSetChanged();
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
