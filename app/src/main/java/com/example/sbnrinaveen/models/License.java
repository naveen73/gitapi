package com.example.sbnrinaveen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class License {

    @SerializedName("key")
    @Expose()
    private String key;

    @SerializedName("name")
    @Expose()
    private String name;

    @SerializedName("spdx_id")
    @Expose()
    private String spdx_id;

    @SerializedName("url")
    @Expose()
    private String url;

    @SerializedName("node_id")
    @Expose()
    private String node_id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpdx_id() {
        return spdx_id;
    }

    public void setSpdx_id(String spdx_id) {
        this.spdx_id = spdx_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
}
