package com.example.sbnrinaveen.network;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.sbnrinaveen.models.License;
import com.example.sbnrinaveen.models.Permission;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "gititems")
public class GitHubResponseItem {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose()
    private int id;

    @ColumnInfo(name = "open_issues_count")
    @SerializedName("open_issues_count")
    @Expose()
    private int open_issues_count;

    @ColumnInfo(name = "license")
    @SerializedName("license")
    @Expose()
    private License license;

    @ColumnInfo(name = "permissions")
    @SerializedName("permissions")
    @Expose()
    private Permission permission;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose()
    private String name;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose()
    private String description;


    public int getOpen_issues_count() {
        return open_issues_count;
    }

    public void setOpen_issues_count(int open_issues_count) {
        this.open_issues_count = open_issues_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
