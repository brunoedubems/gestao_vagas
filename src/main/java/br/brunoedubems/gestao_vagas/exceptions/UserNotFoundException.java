package br.brunoedubems.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("Usuário not found");
    }
}