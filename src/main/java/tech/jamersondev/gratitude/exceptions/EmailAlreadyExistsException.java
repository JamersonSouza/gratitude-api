package tech.jamersondev.gratitude.exceptions;

public class EmailAlreadyExistsException extends CoreException{
    public EmailAlreadyExistsException(String message) {
        super(String.format("E-mail %s informado já pertence a um usuário cadastrado.", message));
    }
}
