package no.kristianped.recipe.services;

import no.kristianped.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    void deleteById(Long recipeId, Long ingredientId);
}
