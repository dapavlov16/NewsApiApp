package com.example.newsapiapp;

import android.app.Application;

import com.google.gson.Gson;

public class NewsApiApplication extends Application {

    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        initRepository();
    }

    private void initRepository() {
        repository = new Repository(this, new Gson());
    }

    public Repository getRepository() {
        return repository;
    }
}
