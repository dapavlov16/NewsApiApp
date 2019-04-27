package com.example.newsapiapp;

import android.content.Context;

import com.example.newsapiapp.model.Article;
import com.example.newsapiapp.model.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Repository {

    private Context context;
    private Gson gson;
    private List<Article> articles;

    public Repository(final Context context, final Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    public List<Article> getArticles() {
        Response response = gson.fromJson(readJsonFile(context.getResources().openRawResource(R.raw.response)), new TypeToken<Response>() {}.getType());
        return response.getArticles();
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
}
