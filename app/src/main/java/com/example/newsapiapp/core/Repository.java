package com.example.newsapiapp.core;

import android.content.Context;
import android.util.Log;

import com.example.newsapiapp.R;
import com.example.newsapiapp.model.Article;
import com.example.newsapiapp.model.Response;
import com.example.newsapiapp.network.NewsApi;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class Repository {

    private Context context;
    private Gson gson;
    private NewsApi api;
    private List<Article> articles;

    private BehaviorSubject<List<Article>> repositorySubject = BehaviorSubject.create();

    public Repository(final Context context, final Gson gson, final NewsApi api) {
        this.context = context;
        this.gson = gson;
        this.api = api;
    }

    public Observable<List<Article>> getRepositoryObservable() {
        return repositorySubject.hide();
    }

    public Article getArticle(int i) {
        return articles.get(i);
    }

    private String readJsonFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] bufferByte = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bufferByte)) != -1) {
                outputStream.write(bufferByte, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    public void executeGetRequest() {
        Log.e("WOW", "executeGetRequest: ");
        if (articles == null) {
            Map<String, String> map = new HashMap<>();
            map.put("country", "us");
            map.put("apiKey", context.getString(R.string.api_key));
            api.topHeadlines(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Response>() {

                        @Override
                        public void onSuccess(Response response) {
                            Log.e("WOW", "onSuccess: " + response.getStatus());
                            articles = response.getArticles();
                            repositorySubject.onNext(articles);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }
}
