package com.example.sbnrinaveen.persistence;



import androidx.room.TypeConverter;

import com.example.sbnrinaveen.models.License;
import com.example.sbnrinaveen.models.Permission;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converters {

    @TypeConverter
    public static License fromString(String value){
        Type listType = new TypeToken<License>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromLicense(License license){
        Gson gson = new Gson();
        String json = gson.toJson(license);
        return json;
    }

    @TypeConverter
    public static Permission fromStringPermission(String value){
        Type listType = new TypeToken<Permission>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPermission(Permission permission){
        Gson gson = new Gson();
        String json = gson.toJson(permission);
        return json;
    }
}














