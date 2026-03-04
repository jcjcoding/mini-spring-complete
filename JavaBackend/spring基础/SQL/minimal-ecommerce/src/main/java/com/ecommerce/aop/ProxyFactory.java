package com.ecommerce.aop;

import com.ecommerce.ioc.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class ProxyFactory {
    private final BeanFactory beanFactory;
    //保持常量
    public ProxyFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object createProxy(Object targetBean) throws Exception {
        Class<?> targetClass = targetBean.getClass();

        if (targetBean instanceof Aspect) {
            return targetBean;
        }

        List<Advice> advices = findMatchingAdvices(targetClass);

        if (advices.isEmpty()) {
            return targetBean;
            //不传null 直接把原参数放回 简洁方便
        }

        return Proxy.newProxyInstance(
                targetClass.getClassLoader(),
                targetClass.getInterfaces(),
                new AopInvocationHandler(targetBean,advices)
        );
    }

    static class Advice {
        Object aspect;
        Method adviceMethod;
        AdviceType type;
        String pointcutExpression;

        public Advice(Object aspect, Method adviceMethod, AdviceType type, String pointcutExpression) {
            this.aspect = aspect;
            this.adviceMethod = adviceMethod;
            this.type = type;
            this.pointcutExpression = pointcutExpression;
        }
    }

    enum AdviceType {
        BEFORE, AROUND,AFTER_THROWING
    }

    static class AopInvocationHandler implements InvocationHandler {
        private final Object target; // 目标对象（如ProductService实例）
        private final List<Advice> advices;

        public AopInvocationHandler(Object target, List<Advice> advices) {
            this.target = target;
            this.advices = advices;
        }

        private boolean isMethodMatch(Method currentMethod, String pointcutExpr) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression(pointcutExpr);
            // 校验“当前方法 + 目标类”是否匹配（精准到方法）
            return pointcut.matches(currentMethod, target.getClass());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // args为null 转为空数组，避免后续空指针
            if (args == null) {
                args = new Object[0];
            }

            // 执行@Before
            for (Advice advice : advices) {
                if (advice.type == AdviceType.BEFORE&&isMethodMatch(method, advice.pointcutExpression)) {
                    advice.adviceMethod.invoke(advice.aspect, method, args);
                }
            }

            Object result = null;
            Throwable exception = null;

            try {
                // 执行@Around

                boolean aroundExecuted = false;

                for (Advice advice : advices) {
                    if (advice.type == AdviceType.AROUND&&isMethodMatch(method, advice.pointcutExpression)) {
                        // 关键：参数数组和aroundMethod的参数列表一致（Method, Object[], Object）
                        result = advice.adviceMethod.invoke(advice.aspect, method, args, target);
                        aroundExecuted = true;

                    }
                }

                //
                if (!aroundExecuted) {
                    result = method.invoke(target, args);
                }
            } catch (Exception e) {
                // 新增：循环提取 getCause()，穿透多层 InvocationTargetException
                Throwable realException = e;
                // 循环条件：只要当前异常是 InvocationTargetException，就继续取它的 Cause
                while (realException instanceof java.lang.reflect.InvocationTargetException && realException.getCause() != null) {
                    realException = realException.getCause();
                }

                //System.out.println("穿透包装后，真正异常类型：" + realException.getClass().getName());
                //System.out.println("真正异常信息：" + realException.getMessage()); // 这里会打印“商品已经存在”

                exception = realException;
                throw exception;
            } finally {
                // 执行@AfterThrowing
                if (exception != null) {
                    for (Advice advice : advices) {
                        if (advice.type == AdviceType.AFTER_THROWING&&isMethodMatch(method, advice.pointcutExpression)) {

                            advice.adviceMethod.invoke(advice.aspect, method, exception);
                        }
                    }
                }
            }
            return result;
        }
    }

    public List<Advice> findMatchingAdvices(Class<?> targetClass) throws Exception {

        //返回advices
        List<Advice> advices = new ArrayList<Advice>();

        //切点类用于检查是否切入表达式
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        
        LogAspect aspect = beanFactory.getBean(LogAspect.class);
        if (aspect == null) return advices;

        for(Method aspectMethod : LogAspect.class.getDeclaredMethods()) {
            //@Before
            if (aspectMethod.isAnnotationPresent(Before.class)) {
                Before before = aspectMethod.getAnnotation(Before.class);
                //载入表达式
                String pointcutExpression = before.value();
                pointcut.setExpression(pointcutExpression);

                if(pointcut.matches(targetClass)) {
                    //System.out.println("Before targetClass:"+targetClass);
                    advices.add(new Advice(aspect, aspectMethod, AdviceType.BEFORE, pointcutExpression));
                }
            }

            //@Around
            if (aspectMethod.isAnnotationPresent(Around.class)) {
                Around around = aspectMethod.getAnnotation(Around.class);
                String pointcutExpression = around.value();
                pointcut.setExpression(pointcutExpression);
                
                if (pointcut.matches(targetClass)) {
                    //System.out.println("Around targetClass:"+targetClass);
                    advices.add(new Advice(aspect, aspectMethod, AdviceType.AROUND, pointcutExpression));
                }
            }

            //@AfterThrowing
            if (aspectMethod.isAnnotationPresent(AfterThrowing.class)) {
                AfterThrowing afterThrowing = aspectMethod.getAnnotation(AfterThrowing.class);
                String pointcutExpression = afterThrowing.value();
                pointcut.setExpression(pointcutExpression);
                
                if (pointcut.matches(targetClass)) {
                    //System.out.println("AfterThrowing targetClass:"+targetClass);
                    advices.add(new Advice(aspect, aspectMethod, AdviceType.AFTER_THROWING, pointcutExpression));
                }
            }
        }

        return advices;
    }


}
