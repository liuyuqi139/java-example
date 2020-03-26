package jvm;

/**
 * 你真以为你会做这道JVM面试题？
 * <p>createTime：2019-03-27 10:55:49
 *
 * @author liuyq
 * @since 1.0
 */
public class ClassLoad {
    public static void main(String[] args) {
        staticFunction();
    }

    static ClassLoad st = new ClassLoad();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    ClassLoad() {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
//        this.staticFunction();
//        staticFunction();
    }

    public static void staticFunction() {
        System.out.println("b=" + b);
        System.out.println("4");
    }

    int a = 110;
    static int b = 112;

    //    运行结果
    //    2
    //    3
    //    a=110,b=0
    //    1
    //    b=112
    //    4

    //    有没有答对呢？这里主要的点之一：实例初始化不一定要在类初始化结束之后才开始初始化。
    //
    //    类的生命周期是：加载->验证->准备->解析->初始化->使用->卸载。
    //
    //    只有在准备阶段和初始化阶段才会涉及类变量的初始化和赋值，因此只针对这两个阶段进行分析；
    //
    //    类的准备阶段需要做是为类变量分配内存并设置默认值，因此类变量st为null、b为0；
    //
    //    需要注意的是如果类变量是final，编译时javac将会为value生成ConstantValue属性，在准备阶段虚拟机就会根据ConstantValue的设置将变量设置为指定的值。
    //
    //    如果这里这么定义：static final int b=112，那么在准备阶段b的值就是112，而不再是0了。
    //
    //    类的初始化阶段需要做的是执行类构造器。
    //
    //    类构造器是编译器收集所有静态语句块和类变量的赋值语句，按语句在源码中的顺序合并生成类构造器，对象的构造方法是()，类的构造方法是()，可以在堆栈信息中看到。
    //
    //    因此，先执行第一条静态变量的赋值语句，即st = new StaticTest ()，此时会进行对象的初始化。
    //
    //    对象的初始化是先初始化成员变量，再执行构造方法。因此打印2->设置a为110->执行构造方法(打印3,此时a已经赋值为110，但是b只是设置了默认值0，并未完成赋值动作)。
    //
    //    等对象的初始化完成后，继续执行之前的类构造器的语句。接下来就不详细说了，按照语句在源码中的顺序执行即可。
}

// 类加载
class Singleton {
    private static Singleton singleton = new Singleton();
    public static int counter1;
    public static int counter2 = 0;

    private Singleton() {
        counter1++;
        counter2++;
    }

    public static Singleton getSingleton() {
        return singleton;
    }

    public static void main(String[] args) {
        System.out.println("counter1：" + counter1);
        System.out.println("counter2：" + counter2);
    }

    //    运行结果
    //    counter1：1
    //    counter2：0

    //    1执行TestSingleton第一句的时候，因为我们没有对Singleton类进行加载和连接，所以我们首先需要对它进行加载和连接操作。在连接阶-准备阶段，我们要讲给静态变量赋予默认初始值。
    //    singleton =null
    //    counter1 =0
    //    counter2 =0
    //
    //    2加载和连接完毕之后，我们再进行初始化工作。初始化工作是从上往下依次执行的，注意这个时候还没有调用Singleton.getSingleton();
    //    首先 singleton = new Singleton();这样会执行构造方法内部逻辑，进行++；此时counter1=1，counter2 =1 ；
    //    接下来再看第二个静态属性，我们并没有对它进行初始化，所以它就没办法进行初始化工作了；

    //    第三个属性counter2我们初始化为0 ,而在初始化之前counter2=1，执行完counter2=0之后counter2=0了；
}

// 类加载
class SingletonB {
    public static int counter1;
    public static int counter2 = 0;
    private static SingletonB singleton = new SingletonB();

    private SingletonB() {
        counter1++;
        counter2++;
    }

    public static SingletonB getSingleton() {
        return singleton;
    }

    public static void main(String[] args) {
        System.out.println("counter1：" + counter1);
        System.out.println("counter2：" + counter2);
    }

    //    运行结果
    //    counter1：1
    //    counter2：1
}

// 类加载
class FinalTest {
    private static FinalTest singleton = new FinalTest();
    public static int counter1;
    public static int counter2 = 0;
    public static final int counter3 = 2;

    private FinalTest() {
        counter1++;
        counter2++;
        System.out.println("counter1：" + counter1);
        System.out.println("counter2：" + counter2);
        System.out.println("counter2：" + counter3);
    }

    public static FinalTest getSingleton() {
        return singleton;
    }

    public static void main(String[] args) {
        System.out.println("counter1：" + counter1);
        System.out.println("counter2：" + counter2);
        System.out.println("counter2：" + counter3);
    }

    //    运行结果
    //    counter1：1
    //    counter2：1
}

// 类加载 内部类
class InnerTest {
    private static InnerTest singleton = new InnerTest();
    public static int counter1;
    public static int counter2 = 0;
    public static final int counter3 = 2;

    private InnerTest() {
        counter1++;
        counter2++;
        System.out.println("counter1：" + counter1);
        System.out.println("counter2：" + counter2);
        System.out.println("counter2：" + counter3);
    }

    public static InnerTest getSingleton() {
        return singleton;
    }

    static class StaticInnerTest {
        public static int counter4;
        public static int counter5 = 1;
        public static final int counter6 = 2;
    }

    public static void main(String[] args) {
        System.out.println("counter1：" + counter1);
        System.out.println("counter2：" + counter2);
        System.out.println("counter2：" + counter3);
        System.out.println("counter4：" + StaticInnerTest.counter4);
        System.out.println("counter5：" + StaticInnerTest.counter5);
        System.out.println("counter6：" + StaticInnerTest.counter6);
    }

    //    运行结果
    //    counter1：1
    //    counter2：1
}