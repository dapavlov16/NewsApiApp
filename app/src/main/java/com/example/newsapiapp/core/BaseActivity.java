package com.example.newsapiapp.core;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapiapp.R;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public abstract class BaseActivity extends AppCompatActivity {

    protected Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        router = NewsApiApplication.INSTANCE.getRouter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NewsApiApplication.INSTANCE.getNavigatorHolder().setNavigator(new SupportAppNavigator(this, R.id.container));
    }

    @Override
    protected void onPause() {
        super.onPause();
        NewsApiApplication.INSTANCE.getNavigatorHolder().removeNavigator();
    }
}
