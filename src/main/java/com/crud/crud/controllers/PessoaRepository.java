package com.crud.crud.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crud.crud.models.Pessoa;


public interface PessoaRepository extends JpaRepository<Pessoa, Integer>, JpaSpecificationExecutor<Pessoa> {

    @Query("SELECT COUNT(p) > 0 FROM Pessoa p WHERE p.pes_cpf = :cpf")
    boolean ExisteCPF(@Param("cpf") String cpf);
}


