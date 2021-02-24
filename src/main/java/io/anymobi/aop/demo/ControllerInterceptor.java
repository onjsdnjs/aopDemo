package io.anymobi.aop.demo;

import com.sun.org.apache.xml.internal.utils.Hashtree2Node;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class ControllerInterceptor implements HandlerInterceptor {

    @Autowired
    private AopController aopController;

    private Map<Class<?>, Advice> adviceMap = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(aopController instanceof Advised){

            Advised advised = (Advised) aopController;
            Advisor[] advisors = advised.getAdvisors();
            Advice aopAdvice = null;

            for(Advisor advisor : advisors){
                if(advisor.getAdvice() instanceof AopControllerInterceptor){
                    aopAdvice = adviceMap.get(AopControllerInterceptor.class);
                }
            }

            if(aopAdvice == null){
                AopControllerInterceptor aopControllerInterceptor = new AopControllerInterceptor(advised.getTargetSource().getTarget());
                adviceMap.put(AopControllerInterceptor.class, aopControllerInterceptor);
                advised.addAdvice(0, aopControllerInterceptor);
            }

            MemberDto memberDto = new MemberDto(Thread.currentThread().getName());
            Object retVal = aopController.aopAdapter(request, response, memberDto);

            System.out.println("retVal : " + retVal);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
