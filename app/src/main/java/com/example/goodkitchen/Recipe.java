package com.example.goodkitchen;

import java.io.Serializable;
import android.net.Uri;

public class Recipe implements Serializable {
    private String recipeName;
    private int preparationTime;
    private String ingredientsList;
    private String instructionList;
    private String imageUri;

    // Define a default image URI for recipes without a photo
    private static final String DEFAULT_IMAGE_URI = "android.resource://com.example.goodkitchen/" + R.drawable.defaultimageforrecipe;

    public Recipe() {
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String name) {
        this.recipeName = name;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int time) {
        this.preparationTime = time;
    }

    public String getImageUriString() {
        return imageUri;
    }

    public void setImageUriString(String imageUriString) {
        this.imageUri = imageUriString;
    }

    public String getIngredients() {
        return ingredientsList;
    }

    public void setIngredients(String ingredients) {
        this.ingredientsList = ingredients;
    }

    public String getInstructions() {
        return instructionList;
    }

    public void setInstructions(String instructions) {
        this.instructionList = instructions;
    }

    @Override
    public String toString() {
        return "Name: " + recipeName + "\n" +
                "Preparation time: " + preparationTime + "\n";
    }
}



