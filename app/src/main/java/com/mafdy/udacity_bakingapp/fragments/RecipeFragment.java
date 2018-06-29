package com.mafdy.udacity_bakingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mafdy.udacity_bakingapp.MainActivity;
import com.mafdy.udacity_bakingapp.R;
import com.mafdy.udacity_bakingapp.adapters.RecipeAdapter;
import com.mafdy.udacity_bakingapp.espresso.MyIdlingResource;
import com.mafdy.udacity_bakingapp.objects.Recipe;
import com.mafdy.udacity_bakingapp.retrofit.InterfaceRecipe;
import com.mafdy.udacity_bakingapp.retrofit.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mafdy.udacity_bakingapp.MainActivity.ALL_RECIPES;

/**
 * Created by SBP on 6/13/2018.
 */

public class RecipeFragment extends Fragment {




    public RecipeFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;

        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        recyclerView=(RecyclerView)  rootView.findViewById(R.id.recycler_view);
        final RecipeAdapter recipesAdapter =new RecipeAdapter((MainActivity)getActivity());
        recyclerView.setAdapter(recipesAdapter);



        if (rootView.getTag()!=null && rootView.getTag().equals("phone-land")){
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),3);
            recyclerView.setLayoutManager(mLayoutManager);
        }
        else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }

        InterfaceRecipe interfaceRecipe = RetrofitBuilder.Retrieve();
        Call<ArrayList<Recipe>> recipe = interfaceRecipe.getRecipe();

        final MyIdlingResource idlingResource = (MyIdlingResource)((MainActivity)getActivity()).getIdlingResource();

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.d("status code: ", statusCode.toString());

                ArrayList<Recipe> recipes = response.body();

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

                recipesAdapter.setRecipeData(recipes,getContext());
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d("http fail: ", t.getMessage());
            }
        });

        return rootView;
    }




}
