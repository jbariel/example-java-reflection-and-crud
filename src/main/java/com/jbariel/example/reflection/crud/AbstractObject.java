package com.jbariel.example.reflection.crud;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.jbariel.example.reflection.annotations.NotEmpty;
import com.jbariel.example.reflection.annotations.NotNull;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractObject<O extends AbstractObject<O>> {

    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @SuppressWarnings("unchecked")
    protected O me() {
        return (O) this;
    }

    public O withId(UUID id) {
        setId(id);
        return me();
    }

    public boolean isValid() {
        // System.out.println("checking isValid");
        boolean isValid = true;
        for (List<String> errs : validate().values()) {
            isValid = isValid && errs.isEmpty();
        }
        return isValid;
    }

    public Map<String, List<String>> validate() {
        return isRecursivelyValid(this.getClass());
    }

    protected Map<String, List<String>> isRecursivelyValid(Class<?> clazz) {
        Map<String, List<String>> msgs = new HashMap<>();
        String fieldKey = StringUtils.EMPTY;
        for (Field f : Arrays.asList(clazz.getDeclaredFields())) {
            fieldKey = clazz.getCanonicalName() + "#" + f.getName();
            List<String> errs = new ArrayList<>();

            // System.out.println(f.getName());
            String tmp = checkNotNull(f);
            if (null != StringUtils.trimToNull(tmp))
                errs.add(tmp);

            tmp = checkNotEmpty(f);
            if (null != StringUtils.trimToNull(tmp))
                errs.add(tmp);

            msgs.put(fieldKey, errs);
        }
        if (null != clazz.getSuperclass()) {
            msgs.putAll(isRecursivelyValid(clazz.getSuperclass()));
        }
        return msgs;
    }

    protected String checkNotNull(Field f) {
        String rtn = StringUtils.EMPTY;
        NotNull ann = f.getAnnotation(NotNull.class);
        if (null != ann) {
            // do the not null check
            boolean acc = f.isAccessible();
            f.setAccessible(true);
            try {
                if (null == f.get(this)) {
                    rtn = ann.error();
                }
            } catch (IllegalAccessException e) {
                System.err.println("How did we get here? " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            f.setAccessible(acc);
        }
        return rtn;
    }

    protected String checkNotEmpty(Field f) {
        String rtn = StringUtils.EMPTY;
        NotEmpty ann = f.getAnnotation(NotEmpty.class);
        if (null != ann) {
            // do the not null check
            boolean acc = f.isAccessible();
            f.setAccessible(true);
            try {
                String val = (String) f.get(this);
                if (null != val && StringUtils.trimToEmpty(val).isEmpty()) {
                    rtn = ann.error();
                }
            } catch (IllegalAccessException e) {
                System.err.println("How did we get here? " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            f.setAccessible(acc);
        }
        return rtn;
    }
}