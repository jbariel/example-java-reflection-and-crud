package com.jbariel.example.reflection.crud;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public abstract class AbstractObjectTest<O extends AbstractObject<O>> {

    protected abstract O newObject();

    protected abstract O newValidObject();

    @Test
    public void emptyObjectIsInvalid() {
        O obj = newObject();
        assertNotNull(obj, "Should not a null object");
        Map<String, List<String>> msgs = obj.validate();
        msgs.keySet().stream().forEach(k -> msgs.get(k).stream().forEach(v -> System.err.println(k + " :: " + v)));
        assertFalse(msgs.isEmpty(), "Default object should not be valid");
    }

    @Test
    public void checkForValidObject() {
        O obj = newValidObject().withId(UUID.randomUUID());
        assertTrue(obj.isValid(), "Object with ID should be valid");
    }
}