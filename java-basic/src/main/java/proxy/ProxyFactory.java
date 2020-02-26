package proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * <p>createTime：2020-02-25 17:43:14
 *
 * @author liuyq
 * @since 1.0
 */
public class ProxyFactory {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final MethodInterceptor methodInterceptor) {
        return (T) Enhancer.create(targetClass, methodInterceptor);
    }
}
