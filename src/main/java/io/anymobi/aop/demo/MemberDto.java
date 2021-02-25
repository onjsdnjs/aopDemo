package io.anymobi.aop.demo;

import java.io.Serializable;

public class MemberDto implements Serializable {

    private String name;
    private boolean isJson;
    private boolean isForm;

    public MemberDto(String name, boolean b, boolean b1) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isJson() {
        return isJson;
    }

    public void setJson(boolean json) {
        isJson = json;
    }

    public boolean isForm() {
        return isForm;
    }

    public void setForm(boolean form) {
        isForm = form;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "name='" + name + '\'' +
                ", isJson=" + isJson +
                ", isForm=" + isForm +
                '}';
    }
}
