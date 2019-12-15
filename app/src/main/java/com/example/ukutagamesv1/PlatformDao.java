package com.example.ukutagamesv1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ukutagamesv1.model.Platform;

import java.util.List;

@Dao
public interface PlatformDao {

    @Insert
    void insert(Platform platform);

    @Update
    void update(Platform platform);

    @Delete
    void delete(Platform platform);

    @Query("DELETE FROM platform_table")
    void deleteAllPlatforms();

    @Query("SELECT * FROM platform_table ORDER BY name ASC")
    LiveData<List<Platform>> getAllPlatforms();
}
