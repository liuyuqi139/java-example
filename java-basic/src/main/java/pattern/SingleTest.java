package pattern;

/**
 * 单利
 */
public class SingleTest {
}

// 单例模式 饿汉模式
class SingletonHungry {
    private static SingletonHungry singletonHungry = new SingletonHungry();
    //将构造器设置为private禁止通过new进行实例化
    private SingletonHungry() {

    }
    public static SingletonHungry getInstance() {
        return singletonHungry;
    }
}

// 单例模式的懒汉实现1--线程不安全
class SingletonLazy1 {
    private static SingletonLazy1 singletonLazy;

    private SingletonLazy1() {

    }

    public static SingletonLazy1 getInstance() {
        if (null == singletonLazy) {
            try {
                // 模拟在创建对象之前做一些准备工作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            singletonLazy = new SingletonLazy1();
        }
        return singletonLazy;
    }
}

// 单例模式的懒汉实现2--线程安全
// 通过设置同步方法，效率太低，整个方法被加锁
class SingletonLazy2 {
    private static SingletonLazy2 singletonLazy;

    private SingletonLazy2() {

    }

    public static synchronized SingletonLazy2 getInstance() {
        try {
            if (null == singletonLazy) {
                // 模拟在创建对象之前做一些准备工作
                Thread.sleep(1000);
                singletonLazy = new SingletonLazy2();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return singletonLazy;
    }
}

// 使用静态内部类实现单例模式--线程安全
class SingletonStaticInner {
    private SingletonStaticInner() {

    }
    private static class SingletonInner {
        private static SingletonStaticInner singletonStaticInner = new SingletonStaticInner();
    }
    public static SingletonStaticInner getInstance() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return SingletonStaticInner.SingletonInner.singletonStaticInner;
    }
}
