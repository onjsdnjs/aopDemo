package io.anymobi.aop.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class AopControllerInterceptor implements MethodInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        AopController aopController = (AopController)methodInvocation.getThis();
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
        }

        return retVal;
    }
}
