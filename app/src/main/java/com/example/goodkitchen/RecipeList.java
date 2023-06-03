package com.example.goodkitchen;

import java.util.ArrayList;

public class RecipeList {

    private ArrayList<Recipe> recipesList = new ArrayList<>();


    public RecipeList(){
    }

    public ArrayList<Recipe> getRecipes() {
        return recipesList;
    }

    public void addRecipe(Recipe newRecipe){

        recipesList.add(newRecipe);
    }

    @Override
    public String toString() {
        return "ScoreList{" +
                ", recipes =" + recipesList +
                '}';
    }
}
