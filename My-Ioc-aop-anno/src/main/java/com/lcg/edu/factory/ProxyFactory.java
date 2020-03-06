package com.lcg.edu.factory;

import com.lcg.edu.annotations.Autowired;
import com.lcg.edu.annotations.Component;
import com.lcg.edu.annotations.Transactional;
import com.lcg.edu.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lichenggang
 * @date 2020/3/1 4:03 上午
 * @description
 */
@Component
public class ProxyFactory {

    @Autowired
    private TransactionManager transactionManager;


    public Object getJDKProxy(final Object o) {

        Object proxy = Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = null;
                boolean isTransaction = true;
                //类中没有，方法也没有标记，则不执行事务。
                Transactional transactional = o.getClass().getAnnotation(Transactional.class);
                if (transactional == null) {
                    String name = method.getName();
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Method methodImpl = o.getClass().getMethod(name, parameterTypes);
                    transactional = methodImpl.getAnnotation(Transactional.class);
                    if (transactional == null) {
                        isTransaction = false;
                    }
                }

                if (isTransaction) {
                    //执行事务
                    try {
                        transactionManager.beginTransaction();
                        System.err.println("开启" + method.getName() + "方法事务");
                        invoke = method.invoke(o, args);
                        transactionManager.commit();
                        System.err.println("提交" + method.getName() + "方法事务");
                    } catch (Exception e) {
                        e.printStackTrace();
                        transactionManager.rollback();
                        System.err.println("回滚" + method.getName() + "方法事务");
                        throw e;
                    }
                } else {
                    //不执行事务
                    invoke = method.invoke(o, args);
                }

                return invoke;
            }
        });

        return proxy;
    }

    public Object getCGLIBProxy(final Object object) {

        Object proxy = Enhancer.create(object.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object invoke = null;
                boolean isTransaction = true;
                //类中没有，方法也没有标记，则不执行事务。
                Transactional transactional = o.getClass().getAnnotation(Transactional.class);
                if (transactional == null) {
                    transactional = method.getAnnotation(Transactional.class);
                    if (transactional == null) {
                        isTransaction = false;
                    }
                }
                if (isTransaction) {
                    //执行事务
                    try {
                        transactionManager.beginTransaction();
                        System.err.println("开启" + method.getName() + "方法事务");
                        invoke = method.invoke(o, objects);
                        transactionManager.commit();
                        System.err.println("提交" + method.getName() + "方法事务");
                    } catch (Exception e) {
                        e.printStackTrace();
                        transactionManager.rollback();
                        System.err.println("回滚" + method.getName() + "方法事务");
                        throw e;
                    }
                } else {
                    //不执行事务
                    invoke = method.invoke(o, objects);
                }

                return invoke;
            }
        });

        return proxy;
    }
}
