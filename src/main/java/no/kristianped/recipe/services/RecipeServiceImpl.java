package no.kristianped.recipe.services;

import lombok.extern.slf4j.Slf4j;
import no.kristianped.recipe.commands.RecipeCommand;
import no.kristianped.recipe.converters.RecipeCommandToRecipe;
import no.kristianped.recipe.converters.RecipeToRecipeCommand;
import no.kristianped.recipe.domain.Recipe;
import no.kristianped.recipe.exceptions.NotFoundException;
import no.kristianped.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm the service");
        Set<Recipe> set = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(set::add);
        return set;
    }

    @Override
    public Recipe findById(long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        return recipeOptional.orElseThrow(() -> new NotFoundException("Could not find recipe with ID " + l));
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved Recipe with ID: " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findByCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
