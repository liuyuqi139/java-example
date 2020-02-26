package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 */
public class JdkProxyTest {
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

    public static class AccountAdvice implements InvocationHandler {
        //目标对象
        private IAccountService target;

        public AccountAdvice(IAccountService target) {
            this.target = target;
        }

        /**
         * 代理方法, 每次调用目标方法时都会进到这里
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            before(method);
            return method.invoke(target, args);
        }

        /**
         * 前置增强
         */
        private void before(Method method) {
            System.out.println(method.getName());
            System.out.println("对转账人身份进行验证.");
        }
    }

    public static void main(String[] args) {
        //创建目标对象
        IAccountService target = new AccountServiceImpl();
        //创建代理对象
        IAccountService proxy = (IAccountService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new AccountAdvice(target)
        );
        proxy.transfer();
    }

}
