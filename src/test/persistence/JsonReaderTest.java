package persistence;

import model.Ingredient;
import model.IngredientList;
import model.Recipe;
import model.RecipeList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends  JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RecipeList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyRecipeList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRecipeList.json");
        try {
            RecipeList rl = reader.read();
            assertEquals(0, rl.getRecipes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralRecipeListEmptyIngredientList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRecipeListEmptyIngredientList.json");
        try {
            RecipeList rl = reader.read();
            ArrayList<Recipe> recipes = rl.getRecipes();
            assertEquals(3, recipes.size());
            checkRecipe("boiled eggs",0, "boil eggs for 5 minutes",recipes.get(0));
            checkRecipe("cereal with milk",0,"add milk to cereal",recipes.get(1));
            checkRecipe("grilled cheese sandwich",0,
                    "add cheese to bread and grill for 1-2 minutes", recipes.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralCheckRecipeList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRecipeList.json");
        try {
            RecipeList rl = reader.read();
            ArrayList<Recipe> recipes = rl.getRecipes();
            assertEquals(3, recipes.size());
            checkRecipe("boiled eggs",3,"boil eggs for 5 minutes",recipes.get(0));
            checkRecipe("cereal with milk",2,"add milk to cereal",recipes.get(1));
            checkRecipe("grilled cheese sandwich",2,"add cheese to bread and grill for 1-2 minutes",
                    recipes.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralCheckIngredientList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRecipeList.json");
        try {
            RecipeList rl = reader.read();
            ArrayList<Recipe> recipes = rl.getRecipes();
            assertEquals(3, recipes.size());
            ArrayList<Ingredient> eggsIngredients = recipes.get(0).getIngredientList().getIngredients();
            ArrayList<Ingredient> cerealIngredients = recipes.get(1).getIngredientList().getIngredients();
            ArrayList<Ingredient> cheeseIngredients = recipes.get(2).getIngredientList().getIngredients();
            checkIngredient("eggs",200,"mg",eggsIngredients.get(0));
            checkIngredient("water",300,"ml",eggsIngredients.get(1));
            checkIngredient("salt",10,"mg",eggsIngredients.get(2));
            checkIngredient("cereal",300,"mg",cerealIngredients.get(0));
            checkIngredient("milk",200,"ml",cerealIngredients.get(1));
            checkIngredient("bread",200,"mg",cheeseIngredients.get(0));
            checkIngredient("cheese",100,"mg",cheeseIngredients.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
