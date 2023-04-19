package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


// Unit tests for the Recipe class
public class RecipeTest {

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
    }


    @Test
    public void testRecipe() {
        assertEquals("Boiled eggs", boiledEggs.getName());
        assertEquals(boiledEggsIngredients,boiledEggs.getIngredientList());
        assertEquals(boiledEggsDirections,boiledEggs.getDirections());

        assertEquals("Cereal with milk",cerealWithMilk.getName());
        assertEquals(cerealWithMilkIngredients,cerealWithMilk.getIngredientList());
        assertEquals(cerealWithMilkDirections,cerealWithMilk.getDirections());
    }



}
