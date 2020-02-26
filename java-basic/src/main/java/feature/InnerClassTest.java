package feature;

/**
 *
 */
public class InnerClassTest {
    abstract class Person {
        abstract void play();
    }

    class Man extends Person {

        @Override
        void play() {
            System.out.println("男人玩");
        }
    }

    public static void main(String[] args) {
        Person person = new InnerClassTest().new Man();
        person.play();
    }
}
