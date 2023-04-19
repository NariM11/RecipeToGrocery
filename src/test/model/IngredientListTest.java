package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Unit tests for the IngredientList class
public class IngredientListTest {

    private Ingredient rice;
    private Ingredient egg;
    private Ingredient salt;
    private Ingredient water;
    private Ingredient milk;
    private Ingredient cereal;

    private Recipe testRecipe1;
    private Recipe testRecipe2;
    private Recipe testRecipe3;

    private RecipeList testRecipeList;

    private Ingredient rice1 = new Ingredient("rice",1,"g");
    private Ingredient rice50 = new Ingredient("rice",50,"g");
    private Ingredient rice200 = new Ingredient("rice",200,"g");
    private Ingredient riceAgg = new Ingredient("rice", 200+50+1,"g");
    private Ingredient milk1 = new Ingredient("milk",1,"ml");
    private Ingredient milk100 = new Ingredient("milk",100,"ml");
    private Ingredient milk500 = new Ingredient("milk",500,"ml");
    private Ingredient milkAgg = new Ingredient("milk",500+100+1,"ml");

    private IngredientList ingredientList;
    private ArrayList<Ingredient> testArray;

    @BeforeEach
    void runBefore() {
        ingredientList = new IngredientList();
        testArray = new ArrayList<>();

        rice = new Ingredient("rice", 200,"g");
        egg = new Ingredient("egg",200,"g");
        salt = new Ingredient("salt",10,"g");
        water = new Ingredient("water",10,"ml");
        cereal = new Ingredient("cereal",100,"g");
        milk = new Ingredient("milk",300,"ml");

        testRecipe1 = new Recipe("boiled eggs",new IngredientList(),"test directions1");
        testRecipe2 = new Recipe("cereal with milk",new IngredientList(),"test directions2");
        testRecipe3 = new Recipe("test recipe", new IngredientList(), "test directions 3");
        testRecipe1.getIngredientList().addIngredient(egg);
        testRecipe1.getIngredientList().addIngredient(milk);
        testRecipe1.getIngredientList().addIngredient(salt);
        testRecipe2.getIngredientList().addIngredient(cereal);
        testRecipe2.getIngredientList().addIngredient(milk);
        testRecipeList = new RecipeList();

    }

    @Test
    public void testIngredientList() {
        assertTrue(ingredientList.getIngredients().isEmpty());
    }

    @Test
    public void testAddIngredient() {
        ingredientList.addIngredient(rice1);
        testArray.add(rice1);
        assertEquals(testArray,ingredientList.getIngredients());
        ingredientList.addIngredient(rice50);
        ingredientList.addIngredient(rice200);
        ingredientList.addIngredient(salt);
        ingredientList.addIngredient(milk1);
        ingredientList.addIngredient(milk100);
        testArray.add(rice50);
        testArray.add(rice200);
        testArray.add(salt);
        testArray.add(milk1);
        testArray.add(milk100);
        assertEquals(testArray,ingredientList.getIngredients());
        assertEquals(6,ingredientList.getIngredients().size());
    }

    @Test
    public void testRemoveIngredient() {
        assertFalse(ingredientList.removeIngredient("rice"));
        ingredientList.addIngredient(rice1);
        assertTrue(ingredientList.removeIngredient("rice"));
        assertTrue(ingredientList.getIngredients().isEmpty());
        ingredientList.addIngredient(milk1);
        ingredientList.addIngredient(rice50);
        ingredientList.addIngredient(salt);
        ingredientList.addIngredient(rice200);
        ingredientList.addIngredient(milk100);
        testArray.add(milk1);
        testArray.add(rice50);
        testArray.add(salt);
        testArray.add(rice200);
        testArray.add(milk100);
        testArray.remove(milk1);
        testArray.remove(milk100);
        assertTrue(ingredientList.removeIngredient("milk"));
        assertEquals(testArray,ingredientList.getIngredients());
        testArray.remove(salt);
        assertTrue(ingredientList.removeIngredient("salt"));
        assertEquals(testArray,ingredientList.getIngredients());
        assertFalse(ingredientList.removeIngredient("pumpkin"));
        assertEquals(2,ingredientList.getIngredients().size());
    }

