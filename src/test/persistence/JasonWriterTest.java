package persistence;

import model.Ingredient;
import model.IngredientList;
import model.Recipe;
import model.RecipeList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JasonWriterTest extends JsonTest {

    private RecipeList rl;
    private RecipeList rl1;
    private Recipe boiledEggs;
    private Recipe cerealMilk;
    private Recipe grilledCheese;
    private ArrayList<Ingredient> eggsIngredients;
    private ArrayList<Ingredient> eggsCereal;
    private ArrayList<Ingredient> eggsCheese;
    private Ingredient egg;
    private Ingredient water;
    private Ingredient salt;
    private Ingredient cereal;
    private Ingredient milk;
    private Ingredient bread;
    private Ingredient cheese;



    @BeforeEach
    public void runBefore(){
        rl =  new RecipeList();
        rl1 =  new RecipeList();
        boiledEggs = new Recipe("boiled eggs",new IngredientList(),"boil eggs for 5 minutes");
        cerealMilk = new Recipe("cereal with milk",new IngredientList(),"add milk to cereal");
        grilledCheese = new Recipe("grilled cheese sandwich",new IngredientList(),
                "add cheese to bread and grill for 1-2 minutes");
        egg = new Ingredient("eggs",200,"mg");
        water = new Ingredient("water",300,"ml");
        salt = new Ingredient("salt",10,"mg");
        cereal = new Ingredient("cereal",300,"mg");
        milk = new Ingredient("milk",200,"ml");
        bread = new Ingredient("bread",200,"mg");
        cheese = new Ingredient("cheese",100,"mg");
        rl1.addRecipe(boiledEggs);
        rl1.addRecipe(cerealMilk);
        rl1.addRecipe(grilledCheese);
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/myInv\0alid:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyRecipeList() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecipeList.json");
            writer.open();
            writer.write(rl);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyRecipeList.json");
            rl = reader.read();
            assertEquals(0, rl.getRecipes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralRecipeListEmptyIngredientList() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecipeListEmptyIngredientList.json");
            rl.addRecipe(boiledEggs);
            rl.addRecipe(cerealMilk);
            rl.addRecipe(grilledCheese);
            writer.open();
            writer.write(rl);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralRecipeListEmptyIngredientList.json");
            rl = reader.read();
            assertEquals(3, rl.getRecipes().size());
            checkRecipe("boiled eggs",0,"boil eggs for 5 minutes",rl.getRecipes().get(0));
            checkRecipe("cereal with milk",0,"add milk to cereal",rl.getRecipes().get(1));
            checkRecipe("grilled cheese sandwich",0,
                    "add cheese to bread and grill for 1-2 minutes", rl.getRecipes().get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }




    // MODIFIES: this
    // EFFECTS: initializes recipe lists for testing

    public void runBeforeTestingGeneralRecipeList() {
        boiledEggs.getIngredientList().getIngredients().add(egg);
        boiledEggs.getIngredientList().getIngredients().add(water);
        boiledEggs.getIngredientList().getIngredients().add(salt);
        cerealMilk.getIngredientList().getIngredients().add(cereal);
        cerealMilk.getIngredientList().getIngredients().add(milk);
        grilledCheese.getIngredientList().getIngredients().add(bread);
        grilledCheese.getIngredientList().getIngredients().add(cheese);
    }

    @Test
    public void testReaderGeneralCheckRecipeList() {
        try {
            runBeforeTestingGeneralRecipeList();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecipeList.json");
            writer.open();
            writer.write(rl1);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralRecipeList.json");
            rl1 = reader.read();
            assertEquals(3, rl1.getRecipes().size());
            checkRecipe("boiled eggs",3,"boil eggs for 5 minutes",rl1.getRecipes().get(0));
            checkRecipe("cereal with milk",2,"add milk to cereal",rl1.getRecipes().get(1));
            checkRecipe("grilled cheese sandwich",2,
                    "add cheese to bread and grill for 1-2 minutes", rl1.getRecipes().get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testReaderGeneralCheckIngredientList() {
        try {
            runBeforeTestingGeneralRecipeList();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecipeListEmptyIngredientList.json");
            writer.open();
            writer.write(rl1);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralRecipeListEmptyIngredientList.json");
            rl1 = reader.read();
            ArrayList<Recipe> recipes = rl1.getRecipes();
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
            fail("Exception should not have been thrown");
        }
    }
}
