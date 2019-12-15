package com.example.ukutagamesv1;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ukutagamesv1.model.Platform;

@Database(entities = {Platform.class}, version = 2)
public abstract class PlatformDatabase extends RoomDatabase {

    private static PlatformDatabase instance;

    public abstract PlatformDao platformDao();

    public static synchronized PlatformDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PlatformDatabase.class, "platform_database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynchTack(instance).execute();
        }
    };

    private static class PopulateDbAsynchTack extends AsyncTask<Void, Void, Void>{
        private PlatformDao platformDao;

        private PopulateDbAsynchTack(PlatformDatabase db){
            platformDao = db.platformDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            platformDao.insert(new Platform("PlayStation 4", "Sony Entertainment","2013"));
            platformDao.insert(new Platform("XBox One", "Microsoft","2013"));
            platformDao.insert(new Platform("XBox 360", "Microsoft","2005"));
            platformDao.insert(new Platform("Windows", "Microsoft","1985"));
            return null;
        }
    }
}


