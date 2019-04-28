package com.example.newsapiapp.features.details;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.newsapiapp.core.BaseViewModel;
import com.example.newsapiapp.core.NewsApiApplication;
import com.example.newsapiapp.core.Repository;
import com.example.newsapiapp.model.Article;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class DetailsViewModel extends BaseViewModel {

    private Repository repository;
    private BehaviorSubject<Article> detailsSubject = BehaviorSubject.create();

    public Observable<Article> getDetailsObservable() {
        return detailsSubject.hide();
    }

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = ((NewsApiApplication)application).getRepository();
    }

    public void load(int position) {
        detailsSubject.onNext(repository.getArticles().get(position));
    }
}
