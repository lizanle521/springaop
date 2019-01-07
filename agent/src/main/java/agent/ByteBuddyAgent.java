package agent;

import inteceptor.TimeInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * 使用bytebuddy 进行类的增强
 * @author lizanle
 * @Date 2019/1/7 10:43
 */
public class ByteBuddyAgent {
    public static void premain(String agentArgs, Instrumentation ins){
        System.out.println("this is a perform monitor agent");
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer(){
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
                return builder.method(ElementMatchers.<MethodDescription>any())
                        .intercept(MethodDelegation.to(TimeInterceptor.class));
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, DynamicType dynamicType) {
                System.out.println("onTransformation");
            }

            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                System.out.println("onIgnored");
            }

            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, Throwable throwable) {
                System.out.println("onError");
            }

            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule) {
                System.out.println("onComplete");
            }
        };

        new AgentBuilder.Default()
                .type(ElementMatchers.<TypeDescription>nameStartsWith("com.lzl.springaop"))
                .transform(transformer)
                .with(listener)
                .installOn(ins);
    }
}
