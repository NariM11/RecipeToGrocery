# My Personal Project - RecipeToGrocery

## What will the application do?

**RecipeToGrocery** is a Java desktop application that generates a 
shopping list of cooking ingredients given a selected list of 
available recipes. Users can select their favourite recipes from
the current selection or add their own recipe. **RecipeToGrocery** will then
combine unique and repeating ingredients across selected recipes
in the aggregate shopping list.

## Who will use it?

**RecipeToGrocery** can be used by anyone who likes to cook and is looking 
for a more efficient way to shop ingredients. It can also be used by people looking to store their
favorite cooking recipes and explore new recipes.


## Why is this project of interest to me?

The idea for this application came to me when I was doing my weekly
groceries. I usually cook 3-4 times a week and get all
of my cooking ideas from online recipe websites. However, I realized that
it may not be the most efficient way of doing things for the 
following reasons:
- Free recipe websites often have pop-up ad displays.
- Recipe websites hide the required ingredients section at the bottom of the webpage. It can
take around 5-10 seconds to find the ingredients for each visited recipe page.
- Many recipes have some common ingredients (e.g. chicken, onion,
garlic). I sometimes need to come back to the same grocery section 
to add ingredients from the same store section after opening the next 
recipe webpage.

**RecipeToGrocery** can help improve grocery shopping experience and will also provide a platform for
storing your favourite cooking recipes.

## User stories

- As a user, I want to be able to view a list of available recipes.
- As a user, I want to be able to create a new recipe and 
add it to the list of available recipes (i.e. an X to a Y).
- As a user, I want to be able to add recipe ingredients to a shopping 
list.
- As a user, I want to generate a shopping list for each added recipe
- As a user, I want to be able to save my current recipe list onto a file (if I so choose)
- As a user, I want to load recipe list previously saved to a file (if I choose)


# Instructions

- You can generate the first required action related to adding Xs to a Y by...
  - Add a new recipe to a list of added recipes by entering
    - Recipe Name
    - Ingredients including ingredient name, amount (int), units of measurement
    - Directions
  - Press "Add New Recipe" to add a new recipe
  - The "View added recipes" combo box will now reflect newly added recipe

- You can generate the second required action related to adding Xs to a Y by...
  - In the "View added recipes" combo box, select a recipe
  - Press "Create Shopping List"
  - The shopping list will appear at the bottom of the screen

- You can locate my visual component by navigating to top of the app screen
- You can save the state of my application by pressing "Save recipes"
- You can reload the state of my application by pressing "Load recipes"







