package br.brunoedubems.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.brunoedubems.gestao_vagas.exceptions.UserFoundException;
import br.brunoedubems.gestao_vagas.modules.company.entities.CompanyEntity;
import br.brunoedubems.gestao_vagas.modules.company.useCases.repositories.CompanyRepository;

@Service
public class CreateComnpanyUseCase {
    
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity){

        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((user) ->{
            throw new UserFoundException();
        });
        return this.companyRepository.save(companyEntity);
    }
}
