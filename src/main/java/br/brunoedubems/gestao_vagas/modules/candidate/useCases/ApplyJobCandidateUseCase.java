package br.brunoedubems.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.brunoedubems.gestao_vagas.exceptions.JobNotFoundException;
import br.brunoedubems.gestao_vagas.exceptions.UserNotFoundException;
import br.brunoedubems.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.brunoedubems.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    
    public void execulte(UUID idCandidate, UUID idJob){
      this.candidateRepository.findById(idCandidate)
      .orElseThrow(() ->{
        throw new UserNotFoundException();
      });

      this.jobRepository.findById(idJob)
      .orElseThrow(() ->{
        throw new JobNotFoundException();
      });
        

    }
}
