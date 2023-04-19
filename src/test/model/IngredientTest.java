package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


// Unit tests for the Ingredient class
public class IngredientTest {

    private Ingredient rice;
    private Ingredient milk;


    @BeforeEach
    public void runBefore() {
        rice = new Ingredient("rice", 200,"g");
        milk = new Ingredient("milk",300,"ml");

    }

    @Test
    public void testIngredient() {
        assertEquals("milk",milk.getName());
        assertEquals("rice",rice.getName());
        assertEquals(300,milk.getAmount());
        assertEquals(200,rice.getAmount());
        assertEquals("g",rice.getUnit());
        assertEquals("ml",milk.getUnit());
    }

    @Test
    public void testSetAmount() {
        rice = new Ingredient("rice", 200,"g");
        rice.setAmount(1);
        assertEquals(1,rice.getAmount());
        rice.setAmount(500);
        assertEquals(500,rice.getAmount());

        milk = new Ingredient("milk", 100,"ml");
        milk.setAmount(150);
        assertEquals(150,milk.getAmount());

    }

    @Test
    public void testGetIngredientString() {
        rice = new Ingredient("rice", 200,"g");
        milk = new Ingredient("milk",300,"ml");

        assertEquals("rice: 200 g",rice.getIngredientString());
        assertEquals("milk: 300 ml",milk.getIngredientString());
    }
}
