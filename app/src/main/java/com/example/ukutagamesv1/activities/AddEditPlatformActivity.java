package com.example.ukutagamesv1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ukutagamesv1.R;

public class AddEditPlatformActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.ukutagamesv1.activities.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.ukutagamesv1.activities.EXTRA_TITLE";
    public static final String EXTRA_DEVELOPER =
            "com.example.ukutagamesv1.activities.EXTRA_DEVELOPER";
    public static final String EXTRA_LAUNCH =
            "com.example.ukutagamesv1.activities.EXTRA_LAUNCH";
    private EditText editTextTitle;
    private EditText editTextDeveloper;
    private EditText editTextLaunch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplatform);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDeveloper = findViewById(R.id.edit_text_developer);
        editTextLaunch = findViewById(R.id.edit_text_launch);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Platform");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDeveloper.setText(intent.getStringExtra(EXTRA_DEVELOPER));
            editTextLaunch.setText(intent.getStringExtra(EXTRA_LAUNCH));
        }
        else {
            setTitle("Add Platform");
        }
    }

    private void savePlatform(){
        String title = editTextTitle.getText().toString();
        String developer = editTextDeveloper.getText().toString();
        String launch = editTextLaunch.getText().toString();

        if(title.trim().isEmpty() || developer.trim().isEmpty() || launch.trim().isEmpty()){
            Toast.makeText(this, "Insert Platform Name, Developer and Launch year", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DEVELOPER, developer);
        data.putExtra(EXTRA_LAUNCH, launch);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_platform_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_platform:
                savePlatform();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
