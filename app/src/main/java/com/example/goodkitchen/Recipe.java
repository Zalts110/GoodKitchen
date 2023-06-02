package com.example.goodkitchen;

import java.util.ArrayList;

public class Recipe {

    private String recipeName;
    private int preparationTime;
//    private ArrayList<String> ingrideitnsList = new ArrayList<>();
//    private ArrayList<String> instructionList = new ArrayList<>();

    private String ingrideitnsList;
    private String instructionList;


    public Recipe(){
//        ingrideitnsList = new ArrayList<String>();
//        instructionList = new ArrayList<String>();
    }

    public String getRecipeName()
{
    return this.recipeName;
}

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setRecipeName(String name){
        this.recipeName = name;
    }

    public void setPreparationTime(int time){
        this.preparationTime = time;
    }

    public String setIngridents(String ingridients) {
        this.ingrideitnsList = ingridients;
        return this.ingrideitnsList;
    }
    public String setInstructions(String instructions) {
        this.instructionList = instructions;
        return this.instructionList;
    }


    public String toString() {
        return "Name" +
                recipeName  + '\'' +
                "Preparation time" +
                preparationTime + '\''
                ;
    }
}
