package com.example.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //진입 전
        log.info(">>>>>>> 진입 전");

        var req = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

//        var br = req.getReader();
//        var list = br.lines().toList();
//
//        list.forEach(it -> {
//            log.info("{}", it);
//        });

        filterChain.doFilter(req, res);

        var reqJson = new String(req.getContentAsByteArray());
        log.info("req : {}", reqJson);

        var resJson = new String(res.getContentAsByteArray());
        log.info("res : {}", resJson);

        log.info("<<<<<<<<< 리턴");
        //진입 후

        res.copyBodyToResponse();
    }
}
