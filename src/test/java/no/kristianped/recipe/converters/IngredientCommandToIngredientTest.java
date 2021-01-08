package no.kristianped.recipe.converters;

import no.kristianped.recipe.commands.IngredientCommand;
import no.kristianped.recipe.commands.UnitOfMeasureCommand;
import no.kristianped.recipe.domain.Ingredient;
import no.kristianped.recipe.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    private static final Recipe RECIPE = new Recipe();
    private static final Long ID_VALUE = Long.valueOf(1l);
    private static final String DESCRIPTION = "Cheeseburger";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = Long.valueOf(2l);

    private IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setId(ID_VALUE);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        // when
        Ingredient ingredient = converter.convert(ingredientCommand);

        // then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
    }

    @Test
    void convertWithNullUom() {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setId(ID_VALUE);

        // when
        Ingredient ingredient = converter.convert(ingredientCommand);

        // then
        assertNotNull(ingredient);
        assertNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}