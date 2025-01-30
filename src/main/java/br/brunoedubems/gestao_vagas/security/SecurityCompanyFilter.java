package br.brunoedubems.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.brunoedubems.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // simplesmente um objeto que o Spring cria, configura e gerencia.
public class SecurityCompanyFilter extends OncePerRequestFilter { // OncePerRequestFilter: Garante que o filtro será executado
                                                           // uma vez por requisição.

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal( // Esse método executa a verificação de autenticação.
            HttpServletRequest request, // Representa a requisição que o cliente fez.
            HttpServletResponse response, // Representa a resposta que será enviada.
            FilterChain filterChain) // Permite que a requisição siga para o próximo filtro ou controller.
            throws ServletException, IOException {

        String header = request.getHeader("Authorization"); // Aqui o filtro busca o token JWT no cabeçalho o nome de
                                                            // "Authorization" da requisição.

        if (request.getRequestURI().startsWith("/company")) {
            if (header != null) { 
                var token = this.jwtProvider.validateToken(header);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                var roles = token.getClaim("roles").asList(Object.class);
                var grants = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList();

                request.setAttribute("company_id", token.getSubject()); 
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null, // Cria um objeto de autenticação com o ID extraído do token.
                        grants);

                SecurityContextHolder.getContext().setAuthentication(auth); // Registra o usuário como autenticado no
                                                                            // Spring Security
            }
        }

        filterChain.doFilter(request, response);// Se tudo deu certo, a requisição continua normalmente.
    }

}
