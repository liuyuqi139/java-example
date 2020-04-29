package callback;

/**
 * 回调
 */
public class CallBackTest {
    public interface CallBack {
        void call(String s);
    }

    public void test(CallBack callback) {
        System.out.println("test");
        callback.call("123");
    }

    public static void main(String[] args) {
        CallBackTest a = new CallBackTest();
        a.test(s -> {
            System.out.println(s);
            System.out.println("execute");
        });
        System.out.println("end");
    }
}
