package proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理
 */
public class CglibProxyTest {
    public interface IAccountService {
        //主业务逻辑: 转账
        void transfer();
    }

    public static class AccountServiceImpl implements IAccountService {
        @Override
        public void transfer() {
            System.out.println("调用dao层,完成转账主业务.");
        }
    }

    public static abstract class BaseAspect implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object result = null;

            begin();
            try {
                if (isIntercept(method, args)) {
                    before();
                    result = methodProxy.invokeSuper(obj, args);
                    after();
                } else {
                    result = methodProxy.invokeSuper(obj, args);
                }
            } catch (Exception e) {
                error(e);
                throw e;
            } finally {
                end();
            }
            return result;
        }

        /**
         * 开始增强
         */
        public void begin() {
        }

        /**
         * 切入点判断
         */
        public boolean isIntercept(Method method, Object[] args) throws Throwable {
            return true;
        }

        /**
         * 前置增强
         */
        public void before() throws Throwable {
        }

        /**
         * 后置增强
         */
        public void after() throws Throwable {
        }

        /**
         * 异常增强
         */
        public void error(Throwable e) {
        }

        /**
         * 最终增强
         */
        public void end() {
        }
    }

    public static class AccountAspect extends BaseAspect {

        /**
         * 切入点
         */
        @Override
        public boolean isIntercept(Method method, Object[] args) throws Throwable {
            return method.getName().equals("transfer");
        }

        /**
         * 前置增强
         */
        @Override
        public void before() throws Throwable {
            System.out.println("对转账人身份进行验证.");
        }
    }

    public static void main(String[] args) {
        //创建目标对象
        IAccountService target = new AccountServiceImpl();
        //切面
        BaseAspect accountAspect = new AccountAspect();
        //创建代理对象
        IAccountService proxy = ProxyFactory.createProxy(target.getClass(), accountAspect);
        proxy.transfer();
    }
}
