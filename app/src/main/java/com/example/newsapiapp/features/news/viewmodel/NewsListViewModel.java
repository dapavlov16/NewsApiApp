package com.example.newsapiapp.features.news.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.newsapiapp.core.BaseViewModel;
import com.example.newsapiapp.core.NewsApiApplication;
import com.example.newsapiapp.core.Repository;
import com.example.newsapiapp.features.Screens;
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
        newsSubject.onNext(repository.getArticles());
    }

    public void openDetails(Integer i) {
        router.navigateTo(new Screens.DetailsListScreen(i));
    }
}
