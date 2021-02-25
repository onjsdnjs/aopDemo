package io.anymobi.aop.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.HttpServletRequest;

public class AopControllerInterceptor implements MethodInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Object[] arguments = methodInvocation.getArguments();
        HttpServletRequest request = (HttpServletRequest) arguments[0];
        MemberDto memberDto = (MemberDto) arguments[2];

        if (memberDto != null) {
            if (request.getRequestURI().endsWith("json")) {
                memberDto.setResult(objectMapper.writeValueAsString(memberDto));

            } else if (request.getRequestURI().endsWith("form")) {
                memberDto.setResult(memberDto.toString());
            }
        }

        Object retVal = methodInvocation.proceed();

        return retVal;
    }
}
