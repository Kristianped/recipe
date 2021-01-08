package no.kristianped.recipe.converters;

import lombok.Synchronized;
import no.kristianped.recipe.commands.UnitOfMeasureCommand;
import no.kristianped.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source == null)
            return null;

        final UnitOfMeasureCommand unit = new UnitOfMeasureCommand();
        unit.setId(source.getId());
        unit.setDescription(source.getDescription());

        return unit;
    }
}
