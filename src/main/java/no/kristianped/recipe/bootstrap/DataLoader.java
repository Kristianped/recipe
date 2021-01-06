package no.kristianped.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import no.kristianped.recipe.domain.*;
import no.kristianped.recipe.repositories.CategoryRepository;
import no.kristianped.recipe.repositories.RecipeRepository;
import no.kristianped.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("Loading Bootstrap data");
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        // Get Unit of measure
        Optional<UnitOfMeasure> eachOptional = unitOfMeasureRepository.findUnitOfMeasureByDescription("Each");
        Optional<UnitOfMeasure> tableSpoonOptional = unitOfMeasureRepository.findUnitOfMeasureByDescription("Each");
        Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findUnitOfMeasureByDescription("Each");
        Optional<UnitOfMeasure> dashOptional = unitOfMeasureRepository.findUnitOfMeasureByDescription("Each");
        Optional<UnitOfMeasure> pintOptional = unitOfMeasureRepository.findUnitOfMeasureByDescription("Each");
        Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findUnitOfMeasureByDescription("Each");

        UnitOfMeasure eachUom = eachOptional.orElseThrow(() -> new RuntimeException("Each-uom is not found"));
        UnitOfMeasure tablespoonUom = tableSpoonOptional.orElseThrow(() -> new RuntimeException("Tablespoon-uom is not found"));
        UnitOfMeasure teaspoonUom = teaspoonOptional.orElseThrow(() -> new RuntimeException("Teaspoon-uom is not found"));
        UnitOfMeasure dashUom = dashOptional.orElseThrow(() -> new RuntimeException("Dash-uom is not found"));
        UnitOfMeasure pintUom = pintOptional.orElseThrow(() -> new RuntimeException("Pint-uom is not found"));
        UnitOfMeasure cupUom = cupOptional.orElseThrow(() -> new RuntimeException("Cup-uom is not found"));

        // Get categories
        Optional<Category> americanOptional = categoryRepository.findCategoryByDescription("American");
        Optional<Category> mexicanOptional = categoryRepository.findCategoryByDescription("Mexican");

        Category americanCategory = americanOptional.orElseThrow(() -> new RuntimeException("American category not found"));
        Category mexicanCategory = mexicanOptional.orElseThrow(() -> new RuntimeException("Mexican category not found"));


        // Yumy guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("""
                1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.
                2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)
                3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.
                Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.
                Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.
                Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.
                4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.
                """);

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("""
                For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados
                Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.
                The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.
                To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.
                """);
        guacRecipe.setNotes(guacNotes);

        guacRecipe
                .addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom))
                .addIngredient(new Ingredient("Kosher salt", new BigDecimal(0.5), teaspoonUom))
                .addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoonUom))
                .addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoonUom))
                .addIngredient(new Ingredient("serrano chiles, stems and seed removed, minced", new BigDecimal(2), eachUom))
                .addIngredient(new Ingredient("Chilantro", new BigDecimal(2), tablespoonUom))
                .addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom))
                .addIngredient(new Ingredient("ripe tomato, seeds and pulp removed", new BigDecimal(0.5), eachUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        // Yummy tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setDirections("""
                1 Prepare a gas or charcoal grill for medium-high, direct heat.
                2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.
                Set aside to marinate while the grill heats and you prepare the rest of the toppings.
                3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.
                4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.
                Wrap warmed tortillas in a tea towel to keep them warm until serving.
                5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.
                """);

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("""
                We have a family motto and it is this: Everything goes better in a tortilla.
                Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.
                Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!
                First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.
                Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!
                """);
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe
                .addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoonUom))
                .addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUom))
                .addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoonUom))
                .addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUom))
                .addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoonUom))
                .addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom))
                .addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tablespoonUom))
                .addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoonUom))
                .addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoonUom))
                .addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tablespoonUom))
                .addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom))
                .addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupUom))
                .addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom))
                .addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom))
                .addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom))
                .addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom))
                .addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom))
                .addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupUom))
                .addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        return List.of(guacRecipe, tacosRecipe);
    }
}
