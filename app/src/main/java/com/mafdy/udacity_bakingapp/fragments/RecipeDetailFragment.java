package com.mafdy.udacity_bakingapp.fragments;

import android.content.Context;
import android.net.Uri;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.mafdy.udacity_bakingapp.R;
import com.mafdy.udacity_bakingapp.RecipeDetailActivity;
import com.mafdy.udacity_bakingapp.adapters.RecipeDetailAdapter;
import com.mafdy.udacity_bakingapp.objects.Ingredient;
import com.mafdy.udacity_bakingapp.objects.Recipe;
import com.mafdy.udacity_bakingapp.widget.UpdateBakingService;

import android.widget.TextView;
import android.widget.Toast;


import android.view.ViewGroup;

import static com.mafdy.udacity_bakingapp.MainActivity.SELECTED_RECIPES;


/**
 * Created by SBP on 6/13/2018.
 */


public class RecipeDetailFragment extends Fragment  {

    ArrayList<Recipe> recipe;
    String recipeName;

    public RecipeDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;
        TextView textView;

        recipe = new ArrayList<>();


        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);

        }
        else {
            recipe =getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }

        List<Ingredient> ingredients = recipe.get(0).getIngredients();
        recipeName=recipe.get(0).getName();

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        textView = (TextView)rootView.findViewById(R.id.recipe_detail_text);

        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();



        for(Ingredient a : ingredients)
        {
            textView.append("\u2022 "+ a.getIngredient()+" ");
            textView.append("( "+a.getQuantity().toString()+"  " + a.getMeasure() + " ) \n");


            recipeIngredientsForWidgets.add(a.getIngredient()+"\n"+
                    "Quantity: "+a.getQuantity().toString()+"\n"+
                    "Measure: "+a.getMeasure()+"\n");
        }



        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        RecipeDetailAdapter mRecipeDetailAdapter =new RecipeDetailAdapter((RecipeDetailActivity)getActivity());
        recyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.setMasterRecipeData(recipe,getContext());


       UpdateBakingService.startBakingService(getContext(),recipeIngredientsForWidgets);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_RECIPES, recipe);
        currentState.putString("Title",recipeName);
    }


}


