package com.example.filter.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class OpenApiInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("pre handle");
        // 리턴 값이 true면 controller에 전달, false면 전달하지 않는다
        // Object handler : 내가 가야할 컨트롤러에 대한 정보가 들어있음

        var handlerMethod = (HandlerMethod)handler;

        // HandlerMethod로 타입 캐스팅 시키면 메소드가 어노테이션을 가지고 있는지를 체크한다.
        var methodLevel = handlerMethod.getMethodAnnotation(OpenApi.class);
        if(methodLevel != null){
            log.info("method level");
            return true;
        }

        // handlerMethod가 속한 클래스의 타입을 반환해 해당 클래스가 OpenApi 어노테이션이 있는지 체크
        var classLevel = handlerMethod.getBeanType().getAnnotation(OpenApi.class);
        if(classLevel != null){
            log.info("class level");
            return true;
        }

        log.info("open api 아닙니다 : {}", request.getRequestURI());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("post handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("after completion");
    }
}
