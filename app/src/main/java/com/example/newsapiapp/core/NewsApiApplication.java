package com.example.newsapiapp.core;

import android.app.Application;

import com.example.newsapiapp.network.NewsApi;
import com.google.gson.Gson;
import com.jakewharton.threetenabp.AndroidThreeTen;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import saschpe.android.customtabs.CustomTabsActivityLifecycleCallbacks;

public class NewsApiApplication extends Application {

    public static NewsApiApplication INSTANCE;
    private Repository repository;
    private Cicerone<Router> cicerone;
    private NewsApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        AndroidThreeTen.init(this);
        initApi();
        initCicerone();
        initRepository();
        registerActivityLifecycleCallbacks(new CustomTabsActivityLifecycleCallbacks());
    }

    private void initApi() {
        api = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsApi.class);
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    private void initRepository() {
        repository = new Repository(this, api);
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public Repository getRepository() {
        return repository;
    }
}
