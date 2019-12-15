package com.example.ukutagamesv1;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ukutagamesv1.model.Platform;

import java.util.List;

public class PlatformViewModel extends AndroidViewModel {
    private PlatformRepository repository;
    private LiveData<List<Platform>> allPlatforms;

    public PlatformViewModel(@NonNull Application application) {
        super(application);
        repository = new PlatformRepository(application);
        allPlatforms = repository.getAllPlatforms();
    }

    public void insert(Platform platform)
    {
        repository.insert(platform);
    }

    public void update(Platform platform)
    {
        repository.update(platform);
    }

    public void delete(Platform platform)
    {
        repository.delete(platform);
    }

    public void deleteAll()
    {
        repository.deleteAll();
    }

    public LiveData<List<Platform>> getAllPlatforms() {
        return allPlatforms;
    }
}
