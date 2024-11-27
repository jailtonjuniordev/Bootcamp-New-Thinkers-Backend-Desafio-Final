package com.jjdev.bootcamp_new_thinkers.domain.repository;

import com.jjdev.bootcamp_new_thinkers.domain.entity.pessoa.Pessoa;
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

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {

    Optional<Pessoa> findByLogin(String login);

    default List<Pessoa> buscarPorParametros(Map<String, Object> parametros) {
        Specification<Pessoa> specification = (Root<Pessoa> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            parametros.forEach((key, value) -> {
                if (value != null) {
                    switch (key) {
                        case "codigoPessoa":
                            predicates.add(cb.equal(root.get("codigoPessoa"), value));
                            break;
                        case "login":
                            predicates.add(cb.like(cb.lower(root.get("login")), "%" + value.toString().toLowerCase() + "%"));
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
