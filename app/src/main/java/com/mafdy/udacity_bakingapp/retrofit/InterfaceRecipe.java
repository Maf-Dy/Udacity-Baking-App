package com.mafdy.udacity_bakingapp.retrofit;

import com.mafdy.udacity_bakingapp.objects.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by SBP on 6/13/2018.
 */

public interface InterfaceRecipe {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}