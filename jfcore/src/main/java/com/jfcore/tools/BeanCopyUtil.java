package com.jfcore.tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.util.ClassUtils;

public class BeanCopyUtil{
    //用于缓存方法句柄,避免每次执行复制时获取,提高copy性能
    private static final ConcurrentHashMap<CacheKey, List<CopyMethodHandle>> methodHandleCache = new ConcurrentHashMap<>();

    //从缓存中获取或生成Getter/Setter方法句柄,并执行赋值
    public static <T> T copy(Object source, T target) {
        List<CopyMethodHandle> getSetMethodHandles = methodHandleCache
                .computeIfAbsent(new CacheKey(source.getClass(), target.getClass()),
                        key -> getCopyMethodHandles(source, target));
        try {
            for (CopyMethodHandle getSetMethodHandle : getSetMethodHandles) {
                getSetMethodHandle.invoke(source, target);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return target;
    }
    //获取Getter/Setter方法句柄
    private static <T> List<CopyMethodHandle> getCopyMethodHandles(Object source, T target) {
        ArrayList<CopyMethodHandle> methodHandles = new ArrayList<>();
        PropertyDescriptor[] sourcePropertyDescs = getPropertyDescs(source.getClass());
        Map<String, PropertyDescriptor> targetPropertyDescsMap = getPropertyDescsMap(target.getClass());

        for (PropertyDescriptor sourcePropertyDesc : sourcePropertyDescs) {
            Method readMethod = sourcePropertyDesc.getReadMethod();
            if (readMethod != null) {
                PropertyDescriptor targetPropertyDesc = targetPropertyDescsMap.get(sourcePropertyDesc.getName());
                Method writeMethod = null;
                if (targetPropertyDesc != null
                        && (writeMethod = targetPropertyDesc.getWriteMethod()) != null
                        && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        //利用Java7的API获取方法句柄
                        MethodHandle getMethodHandle = MethodHandles.lookup().unreflect(readMethod);
                        MethodHandle setMethodHandle = MethodHandles.lookup().unreflect(writeMethod);

                        methodHandles.add(new CopyMethodHandle(getMethodHandle, setMethodHandle));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return methodHandles;
    }

    //缓存Key,需要重写hashCode和equals方法
    public static class CacheKey {
        private Class<?> source;
        private Class<?> target;

        public CacheKey(Class<?> source, Class<?> target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(source, cacheKey.source) && Objects.equals(target, cacheKey.target);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new Object[]{source, target});
        }
    }

    //封装源实体类的Getter方法句柄和目标实体类的Setter方法句柄
    static class CopyMethodHandle {
        private MethodHandle sourceGet;
        private MethodHandle targetSet;

        public CopyMethodHandle(MethodHandle sourceGet, MethodHandle targetSet) {
            this.sourceGet = sourceGet;
            this.targetSet = targetSet;
        }
        //执行方法句柄,调用Setter方法赋值
        public void invoke(Object source, Object target) throws Throwable {
            Object value = sourceGet.invoke(source);
            targetSet.invoke(target, value);
        }
    }

    private static PropertyDescriptor[] getPropertyDescs(Class<?> clazz) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (beanInfo == null || beanInfo.getPropertyDescriptors() == null) {
            return new PropertyDescriptor[0];
        }
        return beanInfo.getPropertyDescriptors();
    }

    private static Map<String, PropertyDescriptor> getPropertyDescsMap(Class<?> clazz) {
        return Arrays.stream(getPropertyDescs(clazz))
                .collect(Collectors.toMap(PropertyDescriptor::getName, (desc) -> desc));
    }
}

