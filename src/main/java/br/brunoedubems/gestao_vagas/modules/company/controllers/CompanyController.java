package br.brunoedubems.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.brunoedubems.gestao_vagas.modules.company.entities.CompanyEntity;
import br.brunoedubems.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/company")
public class CompanyController {
    
    @Autowired
    private CreateCompanyUseCase createComnpanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
       try {
           var  result = this.createComnpanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
       return ResponseEntity.badRequest().body(e.getMessage());
       }
       
        
    }
    
}