    @Test
    public void testAggregateIngredientListOne() {
        ingredientList.addIngredient(rice1);
        testArray.add(rice1);
        ingredientList.aggregateIngredientList();
        for (int i = 0; i < testArray.size(); i++) {
            assertEquals(testArray.get(i).getAmount(),ingredientList.getIngredients().get(i).getAmount());
            assertEquals(testArray.get(i).getName(),ingredientList.getIngredients().get(i).getName());
            assertEquals(testArray.get(i).getUnit(),ingredientList.getIngredients().get(i).getUnit());
        }
        assertEquals(1,ingredientList.getIngredients().size());
    }

    @Test
    public void testAggregateIngredientListTwoDifferent() {
        ingredientList.addIngredient(rice1);
        ingredientList.addIngredient(milk100);
        testArray.add(rice1);
        testArray.add(milk100);
        ingredientList.aggregateIngredientList();
        for (int i = 0; i < testArray.size(); i++) {
            assertEquals(testArray.get(i).getAmount(),ingredientList.getIngredients().get(i).getAmount());
            assertEquals(testArray.get(i).getName(),ingredientList.getIngredients().get(i).getName());
            assertEquals(testArray.get(i).getUnit(),ingredientList.getIngredients().get(i).getUnit());
        }
        assertEquals(2,ingredientList.getIngredients().size());
    }

    @Test
    public void testAggregateIngredientListOneAggregation() {
        ingredientList.addIngredient(rice1);
        ingredientList.addIngredient(milk100);
        ingredientList.addIngredient(salt);
        ingredientList.addIngredient(rice50);
        ingredientList.addIngredient(rice200);
        ingredientList.aggregateIngredientList();
        testArray.add(riceAgg);
        testArray.add(milk100);
        testArray.add(salt);
        assertEquals(testArray.size(), ingredientList.getIngredients().size());
        for (int i = 0; i < testArray.size(); i++) {
            assertEquals(testArray.get(i).getAmount(), ingredientList.getIngredients().get(i).getAmount());
            assertEquals(testArray.get(i).getName(), ingredientList.getIngredients().get(i).getName());
            assertEquals(testArray.get(i).getUnit(), ingredientList.getIngredients().get(i).getUnit());
        }
        assertEquals(3,ingredientList.getIngredients().size());
    }

    @Test
    public void testAggregateIngredientListTwoAggregations() {
        ingredientList.addIngredient(rice1);
        ingredientList.addIngredient(salt);
        ingredientList.addIngredient(milk100);
        ingredientList.addIngredient(rice50);
        ingredientList.addIngredient(rice200);
        ingredientList.addIngredient(milk1);
        ingredientList.addIngredient(milk500);
        ingredientList.aggregateIngredientList();
        testArray.add(riceAgg);
        testArray.add(salt);
        testArray.add(milkAgg);
        assertEquals(testArray.size(), ingredientList.getIngredients().size());
        for (int i = 0; i < testArray.size(); i++) {
            assertEquals(testArray.get(i).getAmount(),ingredientList.getIngredients().get(i).getAmount());
            assertEquals(testArray.get(i).getName(),ingredientList.getIngredients().get(i).getName());
            assertEquals(testArray.get(i).getUnit(),ingredientList.getIngredients().get(i).getUnit());
        }
        assertEquals(3,ingredientList.getIngredients().size());
    }

    @Test
    public void testAddIngredientsFromRecipesListEmpty() {
        IngredientList testIngredientList = new IngredientList();
        testIngredientList.addIngredientsFromRecipesList(testRecipeList);
        assertEquals(0,testIngredientList.getIngredients().size());
    }

    @Test
    public void testAddIngredientsFromRecipesListNotEmpty() {
        IngredientList testIngredientList = new IngredientList();
        testRecipeList.addRecipe(testRecipe1);
        testRecipeList.addRecipe(testRecipe2);
        testRecipeList.addRecipe(testRecipe3);
        testIngredientList.addIngredientsFromRecipesList(testRecipeList);
        assertEquals(5,testIngredientList.getIngredients().size());
        assertEquals(egg,testIngredientList.getIngredients().get(0));
        assertEquals(milk,testIngredientList.getIngredients().get(1));
        assertEquals(salt,testIngredientList.getIngredients().get(2));
        assertEquals(cereal,testIngredientList.getIngredients().get(3));
        assertEquals(milk,testIngredientList.getIngredients().get(4));
    }

}
