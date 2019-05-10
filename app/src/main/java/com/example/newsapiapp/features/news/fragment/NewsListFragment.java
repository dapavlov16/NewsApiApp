package com.example.newsapiapp.features.news.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.newsapiapp.R;
import com.example.newsapiapp.core.BaseFragment;
import com.example.newsapiapp.core.SimpleDisposable;
import com.example.newsapiapp.features.news.NewsRecyclerViewAdapter;
import com.example.newsapiapp.features.news.viewmodel.NewsListViewModel;
import com.example.newsapiapp.model.Article;
import com.jakewharton.rxbinding3.appcompat.RxSearchView;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class NewsListFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter adapter;
    private SpeedDialView speedDialView;
    private SearchView searchView;

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
        speedDialView = view.findViewById(R.id.speed_dial);
        searchView = view.findViewById(R.id.search_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    searchView.clearFocus();
                    if (searchView.getQuery().toString().isEmpty()) {
                        searchView.setIconified(true);
                    }
                }
            }
        });
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action_category, R.drawable.ic_settings_white_24dp)
                        .setLabel(R.string.fab_label_category)
                        .create()
        );
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action_country, R.drawable.ic_settings_white_24dp)
                        .setLabel(R.string.fab_label_country)
                        .create()
        );
//        speedDialView.addActionItem(
//                new SpeedDialActionItem.Builder(R.id.fab_action_language, R.drawable.ic_settings_white_24dp)
//                        .setLabel(R.string.fab_label_language)
//                        .create()
//        );

        speedDialView.setOnActionSelectedListener(speedDialActionItem -> {
            switch (speedDialActionItem.getId()) {
                case R.id.fab_action_category:
                    Toast.makeText(getContext(), "category", Toast.LENGTH_SHORT).show();
                    viewModel.buildDialog(getContext(), R.string.fab_label_category);
                    return false;
                case R.id.fab_action_country:
                    Toast.makeText(getContext(), "country", Toast.LENGTH_SHORT).show();
                    viewModel.buildDialog(getContext(), R.string.fab_label_country);
                    return false;
//                case R.id.fab_action_language:
//                    Toast.makeText(getContext(), "language", Toast.LENGTH_SHORT).show();
//                    viewModel.buildDialog(getContext(), R.string.fab_label_language);
//                    return false;
                default:
                    return false;
            }
        });

        viewModel.loadNews("");
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
                        Log.e("WOW", "onNext: click");
                        if (searchView.hasFocus()) {
                            searchView.clearFocus();
                            if (searchView.getQuery().toString().isEmpty()) {
                                searchView.setIconified(true);
                            }
                        } else {
                            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                                    .addDefaultShareMenuItem()
                                    .setToolbarColor(getView().getResources().getColor(R.color.colorPrimary))
                                    .setShowTitle(true)
                                    .build();
                            CustomTabsHelper.addKeepAliveExtra(getView().getContext(), customTabsIntent.intent);
                            CustomTabsHelper.openCustomTab(getView().getContext(), customTabsIntent,
                                    Uri.parse(url), new WebViewFallback());
                        }
                    }
                }),
                viewModel.getDialogObservable().subscribeWith(new SimpleDisposable<MaterialDialog>() {
                    @Override
                    public void onNext(MaterialDialog materialDialog) {
                        materialDialog.show();
                    }
                }),
                RxSearchView.queryTextChanges(searchView)
                        .skipInitialValue()
                        .map(text -> text.toString().toLowerCase().trim())
                        .debounce(250, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeWith(new SimpleDisposable<String>() {
                            @Override
                            public void onNext(String s) {
                                viewModel.loadNews(s);
                            }
                        })
        );
    }
}
