package br.brunoedubems.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.brunoedubems.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.brunoedubems.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {
    
    @Value("${security.token.secret}")
    private String secretkey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
        var company = this.companyRepository
        .findByUsername(authCompanyDTO.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("Username/password incorrect1"));

            var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

            if(!passwordMatches){
                throw new AuthenticationException("Username/password incorrect");            
            }

            Algorithm algorithm = Algorithm.HMAC256(secretkey);

            var token = JWT.create()
            .withIssuer("javagas") // quem emitiu o token
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .withSubject(company.getId().toString()) //Adiciona o ID da empresa como o assunto
            .sign(algorithm); //assinando-o com o algoritmo HMAC256

            return token;
    }
}
