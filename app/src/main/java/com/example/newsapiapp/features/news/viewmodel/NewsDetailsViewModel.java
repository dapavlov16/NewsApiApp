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

public class NewsDetailsViewModel extends BaseViewModel {

    private Repository repository;
    private BehaviorSubject<Article> detailsSubject = BehaviorSubject.create();

    public Observable<Article> getDetailsObservable() {
        return detailsSubject.hide();
    }

    public NewsDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = ((NewsApiApplication) application).getRepository();
    }

    public void load(int position) {
        detailsSubject.onNext(repository.getArticle(position));
    }
}
