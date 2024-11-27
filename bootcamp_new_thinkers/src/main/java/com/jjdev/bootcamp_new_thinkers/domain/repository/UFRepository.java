package com.jjdev.bootcamp_new_thinkers.domain.repository;

import com.jjdev.bootcamp_new_thinkers.domain.entity.uf.UF;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UFRepository extends JpaRepository<UF, Long>, JpaSpecificationExecutor<UF> {

    @Query("SELECT uf FROM UF uf WHERE (:sigla IS NULL OR uf.sigla = :sigla) AND (:nome IS NULL OR uf.nome = :nome)")
    Optional<UF> findBySiglaOrNome(@Param("sigla") String sigla, @Param("nome") String nome);

    default List<UF> buscarPorParametros(Map<String, Object> parametros) {
        Specification<UF> specification = (Root<UF> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            parametros.forEach((key, value) -> {
                if (value != null) {
                    switch (key) {
                        case "codigoUF":
                            predicates.add(cb.equal(root.get("codigoUF"), value));
                            break;
                        case "sigla":
                            predicates.add(cb.equal(root.get("sigla"), value));
                            break;
                        case "nome":
                            predicates.add(cb.like(cb.lower(root.get("nome")), "%" + value.toString().toLowerCase() + "%"));
                            break;
                        case "status":
                            predicates.add(cb.equal(root.get("status"), value));
                            break;
                    }
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(specification);
    }
}
