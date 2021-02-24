package io.anymobi.aop.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AopControllerInterceptor implements MethodInterceptor {

    private Object aopController;

    public AopControllerInterceptor(Object aopController) {

        this.aopController = aopController;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        ObjectMapper objectMapper = new ObjectMapper();
        Object[] arguments = methodInvocation.getArguments();
        HttpServletRequest request = (HttpServletRequest) arguments[0];
        MemberDto memberDto = (MemberDto) arguments[2];

        Arrays.stream(arguments).forEach(a -> System.out.println("arguments = " + a));

        boolean isJson = request.getRequestURI().endsWith("json");
        boolean isForm = request.getRequestURI().endsWith("form");

        Method[] declaredMethods = aopController.getClass().getDeclaredMethods();
        Map<String, Method> methodMap = new HashMap<String, Method>();
        Map<String, String> argMap = new HashMap<String, String>();

        for (Method method : declaredMethods) {
            String name = method.getName();

            if ("aopService".equals(name)) {
                if (isJson) {
                    methodMap.put("json", method);
                    argMap.put("jsonArg",objectMapper.writeValueAsString(memberDto));

                } else if (isForm) {
                    methodMap.put("form", method);
                    if(memberDto != null){
                        argMap.put("formArg",memberDto.toString());
                    }
                }
            }
        }

        Object retVal = null;
        if (isJson) {
            arguments[2] = argMap.get("jsonArg");
            retVal = methodMap.get("json").invoke(aopController, arguments);

        } else if (isForm) {
            arguments[2] = argMap.get("formArg");
            retVal = methodMap.get("form").invoke(aopController, arguments);

        }
        return retVal;
    }
}
