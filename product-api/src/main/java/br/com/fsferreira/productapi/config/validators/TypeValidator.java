package br.com.fsferreira.productapi.config.validators;

import br.com.fsferreira.productapi.config.exception.GenericUserException;

import java.util.UUID;

public class TypeValidator {
    public static UUID validateUUID(String id, String message) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException exception) {
            throw new GenericUserException(message);
        }
    }
}
