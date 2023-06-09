package com.example.goodkitchen;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class Recipe implements Serializable {
    private String recipeName;
    private int preparationTime;
    private String ingredientsList;
    private String instructionList;
    private String imageUri;

    private String id;

    private String UID;

    // Define a default image URI for recipes without a photo
    private static final String DEFAULT_IMAGE_URI = "android.resource://com.example.goodkitchen/" + R.drawable.defaultimageforrecipe;

    public Recipe() {
        this.id = String.valueOf(Instant.now().toEpochMilli());
        this.UID = FirebaseAuth.getInstance().getUid();
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", preparationTime=" + preparationTime +
                ", ingredientsList='" + ingredientsList + '\'' +
                ", instructionList='" + instructionList + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", id='" + id + '\'' +
                ", UID='" + UID + '\'' +
                '}';
    }
}


