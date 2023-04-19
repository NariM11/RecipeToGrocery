package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of recipes
public class RecipeList implements Writable {

    private ArrayList<Recipe> recipes;
    private EventLog eventLog;


    // EFFECTS: recipes set to empty, instantiates eventLog
    public RecipeList() {
        eventLog = EventLog.getInstance();
        this.recipes = new ArrayList<Recipe>();
    }

    // REQUIRES: no two items in recipes should have the same name
    // MODIFIES: this
    // EFFECTS: add a recipe to recipes
    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
        eventLog.logEvent(new Event(recipe.getName() + " recipe has been added to the recipe list"));

    }

    // MODIFIES: this
    // EFFECTS: remove recipe in recipes with same name, returns true, if no items with matching name returns false
    public boolean removeRecipe(String name) {
        Recipe recipeToRemove = null;
        Boolean containsName = false;
        for (Recipe recipe : this.recipes) {
            if (recipe.getName() == name) {
                recipeToRemove = recipe;
                containsName = true;
            }
        }

        if (containsName) {
            this.recipes.remove(recipeToRemove);
        }
        return containsName;
        
    }

    // EFFECTS: returns JSON format representation of this recipe list
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("recipes",recipesToJson());
        return json;
    }

    // EFFECTS: returns recipes in this RecipeList as a JSON array
    private JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Recipe recipe : this.recipes) {
            jsonArray.put(recipe.toJson());
        }
        return jsonArray;
    }


    // EFFECTS: Returns recipes
    public ArrayList<Recipe> getRecipes() {
        return this.recipes;
    }

}
