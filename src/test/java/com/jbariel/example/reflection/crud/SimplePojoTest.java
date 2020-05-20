package com.jbariel.example.reflection.crud;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class SimplePojoTest extends AbstractObjectTest<SimplePojo> {

    @Override
    protected SimplePojo newObject() {
        return new SimplePojo();
    }

    @Override
    protected SimplePojo newValidObject() {
        return newObject().withVal2(StringUtils.EMPTY);
    }

    @Test
    public void checkVal2() {
        SimplePojo pojo = newObject().withId(UUID.randomUUID());
        assertFalse(pojo.isValid(), "Val2 should be a pain");
        assertFalse(pojo.withVal2(StringUtils.EMPTY).isValid(), "Val2 needs an actual value");

        Map<String, List<String>> msgs = pojo.withVal2("val2").validate();
        msgs.keySet().stream().forEach(k -> msgs.get(k).stream().forEach(v -> System.err.println(k + " :: " + v)));

        assertTrue(pojo.withVal2("val2").isValid(), "Val2 should be happy");
    }

}