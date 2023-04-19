package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents list of ingredients
public class IngredientList implements Writable {

    private ArrayList<Ingredient> ingredients;
    private EventLog eventLog;

    // EFFECTS: ingredients set to empty
    public IngredientList() {
        this.ingredients = new ArrayList<Ingredient>();
        eventLog = EventLog.getInstance();
    }

    // MODIFIES: this
    // EFFECTS: add an ingredient to ingredients
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    // MODIFIES: this
    // EFFECTS: remove items in ingredients with same name, returns true, if no items with matching name returns false
    public boolean removeIngredient(String name) {

        ArrayList<Ingredient> ingredientsToRemove = new ArrayList<Ingredient>();
        Boolean containsName = false;

        for (Ingredient ing : this.ingredients) {
            if (ing.getName() == name) {
                ingredientsToRemove.add(ing);
                containsName = true;
            }
        }
        this.ingredients.removeAll(ingredientsToRemove);
        return containsName;
    }

    // MODIFIES: this
    // EFFECTS: adds every ingredient in every recipe in the given list to ingredeints
    public void addIngredientsFromRecipesList(RecipeList recipes) {
        for (Recipe recipe: recipes.getRecipes()) {
            for (Ingredient ingredient: recipe.getIngredientList().getIngredients()) {
                this.ingredients.add(ingredient);
            }
        }
    }

    // REQUIRES: Items in ingredients with matching name should have the same units of measurements
    //           # of items in ingredients > 0
    // MODIFIES: this
    // EFFECTS: Replaces amount in every item of the list with the total amount for ingredients with matching names,
    //          removes duplicates
    public void aggregateIngredientList() {
        ArrayList<Ingredient> aggregatedIngredients = new ArrayList<Ingredient>();
        Boolean isContained = false;

        for (Ingredient ing : this.ingredients) {
            for (Ingredient ingAgg : aggregatedIngredients) {
                if (ing.getName() == ingAgg.getName()) {
                    ingAgg.setAmount(ing.getAmount() + ingAgg.getAmount());
                    isContained = true;
                }
            }

            if (isContained == false) {
                aggregatedIngredients.add(ing);
            }
        }
        this.ingredients = aggregatedIngredients;
    }

    // EFFECTS: returns JSON format representation of this recipe list
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ingredients",ingredientsToJson());
        return json;
    }

    // EFFECTS: returns ingredients in this IngredientList as a JSON array
    private JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Ingredient ingredient : this.ingredients) {
            jsonArray.put(ingredient.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: Returns ingredients
    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }


}
