package persistence;

import model.Ingredient;
import model.IngredientList;
import model.Recipe;
import model.RecipeList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// Methods in this class were modelled based on JasonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RecipeList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipeList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses recipe list from JSON object and returns it
    private RecipeList parseRecipeList(JSONObject jsonObject) {
        RecipeList recipes = new RecipeList();
        addRecipeList(recipes, jsonObject);
        return recipes;
    }

    // MODIFIES: rl
    // EFFECTS: parses recipes from JSON object and adds them to rl
    private void addRecipeList(RecipeList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipe(rl, nextRecipe);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses Recipe from JSON object and adds it to recipe list
    private void addRecipe(RecipeList rl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONObject jsonObjectIngredients = jsonObject.getJSONObject("ingredients");
        IngredientList ingredients = new IngredientList();
        addIngredientList(ingredients, jsonObjectIngredients);
        String directions = jsonObject.getString("directions");
        Recipe recipe = new Recipe(name, ingredients,directions);
        rl.addRecipe(recipe);
    }

    // MODIFIES: il
    // EFFECTS: parses ingredients from JSON object and adds them to il
    private void addIngredientList(IngredientList il, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            addIngredient(il, nextIngredient);
        }
    }

    // MODIFIES: il
    // EFFECTS: parses ingredient from JSON object and adds it to ingredient list
    private void addIngredient(IngredientList il, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int amount = jsonObject.getInt("amount");
        String units = jsonObject.getString("unit");
        Ingredient ingredient = new Ingredient(name, amount, units);
        il.addIngredient(ingredient);
    }

}
