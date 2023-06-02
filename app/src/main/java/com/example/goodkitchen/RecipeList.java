package com.example.goodkitchen;

import java.util.ArrayList;

public class RecipeList {

    private ArrayList<Recipe> recipesList = new ArrayList<>();


    public RecipeList(){
    }

    public ArrayList<Recipe> getRecipes() {
        return recipesList;
    }

    public void addRecipe(String recipeName, int preparingTime,String ingridients, String instructions){

        Recipe newRecipe = new Recipe();
        newRecipe.setRecipeName(recipeName);
        newRecipe.setPreparationTime(preparingTime);
        newRecipe.setIngridents(ingridients);
        newRecipe.setInstructions(instructions);
        recipesList.add(newRecipe);
    }

    @Override
    public String toString() {
        return "ScoreList{" +
                ", recipes =" + recipesList +
                '}';
    }
}
