package inteceptor;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author lizanle
 * @Date 2019/1/7 11:03
 */
public class TimeInterceptor {
    @RuntimeType
    public static Object intecept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try{
            return callable.call();
        }finally {
            System.out.println(method + ": took " +( System.currentTimeMillis() - start)+ " ms");
        }
    }
}
