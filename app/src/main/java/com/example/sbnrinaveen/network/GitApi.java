package com.example.sbnrinaveen.network;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitApi {

    // GET
    @GET("orgs/octokit/repos")
    LiveData<ApiResponse<ArrayList<GitHubResponseItem>>> getGitHubItems(@Query("page") String page,
                                                                        @Query("per_page") String per_page);
}
