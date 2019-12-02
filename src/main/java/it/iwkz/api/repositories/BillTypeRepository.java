package it.iwkz.api.repositories;

import it.iwkz.api.models.BillType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillTypeRepository extends JpaRepository<BillType, Long> {
}
