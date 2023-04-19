package model;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



// Unit tests for the RecipeList class
public class RecipeListTest {

    private Recipe boiledEggs;
    private IngredientList boiledEggsIngredients;
    private ArrayList<Ingredient> boiledEggsIngredientsArray;
    private Ingredient egg = new Ingredient("egg",200,"g");
    private Ingredient salt = new Ingredient("salt",10,"g");
    private Ingredient water = new Ingredient("water",10,"ml");
    private String boiledEggsDirections =
            ("1. Bring water to boil in a pot \n" +
                    "2. Put eggs in a pot with boiling water and set a timer for 5 minutes \n" +
                    "3. Turn off heat and fill the pot with cold water for 1 minute \n" +
                    "4. Boiled eggs are ready to eat! \n");
    private Recipe cerealWithMilk;
    private IngredientList cerealWithMilkIngredients;
    private ArrayList<Ingredient> cerealWithMilkIngredientsArray;
    private Ingredient cereal = new Ingredient("cereal",100,"g");
    private Ingredient milk = new Ingredient("milk",300,"ml");;
    private String cerealWithMilkDirections = ("1. Pour milk in a bowl \n" +  "2. Add cereal \n" + "3. All done! \n");
    private Recipe nullRecipe;

    private RecipeList recipeList;
    private ArrayList<Recipe> testArray;

    @BeforeEach
    public void runBefore() {
        boiledEggsIngredients = new IngredientList();
        boiledEggsIngredients.addIngredient(egg);
        boiledEggsIngredients.addIngredient(salt);
        boiledEggsIngredients.addIngredient(water);
        cerealWithMilkIngredients = new IngredientList();
        cerealWithMilkIngredients.addIngredient(cereal);
        cerealWithMilkIngredients.addIngredient(milk);
        boiledEggs = new Recipe("Boiled eggs",boiledEggsIngredients, boiledEggsDirections);
        cerealWithMilk = new Recipe ("Cereal with milk",cerealWithMilkIngredients,cerealWithMilkDirections);
        nullRecipe = new Recipe("Null",new IngredientList(),"No directions");
        testArray = new ArrayList<Recipe>();
        recipeList = new RecipeList();
    }

    @Test
    public void testRecipeList() {
        assertTrue(recipeList.getRecipes().isEmpty());
    }

    @Test
    public void testAddRecipe() {
        testArray.add(boiledEggs);
        recipeList.addRecipe(boiledEggs);
        assertEquals(testArray,recipeList.getRecipes());
        testArray.add(cerealWithMilk);
        recipeList.addRecipe(cerealWithMilk);
        assertEquals(testArray,recipeList.getRecipes());
        testArray.add(nullRecipe);
        recipeList.addRecipe(nullRecipe);
        assertEquals(testArray,recipeList.getRecipes());

    }

    @Test
    public void testRemoveRecipe() {
        recipeList.addRecipe(boiledEggs);
        assertTrue(recipeList.removeRecipe("Boiled eggs"));
        assertEquals(testArray,recipeList.getRecipes());

        recipeList.addRecipe(boiledEggs);
        recipeList.addRecipe(cerealWithMilk);
        recipeList.addRecipe(nullRecipe);

        testArray.add(boiledEggs);
        testArray.add(cerealWithMilk);
        testArray.add(nullRecipe);

        assertFalse(recipeList.removeRecipe("boiled Eggs"));
        assertFalse(recipeList.removeRecipe("fried eggs"));

        assertTrue(recipeList.removeRecipe("Cereal with milk"));

        testArray.remove(cerealWithMilk);
        assertEquals(testArray,recipeList.getRecipes());



    }

}

