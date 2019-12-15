package com.example.ukutagamesv1;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ukutagamesv1.model.Platform;

import java.util.List;

public class PlatformRepository {
    private PlatformDao platformDao;
    private LiveData<List<Platform>> allPlatforms;

    public PlatformRepository(Application application)
    {
        PlatformDatabase database = PlatformDatabase.getInstance(application);
        platformDao = database.platformDao();
        allPlatforms = platformDao.getAllPlatforms();
    }

    public void insert(Platform platform)
    {
        new InsertPlatformAsynchTask(platformDao).execute(platform);
    }

    public void update(Platform platform)
    {
        new UpdatePlatformAsynchTask(platformDao).execute(platform);
    }

    public void delete(Platform platform)
    {
        new DeletePlatformAsynchTask(platformDao).execute(platform);
    }

    public void deleteAll()
    {
        new DeleteAllPlatformsAsynchTask(platformDao).execute();
    }

    public LiveData<List<Platform>> getAllPlatforms() {
        return allPlatforms;
    }

    private static class InsertPlatformAsynchTask extends AsyncTask<Platform,Void,Void> {
        private PlatformDao platformDao;

        private InsertPlatformAsynchTask(PlatformDao platformDao){
            this.platformDao = platformDao;
        }

        @Override
        protected Void doInBackground(Platform... platforms) {
            platformDao.insert(platforms[0]);
            return null;
        }
    }

    private static class UpdatePlatformAsynchTask extends AsyncTask<Platform,Void,Void> {
        private PlatformDao platformDao;

        private UpdatePlatformAsynchTask(PlatformDao platformDao){
            this.platformDao = platformDao;
        }

        @Override
        protected Void doInBackground(Platform... platforms) {
            platformDao.update(platforms[0]);
            return null;
        }
    }

    private static class DeletePlatformAsynchTask extends AsyncTask<Platform,Void,Void> {
        private PlatformDao platformDao;

        private DeletePlatformAsynchTask(PlatformDao platformDao){
            this.platformDao = platformDao;
        }

        @Override
        protected Void doInBackground(Platform... platforms) {
            platformDao.delete(platforms[0]);
            return null;
        }
    }

    private static class DeleteAllPlatformsAsynchTask extends AsyncTask<Void,Void,Void> {
        private PlatformDao platformDao;

        private DeleteAllPlatformsAsynchTask(PlatformDao platformDao){
            this.platformDao = platformDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            platformDao.deleteAllPlatforms();
            return null;
        }
    }
}
