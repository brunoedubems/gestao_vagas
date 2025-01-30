package br.brunoedubems.gestao_vagas.modules.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.brunoedubems.gestao_vagas.exceptions.JobNotFoundException;
import br.brunoedubems.gestao_vagas.exceptions.UserNotFoundException;
import br.brunoedubems.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.brunoedubems.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.brunoedubems.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.brunoedubems.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApllyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase apllyJobCandidateUseCase;
    
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    
    @Test
    @DisplayName("Não deve ser possível se candidatar ao emprego com candidato não encontrado")
    public void should_not_be_able_to_apply_job_with_candidate_not_found(){
        try {
            apllyJobCandidateUseCase.execute(null, null);
            
        } catch (Exception e) {
           assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    public void should_not_be_able_to_apply_job_with_job_not_found(){
        var idCandidate = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
           apllyJobCandidateUseCase.execute(idCandidate ,null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }
    
}
