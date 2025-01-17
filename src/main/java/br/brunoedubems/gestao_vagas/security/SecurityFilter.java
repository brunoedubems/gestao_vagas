package br.brunoedubems.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.brunoedubems.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //simplesmente um objeto que o Spring cria, configura e gerencia. 
public class SecurityFilter extends OncePerRequestFilter { // OncePerRequestFilter: Garante que o filtro será executado uma vez por requisição.

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal( // Esse método executa a verificação de autenticação.
            HttpServletRequest request, // Representa a requisição que o cliente fez.
            HttpServletResponse response, // Representa a resposta que será enviada.
            FilterChain filterChain) // Permite que a requisição siga para o próximo filtro ou controller.
            throws ServletException, IOException {

        SecurityContextHolder.getContext().setAuthentication(null); // Garante que nenhuma autenticação antiga afete a nova requisição.

        String header = request.getHeader("Authorization"); //Aqui o filtro busca o token JWT no cabeçalho o nome de "Authorization" da requisição.


        if (header != null) { //Se o token for inválido ou expirado, retorna 401 Unauthorized e encerra o processo. código verifica se o cabeçalho "Authorization" da requisição não está vazio.
            var subjectToken = this.jwtProvider.validateToken(header);
            if (subjectToken.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute("company_id", subjectToken); //Salva o token no request para ser usado depois.
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken, null, //Cria um objeto de autenticação com o ID extraído do token.
                    Collections.emptyList());
                   
            SecurityContextHolder.getContext().setAuthentication(auth); //Registra o usuário como autenticado no Spring Security
        }

        filterChain.doFilter(request, response);//Se tudo deu certo, a requisição continua normalmente.
    }

}
