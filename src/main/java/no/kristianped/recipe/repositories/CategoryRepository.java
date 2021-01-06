package no.kristianped.recipe.repositories;

import no.kristianped.recipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findCategoryByDescription(String description);
}
