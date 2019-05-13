package com.example.newsapiapp.features.news.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.newsapiapp.R;
import com.example.newsapiapp.core.BaseViewModel;
import com.example.newsapiapp.core.NewsApiApplication;
import com.example.newsapiapp.core.Repository;
import com.example.newsapiapp.model.Article;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import retrofit2.HttpException;


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

    public void loadNews(String q) {
        addDisposables(
                repository
                        .executeGetRequest(q)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Article>>() {

                                           @Override
                                           public void onSuccess(List<Article> articles) {
                                               newsSubject.onNext(articles);
                                           }

                                           @Override
                                           public void onError(Throwable e) {
                                               if (e instanceof HttpException) {
                                                   Toast.makeText(getApplication().getApplicationContext(),
                                                           (((HttpException) e).code() == 401) ? "API key is missing" : e.getMessage(),
                                                           Toast.LENGTH_LONG).show();
                                               } else if (e instanceof UnknownHostException) {
                                                   Toast.makeText(getApplication().getApplicationContext(),
                                                           "Connection problem", Toast.LENGTH_LONG).show();
                                               }
                                               newsSubject.onNext(new ArrayList<>());
                                           }
                                       }
                        )
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
                            if (repository.getCurrentCategory() != which) {
                                repository.setCurrentCategory(which);
                                loadNews("");
                            }
                            return true;
                        });
                break;
            case R.string.fab_label_country:
                builder
                        .items(R.array.countries_list)
                        .itemsCallbackSingleChoice(repository.getCurrentCountry(), (dialog, itemView, which, text) -> {
                            if (repository.getCurrentCountry() != which) {
                                repository.setCurrentCountry(which);
                                loadNews("");
                            }
                            return true;
                        });
                break;
        }
        dialogSubject.onNext(builder.build());
    }
}
