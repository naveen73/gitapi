package com.example.sbnrinaveen.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sbnrinaveen.network.GitHubResponseItem;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface GitItemsDao {

    @Insert(onConflict = REPLACE)
    void insertGitHubResponseItems(GitHubResponseItem... gitresponseItem);


    @Query("SELECT * FROM gititems LIMIT (:pageNumber * :maxrows)")
    LiveData<List<GitHubResponseItem>> getGitItems(int pageNumber,int maxrows);
}
