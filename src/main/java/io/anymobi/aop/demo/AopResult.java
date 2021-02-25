package io.anymobi.aop.demo;

public class AopResult {

    private static ThreadLocal<String> aopResult = new ThreadLocal<>();

    public static String getResult(){
        return aopResult.get();
    }

    public static void setResult(String result){
        aopResult.set(result);
    }

    public static void clear(){
        aopResult.remove();
    }
}
