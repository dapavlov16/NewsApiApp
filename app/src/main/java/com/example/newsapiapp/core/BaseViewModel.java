package com.example.newsapiapp.core;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

public abstract class BaseViewModel extends AndroidViewModel {

    protected Router router;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        router = NewsApiApplication.INSTANCE.getRouter();
    }

    protected void addDisposables(Disposable... disposables){
        for (Disposable d: disposables) {
            compositeDisposable.add(d);
        }
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
