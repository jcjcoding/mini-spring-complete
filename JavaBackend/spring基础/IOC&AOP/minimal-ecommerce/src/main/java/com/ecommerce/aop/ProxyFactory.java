package com.ecommerce.aop;

import com.ecommerce.ioc.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

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

        public Advice(Object aspect, Method adviceMethod, AdviceType type) {
            this.aspect = aspect;
            this.adviceMethod = adviceMethod;
            this.type = type;
        }
    }

    enum AdviceType {
        BEFORE, AFTER,AFTER_THROWING
    }

    static class AopInvocationHandler implements InvocationHandler {
        private final Object target;
        private final List<Advice> advices;

        public AopInvocationHandler(Object target, List<Advice> advices) {
            this.target = target;
            this.advices = advices;
        }

        @Override
        public Object invoke(Object proxy,Method method,Object[] args) throws Throwable{
            String methodName = method.getName();
            long startTime = 0;

            try{
                for(Advice advice : advices){
                    if(advice.type == AdviceType.BEFORE){
                        //advice.adviceMethod.invoke(advice.aspect,args);
                        advice.adviceMethod.invoke(advice.aspect, new Object[]{args});
                    }
                }

                startTime = System.currentTimeMillis();
                Object result = method.invoke(target,args);
                return result;
            } catch (Exception e){
                Throwable cause = e.getCause();
                for(Advice advice : advices){
                    if(advice.type == AdviceType.AFTER_THROWING){
                        //advice.adviceMethod.invoke(advice.aspect,args);
                        advice.adviceMethod.invoke(advice.aspect, cause);
                    }
                }
                throw cause;
            } finally {
                for(Advice advice : advices){
                    if(advice.type == AdviceType.AFTER){
                        advice.adviceMethod.invoke(advice.aspect,startTime);
                    }
                }
            }

        }
    }



    //添加advice
    public List<Advice> findMatchingAdvices(Class<?> targetClass) throws Exception {
        List<Advice> advices = new ArrayList<Advice>();

        //只处理productlogaspect切面
        ProductLogAspect aspect = (ProductLogAspect) beanFactory.getBean(ProductLogAspect.class);
        if (aspect == null) return advices;

        for(Method aspectMethod : ProductLogAspect.class.getDeclaredMethods()) {
            //检查是否有@Before
            if (aspectMethod.isAnnotationPresent(Before.class)) {
                Before before = aspectMethod.getAnnotation(Before.class);
                String pointcutMethod = before.value();

                //确保切面类和目标类都有该方法
                if(hasMethod(targetClass, pointcutMethod)) {
                    advices.add(new Advice(aspect, aspectMethod, AdviceType.BEFORE));
                }
            }

            //@After
            if (aspectMethod.isAnnotationPresent(After.class)) {
                After after = aspectMethod.getAnnotation(After.class);
                String pointcutMethod = after.value();

                if (hasMethod(targetClass, pointcutMethod)) {
                    advices.add(new Advice(aspect, aspectMethod, AdviceType.AFTER));
                }
            }

            //@AfterThrowing
            if (aspectMethod.isAnnotationPresent(AfterThrowing.class)) {
                AfterThrowing afterThrowing = aspectMethod.getAnnotation(AfterThrowing.class);
                String pointcutMethod = afterThrowing.value();

                if (hasMethod(targetClass, pointcutMethod)) {
                    advices.add(new Advice(aspect, aspectMethod, AdviceType.AFTER_THROWING));
                }
            }
        }


        return advices;
    }

    //判断类是否有该方法
    private boolean hasMethod(Class<?> clazz, String methodName) {
        for(Method method : clazz.getDeclaredMethods()) {
            if(method.getName().equals(methodName)){
                return true;
            }
        }
        return false;
    }
}
