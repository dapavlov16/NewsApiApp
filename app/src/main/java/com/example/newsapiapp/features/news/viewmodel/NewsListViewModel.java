package com.example.newsapiapp.features.news.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.newsapiapp.core.BaseViewModel;
import com.example.newsapiapp.core.NewsApiApplication;
import com.example.newsapiapp.core.Repository;
import com.example.newsapiapp.core.SimpleDisposable;
import com.example.newsapiapp.model.Article;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class NewsListViewModel extends BaseViewModel {

    private Repository repository;
    private BehaviorSubject<List<Article>> newsSubject = BehaviorSubject.create();

    public Observable<List<Article>> getNewsObservable() {
        return newsSubject.hide();
    }

    public NewsListViewModel(@NonNull Application application) {
        super(application);
        repository = ((NewsApiApplication) application).getRepository();
    }

    public void loadNews() {
        repository.executeGetRequest();
        addDisposables(
                repository
                        .getRepositoryObservable()
                        .subscribeWith(new SimpleDisposable<List<Article>>() {
                            @Override
                            public void onNext(List<Article> articles) {
                                newsSubject.onNext(articles);
                            }
                        })
        );
    }
}
