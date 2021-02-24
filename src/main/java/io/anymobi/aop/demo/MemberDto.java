package io.anymobi.aop.demo;

import java.io.Serializable;

public class MemberDto implements Serializable {

    private String name;

    public MemberDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
