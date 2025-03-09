package com.example.filter.aop;

import com.example.filter.model.UserRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class TimerAop {

    // 스프링에서 관리되고 있는 Bean 인 경우에만, 스프링 AOP 가 동작을 한다.
    // 빈이 아닌 다른 곳에 적용하고 싶다면 AspectJ라는 라이브러리를 사용하면 된다.
    @Pointcut(value = "within(com.example.filter.controller.UserApiController)")
    public void timerPointCut(){
    }

    // 메소드 시작 전
    @Before(value = "timerPointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before");
    }

    // 메소드가 끝난 지점
    @After(value = "timerPointCut()")
    public void after(JoinPoint joinPoint){
        System.out.println("after");
    }

    // 메소드 실행에 성공했을때
    @AfterReturning(value = "timerPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        System.out.println("after returning");
    }
    // 예외가 발생했을 때
    @AfterThrowing(value = "timerPointCut()", throwing = "tx")
    public void afterThrowing(JoinPoint joinPoint, Throwable tx){
        System.out.println("after throwing");
    }
    // 메소드 실행 앞뒤
    @Around(value = "timerPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("메소드 실행 이전");

        Arrays.stream(joinPoint.getArgs()).forEach(
                it -> {
                    if(it instanceof UserRequest){
                        var tempUser = (UserRequest)it;
                        var phoneNumber = tempUser.getPhoneNumber().replace("-", "");
                        tempUser.setPhoneNumber(phoneNumber);
                    }
                }
        );

        // 암,복호화 / 로깅
        var newObjs = Arrays.asList(
                new UserRequest()
        );

        var stopWatch = new StopWatch();

        stopWatch.start();
        joinPoint.proceed(newObjs.toArray());
        stopWatch.stop();

        System.out.println("총 소요된 시간 MS : " + stopWatch.getTotalTimeMillis());
        System.out.println("메소드 실행 이후");
    }

}
