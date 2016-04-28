package com.themealz.themealz;

import android.app.Application;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class TheMealzApplication extends Application {

    private ArrayList<HashMap<String, String>> mealOptionsArrayList;
    private HashMap<String, ArrayList<HashMap<String, String>>> mealOptionFlavorsHashMap;
    private HashMap<String, String> selectedMeal;

    private String[] pluck(String key) {
        ArrayList<String> returnedValues = new ArrayList<String>();

        for (HashMap<String, String> mealOption : this.mealOptionsArrayList) {
            returnedValues.add(mealOption.get(key));
        }

        return returnedValues.toArray(new String[returnedValues.size()]);
    }

    public TheMealzApplication() {
        this.mealOptionsArrayList = new ArrayList<HashMap<String, String>>();
        this.mealOptionFlavorsHashMap = new HashMap<String, ArrayList<HashMap<String, String>>>();
    }

    public ArrayList<HashMap<String, String>> getMealOptionsArrayList() {
        return this.mealOptionsArrayList;
    }

    public ArrayList<String> getMealOptionsTitlesArrayList() {
        ArrayList<String> titles = new ArrayList();
        for (HashMap<String, String> mealOtion : this.mealOptionsArrayList) {
            titles.add(mealOtion.get("title") + "(" + TextUtils.join(", ", getMealOptionFlavorsTitlesArrayList(mealOtion.get("id"))) + ")");
        }

        return titles;
    }

    public ArrayList<String> getMealOptionFlavorsTitlesArrayList(String parentID) {
        ArrayList<String> titles = new ArrayList();
        for (HashMap<String, String> mealOtionFlavor : this.mealOptionFlavorsHashMap.get(parentID)) {
            titles.add(mealOtionFlavor.get("title"));
        }

        return titles;
    }

    public void setMealIdsOptionsList(ArrayList<HashMap<String, String>> mealsOptionsList) {
        this.mealOptionsArrayList = mealsOptionsList;
    }

    public void addToMealOptionsMap(final String mealsOptionId, final String mealsOptionTitle, final String mealsOptionImageURL) {
        this.mealOptionsArrayList.add(new HashMap<String, String>() {{
            put("id", mealsOptionId);
            put("title", mealsOptionTitle);
            put("imageURL", mealsOptionImageURL);
        }});
    }

    public void removeFromMealOptionsMap(String parentID) {
        HashMap<String, String> mealOptionToRemove = null;

        for (HashMap<String, String> mealOption : this.mealOptionsArrayList) {
            if (mealOption.get("id").equals(parentID)) {
                mealOptionToRemove = mealOption;
                break;
            }
        }

        if (mealOptionToRemove != null) {
            this.mealOptionsArrayList.remove(mealOptionToRemove);
        }
    }

    public void addMealOptionFlavor(String parentID, final String mealOptionFlavorId, final String mealOptionFlavorTitle) {
        if (this.mealOptionFlavorsHashMap.get(parentID) == null) {
            this.mealOptionFlavorsHashMap.put(parentID, new ArrayList<HashMap<String, String>>());
        }

        ArrayList<HashMap<String, String>> flavors = this.mealOptionFlavorsHashMap.get(parentID);
        for (HashMap<String, String> flavor : flavors) {
            if (flavor.get("id").equals(mealOptionFlavorId)) {
                return;
            }
        }
        flavors.add(new HashMap<String, String>() {{
            put("id", mealOptionFlavorId);
            put("title", mealOptionFlavorTitle);
        }});
    }

    public void removeMealOptionFlavor(String parentID, final String mealOptionFlavorId) {
        if (this.mealOptionFlavorsHashMap.get(parentID) == null) {
            this.mealOptionFlavorsHashMap.put(parentID, new ArrayList<HashMap<String, String>>());
        }

        ArrayList<HashMap<String, String>> flavors = this.mealOptionFlavorsHashMap.get(parentID);
        for (HashMap<String, String> flavor : flavors) {
            if (flavor.get("id").equals(mealOptionFlavorId)) {
                flavors.remove(flavor);
            }
        }
    }

    public void clearMealOptionsMap() {
        this.mealOptionsArrayList.clear();
    }

    public String[] getIdsArray() {
        return pluck("id");
    }

    public String[] getTitlesArray() {
        return pluck("title");
    }

    public String[] getImageURLsArray() {
        return pluck("imageURL");
    }

    public HashMap<String, String> getSelectedMeal() {
        return selectedMeal;
    }

    public void setSelectedMeal(HashMap<String, String> selectedMeal) {
        this.selectedMeal = selectedMeal;
    }
}