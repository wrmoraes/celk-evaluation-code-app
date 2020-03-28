package br.com.celk.evaluation.ufengine.repository;

import br.com.celk.evaluation.ufengine.domain.County;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the County entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountyRepository extends JpaRepository<County, Long> {
}
