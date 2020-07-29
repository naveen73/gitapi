package com.example.sbnrinaveen.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sbnrinaveen.network.GitHubResponseItem;

@Database(entities = {GitHubResponseItem.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class GitDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "git_db";

    private static GitDatabase instance;

    public static GitDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    GitDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract GitItemsDao getGitItemsDao();
}
