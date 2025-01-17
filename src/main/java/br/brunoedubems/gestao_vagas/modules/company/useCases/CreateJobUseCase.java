package br.brunoedubems.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.brunoedubems.gestao_vagas.modules.company.entities.JobEntity;
import br.brunoedubems.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity JobEntety) {
        return this.jobRepository.save(JobEntety);
    }
}
