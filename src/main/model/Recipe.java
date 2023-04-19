package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a recipe having a name (String), list of ingredients (IngredientList) and cooking directions (String)
public class Recipe implements Writable {
    private String name;
    private IngredientList ingredients;
    private String directions;

    // REQUIRES: ingredients must be non-empty, name must not be empty
    // EFFECTS: ingredient name is set to name, amount needed is set to amount, units of measurement set to unit
    public Recipe(String name, IngredientList ingredients, String directions) {
        this.name = name;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    // EFFECTS: returns JSON format representation of this recipe
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",this.name);
        json.put("ingredients",this.ingredients.toJson());
        json.put("directions",this.directions);
        return json;
    }


    // GETTERS
    public String getName() {
        return this.name;
    }

    public String getDirections() {
        return this.directions;
    }

    public IngredientList getIngredientList() {
        return this.ingredients;
    }

}
