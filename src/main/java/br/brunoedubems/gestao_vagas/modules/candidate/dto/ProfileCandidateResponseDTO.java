package br.brunoedubems.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Desenvolvedora java")
    private String description;

    @Schema(example = "Maria")
    private String username;

    @Schema(example = "Maria@gmail.com")
    private String email;

    @Schema(example = "Maria de souza")
    private String name;
    
    private UUID id;

}
