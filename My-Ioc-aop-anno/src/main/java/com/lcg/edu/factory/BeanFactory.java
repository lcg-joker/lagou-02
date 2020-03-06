package com.lcg.edu.factory;

import com.lcg.edu.annotations.*;
import com.lcg.edu.utils.PackageScan;
import com.lcg.edu.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lichenggang
 * @date 2020/3/1 2:52 上午
 * @description
 */
public class BeanFactory {

    /**
     * 任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
     * 任务二：对外提供获取实例对象的接口（根据id获取）
     */

    //bean容器
    private static Map<String, Object> map = new HashMap();

    //任务1，解析加载xml配置以及注解到map中

    static {
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        SAXReader saxReader = new SAXReader();
        try {
            //解析xml
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            //解析bean
            Element element = (Element) rootElement.selectSingleNode("//component-scan");
            //获取扫描的包
            String basePack = element.attributeValue("base-package");

            //扫描包下.class文件，获取到全类限定名
            List<String> classNameList = PackageScan.doScan(basePack);


            //需要实例化类集合，beanId,Class对象
            Map<String, Class<?>> classMap = new HashMap<>();


            //region 注册所有标有相应注的bean
            for (String aClass : classNameList) {

                //获取到类到Class对象
                Class<?> clazz = Class.forName(aClass);

                //忽视注解类
                if (Annotation.class.isAssignableFrom(clazz)) {
                    continue;
                }

                //获取类的注解
                Service service = clazz.getAnnotation(Service.class);
                Repository repository = clazz.getAnnotation(Repository.class);
                Component component = clazz.getAnnotation(Component.class);


                String id = null;

                //region 获取到beanId
                if (service != null) {
                    id = service.value();
                    if (StringUtils.isEmpty(id)) {
                        id = StringUtils.toLowerCaseFirstOne(clazz.getSimpleName());
                    }
                }
                if (repository != null) {
                    id = repository.value();
                    if (StringUtils.isEmpty(id)) {
                        id = StringUtils.toLowerCaseFirstOne(clazz.getSimpleName());
                    }
                }
                if (component != null) {
                    id = component.value();
                    if (StringUtils.isEmpty(id)) {
                        id = StringUtils.toLowerCaseFirstOne(clazz.getSimpleName());
                    }
                }

                //endregion

                if (StringUtils.isNotEmpty(id)) {
                    classMap.put(id, clazz);
                    //反射创建对象存入map
                    Object o = clazz.newInstance();
                    map.put(id, o);
                }
            }
            //endregion


            //region 扫描所有bean中的属性注解
            for (String id : classMap.keySet()) {
                //目标对象
                Object o = map.get(id);
                //获取到字段
                Field[] fields = classMap.get(id).getDeclaredFields();

                for (Field field : fields) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    if (autowired == null) {
                        continue;
                    }
                    //获取对象id
                    String fieldName = field.getName();
                    Object fieldObject = map.get(fieldName);
                    //反射赋值
                    field.setAccessible(true);
                    field.set(o, fieldObject);
                }
            }
            //endregion


            //region 扫描所有实现Transactional注解的类，替换bean为代理对象
            for (String id : classMap.keySet()) {
                //目标对象
                Object o = map.get(id);

                Class<?> clazz = classMap.get(id);

                Transactional transactional = clazz.getAnnotation(Transactional.class);

                if (transactional == null) {
                    //判断方法
                    boolean isExist = false;
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        transactional = method.getAnnotation(Transactional.class);
                        if (transactional != null) {
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        continue;
                    }
                }

                ProxyFactory proxyFactory = (ProxyFactory) map.get("proxyFactory");
                if (proxyFactory == null) {
                    continue;
                }
                Class<?>[] interfaces = o.getClass().getInterfaces();
                if (interfaces.length == 0) {
                    Object cglibProxy = proxyFactory.getCGLIBProxy(o);
                    map.put(id, cglibProxy);
                } else {
                    Object jdkProxy = proxyFactory.getJDKProxy(o);
                    map.put(id, jdkProxy);
                }
            }
            //endregion


        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }


    //任务二：对外提供获取实例对象的接口（根据id获取）
    public static Object getBean(String beanId) {
        return map.get(beanId);
    }


}
