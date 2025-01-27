package br.brunoedubems.gestao_vagas.modules.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.brunoedubems.gestao_vagas.exceptions.UserNotFoundException;
import br.brunoedubems.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.brunoedubems.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.brunoedubems.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApllyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase ApllyJobCandidateUseCase;
    
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    
    @Test
    @DisplayName("should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found(){
        try {
            ApllyJobCandidateUseCase.execulte(null, null);
            
        } catch (Exception e) {
           assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }
}
