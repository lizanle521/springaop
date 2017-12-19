package com.lzl.springaop.bean.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Lizanle on 2017/12/19.
 */
@Component
@Aspect
public class BusinessValidatorAspect {

    private static final Logger logger = LoggerFactory.getLogger(BusinessValidatorAspect.class);

    /**
     * 参数校验类
     */
    private static Validator validator;

    /**
     * 内部类连接符
     */
    public static final String INNERCLASSSTR = "$";

    // 定义切入点:business service
    @Pointcut(value = "execution(public * com.lzl.*.*(..))")
    private void businessServicePoincut() {
    }

    // 前置aop
    @Before(value = "businessServicePoincut()")
    public void beforeAdvice(JoinPoint pj) throws Throwable {
        // 1.校验方法入参
        validateMethodParams(pj);
        // 2.校验入参内的属性
        validateParamsProperty(pj);
    }


    /**
     * 校验方法入参
     *
     * @param pj aop坏绕切面入参
     */
    private void validateMethodParams(JoinPoint pj) throws Exception {
        MethodSignature signature = (MethodSignature) pj.getSignature();
        // 代理的方法
        Method method = signature.getMethod();
        // 是否需要校验方法入参
        if (isNeedValidateMethod(method)) {
            // 代理类
            Class aopClass = pj.getTarget().getClass();
            // 方法参数列表
            Object[] parms = pj.getArgs();
            // 分组校验注解类
            Class methodAnnotation = getMethodGroupAnnotation(pj.getTarget().getClass(), pj.getSignature().getName());
            Set<ConstraintViolation<Object>> violations = getValidateResultForMethod(aopClass.newInstance(), method, parms, methodAnnotation);
            throwException(violations);
        }
    }

    /**
     * 校验入参属性
     *
     * @param pj aop坏绕切面入参
     */
    private void validateParamsProperty(JoinPoint pj) {
        // 方法参数列表
        Object[] params = pj.getArgs();
        if (params == null || params.length < 1) {
            return;
        }
        // 分组校验注解类
        Class methodAnnotation = getMethodGroupAnnotation(pj.getTarget().getClass(), pj.getSignature().getName());
        for (Object param : params) {
            if (param == null) {
                continue;
            }
            Set<ConstraintViolation<Object>> violations = getValidateResultForParam(param, methodAnnotation);
            throwException(violations);
        }
    }

    private void throwException(Set<ConstraintViolation<Object>> violations){
        if(CollectionUtils.isEmpty(violations)){
            return;
        }
        for(ConstraintViolation<Object> violation : violations){
            throw new RuntimeException(violation.getMessage());
        }
    }

    /**
     * 判断是否需要先验证方法中的入参
     *
     * @param method aop方法类
     */
    private boolean isNeedValidateMethod(Method method) {
        for (Annotation[] annotations : method.getParameterAnnotations()) {
            if (annotations.length > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取aop方法对应的分组校验注解类
     *
     * @param aopClass   aop类
     * @param methodName aop方法名称
     * @return 分组校验注解类
     */
    private Class getMethodGroupAnnotation(Class aopClass, String methodName) {
        Class[] interfaces = aopClass.getInterfaces();
        if (interfaces.length > 0) {
            String interfaceName = interfaces[0].getName();
            // 判断是否有对应方法的分组校验注解
            try {
                String annotationName = interfaceName + INNERCLASSSTR + getDubboTypeAnnonName(methodName);
                return Class.forName(annotationName);
            } catch (ClassNotFoundException e) {
                // 不存在这个注解
            }
        }
        return null;
    }

    /**
     * 获取dubbo验证规则下的分组校验的注解名称
     *
     * @param methodName aop的方法名称
     * @return 注解名称
     */
    private String getDubboTypeAnnonName(String methodName) {
        return methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
    }

    /**
     * 校验方法中的入参,并返回验证结果
     *
     * @param classInstance 方法所在类的实例
     * @param method        方法类
     * @param params        方法值数组
     * @return 校验结果
     */
    @SuppressWarnings("unchecked")
    private Set<ConstraintViolation<Object>> getValidateResultForMethod(Object classInstance, Method method, Object[] params, Class methodAnnotation) {
        if (methodAnnotation == null) {
            return getValidator().forExecutables().validateParameters(classInstance, method, params);
        }
        return getValidator().forExecutables().validateParameters(classInstance, method, params, Default.class, methodAnnotation);
    }

    /**
     * 校验入参
     *
     * @param param            入参
     * @param methodAnnotation 分组注解类
     * @return 校验结果
     */
    @SuppressWarnings("unchecked")
    private Set<ConstraintViolation<Object>> getValidateResultForParam(Object param, Class methodAnnotation) {
        if (methodAnnotation == null) {
            return getValidator().validate(param);
        }
        return getValidator().validate(param, Default.class, methodAnnotation);
    }

    /**
     * 从校验结果中格式化出错误信息
     *
     * @param violations 参数校验结果
     * @return 错误信息集合, 格式:[a:reason]
     */
    private List<String> getErrorMsg(Set<ConstraintViolation<Object>> violations) {
        List<String> result = new ArrayList<>();
        if (violations == null || violations.isEmpty()) {
            return result;
        }

        for (ConstraintViolation<Object> violation : violations) {
            result.add(violation.getPropertyPath() + ":" + violation.getMessage());
        }
        return result;
    }

    /**
     * 获取validator
     *
     * @return 参数校验类
     */
    private Validator getValidator() {
        if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        return validator;
    }
}