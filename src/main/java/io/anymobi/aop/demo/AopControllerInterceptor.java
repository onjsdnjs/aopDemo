package io.anymobi.aop.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * bypass 일경우에는 이 클래스가 필요하지 않음 ( methodInvocation.proceed() )
 * methodInvocation.proceed() 를 사용하지 않고 직접 메소드를 핸들링 할 경우 필요함
 */
public class AopControllerInterceptor implements MethodInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        // bypass
        Object retVal = methodInvocation.proceed();

        // 리플렉션을 사용해서 직접 메소드 핸들링
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
