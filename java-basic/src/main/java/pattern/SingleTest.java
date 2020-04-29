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

/**
 * 懒汉实现3--线程安全，双重校验的方式
 * volatile关键字来修饰singleton，其最关键的作用是防止指令重排
 */
class SingletonLazy3 {
    private static volatile SingletonLazy3 singleton;

    private SingletonLazy3() {
    }

    public static SingletonLazy3 getSingleton() {
        if (singleton == null) {
            synchronized (SingletonLazy3.class) {
                if (singleton == null) {
                    singleton = new SingletonLazy3();
                }
            }
        }
        return singleton;
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

/**
 * 枚举单例示例
 */
class User {
    //私有化构造函数
    private User() {
    }

    //定义一个静态枚举类
    enum SingletonEnum {
        //创建一个枚举对象，该对象天生为单例
        INSTANCE;
        private User user;

        //私有化枚举的构造函数
        SingletonEnum() {
            user = new User();
        }

        public User getInstnce() {
            return user;
        }
    }

    //对外暴露一个获取User对象的静态方法
    public static User getInstance() {
        return SingletonEnum.INSTANCE.getInstnce();
    }
}

class Test {
    public static void main(String [] args){
        System.out.println(User.getInstance());
        System.out.println(User.getInstance());
        System.out.println(User.getInstance()==User.getInstance());
    }
}
