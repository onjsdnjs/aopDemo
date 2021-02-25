package io.anymobi.aop.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AopController {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String aopAdapter(HttpServletRequest request, HttpServletResponse response, MemberDto memberDto) throws JsonProcessingException {

        if(memberDto != null){

            if (AopResult.getResult() == null) {

                boolean isJson = request.getRequestURI().endsWith("json");
                boolean isForm = request.getRequestURI().endsWith("form");

                if(isJson){
                    objectMapper.writeValueAsString(memberDto);
                    AopResult.setResult(objectMapper.writeValueAsString(memberDto));

                }else if(isForm){
                    AopResult.setResult(memberDto.toString());

                }
            }
        }
        return "bypass";
    }

    @GetMapping("/aopService/{id}")
    public String aopService(HttpServletRequest request, HttpServletResponse response, String memberDto) {

        if(memberDto != null && !"null".equals(memberDto)){
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
