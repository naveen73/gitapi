package com.example.sbnrinaveen.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitHubResponse {

    public List<GitHubResponseItem> responseItems;

    public int getCount() {
        return responseItems.size();
    }

}
