package com.ks.fanat94.kschool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 01.03.2016.
 */
public class NewsDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView collapseimage;
    Toolbar mmToolbar;
    FeedItem p;
    String title;
    String description;
    TextView nd_description;
    TextView nd_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_activity);

        collapseimage = (ImageView) findViewById(R.id.collapseimage);
        collapseimage.setImageResource(R.drawable.collapseimage);

        nd_description = (TextView) findViewById(R.id.nd_description);
        nd_title = (TextView) findViewById(R.id.nd_title);

        Intent intent = getIntent();

        int fii = intent.getIntExtra("ii", 0);
        //Toast.makeText(this, "NewsDetaleActivity+get" + Integer.toString(fii), Toast.LENGTH_SHORT).show();

        ArrayList<FeedItem> content=intent.getParcelableArrayListExtra("content");
        p=content.get(fii);
        title=p.getTitle();
        description=p.getDescription();


        mmToolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(mmToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);


        nd_description.setText(description);
        nd_title.setText(title);

        setPalette();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setPalette() {
        Bitmap bitmap = ((BitmapDrawable) collapseimage.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

            }
        });

    }

}
