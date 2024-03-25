package com.crud.crud.controllers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.crud.models.Pessoa;
import com.crud.crud.models.PessoaCad;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository repo;

    @GetMapping({"", "/"})
    public String ListaPessoa(
        @RequestParam(name = "nome", required = false) String nome,
        @RequestParam(name = "cpf", required = false) String cpf,
        @RequestParam(name = "status", required = false) Integer status,
        Model model
    ) {
        Specification<Pessoa> spec = Specification.where(null);

        if (nome != null && !nome.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("pes_nome")), "%" + nome.toLowerCase() + "%"));
        }
        if (cpf != null && !cpf.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("pes_cpf"), cpf));
        }
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("pes_flg_atv"), status));
        }

        List<Pessoa> pessoas = repo.findAll(spec);

        model.addAttribute("pessoa", pessoas);
        return "pessoas/index";
    }


    @GetMapping("/create")
    public String MostraCriaPessoa(Model model) {
        PessoaCad pessoacad = new PessoaCad();
        model.addAttribute("pessoacad", pessoacad);
        return "pessoas/create";
    }

    @PostMapping("/create")
    public String CriaPessoa(
        @Valid @ModelAttribute PessoaCad pessoacad,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("pessoacad", pessoacad);
            return "pessoas/create";
        }
        
        // Verifica se uma pessoa com o mesmo CPF já existe
        if (repo.ExisteCPF(pessoacad.getCpf())) {
            model.addAttribute("pessoacad", pessoacad);        
            return "pessoas/create";
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setPes_nome(pessoacad.getNome());
        pessoa.setPes_cpf(pessoacad.getCpf());
        pessoa.setPes_telefone(pessoacad.getTelefone());
        pessoa.setPes_email(pessoacad.getEmail());
        pessoa.setPes_dt_ult_modificacao(LocalDateTime.now()); // Definindo a data de modificação como o momento atual.
        pessoa.setPes_dt_cadastro(new Date()); // Definindo a data de cadastro como o momento atual.
        pessoa.setStatus(1); // Definindo o status como ativo


        repo.save(pessoa);
        return "redirect:/pessoas";
    }  
    
    @GetMapping("/edit")
    public String MostraEditaPessoa(Model model, @RequestParam int id) {
        try {
            Pessoa pessoa = repo.findById(id).get();
            model.addAttribute("pessoa", pessoa);

            PessoaCad pessoacad = new PessoaCad();
            pessoacad.setNome(pessoa.getPes_nome());
            pessoacad.setEmail(pessoa.getPes_email());
            pessoacad.setTelefone(pessoa.getPes_telefone());
            pessoacad.setCpf(pessoa.getPes_cpf());
            pessoacad.setStatus(pessoa.getStatus());

            model.addAttribute("pessoacad", pessoacad);

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());

        }
        return "pessoas/EditaPessoas";
    }
    

    @PostMapping("/edit")
    public String AtualizaPessoa(Model model, @RequestParam int id, @Valid @ModelAttribute PessoaCad pessoacad, BindingResult result) {
        try {
            Pessoa pessoa = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada com o ID: " + id));

            model.addAttribute("pessoa", pessoa);

            if (result.hasErrors()) {
                return "pessoas/EditaPessoas";
            }

            // Verifica se o CPF já está sendo utilizado por outra pessoa
            if (!pessoa.getPes_cpf().equals(pessoacad.getCpf()) && repo.ExisteCPF(pessoacad.getCpf())) {
                model.addAttribute("error", "CPF já cadastrado");
                model.addAttribute("pessoacad", pessoacad);
                return "pessoas/EditaPessoas";
            }

            pessoa.setPes_nome(pessoacad.getNome());
            pessoa.setPes_cpf(pessoacad.getCpf());
            pessoa.setPes_telefone(pessoacad.getTelefone());
            pessoa.setPes_email(pessoacad.getEmail());
            pessoa.setPes_dt_ult_modificacao(LocalDateTime.now()); // Atualiza a data de modificação

            repo.save(pessoa);

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

        return "redirect:/pessoas";
    }
    
    @GetMapping("/delete")
    public String DeletaPessoa(
        @RequestParam int id
    ) {
        try {
            Optional<Pessoa> optionalPessoa = repo.findById(id);
            if (optionalPessoa.isPresent()) {
                Pessoa pessoa = optionalPessoa.get();
                pessoa.setStatus(0); // setando o valor para 'inativo'
                repo.save(pessoa);
            } else {
                System.out.println("Registro não encontrado com ID: " + id);
            }
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

        return "redirect:/pessoas";
    }


}
