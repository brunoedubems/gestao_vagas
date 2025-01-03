package br.brunoedubems.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(){
        super("Usuário já existe, pois o email ou username já cadastrado");
    }
}
