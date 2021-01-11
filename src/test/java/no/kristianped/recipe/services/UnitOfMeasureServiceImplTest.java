package no.kristianped.recipe.services;

import no.kristianped.recipe.commands.UnitOfMeasureCommand;
import no.kristianped.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import no.kristianped.recipe.domain.UnitOfMeasure;
import no.kristianped.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UnitOfMeasureServiceImpl(repository, converter);
    }

    @Test
    void listAllUoms() {
        // given
        Set<UnitOfMeasure> set = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        set.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        set.add(uom2);

        // when
        Mockito.when(repository.findAll()).thenReturn(set);
        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        // then
        assertEquals(2, commands.size());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}