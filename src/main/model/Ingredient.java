package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an ingredient with a name (String), amount needed for a recipe (String), units of measurement (String)
public class Ingredient implements Writable {

    private String name;
    private int amount;
    private String unit;
    private EventLog eventLog;

     // REQUIRES: amount > 0; for simplicity all units should be entered as either milliliters (ml) or grams (g)
     //           name should be lowercase and not empty
     // EFFECTS: ingredient name is set to name, amount needed is set to amount, units of measurement set to unit
    public Ingredient(String name, int amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.eventLog = EventLog.getInstance();
    }

    // REQUIRES: newAmount > 0
    // EFFECTS: returns name of ingredient
    public void setAmount(int newAmount) {
        this.amount = newAmount;
    }

    // GETTERS

    // EFFECTS: returns name of ingredient
    public String getName() {
        return this.name;
    }


    // EFFECTS: returns amount needed of the ingredient of the recipe
    public int getAmount() {
        return this.amount;
    }

    // EFFECTS: returns measurement units of the ingredient of the recipe
    public String getUnit() {
        return this.unit;
    }

    // EFFECTS: returns JSON format representation of this recipe list
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",this.name);
        json.put("amount",this.amount);
        json.put("unit",this.unit);
        return json;
    }

    // EFFECTS: returns combined string of ingredient name, amount and units of measurement
    // MODIFIES: this
    public String getIngredientString() {
        eventLog.logEvent(new Event("Retrieving " + name + " ingredient info for shopping list"));
        return getName() + ": " + getAmount() + " " + getUnit();
    }

}
