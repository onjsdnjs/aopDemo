package io.anymobi.aop.demo;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class ControllerInterceptor implements HandlerInterceptor {

    private Map<Class<?>, Advice> adviceMap = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof  HandlerMethod) {

            Object bean = ((HandlerMethod) handler).getBean();
            MemberDto memberDto = new MemberDto(Thread.currentThread().getName(), "Aop is very great technology");

            if (bean instanceof Advised) {

                Advised advised = (Advised) bean;
                Advice aopAdvice = adviceMap.get(AopControllerInterceptor.class);

                if (aopAdvice == null) {
                    AopControllerInterceptor aopControllerInterceptor = new AopControllerInterceptor();
                    adviceMap.put(AopControllerInterceptor.class, aopControllerInterceptor);
                    advised.addAdvice(0, aopControllerInterceptor);
                }

            }

            ((AopController) bean).aopAdapter(request, response, memberDto);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        AopResult.clear();

    }
}
