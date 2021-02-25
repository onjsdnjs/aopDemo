package io.anymobi.aop.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AopController {

    public String aopAdapter(HttpServletRequest request, HttpServletResponse response, MemberDto memberDto) throws JsonProcessingException {
        return "bypass";
    }

    @GetMapping("/aopService/{id}")
    public String aopService(HttpServletRequest request, HttpServletResponse response, String memberDto) {

        if(memberDto != null && !"null".equals(memberDto)){
            if (AopResult.getResult() == null) {
                AopResult.setResult(memberDto);
            }
            return AopResult.getResult();
        }else{
            try{
                return AopResult.getResult();
            }finally{
                AopResult.clear();
            }
        }
    }
}
