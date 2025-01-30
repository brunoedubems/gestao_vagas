package br.brunoedubems.gestao_vagas.modules.candidate.repositories;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.brunoedubems.gestao_vagas.modules.candidate.entities.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID>{

}
