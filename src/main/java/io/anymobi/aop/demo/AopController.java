package io.anymobi.aop.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AopController {

    private Map<String, String> map = new HashMap<>();

    public String aopAdapter(HttpServletRequest request, HttpServletResponse response, MemberDto memberDto) throws JsonProcessingException {
        return "bypass";
    }

    @GetMapping("/aopService/{id}")
    public String aopService(HttpServletRequest request, HttpServletResponse response, String result) {

        if (map.get(Thread.currentThread().getName()) == null) {
            map.put(Thread.currentThread().getName(), result);
        }

        return map.get(Thread.currentThread().getName());
    }
}
