package it.iwkz.api.repositories;

import it.iwkz.api.models.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomesRepository extends JpaRepository<Income, Long> {
    @Query("SELECT i FROM Income i WHERE i.month=:month AND i.year=:year")
    Page<Income> findByMonthYear(@Param("month") int month, @Param("year") int year, Pageable pageable);
}
