package it.iwkz.api.repositories;

import it.iwkz.api.models.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b FROM Bill b WHERE b.month=:month AND b.year=:year")
    Page<Bill> findByMonthYear(@Param("month") int month, @Param("year") int year, Pageable pageable);
}
