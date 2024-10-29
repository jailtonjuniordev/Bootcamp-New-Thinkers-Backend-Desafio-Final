package com.jjdev.bootcamp_new_thinkers.domain.repository.uf;


import com.jjdev.bootcamp_new_thinkers.domain.entity.municipio.Municipio;
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

public interface MunicipioRepository extends JpaRepository<Municipio, Long>, JpaSpecificationExecutor<Municipio> {

    Optional<Municipio> findByNome(String nome);

    default List<Municipio> buscarPorParametros(Map<String, Object> parametros) {
        Specification<Municipio> specification = (Root<Municipio> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            parametros.forEach((key, value) -> {
                if (value != null) {
                    switch (key) {
                        case "codigoMunicipio":
                            predicates.add(cb.equal(root.get("codigoMunicipio"), value));
                            break;
                        case "codigoUF":
                            predicates.add(cb.equal(root.get("codigoUF").get("codigoUF"), value));
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

