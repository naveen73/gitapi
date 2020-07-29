package com.example.sbnrinaveen.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.sbnrinaveen.network.GitHubResponseItem;
import com.example.sbnrinaveen.repositories.GitHubItemsRepository;
import com.example.sbnrinaveen.util.Resource;

import java.util.List;

public class GitListViewModel extends AndroidViewModel {

    public static final String QUERY_EXHAUSTED = "No more results.";

    private MediatorLiveData<Resource<List<GitHubResponseItem>>> gitHubResponseItems = new MediatorLiveData<>();
    private GitHubItemsRepository gitHubItemsRepository;

    // query extras
    private boolean isQueryExhausted;
    private int pageNumber;
    private int maxrows;
    private boolean cancelRequest;

    public GitListViewModel(@NonNull Application application) {
        super(application);
        gitHubItemsRepository = GitHubItemsRepository.getInstance(application);
    }

    public LiveData<Resource<List<GitHubResponseItem>>> getGitHubItems(){
        return gitHubResponseItems;
    }

    public int getPageNumber(){
        return pageNumber;
    }

    public void getGitHubItemsFromApi(int pageNumber, int maxrows){
        if(pageNumber == 0){
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
        this.maxrows = maxrows;
        isQueryExhausted = false;
        executeSearch();
    }


    public void searchNextPage(){
        if(!isQueryExhausted){
            pageNumber++;
            executeSearch();
        }
    }

    private void executeSearch(){
        final LiveData<Resource<List<GitHubResponseItem>>> repositorySource = gitHubItemsRepository.getGitHubItems(pageNumber, maxrows);
        gitHubResponseItems.addSource(repositorySource, new Observer<Resource<List<GitHubResponseItem>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<GitHubResponseItem>> listResource) {
                if(listResource != null){
                    if(listResource.status == Resource.Status.SUCCESS){
                        if(listResource.data != null){
                            if(listResource.data.size() == 0 ){
                                gitHubResponseItems.setValue(
                                        new Resource<List<GitHubResponseItem>>(
                                                Resource.Status.ERROR,
                                                listResource.data,
                                                QUERY_EXHAUSTED
                                        )
                                );
                                isQueryExhausted = true;
                            }
                        }
                        gitHubResponseItems.removeSource(repositorySource);
                    }
                    else if(listResource.status == Resource.Status.ERROR){
                        if(listResource.message.equals(QUERY_EXHAUSTED)){
                            isQueryExhausted = true;
                        }
                        gitHubResponseItems.removeSource(repositorySource);
                    }
                    gitHubResponseItems.setValue(listResource);
                }
                else{
                    gitHubResponseItems.removeSource(repositorySource);
                }
            }
        });
    }

}
