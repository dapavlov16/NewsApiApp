package com.example.newsapiapp.features.news.activity;

import android.os.Bundle;

import com.example.newsapiapp.R;
import com.example.newsapiapp.core.BaseActivity;
import com.example.newsapiapp.features.Screens;

public class NewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        router.replaceScreen(new Screens.NewsListScreen());
    }
}
