package com.example.goodkitchen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecipeList {
    private ArrayList<Recipe> recipesList = new ArrayList<>();

    public RecipeList() {
    }

    public ArrayList<Recipe> getRecipes() {
        return recipesList;
    }

    public void addRecipe(Recipe newRecipe) {
        recipesList.add(newRecipe);
    }

    public void sortByName() {
        Collections.sort(recipesList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2) {
                return recipe1.getRecipeName().compareToIgnoreCase(recipe2.getRecipeName());
            }
        });
    }

    public void sortByPreparationTime() {
        Collections.sort(recipesList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2) {
                return Integer.compare(recipe1.getPreparationTime(), recipe2.getPreparationTime());
            }
        });
    }

    public void setRecipesList(ArrayList<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    public int size() {
        return recipesList.size();
    }

    public Recipe get(int position) {
        return recipesList.get(position);
    }

    public boolean containsRecipe(Recipe recipe) {
        for (Recipe r : recipesList) {
            if (r.getId().equals(recipe.getId())) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        recipesList.clear();
    }


    @Override
    public String toString() {
        return "RecipeList{" +
                "recipes=" + recipesList +
                '}';
    }
}

