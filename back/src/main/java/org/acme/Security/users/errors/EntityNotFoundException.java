package org.acme.Security.users.errors;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String id) {
        super("Entity with id " + id + " not found");
    }
}
