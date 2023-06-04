package com.example.goodkitchen;

import java.util.ArrayList;

public class DataManager {

    public static RecipeList childrenRecipe = new RecipeList();
    public static RecipeList dessertRecipe = new RecipeList();
    public static RecipeList mainCourseRecipe = new RecipeList();
    public static RecipeList startersRecipe = new RecipeList();

    public DataManager() {
    }

    public static RecipeList getRecipeListByCategory(String categoryName) {
        switch (categoryName) {
            case "Children Meal":
                return childrenRecipe;
            case "Desserts":
                return dessertRecipe;
            case "Main Course":
                return mainCourseRecipe;
            case "Starters":
                return startersRecipe;
            default:
                return null;
        }
    }
}
