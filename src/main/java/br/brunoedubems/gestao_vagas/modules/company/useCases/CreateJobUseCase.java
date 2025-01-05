package br.brunoedubems.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.brunoedubems.gestao_vagas.modules.company.entities.JobEntety;
import br.brunoedubems.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobEntety execute(JobEntety JobEntety) {
        return this.jobRepository.save(JobEntety);
    }
}
