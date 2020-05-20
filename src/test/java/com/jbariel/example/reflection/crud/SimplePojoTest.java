package com.jbariel.example.reflection.crud;

import org.apache.commons.lang3.StringUtils;

public class SimplePojoTest extends AbstractObjectTest<SimplePojo> {

    @Override
    protected SimplePojo newObject() {
        return new SimplePojo();
    }

    @Override
    protected SimplePojo newValidObject() {
        return newObject().withVal2(StringUtils.EMPTY);
    }

}