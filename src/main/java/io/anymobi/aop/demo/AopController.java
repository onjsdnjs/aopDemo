package io.anymobi.aop.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AopController {

    public String aopAdapter(HttpServletRequest request, HttpServletResponse response, MemberDto memberDto) throws JsonProcessingException {

        if (memberDto != null) {
            if (AopResult.getResult() == null) {
                AopResult.setResult(memberDto.getResult());
            }
        }
        return AopResult.getResult();
    }

        @GetMapping("/aopService/{id}")
        public String aopService (HttpServletRequest request, HttpServletResponse response, String memberDto){

            return AopResult.getResult();

        }
    }
