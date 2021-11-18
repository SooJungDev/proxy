package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통로직1 시작
        log.info("start");
        String result1 = target.callA(); // 호출하는 메서드가 다름
        log.info("result ={}", result1);
        // 공통로직1 종료

        // 공통로직2 시작
        log.info("start");
        String result2 = target.callB();// 호출하는 메서드가 다름
        log.info("result ={}", result2);
        // 공통로직2 종료
    }

    @Test
    void reflection1() throws Exception {
        // 클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1 = {}", result1);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2 = {}", result2);
    }

    @Test
    void reflection2() throws Exception {
        // 클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);

    }

    /**
     * 공통 처리 로직
     * Method 라는 메타 정보를 통해서 호출할 메서드 정보가 동적으로 제공한다
     * 정적인 코드를 리플렉션을 사용해서 Method 라는 메타 정보로 추상화 했다. 덕분에 공통 로직을 만들 수 있게 되었다
     * 리플렉션은 가급적 사용 X
     * 리플렉션을 사용하면 클래스와 메서드 메타정보를 사용해서 어플리케이션을 동적으로 유연하게 만들 수 있다.
     * 런타임에 동작하기 떄문에 컴파일 시점에 오류를 잡을 수 없다.
     * @param method
     * @param target
     * @throws Exception
     */
    private void dynamicCall(Method method, Object target) throws Exception {
        // 공통로직1 시작
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }


        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
