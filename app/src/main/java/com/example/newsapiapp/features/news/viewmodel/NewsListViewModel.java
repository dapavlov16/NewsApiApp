package com.example.newsapiapp.features.news.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.newsapiapp.R;
import com.example.newsapiapp.core.BaseViewModel;
import com.example.newsapiapp.core.NewsApiApplication;
import com.example.newsapiapp.core.Repository;
import com.example.newsapiapp.core.SimpleDisposable;
import com.example.newsapiapp.model.Article;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


public class NewsListViewModel extends BaseViewModel {

    private Repository repository;
    private BehaviorSubject<List<Article>> newsSubject = BehaviorSubject.create();
    private PublishSubject<MaterialDialog> dialogSubject = PublishSubject.create();

    public Observable<List<Article>> getNewsObservable() {
        return newsSubject.hide();
    }

    public Observable<MaterialDialog> getDialogObservable() {
        return dialogSubject.hide();
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

    public void buildDialog(Context context, @StringRes int fab_type) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(fab_type)
                .positiveText(R.string.apply);
        switch (fab_type) {
            case R.string.fab_label_category:
                builder
                        .items(R.array.categories)
                        .itemsCallbackSingleChoice(repository.getCurrentCategory(), (dialog, itemView, which, text) -> {
                            repository.setCurrentCategory(which);
                            loadNews();
                            return true;
                        });
                break;
            case R.string.fab_label_country:
                builder
                        .items(R.array.countries_list)
                        .itemsCallbackSingleChoice(repository.getCurrentCountry(), (dialog, itemView, which, text) -> {
                            repository.setCurrentCountry(which);
                            loadNews();
                            return true;
                        });
                break;
        }
        dialogSubject.onNext(builder.build());
    }
}
