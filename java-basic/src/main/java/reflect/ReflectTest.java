package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>createTime：2019-04-29 11:12:59
 *
 * @author liuyq
 * @since 1.0
 */
public class ReflectTest {
    public int b;
    private int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        System.out.println("set a = " + a);
        this.a = a;
    }

    private void test() {
        System.out.println("私有方法");
    }
}

class C {
    public static void main(String[] args) {
        ReflectTest reflectTest = new ReflectTest();

        // 方式1
        Class c1 = ReflectTest.class;

        System.out.println(c1.getName());   // com.liuyq.ReflectTest
        System.out.println(c1.getSimpleName()); // ReflectTest
        Method[] methods = c1.getMethods();     // 获取所有的 public方法，包括父类继承的public
        for (Method method : methods) {
            System.out.println("methods：" + method.getName());
        }

        // 获取该类所有的方法，包括private ,但不包括继承的方法
        Method[] declaredMethods = c1.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            System.out.println("declaredMethods：" + declaredMethods[i].getName());
        }

        // 方式2
        Class c2;
        try {
            c2 = Class.forName("com.liuyq.ReflectTest");
            Field[] fields = c2.getFields();    // 获取所有的public属性，包括父类继承的public
            for (Field field : fields) {
                System.out.println("field：" + field.getName());
            }
            Field[] declaredFields = c2.getDeclaredFields();    // 获取所有包括private ,但不包括继承的属性
            for (Field field : declaredFields) {
                System.out.println("declaredFields：" + field.getName());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // 方式3
        Class c3 = reflectTest.getClass();
        try {
            Method setA = c3.getMethod("setA", int.class);
            setA.invoke(reflectTest, 10);   // 方法的反射操作
            Method getA = c3.getMethod("getA");
            System.out.println("c3_getA：" + getA.invoke(reflectTest));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ReflectTest reflectTest2;
        try {
            reflectTest2 = (ReflectTest) c3.newInstance();
            System.out.println("getA()：" + reflectTest2.getA());
            Field field = c3.getDeclaredField("a");
            field.setAccessible(true);  // 私有属性必须打开可见权限
            field.set(reflectTest2, 8);
            System.out.println("c3_a：" + field.get(reflectTest2));
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
