package com.example.sbnrinaveen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sbnrinaveen.adapter.GitRecyclerAdapter;
import com.example.sbnrinaveen.network.ApiResponse;
import com.example.sbnrinaveen.network.GitHubResponseItem;
import com.example.sbnrinaveen.network.ServiceGenerator;
import com.example.sbnrinaveen.util.Resource;
import com.example.sbnrinaveen.viewmodel.GitListViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.sbnrinaveen.viewmodel.GitListViewModel.QUERY_EXHAUSTED;

public class MainActivity extends AppCompatActivity {
    public ProgressBar mProgressBar;
    public Button mButton;
    private GitListViewModel mGitListViewModel;
    private RecyclerView mRecyclerView;
    private GitRecyclerAdapter mAdapter;

    public static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recyclerview);
        final LifecycleOwner activity = this;

        mGitListViewModel = ViewModelProviders.of(this).get(GitListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        getGitItems();
    }



    private void subscribeObservers(){
        mGitListViewModel.getGitHubItems().observe(this, new Observer<Resource<List<GitHubResponseItem>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<GitHubResponseItem>> listResource) {
                if(listResource != null){
                    Log.d(TAG, "onChanged: status: " + listResource.status);
                    if(listResource.data != null){
                        switch (listResource.status){
                            case LOADING:{
                                if(mGitListViewModel.getPageNumber() > 1){
                                    mAdapter.displayLoading();
                                    showProgressBar(false);
                                }
                                else{
                                    showProgressBar(true);
                                }
                                break;
                            }

                            case ERROR:{
                                Toast.makeText(MainActivity.this, listResource.message, Toast.LENGTH_LONG).show();
                                showProgressBar(false);
                                mAdapter.hideLoading();
                                mAdapter.setItems(listResource.data);
                                if(listResource.message.equals(QUERY_EXHAUSTED)){
                                    Log.d(TAG, "onChanged: exhausted: ");
                                }
                                break;
                            }

                            case SUCCESS:{
                                showProgressBar(false);
                                mAdapter.hideLoading();
                                mAdapter.setItems(listResource.data);
                                break;
                            }
                        }
                    }
                }
            }
        });

    }

    private void getGitItems(){
        mGitListViewModel.getGitHubItemsFromApi(1, 10);
    }

    private void initRecyclerView(){
        mAdapter = new GitRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!mRecyclerView.canScrollVertically(1)){
                    mGitListViewModel.searchNextPage();
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}