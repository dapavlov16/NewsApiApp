package com.example.newsapiapp.features;

import androidx.fragment.app.Fragment;

import com.example.newsapiapp.features.news.NewsListFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final class NewsListScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return new NewsListFragment();
        }
    }
}
