package br.brunoedubems.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.brunoedubems.gestao_vagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter{

    @Autowired
    private JWTCandidateProvider jwtProvider;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

               // SecurityContextHolder.getContext().setAuthentication(null);
                String header = request.getHeader("authorization");

                if(request.getRequestURI().startsWith("/auth")){
                    if(header != null){
                        var token = this.jwtProvider.validateToken(header);
    
                        if(token == null){
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED );
                            return;
                        }
                        request.setAttribute("candidate_id", token.getSubject());
                        var roles = token.getClaim("roles").asList(Object.class);

                        var grants = roles.stream()
                        .map(
                            role -> new SimpleGrantedAuthority("ROLE_" + role.toString())
                        ).toList();

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null,
                        grants);
                   
            SecurityContextHolder.getContext().setAuthentication(auth); //Registra o usuário como autenticado no Spring Security
                    }

                }
                
                filterChain.doFilter(request, response);
    }
    
}
