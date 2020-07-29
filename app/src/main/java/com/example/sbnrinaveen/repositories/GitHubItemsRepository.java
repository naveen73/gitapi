package com.example.sbnrinaveen.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.sbnrinaveen.AppExecutors;
import com.example.sbnrinaveen.network.ApiResponse;
import com.example.sbnrinaveen.network.GitHubResponseItem;
import com.example.sbnrinaveen.network.ServiceGenerator;
import com.example.sbnrinaveen.persistence.GitDatabase;
import com.example.sbnrinaveen.persistence.GitItemsDao;
import com.example.sbnrinaveen.util.NetworkBoundResource;
import com.example.sbnrinaveen.util.Resource;

import java.util.ArrayList;
import java.util.List;

public class GitHubItemsRepository {

    private static GitHubItemsRepository instance;
    private GitItemsDao recipeDao;

    public static GitHubItemsRepository getInstance(Context context){
        if(instance == null){
            instance = new GitHubItemsRepository(context);
        }
        return instance;
    }


    private GitHubItemsRepository(Context context) {
        recipeDao = GitDatabase.getInstance(context).getGitItemsDao();
    }


    public LiveData<Resource<List<GitHubResponseItem>>> getGitHubItems(final int page, final int maxrows){
        return new NetworkBoundResource<List<GitHubResponseItem>, ArrayList<GitHubResponseItem>>(AppExecutors.getInstance()){

            @Override
            protected void saveCallResult(@NonNull ArrayList<GitHubResponseItem> items) {
                GitHubResponseItem[] recipes = new GitHubResponseItem[items.size()];
                recipeDao.insertGitHubResponseItems((GitHubResponseItem[]) (items.toArray(recipes)));
            }

            @Override
            protected boolean shouldFetch(@Nullable List<GitHubResponseItem> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<GitHubResponseItem>> loadFromDb() {
                return recipeDao.getGitItems(page, maxrows);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ArrayList<GitHubResponseItem>>> createCall() {
                return ServiceGenerator.getGitApi()
                        .getGitHubItems(String.valueOf(page),String.valueOf(maxrows));
            }
        }.getAsLiveData();
    }
}
