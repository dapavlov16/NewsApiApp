package com.example.newsapiapp.features.details;

import android.os.Bundle;

import com.example.newsapiapp.R;
import com.example.newsapiapp.core.BaseActivity;
import com.example.newsapiapp.features.Screens;

public class DetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        router.replaceScreen(new Screens.DetailsListScreen());
    }
}
