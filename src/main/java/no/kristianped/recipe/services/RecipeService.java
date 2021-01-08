package no.kristianped.recipe.services;

import no.kristianped.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(long anyLong);
}
