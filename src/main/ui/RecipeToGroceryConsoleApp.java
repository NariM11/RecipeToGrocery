package ui;


import model.Ingredient;
import model.IngredientList;
import model.Recipe;
import model.RecipeList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// // RecipeToGrocery application; (citation: this ui class is modelled based on AccountNotRobust ui class)
public class RecipeToGroceryConsoleApp {
    private static final String JSON_STORE = ".data/recipes.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    IngredientList shoppingList;
    RecipeList allRecipes;
    RecipeList addedRecipes;
    Scanner input;

    // MODIFIES: this
    // EFFECTS: runs the RecipeToGrocery application
    public RecipeToGroceryConsoleApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRecipeToGrocery();
    }

    // MODIFIES: this
    // EFFECTS: Processes user inputs
    private void runRecipeToGrocery() {
        boolean keepGoing = true;
        String command;
        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommandMain(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu used for selecting available options within the application
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t0 -> view all recipes");
        System.out.println("\t1 -> add existing recipe ingredients to shopping list");
        System.out.println("\t2 -> create a new recipe");
        System.out.println("\t3 -> delete a recipe");
        System.out.println("\t4 -> view recipes added to shopping list");
        System.out.println("\t5 -> view shopping list");
        System.out.println("\t6 -> clear list ingredients added to shopping list");
        System.out.println("\t7 -> save current recipe list");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {

        allRecipes = new RecipeList();
        addedRecipes = new RecipeList();
        shoppingList = new IngredientList();
        loadRecipeList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommandMain(String command) {
        if (command.equals("0")) { //view all recipes and open selected revipe
            openSelectedRecipe();
        } else if (command.equals("1")) { //add recipe to shopping list
            addToExistingRecipes();
        } else if (command.equals("2")) { //create a new recipe
            addNewRecipe();
        } else if (command.equals("3")) { //delete a recipe
            deleteRecipe();
        } else if (command.equals("4")) { //view added recipes
            viewRecipes(addedRecipes);
        } else if (command.equals("5")) { //view shopping list
            generateShoppingList();
        }  else if (command.equals("6")) { //clear list of recipes and ingredients added to shopping list
            shoppingList = new IngredientList();
            addedRecipes = new RecipeList();
            System.out.println("shopping list has been refreshed and is now empty");
        }  else if (command.equals("7")) { //clear list of recipes and ingredients added to shopping list
            saveRecipeList();
            System.out.println("current recipe list has been saved!");
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // EFFECTS: displays information from the list of recipes
    private void viewRecipes(RecipeList recipes) {
        if (recipes.getRecipes().isEmpty()) {
            System.out.println("recipe list is empty");
        } else {
            int count = 0;
            for (Recipe recipe: recipes.getRecipes()) {
                System.out.println("\t" + count + " -> " + recipe.getName());
                count += 1;
            }
        }
    }


    // EFFECTS: prints ingredient information from the given list of recipes
    public void printIngredients(IngredientList ingredients) {
        for (Ingredient ingredient: ingredients.getIngredients()) {
            String printLine = ingredient.getName() + ": " + ingredient.getAmount() + " " + ingredient.getUnit();
            System.out.println("\t" + printLine);
        }
    }


    // EFFECTS: Opens a selected a recipe from the shown list of recipes
    private void openSelectedRecipe() {
        viewRecipes(allRecipes);
        String command = input.next().toLowerCase();
        int count = 0;
        for (Recipe recipe: allRecipes.getRecipes()) {
            if (count == Integer.parseInt(command)) {
                System.out.println("\n" + recipe.getName());
                System.out.println("ingredients:");
                printIngredients(recipe.getIngredientList());
                System.out.println("directions");
                System.out.println("\t" + recipe.getDirections());
                break;
            }
            count++;
        }
        if (count == allRecipes.getRecipes().size()) {
            System.out.println("recipe not found");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a recipe to addToExistingRecipes
    private void addToExistingRecipes() {
        System.out.println("\nselect from:");
        viewRecipes(allRecipes);
        String command = input.next().toLowerCase();
        int count = 0;
        for (Recipe recipe : allRecipes.getRecipes()) {
            if (count == Integer.parseInt(command)) {
                addedRecipes.addRecipe(recipe);
                System.out.println(recipe.getName() + " recipe has been added to shopping list");
                break;
            }
            count++;
        }
        if (count == allRecipes.getRecipes().size()) {
            System.out.println("recipe not found");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new recipe to allRecipes
    private void addNewRecipe() {
        Recipe recipe;
        String name;
        String directions;
        IngredientList ingredientList;
        System.out.println("please enter recipe name");
        name = input.next().toLowerCase();
        System.out.println("great! let's now enter ingredients!");
        ingredientList = enterIngredients();
        System.out.println("great! let's now enter directions!");
        directions = enterDirections();
        recipe = new Recipe(name,ingredientList,directions);
        allRecipes.addRecipe(recipe);
        System.out.println("great! " + recipe.getName() + " recipe has been added to list of recipes");
    }


    // EFFECTS: prompts user to enter ingredients and returns the list of ingredients once prompted by user
    private IngredientList enterIngredients() {
        IngredientList ingredients = new IngredientList();
        Ingredient ingredient;
        String name;
        String units;
        int amount;
        String stopCommand = "n";
        while (true) {
            System.out.println("please enter ingredient name. if no more ingredients are required enter n");
            name = input.next().toLowerCase();
            if (name.equals(stopCommand)) {
                System.out.println("all done with ingredients!");
                return ingredients;
            }
            System.out.println("please enter mg or ml for units of measurement");
            units = input.next().toLowerCase();
            System.out.println("how many " + units + " do we need? (please enter whole number)");
            amount = Integer.parseInt(input.next().toLowerCase());
            ingredient = new Ingredient(name,amount,units);
            ingredients.addIngredient(ingredient);
        }
    }

    //EFFECTS: prompts user to enter cooking directions for a recipe and returns the directions once prompted by user
    private String enterDirections() {
        String directions = "";
        String stopCommand = "n";
        String step;
        int count = 1;

        while (true) {
            System.out.println("please enter cooking step. if no more steps are required enter n");
            step = input.next().toLowerCase();
            if (step.equals(stopCommand)) {
                return directions;
            }
            directions += "\n" + count + ". " + step;
            count++;
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter name of a recipe to be deleted and deletes the recipe from allRecipes
    private void deleteRecipe() {
        int count = 0;
        String recipeName = null;

        System.out.println("\nselect from:");
        viewRecipes(allRecipes);
        String command = input.next().toLowerCase();
        for (Recipe recipe : allRecipes.getRecipes()) {
            if (count == Integer.parseInt(command)) {
                recipeName = recipe.getName();
                break;
            }
            count++;
        }
        if (count == allRecipes.getRecipes().size()) {
            System.out.println("recipe not found");
        } else if (recipeName == null) {
            System.out.println("recipe not found");
        } else {
            allRecipes.removeRecipe(recipeName);
            System.out.println(recipeName + " recipe has been deleted from the recipe list");
        }
    }


    // MODIFIES: this
    // EFFECTS: aggregates ingredients in shoppingList and prints out every item in shopping list and amount needed
    private void generateShoppingList() {
        shoppingList = new IngredientList();
        shoppingList.addIngredientsFromRecipesList(addedRecipes);
        shoppingList.aggregateIngredientList();
        System.out.println("Shopping list:");
        printIngredients(shoppingList);
    }


    // EFFECTS: saves the recipe list to file
    private void saveRecipeList() {
        try {
            jsonWriter.open();
            jsonWriter.write(allRecipes);
            jsonWriter.close();
            System.out.println("Saved current recipe list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads recipe list from file
    private void loadRecipeList() {
        try {
            allRecipes = jsonReader.read();
            System.out.println("Loaded the most recently saved recipe list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
