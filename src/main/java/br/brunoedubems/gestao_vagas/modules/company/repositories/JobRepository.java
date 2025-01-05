package br.brunoedubems.gestao_vagas.modules.company.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.brunoedubems.gestao_vagas.modules.company.entities.JobEntety;

public interface JobRepository extends JpaRepository<JobEntety,UUID>{
    
}
