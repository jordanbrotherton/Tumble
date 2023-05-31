package com.jordanjbrotherton.tumble;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jordanjbrotherton.tumble.drawer.*;
import com.jordanjbrotherton.tumble.feed.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Allows the app drawer to init in the background.
        AppDrawerAdapter appAdapter;
        appAdapter = new AppDrawerAdapter(this);

        //App Drawer FAB
        FloatingActionButton appDrawerFAB = findViewById(R.id.appDrawerFAB);
        //FAB Long Click (Settings)
        appDrawerFAB.setOnLongClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Long Press is Placeholder!", Toast.LENGTH_SHORT).show();

            return true;
        });
        //FAB Click (App Drawer)
        appDrawerFAB.setOnClickListener(v -> {
            AppDrawerFragment appDrawer = new AppDrawerFragment(appAdapter);
            appDrawer.show(getSupportFragmentManager(), "appDrawer");
        });

        //Home Bottom AppBar
        BottomAppBar homeAppBar = findViewById(R.id.homeAppBar);

        //Home Bottom AppBar Menu Clicks
        homeAppBar.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.webSearch) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                startActivity(intent);
            } else if (id == R.id.settings) {
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
            return false;
        });

        //Makes the navbar transparent. (Android 11+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { getWindow().setDecorFitsSystemWindows(false); }
        else {
            //For Android 10. "Transparent" navbar.
            switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
                    this.getWindow().setNavigationBarColor(Color.parseColor("#202A30"));
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    this.getWindow().setNavigationBarColor(Color.parseColor("#E6EFF5"));
                    break;
            } }

        //Getting the status bar height for padding.
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        //Getting the navbar height for padding.
        int navigationBarHeight = 0;
        int resourceId2 = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId2 > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        //This is the feed's list. It contains the layout and settings for the widgets.
        final ArrayList<FeedWidget> feedList = new ArrayList<>();
        feedList.add(new FeedWidget("Tumble", "This is a test widget!", 1));
        feedList.add(new FeedWidget("Favorites", "favorite apps", 2));
        feedList.add(new FeedWidget("Tumble Picks", "most used apps go here.", 1));
        feedList.add(new FeedWidget("Media", "Template on Playing Music.", 3));
        feedList.add(new FeedWidget("News", "News Template", 1));

        //Initializing the feed.
        RecyclerView homeFeed = findViewById(R.id.homeFeed);
        homeFeed.setPadding(0,statusBarHeight + 10,0,navigationBarHeight + 200);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        FeedAdapter feedAdapter = new FeedAdapter(feedList);
        homeFeed.setLayoutManager(layoutManager);
        homeFeed.setAdapter(feedAdapter);
    }
}