package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// RecipeToGroceryUI application
public class RecipeToGroceryGUI extends JFrame {

    private static final String JSON_STORE = "./data/recipes.json";
    private static final int NUM_INGREDIENTS = 5;
    private static final int NUM_DIRECTIONS = 5;

    private JPanel panel;
    private GridBagConstraints gridBagConstraints;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private RecipeList addedRecipes;
    private EventLog eventLog;
    private JComboBox<String> comboBox;
    private JButton createShoppingListButton;
    private int coordinateY;

    private JTextField recipeNameTextField;
    private ArrayList<JTextField> ingredientNamesTextFields;
    private ArrayList<JTextField> ingredientAmountsTextFields;
    private ArrayList<JComboBox> ingredientUnitsComboBoxes;
    private ArrayList<JTextField> directionTextFields;
    private JButton addNewRecipeButton;
    private JButton saveRecipesButton;
    private JButton loadRecipesButton;


    // MODIFIES: this
    // EFFECTS: runs the RecipeToGroceryUI application
    public RecipeToGroceryGUI() {
        super("The title");

        coordinateY = 0;
        setup();
        addImage();
        createAddedRecipesSection();
        createViewShoppingListButton();
        createRecipeNameSection();
        createIngredientsSection();
        createDirectionsSection();
        createAddRecipeButton();
        createSaveRecipesButton();
        createLoadRecipesButton();
        createEventLogPrinter();

        getContentPane().add(panel);

        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: print log when app is closed
    private void createEventLogPrinter() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                printLog();
            }
        });
    }


    // EFFECTS: prints events from event log
    public void printLog() {
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }


    // MODIFIES: this
    // EFFECTS: Create a button that allows user to save addedRecipes
    private void createSaveRecipesButton() {
        coordinateY++;
        saveRecipesButton = new JButton("Save recipes");
        addActionListenerSaveRecipes();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = coordinateY;
        panel.add(saveRecipesButton,gridBagConstraints);


    }

    // MODIFIES: this
    // EFFECTS: Create a button that allows user to load previously saved recipes
    private void createLoadRecipesButton() {
        coordinateY++;
        loadRecipesButton = new JButton("Load recipes");
        addActionListenerLoadRecipes();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = coordinateY;
        panel.add(loadRecipesButton,gridBagConstraints);

    }

    // MODIFIES: this
    // EFFECTS: adds previously saved recipes to AddedRecipes
    private void addActionListenerLoadRecipes() {
        loadRecipesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRecipeList();
                comboBox.removeAllItems();
                for (Recipe recipe: addedRecipes.getRecipes()) {
                    comboBox.addItem(recipe.getName());
                }
                setVisible(true);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: save addedRecipes
    private void addActionListenerSaveRecipes() {
        saveRecipesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecipeList();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Create a button that allows us to generate a shopping list
    private void createViewShoppingListButton() {
        coordinateY++;
        createShoppingListButton = new JButton("Create Shopping List");
        addActionListenerViewShoppingList();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = coordinateY;
        panel.add(createShoppingListButton,gridBagConstraints);

    }

    // MODIFIES: this
    // EFFECTS: Create a label for ingredients shopping list
    private void createShoppingList() {

        String selectedRecipeName = comboBox.getSelectedItem().toString();

        coordinateY++;
        addLabelToPanel("Shopping List:",0,coordinateY);
        for (Recipe recipe: addedRecipes.getRecipes()) {
            if (selectedRecipeName.equals(recipe.getName())) {

                for (Ingredient ingredient : recipe.getIngredientList().getIngredients()) {
                    coordinateY++;
                    String ingredientString = ingredient.getIngredientString();
                    addLabelToPanel(ingredientString, 0, coordinateY);
                }
            }
        }
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Create a logo image
    private void addImage() {
        ImageIcon imageIcon = new ImageIcon("./data/Recipe-To-Grocery.png");

        Image image = imageIcon.getImage().getScaledInstance(171,25, Image.SCALE_SMOOTH);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = coordinateY;
        panel.add(label,gridBagConstraints);
        coordinateY++;
    }

    // MODIFIES: this
    // EFFECTS: Create a recipe name section with a label and a text field
    private void createRecipeNameSection() {
        coordinateY++;
        addLabelToPanel("Add new recipe",0,coordinateY);
        coordinateY++;
        recipeNameTextField = addTextFieldToPanel(1, coordinateY);
        addLabelToPanel("Recipe name",0,coordinateY);
    }

    // MODIFIES: this
    // EFFECTS: Create a button allowing user to add a new recipe for the entered name, ingredients, directions
    private void createAddRecipeButton() {
        coordinateY++;
        addNewRecipeButton = new JButton("Add new recipe");
        addActionListenerAddNewRecipe();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = coordinateY;
        panel.add(addNewRecipeButton,gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: adding an action listener for the addNewRecipeButton to create a new recipe and add it to AddedRecipes
    private void addActionListenerAddNewRecipe() {
        addNewRecipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IngredientList ingredientList = new IngredientList();
                String directions = "";
                for (int i = 0; i < ingredientNamesTextFields.size(); i++) {
                    String ingredientName = ingredientNamesTextFields.get(i).getText();
                    if (!ingredientName.equals("")) {
                        int amount = Integer.parseInt(ingredientAmountsTextFields.get(i).getText());
                        String units = ingredientUnitsComboBoxes.get(i).getSelectedItem().toString();
                        ingredientList.addIngredient(new Ingredient(ingredientName,amount,units));
                    }
                }
                for (int i = 0; i < directionTextFields.size(); i++) {
                    if (!directionTextFields.get(i).getText().equals("")) {
                        directions = directions + Integer.toString(i + 1) + ". "
                                + directionTextFields.get(i).getText() + "\r";
                    }

                }
                addedRecipes.addRecipe(new Recipe(recipeNameTextField.getText().toString(),ingredientList,directions));
                comboBox.addItem(recipeNameTextField.getText().toString());
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: creates a label with a shopping list of ingredients
    private void addActionListenerViewShoppingList() {
        createShoppingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                createShoppingList();
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: creates a section where user can enter ingredients for the new recipe
    private void createIngredientsSection() {
        coordinateY++;
        addLabelToPanel("Ingredients",0,coordinateY);
        addLabelToPanel("Ingredient name",1,coordinateY);
        addLabelToPanel("Amount",2,coordinateY);
        addLabelToPanel("Units (mg or ml)",3,coordinateY);
        for (int i = 1; i <= NUM_INGREDIENTS; i++) {
            coordinateY++;
            addLabelToPanel("Ingredient " + Integer.toString(i),0,coordinateY);
            ingredientNamesTextFields.add(addTextFieldToPanel(1,coordinateY));
            ingredientAmountsTextFields.add(addTextFieldToPanel(2,coordinateY));
            ingredientUnitsComboBoxes.add(addUnitsComboBox(3,coordinateY));
        }
    }


    // MODIFIES: this
    // EFFECTS: creates a section where user can enter directions  for the new recipe
    private void createDirectionsSection() {
        coordinateY++;
        for (int i = 1; i <= NUM_DIRECTIONS; i++) {
            coordinateY++;
            addLabelToPanel("Direction " + Integer.toString(i),0,coordinateY);
            directionTextFields.add(addTextFieldToPanel(1,coordinateY));
        }
    }


    // MODIFIES: this
    // EFFECTS: setting up the panel and instantiating fields, also instantiating jsonReader, loads addedRecipes
    public void setup() {

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        eventLog = EventLog.getInstance();

        addedRecipes = new RecipeList();
        setTitle("Recipe Manager");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ingredientNamesTextFields = new ArrayList<JTextField>();
        directionTextFields = new ArrayList<JTextField>();
        ingredientUnitsComboBoxes = new ArrayList<JComboBox>();
        ingredientAmountsTextFields = new ArrayList<JTextField>();
        //addedRecipes = new RecipeList();
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);

    }

    // MODIFIES: this
    // EFFECTS: create a section for added recipes, including a label and a combobox for looking up recipes
    public void createAddedRecipesSection() {

        for (Recipe recipe: addedRecipes.getRecipes()) {
            System.out.println(recipe.getName());
        }


        comboBox = new JComboBox(getRecipeNames().toArray());
        addLabelToPanel("View added recipes",0,coordinateY);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = coordinateY;

        coordinateY++;
        panel.add(comboBox, gridBagConstraints);
    }

    // EFFECTS: returns a list of strings of recipe names from added recipes
    private ArrayList getRecipeNames() {
        ArrayList recipeNames = new ArrayList<>();
        for (Recipe recipe: addedRecipes.getRecipes()) {
            recipeNames.add(recipe.getName());
        }
        return recipeNames;
    }

    // MODIFIES: this
    // EFFECTS: add a label for the given text, x and y coordinates
    public JLabel addLabelToPanel(String text, int x, int y) {
        JLabel label = new JLabel(text);

        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;

        panel.add(label,gridBagConstraints);
        return label;
    }

    // MODIFIES: this
    // EFFECTS: add a text field for the given text, x and y coordinates
    public JTextField addTextFieldToPanel(int x, int y) {
        JTextField textField = new JTextField();
        textField.setColumns(60);
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        panel.add(textField,gridBagConstraints);
        return textField;
    }

    // MODIFIES: this
    // EFFECTS: adds units combo box, allowing users to toggle mg or ml
    public JComboBox addUnitsComboBox(int x, int y) {
        String[] measureUnits = {"mg", "ml"};
        JComboBox unitsComboBox = new JComboBox<>(measureUnits);
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        panel.add(unitsComboBox,gridBagConstraints);
        return unitsComboBox;


    }

    // MODIFIES: this
    // EFFECTS: loads recipe list from file onto addedRecipes
    private void loadRecipeList() {
        try {
            addedRecipes = jsonReader.read();
            System.out.println("Loaded the most recently saved recipe list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the recipe list to file
    private void saveRecipeList() {
        try {
            jsonWriter.open();
            jsonWriter.write(addedRecipes);
            jsonWriter.close();
            System.out.println("Saved current recipe list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
