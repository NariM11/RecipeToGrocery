package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Recipe;
import model.Ingredient;


public class JsonTest {

    // EFFECTS: checks whether the given recipe matches given fields. for Ingredient checks the size of
    // ingredients array in the recipe
    protected void checkRecipe(String name, int ingredientNum, String directions, Recipe recipe) {
        assertEquals(name, recipe.getName());
        assertEquals(directions, recipe.getDirections());
        assertEquals(ingredientNum,recipe.getIngredientList().getIngredients().size());
    }

    // EFFECTS: checks whether the given ingredient matches given fields
    protected void checkIngredient(String name, int amount, String units, Ingredient ingredient) {
        assertEquals(name, ingredient.getName());
        assertEquals(amount, ingredient.getAmount());
        assertEquals(units, ingredient.getUnit());
    }
}
