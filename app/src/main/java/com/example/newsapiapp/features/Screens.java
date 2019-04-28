package com.example.newsapiapp.features;

import androidx.fragment.app.Fragment;

import com.example.newsapiapp.features.details.DetailsFragment;
import com.example.newsapiapp.features.news.NewsListFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final String ARTICLE_POSITION = "ARTICLE_POSITION";

    public static final class NewsListScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return new NewsListFragment();
        }
    }

    public static final class DetailsListScreen extends SupportAppScreen {

        int position;

        public DetailsListScreen(int position) {
            this.position = position;
        }

        @Override
        public Fragment getFragment() {
            return DetailsFragment.newInstance(position);
        }
    }
}
