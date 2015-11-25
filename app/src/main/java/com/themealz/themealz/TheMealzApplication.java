package com.themealz.themealz;

import android.app.Application;

import java.util.HashMap;

public class TheMealzApplication extends Application {

    private HashMap<String, String> mealOptionsMap;

    public TheMealzApplication() {
        this.mealOptionsMap = new HashMap<String, String>();
    }

    public HashMap<String, String> getMealOptionsMap() {
        return this.mealOptionsMap;
    }

    public void setMealIdsOptionsList(HashMap<String, String> mealsOptionsList) {
        this.mealOptionsMap = mealsOptionsList;
    }

    public void addToMealOptionsMap(String mealsOptionId, String mealsOptionTitle) {
        this.mealOptionsMap.put(mealsOptionId, mealsOptionTitle);
    }

    public void removeFromMealOptionsMap(String parentID) {
        Object text = this.mealOptionsMap.remove(parentID);
    }

    public void clearMealOptionsMap() {
        this.mealOptionsMap.clear();
    }

    public String[] getIdsArray() {
        return this.mealOptionsMap.keySet().toArray(new String[this.mealOptionsMap.size()]);
    }

    public String[] getTitlesArray() {
        return this.mealOptionsMap.values().toArray(new String[this.mealOptionsMap.size()]);
    }
}