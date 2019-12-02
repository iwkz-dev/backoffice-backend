package it.iwkz.api.repositories;

import it.iwkz.api.models.IncomeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {
}
