package br.brunoedubems.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service //Indica que essa classe faz parte da lógica de negócios e será gerenciada pelo Spring como um bean.
public class JWTProvider { // nome já indica que essa classe será responsável por fornecer e validar tokens JWT.
    @Value("${security.token.secret}") //busca o valor da chave configurada no arquivo application.properties
    private String secretkey;

    public String validateToken(String token) { //Esse método recebe um token JWT e verifica se ele é válido. Se for, retorna o usuário (return subject). Se não for, retorna uma string vazia.
        token = token.replace("Bearer ", ""); // Removendo o Prefixo "Bearer "    
        
        Algorithm algorithm = Algorithm.HMAC256(secretkey); //O JWT precisa ser verificado com o mesmo algoritmo e chave secreta usados na criação. 
            //Aqui é usado o algoritmo HMAC256, que é seguro e comum para tokens JWT.
             // secretkey garante que só a aplicação consegue validar o token.
        
        try {
            var subject = JWT.require(algorithm) // Define o algoritmo que será usado para verificar o token.
            .build() //Verifica se o token é válido e não foi alterado.
            .verify(token) 
            .getSubject();//se o token for válido, pega o usuário ou ID gravado dentro do token

            return subject;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return "";
        }
    }
}
