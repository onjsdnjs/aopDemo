package io.anymobi.aop.demo;

import java.io.Serializable;

public class MemberDto implements Serializable {

    private String name;
    private String result;

    public MemberDto(String name, String result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "name='" + name + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
