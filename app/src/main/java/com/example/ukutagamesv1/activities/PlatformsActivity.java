package com.example.ukutagamesv1.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ukutagamesv1.PlatformViewModel;
import com.example.ukutagamesv1.R;
import com.example.ukutagamesv1.adapters.PlatformAdapter;
import com.example.ukutagamesv1.model.Platform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlatformsActivity extends AppCompatActivity {
    public static final int ADD_PLATFORM_REQUEST = 1;
    public static final int EDIT_PLATFORM_REQUEST = 2;
    private PlatformViewModel platformViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platforms);

        FloatingActionButton buttonAddPlatform = findViewById(R.id.button_add_platform);
        buttonAddPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlatformsActivity.this, AddEditPlatformActivity.class);
                startActivityForResult(intent, ADD_PLATFORM_REQUEST);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_platforms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PlatformAdapter adapter = new PlatformAdapter();
        recyclerView.setAdapter(adapter);

        platformViewModel = ViewModelProviders.of(PlatformsActivity.this).get(PlatformViewModel.class);
        platformViewModel.getAllPlatforms().observe(this, new Observer<List<Platform>>() {
            @Override
            public void onChanged(List<Platform> platforms) {
                adapter.submitList(platforms);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                platformViewModel.delete(adapter.getPlatformAt(viewHolder.getAdapterPosition()));
                Toast.makeText(PlatformsActivity.this, "Platform deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new PlatformAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Platform platform) {
                Intent intent = new Intent(PlatformsActivity.this, AddEditPlatformActivity.class);
                intent.putExtra(AddEditPlatformActivity.EXTRA_ID, platform.getId());
                intent.putExtra(AddEditPlatformActivity.EXTRA_TITLE, platform.getName());
                intent.putExtra(AddEditPlatformActivity.EXTRA_DEVELOPER, platform.getDeveloper());
                intent.putExtra(AddEditPlatformActivity.EXTRA_LAUNCH, platform.getLaunch());
                startActivityForResult(intent, EDIT_PLATFORM_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PLATFORM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditPlatformActivity.EXTRA_TITLE);
            String developer = data.getStringExtra(AddEditPlatformActivity.EXTRA_DEVELOPER);
            String launch = data.getStringExtra(AddEditPlatformActivity.EXTRA_LAUNCH);

            Platform platform = new Platform(title, developer, launch);
            platformViewModel.insert(platform);

            Toast.makeText(this, "Platform saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_PLATFORM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditPlatformActivity.EXTRA_ID, -1);

            if(id == -1){
                Toast.makeText(this, "Platform can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditPlatformActivity.EXTRA_TITLE);
            String developer = data.getStringExtra(AddEditPlatformActivity.EXTRA_DEVELOPER);
            String launch = data.getStringExtra(AddEditPlatformActivity.EXTRA_LAUNCH);

            Platform platform = new Platform(title,developer,launch);
            platform.setId(id);
            platformViewModel.update(platform);

            Toast.makeText(this, "Platform updated", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Platform not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.platform_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_platforms:
                platformViewModel.deleteAll();
                Toast.makeText(this, "All platforms deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

