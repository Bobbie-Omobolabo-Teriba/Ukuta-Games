package com.example.ukutagamesv1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ukutagamesv1.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class GameActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getExtras().getInt("game_id");
        String name = getIntent().getExtras().getString("game_name");
        String genre = getIntent().getExtras().getString("game_genre");
        String platforms = getIntent().getExtras().getString("game_platforms");
        String rating = getIntent().getExtras().getString("game_rating");
        String release = getIntent().getExtras().getString("game_release");
        String image_url = getIntent().getExtras().getString("game_img");

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_name = findViewById(R.id.aa_game_name);
        TextView tv_platforms = findViewById(R.id.aa_platforms);
        TextView tv_rating = findViewById(R.id.aa_rating);
        TextView tv_release = findViewById(R.id.aa_release);
        //TextView tv_genre = findViewById(R.id.aa_genre);
        ImageView img = findViewById(R.id.aa_thumbnail);

        tv_name.setText(name);
        tv_platforms.setText("Platforms the game is availble on - " + platforms);
        //tv_genre.setText(genre);
        tv_rating.setText(rating);
        tv_release.setText("Date of Release - " + release);

        collapsingToolbarLayout.setTitle(name);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        Glide.with(this).load(image_url).apply(requestOptions).into(img);

    }

}
