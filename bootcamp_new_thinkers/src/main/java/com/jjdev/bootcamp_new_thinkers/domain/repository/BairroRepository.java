package com.jjdev.bootcamp_new_thinkers.domain.repository;

import com.jjdev.bootcamp_new_thinkers.domain.entity.bairro.Bairro;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BairroRepository extends JpaRepository<Bairro, Long>, JpaSpecificationExecutor<Bairro> {

    Optional<Bairro> findByNome(String nome);

    default List<Bairro> buscarPorParametros(Map<String, Object> parametros) {
        Specification<Bairro> specification = (Root<Bairro> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            parametros.forEach((key, value) -> {
                if (value != null) {
                    switch (key) {
                        case "codigoBairro":
                            predicates.add(cb.equal(root.get("codigoBairro"), value));
                            break;
                        case "codigoMunicipio":
                            predicates.add(cb.equal(root.get("codigoMunicipio").get("codigoMunicipio"), value));
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
