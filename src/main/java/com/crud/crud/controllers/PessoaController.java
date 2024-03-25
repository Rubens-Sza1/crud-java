package com.crud.crud.controllers;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.crud.crud.PessoaService;
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
    public ModelAndView CriaPessoa(
        @Valid @ModelAttribute PessoaCad pessoacad,
        BindingResult result,
        Model model
    ) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.addObject("pessoacad", pessoacad);
            mav.setViewName("pessoas/create");
            return mav;
        }
        
        boolean isCreated = pessoaService.createPessoa(pessoacad);
        if (!isCreated) {
            mav.addObject("error", "A pessoa com o mesmo CPF já existe.");
            mav.addObject("pessoacad", pessoacad);
            mav.setViewName("pessoas/create");
            return mav;
        }

        mav.setViewName("redirect:/pessoas");
        return mav;
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
    

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/edit")
    public String AtualizaPessoa(Model model, @RequestParam int id, @Valid @ModelAttribute PessoaCad pessoacad, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "pessoas/EditaPessoas";
            }

            pessoaService.updatePessoa(id, pessoacad);

        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("pessoacad", pessoacad);
            return "pessoas/EditaPessoas";
        }

        return "redirect:/pessoas";
    }
    
    @Autowired
    private PessoaService pessoaService;
    
    @GetMapping("/delete")
    public String deletePessoa(@RequestParam int id) {
        try {
            boolean isDeleted = pessoaService.deletePessoa(id);
            if (isDeleted) {
                return "redirect:/pessoas";
            } else {
                return "error";
            }
        } catch (Exception ex) {
            return "error";
        }
    }


}
