package com.example.newsapiapp.core;

import android.app.Application;

import com.google.gson.Gson;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class NewsApiApplication extends Application {

    public static NewsApiApplication INSTANCE;
    private Repository repository;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        initCicerone();
        initRepository();
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    private void initRepository() {
        repository = new Repository(this, new Gson());
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
