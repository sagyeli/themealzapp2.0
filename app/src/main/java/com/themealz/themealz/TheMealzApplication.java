package com.themealz.themealz;

import android.app.Application;

import java.util.ArrayList;

public class TheMealzApplication extends Application {

    private ArrayList<String> mealOptionIdsList;

    public TheMealzApplication() {
        this.mealOptionIdsList = new ArrayList<String>();
    }

    public ArrayList<String> getMealOptionIdsList() {
        return this.mealOptionIdsList;
    }

    public void setMealIdsOptionsList(ArrayList<String> mealsOptionsList) {
        this.mealOptionIdsList = mealsOptionsList;
    }

    public void addToMealOptionIdsList(String mealsOptionIds) {
        this.mealOptionIdsList.add(mealsOptionIds);
    }

    public void clearMealOptionIdsList() {
        this.mealOptionIdsList.clear();
    }
}