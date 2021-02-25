package io.anymobi.aop.demo;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.HttpServletRequest;

public class AopControllerInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Object[] arguments = methodInvocation.getArguments();
        HttpServletRequest request = (HttpServletRequest) arguments[0];
        MemberDto memberDto = (MemberDto) arguments[2];

        boolean isJson = request.getRequestURI().endsWith("json");
        boolean isForm = request.getRequestURI().endsWith("form");

        if(memberDto != null) {
            memberDto.setJson(isJson);
            memberDto.setForm(isForm);
        }

        Object retVal = methodInvocation.proceed();

        // 리플렉션을 사용해서 직접 메소드 핸들링 할 경우
        /*AopController aopController = (AopController)methodInvocation.getThis();
        Object[] arguments = methodInvocation.getArguments();
        HttpServletRequest request = (HttpServletRequest) arguments[0];
        MemberDto memberDto = (MemberDto) arguments[2];

        boolean isJson = request.getRequestURI().endsWith("json");
        boolean isForm = request.getRequestURI().endsWith("form");

        Method[] declaredMethods = aopController.getClass().getDeclaredMethods();
        Object retVal = null;

        for (Method method : declaredMethods) {
            String name = method.getName();

            if ("aopService".equals(name)) {
                if (isJson) {
                    arguments[2] = objectMapper.writeValueAsString(memberDto);
                    retVal = method.invoke(methodInvocation.getThis(), arguments);

                } else if (isForm) {
                    if(memberDto != null){
                        arguments[2] = memberDto.toString();
                    }
                    retVal = method.invoke(methodInvocation.getThis(), arguments);
                }
            }
        }*/

        return retVal;
    }
}
