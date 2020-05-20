package com.jbariel.example.reflection.crud;

import com.jbariel.example.reflection.annotations.NotEmpty;
import com.jbariel.example.reflection.annotations.NotNull;

public class SimplePojo extends AbstractObject<SimplePojo> {

    private String name;

    @NotNull(error = "I demand val2!")
    @NotEmpty(error = "HAHA empty is not an actual value, try again fool!")
    private String val2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public SimplePojo withName(String name) {
        setName(name);
        return me();
    }

    public SimplePojo withVal2(String val2) {
        setVal2(val2);
        return me();
    }
}